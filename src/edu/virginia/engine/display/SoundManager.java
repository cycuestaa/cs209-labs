package edu.virginia.engine.display;
import java.util.*;
import javax.sound.sampled.*;
import java.net.*;
import java.io.*;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */

public class SoundManager {

    private HashMap<String, Clip>  soundEffects;

    public SoundManager() {
         this.soundEffects = new HashMap<String,Clip>();
    }

    public void LoadSoundEffect(String id, String filename) {
        try {
            URL url = this.getClass().getClassLoader().getResource(filename);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            this.soundEffects.put(id, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
}

