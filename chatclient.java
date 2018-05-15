
/**
 * Write a description of chatclient here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class chatclient {
    JTextField outgoing;
    PrintWriter writer;
    Socket sock;
    public void go(){
       JFrame frame = new JFrame("chat room");
       JPanel mainpanel = new JPanel();
       outgoing = new JTextField(20);
       JButton button = new JButton("Send");
       button.addActionListener(new sendlistener());
       mainpanel.add(outgoing);
       mainpanel.add(button);
       frame.getContentPane().add(BorderLayout.CENTER, mainpanel);
       setupnetworking();
       frame.setSize(400,500);
       frame.setVisible(true);
    }
    private void setupnetworking(){
       try{
       sock = new Socket("127.0.0.1", 5000);
       writer = new PrintWriter(sock.getOutputStream());
       System.out.println("networking established");
       }catch(IOException ex){
          ex.printStackTrace(); 
       }
    }
    public class sendlistener implements ActionListener{
       public void actionPerformed(ActionEvent ev){
         try{
           writer.println(outgoing.getText());
           writer.flush();
         }catch(Exception e){
           e.printStackTrace();   
         }
         outgoing.setText(" ");
         outgoing.requestFocus();
       }
    }
    public static void main(String[] args){
      new chatclient().go();
    }
}
