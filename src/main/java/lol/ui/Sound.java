package lol.ui;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


import java.io.File;
import java.io.IOException;

public class Sound  {
    
    
    public void playSound(){
        try {
            // Open an audio input stream.
            File f = new File("src\\main\\resources\\arrow.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioIn);
            clip.start();
         } catch (UnsupportedAudioFileException e) {
            System.out.println("The file type and format are not recognized");
            e.printStackTrace();
         } catch (IOException e) {            
            e.printStackTrace();
         } catch (LineUnavailableException e) {
            System.out.println("The line cannot be opened.Check if  its used by another application");
            e.printStackTrace();
         }
        
    }
}
