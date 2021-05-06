package lol.ui;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


import java.io.File;
import java.io.IOException;
//Source: Sound from Zapsplat.com
public class Sound  {  
   public void attackSound(String champion){
      switch (champion.toLowerCase()) {
         case "archer":arrowSound();break;
         case "warrior":swordSound();break;      
         default:throw new RuntimeException("The sound for this champion doesn't exist");            
      }
    }
   private void arrowSound(){
      File shootArrow = new File("src\\main\\resources\\attack_sounds\\arrow.wav");
      playSound(shootArrow);     
   }
   private void swordSound(){
      File swordHit = new File("src\\main\\resources\\attack_sounds\\sword.wav");
      playSound(swordHit);     
   }   
   private void playSound(File sound){
      try {                 
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound.toURI().toURL());         
         Clip clip = AudioSystem.getClip();         
         clip.open(audioIn);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         System.out.println("The file type and format are not recognized");
         e.printStackTrace();
      } catch (IOException e) {            
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         System.out.println("The line cannot be opened.Check if its used by another application");
         e.printStackTrace();
      }
    }
}
