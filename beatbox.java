
/**
 * Write a description of beatbox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.midi.*;
public class beatbox {
    JPanel mainpanel;
    ArrayList<JCheckBox> checkboxlist;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame frame;
    String[] instrumentnames = {"Bass Drum", "Closed Hi-Hat","Open Hi-Hat", "Acoustic Snare","Crash Cymbol",
                        "Hand Clap","High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga","Coebell",
                        "Vibraslap", "Low-mid Tom","High Agogo","Open Hi Conga"};
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    public static void main(String[] args){
       beatbox beat = new beatbox();
       beat.go();
    }
    public void go(){
       frame = new JFrame("Cyber BeatBox");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       BorderLayout layout = new BorderLayout();
       JPanel background = new JPanel(layout);
       background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
       checkboxlist = new ArrayList<JCheckBox>();
       Box buttonBox = new Box(BoxLayout.Y_AXIS);
       buttonBox.setBackground(Color.darkGray);
       
       JButton start = new JButton("Start");
       start.addActionListener(new startlistener());
       buttonBox.add(start); 
       
       JButton stop = new JButton("Stop");
       stop.addActionListener(new stoplistener());
       buttonBox.add(stop); 
       
       JButton tempoup = new JButton("Tempo Up");
       tempoup.addActionListener(new tempouplistener());
       buttonBox.add(tempoup); 
       
       JButton tempodown = new JButton("Tempo Down");
       tempodown.addActionListener(new tempodownlistener());
       buttonBox.add(tempodown); 
       
       JButton serializeit = new JButton("serializeIt");
       serializeit.addActionListener(new serializeitlistener());
       buttonBox.add(serializeit); 
       
       JButton restore = new JButton("Restore");
       restore.addActionListener(new restorelistener());
       buttonBox.add(restore); 
       
       Box namebox =new Box(BoxLayout.Y_AXIS);
       for(int i = 0; i <16; i++){
          namebox.add(new Label(instrumentnames[i])); 
       }
  
       background.add(BorderLayout.EAST, buttonBox);
       background.add(BorderLayout.WEST, namebox);
      
       frame.getContentPane().add(background);
       GridLayout grid = new GridLayout(16,16);
       grid.setVgap(1);
       grid.setHgap(2);
       mainpanel = new JPanel(grid);
       background.add(BorderLayout.CENTER, mainpanel);
       for(int j = 0; j <256; j++){
          JCheckBox c = new JCheckBox();
          c.setSelected(false);
          checkboxlist.add(c);
          mainpanel.add(c);
       }
       
      
       setupMidi();
       
       frame.setBounds(50,50,300,300);
       frame.pack();
       frame.setVisible(true);
    }
    public void setupMidi(){
      try{
       sequencer = MidiSystem.getSequencer();
       sequencer.open();
       sequence = new Sequence(Sequence.PPQ, 4);
       track = sequence.createTrack();
       sequencer.setTempoInBPM(120);
      }
      catch(Exception e){e.printStackTrace();}
    }
    public void buildtrackandstart(){
      int[] tracklist = null;
       
      sequence.deleteTrack(track);
      track = sequence.createTrack();
      
      for(int i= 0; i <16; i++){ //for loop for each instrument
           tracklist = new int[16];
           int key = instruments[i];
           for(int j = 0; j <16; j++){ // for each checkbox corresponding to that instrument
              JCheckBox jc = (JCheckBox)checkboxlist.get(j+(16*i));
              if(jc.isSelected()){
                  tracklist[j] = key;  
              }
              else{
                  tracklist[j] = 0;  
              }
           }
           maketracks(tracklist);
           track.add(makeEvent(176,1,127,0,16));
      }
      track.add(makeEvent(192,9,1,0,15));
      try{
         sequencer.setSequence(sequence);
         sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
         sequencer.start();
         sequencer.setTempoInBPM(120);
      }
      catch(Exception e){e.printStackTrace();}
    }
    public class startlistener implements ActionListener{
       public void actionPerformed(ActionEvent a){
           buildtrackandstart();
       }
    }
    public class stoplistener implements ActionListener{
       public void actionPerformed(ActionEvent a){
           sequencer.stop();
       }
    }
    public class tempouplistener implements ActionListener{
       public void actionPerformed(ActionEvent a){
           float tempofactor = sequencer.getTempoFactor();
           sequencer.setTempoFactor((float)(tempofactor *1.03));
       }
    }
    public class tempodownlistener implements ActionListener{
       public void actionPerformed(ActionEvent a){
           float tempofactor = sequencer.getTempoFactor();
           sequencer.setTempoFactor((float)(tempofactor *.97));
       }
    }
    public void maketracks(int[] list){
       for(int i = 0; i <16; i++){
          int key = list[i];
          if(key != 0){
             track.add(makeEvent(144,9,key,100,i));
             track.add(makeEvent(128,9,key,100,i+1));  
          }
       }
    }
    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event =null;
        try{
           ShortMessage a = new ShortMessage();
           a.setMessage(comd,chan,one,two);
           event = new MidiEvent(a, tick);
        }
        catch(Exception e){}
        return event;
    }
    public class serializeitlistener implements ActionListener{
       public void actionPerformed(ActionEvent a){
           boolean[] checkboxstate = new boolean[256];
           for(int i = 0; i < 256; i++){
             JCheckBox check = (JCheckBox)checkboxlist.get(i);
             if(check.isSelected()){
                checkboxstate[i] = true;   
             }
           }
           try{
             JFileChooser filesave = new JFileChooser();
             filesave.showSaveDialog(frame);
             
             FileOutputStream filestream = new FileOutputStream(filesave.getSelectedFile());
             ObjectOutputStream os = new ObjectOutputStream(filestream);
             os.writeObject(checkboxstate);
           }catch(Exception ex){
             ex.printStackTrace(); 
           }
       }
    }
    public class restorelistener implements ActionListener{
       public void actionPerformed(ActionEvent a){
           boolean[] checkboxstate = null;
           
           try{
             JFileChooser file = new JFileChooser();
             file.showOpenDialog(frame);
              
             FileInputStream filein = new FileInputStream(file.getSelectedFile());
             ObjectInputStream in = new ObjectInputStream(filein);
             checkboxstate = (boolean[])in.readObject();
           }catch(Exception ex){
             ex.printStackTrace(); 
           }
           for(int i = 0; i < 256; i++){
             JCheckBox check = (JCheckBox )checkboxlist.get(i);
             if(checkboxstate[i]){
                check.setSelected(true);   
             }
             else{
                check.setSelected(false); 
             }
           }
           sequencer.stop();
           buildtrackandstart();
       }
    }
}
