package lol.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.media.*;
import javafx.scene.media.Media;

//Source: Sound from Zapsplat.com
//Mp3 is not supported
public class Sound  {  
   private File shootArrow;
   private File swordHit;
   private File explodeBuilding;

   public Sound(){      
      this.shootArrow = openSoundFile("attack_sounds/arrow.wav");
      this.swordHit = openSoundFile("attack_sounds/sword.wav");
      this.explodeBuilding = openSoundFile("attack_sounds/explode.wav");
   }
   public void attackSound(String entity){
      switch (entity.toLowerCase()) {
         case "archer":
            playSound(this.shootArrow);
            break;
         case "warrior":
            playSound(this.swordHit);
            break;      
         default:
            throw new RuntimeException("The sound for " + entity + " doesn't exist");            
      }
   } 

   public void destroyBuilding(){
      playSound(this.explodeBuilding);
   }

   private File openSoundFile(String soundPath) {
      File soundFile = new File(getClass().getClassLoader().getResource(soundPath).getFile());
      if(soundFile == null) {
        throw new RuntimeException("The sound file `" + soundPath + "` could not be found.");
      }
      return soundFile;
   }  

   private void playSound(File soundFile) {
      // Media sound = new Media(soundFile.toURI().toString());
      // MediaPlayer mediaPlayer = new MediaPlayer(sound);
      // mediaPlayer.play();
   }
}
