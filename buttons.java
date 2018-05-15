
/**
 * Write a description of buttons here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class buttons {
   JFrame frame;
   JLabel label;
   public static void main(String[] args){
       buttons bb = new buttons();
       bb.go();
    }
    
   public void go(){
      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JButton color = new JButton("change color");
      color.addActionListener(new colorListener());
      JButton labelb = new JButton("change text");
      labelb.addActionListener(new labelListener());
      label = new JLabel("i am a label");
      draw d = new draw();
      frame.getContentPane().add(BorderLayout.SOUTH, color);
      frame.getContentPane().add(BorderLayout.CENTER, d);
      frame.getContentPane().add(BorderLayout.EAST, labelb);
      frame.getContentPane().add(BorderLayout.WEST, label);
      frame.setSize(300, 300);
      frame.setVisible(true);
      
   }
   class colorListener implements ActionListener{
       public void actionPerformed(ActionEvent event){
          frame.repaint(); 
       } 
   }
    class labelListener implements ActionListener{
       public void actionPerformed(ActionEvent event){
          label.setText("i am changed");
       } 
   }
}
