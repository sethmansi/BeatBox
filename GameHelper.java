
/**
 * Write a description of GameHelper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
public class GameHelper {
     public String getuserinput(String prompt){
         String inputline = null;
         System.out.println(prompt + " ");
         try{
             BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
             inputline = is.readLine();
             if(inputline.length() == 0){
             return null;
            }
            
         }
         catch(IOException e){
            System.out.println("IOException :" + e);   
         }
         return inputline;
     }
}
