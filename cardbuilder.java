
/**
 * Write a description of cardbuilder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;     
public class cardbuilder{
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardlist;
    private JFrame frame;
    public static void main(String[] args){
       cardbuilder card = new cardbuilder();
       card.go();
    }
    public void go(){
       frame = new JFrame("quiz card builder");
       JPanel mainpanel = new JPanel();
       Font bigfont = new Font("sanserif", Font.BOLD, 24);
       question = new JTextArea(6,20);
       question.setLineWrap(true);
       question.setWrapStyleWord(true);
       question.setFont(bigfont);
       JScrollPane qscroller = new JScrollPane(question);
       qscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       qscroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       
       answer = new JTextArea(6,20);
       answer.setLineWrap(true);
       answer.setWrapStyleWord(true);
       answer.setFont(bigfont);
       JScrollPane ascroller = new JScrollPane(answer);
       ascroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       ascroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
       
       
       JButton button = new JButton("next card");
       cardlist = new ArrayList<QuizCard>();
       JLabel qlabel = new JLabel("Question");
       JLabel alabel = new JLabel("Answer");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       mainpanel.add(qlabel);
       mainpanel.add(qscroller);
       mainpanel.add(alabel);
       mainpanel.add(ascroller);
       mainpanel.add(button);
       button.addActionListener(new nextlistener());
       JMenuBar menu = new JMenuBar();
       JMenu filemenu = new JMenu("File");
       JMenuItem newm = new JMenuItem("New");
       JMenuItem save = new JMenuItem("Save");
       newm.addActionListener(new newlistener());
       save.addActionListener(new savelistener());
       filemenu.add(newm);
       filemenu.add(save);
       menu.add(filemenu);
       frame.setJMenuBar(menu);
       frame.getContentPane().add(BorderLayout.CENTER, mainpanel);
       frame.setSize(500,600);
       frame.setVisible(true);
    }
    public class nextlistener implements ActionListener{
       public void actionPerformed(ActionEvent event){
          QuizCard card = new QuizCard(question.getText(), answer.getText());
          cardlist.add(card);
          clearcard();
       }
    }
    public class savelistener implements ActionListener{
       public void actionPerformed(ActionEvent event){
          QuizCard card = new QuizCard(question.getText(), answer.getText());
          cardlist.add(card);
          JFileChooser filesave = new JFileChooser();
          filesave.showSaveDialog(frame);
          saveFile(filesave.getSelectedFile());
       }
    }
    private void saveFile(File file){
      try{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for(QuizCard card: cardlist){
           writer.write(card.getQuestion() + "/");
           writer.write(card.getAnswer() + "\n");
        }
        writer.close();
      }catch(IOException e){
        System.out.println("Could not write the cardlist out");
        e.printStackTrace();
      }
    }
    public class newlistener implements ActionListener{
       public void actionPerformed(ActionEvent event){
          cardlist.clear();
          clearcard();
          
       }
    }
    private void clearcard(){
       question.setText(" ");
       answer.setText(" ");
       question.requestFocus();
    }
    
}
