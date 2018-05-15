
/**
 * Write a description of window here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class window implements ActionListener{
  // JButton button;
   JFrame frame;
   public static void main(String[] args){
     window w = new window();
     w.go();
    }
    public void go(){
    frame = new JFrame();
    JButton button = new JButton("click me");
     button.addActionListener(this);
     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     draw d = new draw();
     frame.getContentPane().add(BorderLayout.SOUTH,button);
     frame.getContentPane().add(BorderLayout.CENTER,d);
     frame.setSize(300, 300);
     frame.setVisible(true);  
   }
   public void actionPerformed(ActionEvent event){
   //button.setText("i am clicked"); 
   frame.repaint();
   }
}
