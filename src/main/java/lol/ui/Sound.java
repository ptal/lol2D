package lol.ui;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

//Source: Sound from Zapsplat.com
//Mp3 is not supported
public class Sound  {  
   private File shootArrow;
   private File swordHit;
   private File explodeBuilding;
   public Sound(){
      this.shootArrow = new File("src\\main\\resources\\attack_sounds\\arrow.wav");
      this.swordHit = new File("src\\main\\resources\\attack_sounds\\sword.wav");
      this.explodeBuilding = new File("src\\main\\resources\\attack_sounds\\explode.wav");
   }
   public void attackSound(String entity){
      switch (entity.toLowerCase()) {
         case "archer":playSound(this.shootArrow);break;
         case "warrior":playSound(this.swordHit);break;      
         default:throw new RuntimeException("The sound for " + entity + " doesn't exist");            
      }
   } 
   public void destroyBuilding(){
      playSound(this.explodeBuilding);
   }  
   private void playSound(File sound){
      try {                 
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound.toURI().toURL());         
         Clip clip = AudioSystem.getClip();         
         clip.open(audioIn);
         setVolume(clip);
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
   private void setVolume(Clip clip){
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(-10.0f);
   }

}
