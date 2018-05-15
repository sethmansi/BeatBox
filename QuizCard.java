
/**
 * Write a description of QuizCard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
public class QuizCard {
      public String question;
      public String answer;
      QuizCard(String q, String a){
         question = q;
         answer = a;
      }
      public String getQuestion(){
         return question;  
         
      }
      public String getAnswer(){
         return answer;  
         
      }
      
      
}
