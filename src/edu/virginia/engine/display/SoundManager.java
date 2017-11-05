//package edu.virginia.engine.display;
package edu.virginia.engine.lab4test;

import java.util.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.net.*;
import java.io.*;



public class SoundManager {

    private HashMap<String, Clip>  soundEffects;
    private HashMap<String, Clip>  music;

    public SoundManager() {
         this.soundEffects = new HashMap<String,Clip>();
         this.music = new HashMap<String,Clip>();
    }

    public void LoadSoundEffect(String id, String filename) {
        try {
            String source = filename;
            System.out.println(source);
            URL url = this.getClass().getClassLoader().getResource(source);
            System.out.println(url);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            this.soundEffects.put(id, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSoundEffect(String id) {
        this.soundEffects.get(id).start();
    }

    public void LoadMusic(String id, String filename) {
        try {
            String source = filename;
            URL url = this.getClass().getClassLoader().getResource(source);
            System.out.println(url);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            this.music.put(id, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMusic(String id) {
        this.music.get(id).loop(Clip.LOOP_CONTINUOUSLY);
    }
}

