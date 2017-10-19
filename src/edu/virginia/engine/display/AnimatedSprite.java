package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;

public class AnimatedSprite extends Sprite {

    public ArrayList<Animation> animations;
    public boolean playing;
    public String fileName;
    public ArrayList<BufferedImage> frames;
    public int currentFrame;
    public int startFrame;
    public int endFrame;
    static final int DEFAULT_ANIMATION_SPEED = 1000;
    public int animationSpeed;
    public GameClock gameClock;
    int stopAt;
    BufferedImage displayImage;

    public AnimatedSprite(String id, String filename,Point pos){
        super(id);
        this.fileName = filename;
        this.gameClock=new GameClock();
        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.frames = new ArrayList<BufferedImage>();
        this.playing = false;
        this.stopAt = 0;
    }

    public void initGameClock(){
        if (this.gameClock == null) {
            this.gameClock = new GameClock();
        }
    }

    public void setAnimation(ArrayList<Animation> newAnimations) {
        this.animations = newAnimations;
    }

    public void setAnimationSpeed(int newAnimationSpeed) {
        this.animationSpeed = newAnimationSpeed;
    }
    public int getAnimationSpeed() {return this.animationSpeed; }

    public Animation getAnimation(String id) {
        for (int i=0; i < this.animations.size(); i++) {
            Animation a = this.animations.get(i);
            if (a.getid() == id) {
                return a;
            }
        }
        return null;
    }

    public void animate(String id) {
        Animation a = getAnimation(id);
        animate(a);
    }

    public void animate(Animation a) {
        if (a != null) {
            int startFrame = a.getstartFrame();
            int endFrame = a.getendFrame();
            if (startFrame == endFrame) {
                System.err.println("1 Frame does not an animation make");
            } else {
                this.animate(startFrame, endFrame);
            }
        }
    }

    public void animate(int startFrame, int endFrame) {
        if (startFrame == endFrame) { System.err.println("1 Frame does not an animation make");}
        this.setStartFrame(startFrame);
        this.setEndFrame(endFrame);
        this.initFrame();
        this.initGameClock();
        this.playing = true;
    }

    public void setStartFrame(int s) {
        this.startFrame = s;
    }
    public int getStartFrame() {return this.startFrame;}


    public void setEndFrame(int e) {
        this.endFrame = e;
    }
    public int getEndFrame() {return this.endFrame;}

    public void initFrame() {
        this.currentFrame = this.getStartFrame();
    }

    public void incrementFrame() {
        if (this.currentFrame == this.endFrame) {
            this.currentFrame = startFrame;
        } else {
            this.currentFrame += 1;
        }
    }

    public void initialize(Animation a) {
        if (this.fileName != null) {
            startFrame = a.getstartFrame();
            endFrame = a.getendFrame();
            int numImages = endFrame - startFrame + 1;
            for (int i=0; i < numImages; i++){
                String fullName = this.fileName + "_" + a.getid() + "_" + Integer.toString(i) + ".png";
                BufferedImage image = this.readImage(fullName);
                System.out.println(image);
                this.frames.add(image);
            }
        }
        this.setImage(this.frames.get(0));
    }

    public void stopAnimation() {
        this.stopAnimation((1));
    }

    public void stopAnimation(int stopFrame) {
        this.stopAt = this.startFrame + stopFrame;
    }

    public void checkPlaying() {
        if (this.stopAt != 0) {
            if (this.currentFrame - this.startFrame + 1 > this.stopAt) {
                this.playing = false;
            }
        }
    }

    public void draw(Graphics g) {
        System.out.print(this.playing);
        if (this.playing != false) {

            BufferedImage image;
            double elapsedTime;
            this.checkPlaying();
            elapsedTime = this.gameClock.getElapsedTime();
            if (elapsedTime >= this.animationSpeed) {
                image = this.frames.get(this.currentFrame);
                this.setImage(image);
                this.gameClock.resetGameClock();
                this.incrementFrame();
            }
        }
        super.draw(g);
    }

}