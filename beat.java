
/**
 * Write a description of beat here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.sound.midi.*;
public class beat{
   static JFrame frame  = new JFrame("myfirst music video");
   static drawpanel d;
   public static void main(String[] args){
      beat b = new beat();
      b.go();
   }
   public void setupGUI(){
    d = new drawpanel();
    frame.setContentPane(d);
    frame.setBounds(30,30,300,300);
    frame.setVisible(true);
    }
   public void go(){
       setupGUI();
     try{
     Sequencer sequencer = MidiSystem.getSequencer();
     sequencer.open();
     
     int[] want ={127};
     sequencer.addControllerEventListener(d, want);
     Sequence seq = new Sequence(Sequence.PPQ, 4);
     Track track = seq.createTrack();
     int r =0;
     for(int i = 5; i<60; i+=4){
        r = (int)((Math.random()*50)+1);
        track.add(makeEvent(144,1,r,100,i));   
        track.add(makeEvent(176,1,127,0,i));   
        track.add(makeEvent(128,1,r,100,i+2));   
     } 
     sequencer.setSequence(seq);
     sequencer.setTempoInBPM(220);
     sequencer.start();
   }
   catch(Exception e){e.printStackTrace();}
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
    public class drawpanel extends JPanel implements ControllerEventListener{
        boolean msg = false;
        public void controlChange(ShortMessage event){
            //System.out.println("la");
            msg = true;
            repaint();
        }
        public void paintComponent(Graphics g){
          if(msg){
          Graphics2D g2 = (Graphics2D) g ;
          int red = (int)(Math.random()*255);
          int green = (int)(Math.random()*255);
          int blue = (int)(Math.random()*255);
          g.setColor(new Color(red,green,blue));
          int ht = (int)((Math.random()*120) + 10);
          int width = (int)((Math.random()*120) + 10);
          int x = (int)((Math.random()*40) + 10);
          int y = (int)((Math.random()*40) + 10);
          g.fillRect(x, y, ht, width);
          msg = false;
        }
      }
    }
}
