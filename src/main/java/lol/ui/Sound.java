package lol.ui;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


import java.io.File;
import java.io.IOException;

public class Sound  {
  
    public void soundToPlay(String champion){
      switch (champion.toLowerCase()) {
         case "archer":arrowSound();break;      
         default:throw new RuntimeException("The sound for this champion doesn't exist");            
      }
    }
    private void arrowSound(){
      File shootArrow = new File("src\\main\\resources\\arrow.wav");
      playSound(shootArrow);     
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
