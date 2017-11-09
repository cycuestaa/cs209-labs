package edu.virginia.lab3test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
//import edu.virginia.engine.display.AnimatedSprite;
//import edu.virginia.engine.display.Animation;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */


public class LabThreeSimulator extends Game {
    DisplayObject root;
    DisplayObjectContainer solarsystem;

    //ArrayList<DisplayObject> dis = new ArrayList<DisplayObject>();
    /**
     * Create the sprite objects for our solar system!
     **/

    // (String id, String imageFileName, DisplayObject parent, Point pivot, Point pos)
    Sprite Sun = new Sprite("Sun", "pic_Sun.png", null, new Point(550, 300));
    // origin = the point around where shit pivots
    Point origin = Sun.getPosition();

    Sprite Mercury = new Sprite("Mercury", "pic_Mercury.png", Sun, origin, new Point(100,100));
    Sprite Earth = new Sprite("Earth", "pic_Earth.png", Sun, origin, new Point(200,200));
    Point midEarth = Earth.getPosition();
    // midEarth
    Sprite Moon = new Sprite("Moon", "pic_Moon.png", Earth, midEarth, new Point(100,100));
    Sprite Saturn = new Sprite("Saturn", "pic_Saturn.png", Sun, origin, new Point(600,600));



    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public LabThreeSimulator() {
        super("Lab Three Test Game", 1300, 900);
    }

    public void OurSystem() {
        Sun.addChild(Saturn);
        Sun.addChild(Mercury);
        Sun.addChild(Earth);
        Earth.addChild(Moon);
    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */

    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);
        Mercury.setScaleY(0.25);
        Mercury.setScaleX(0.25);
        Mercury.setRotation(Mercury.getRotation() + 1.6);
        //Mercury.setPivotPoint(new Point(-75, -75));
        Mercury.setPivotPoint(new Point(600, 400));

        Saturn.setScaleY(0.7);
        Saturn.setScaleX(0.7);
        Saturn.setRotation(Saturn.getRotation() + 0.5);
        //Saturn.setPivotPoint(new Point(-175, 75));
        Saturn.setPivotPoint(new Point(500, 200));

        Earth.setScaleY(0.4);
        Earth.setScaleX(0.4);
        Earth.setRotation(Earth.getRotation() + 1);
        //Earth.setPivotPoint(new Point(-75, -75));
        Earth.setPivotPoint(new Point(800,600));

        Moon.setScaleY(0.5);
        Moon.setScaleX(0.5);
        Moon.setRotation(Moon.getRotation() + 2);
        //Moon.setPivotPoint(new Point(-40, 55));
        Moon.setPivotPoint(new Point(0, 0));


		/* Make sure Sun is not null. Swing may auto cause an extra frame to go before everything is initialized */
        if(Sun != null) {
            Sun.update(pressedKeys);
            /*
            * Toggle... Position
            */
            if (pressedKeys.contains(KeyEvent.VK_UP)) {
                Sun.setPosition(new Point(Sun.getPosition().x,
                        Sun.getPosition().y-5));
            }
            if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                Sun.setPosition(new Point(Sun.getPosition().x,
                        Sun.getPosition().y+5));
            }
            if (pressedKeys.contains(KeyEvent.VK_LEFT)){
                Sun.setPosition(new Point(Sun.getPosition().x-5,
                        Sun.getPosition().y));
            }
            if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
                Sun.setPosition(new Point(Sun.getPosition().x+5,
                        Sun.getPosition().y));
            }
            /*
            * Toggle... Pivot Point
            */
            if (pressedKeys.contains(KeyEvent.VK_I)){
                Sun.setPivotPoint(new Point(Sun.getPivotPoint().x,
                        Sun.getPivotPoint().y-5));
            }
            if (pressedKeys.contains(KeyEvent.VK_K)){
                Sun.setPivotPoint(new Point(Sun.getPivotPoint().x,
                        Sun.getPivotPoint().y+5));
            }

            if (pressedKeys.contains(KeyEvent.VK_J)){
                Sun.setPivotPoint(new Point(Sun.getPivotPoint().x-5,
                        Sun.getPivotPoint().y));
            }
            if (pressedKeys.contains(KeyEvent.VK_L)){
                Sun.setPivotPoint(new Point(Sun.getPivotPoint().x+5,
                        Sun.getPivotPoint().y));
            }
            /*
            * Toggle... Scale Size
            */
            if (pressedKeys.contains(KeyEvent.VK_Q)) {
                if (Sun.getScaleX() >= 1.9) {
                    Sun.setScaleX(1.9);
                    Sun.setScaleY(1.9);
                } else {
                    Sun.setScaleX(Sun.getScaleX() + 0.1);
                    Sun.setScaleY(Sun.getScaleY() + 0.1);
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_W)) {
                if (Sun.getScaleX() <= 0.2) {
                    Sun.setScaleX(0.2);
                    Sun.setScaleY(0.2);
                } else {
                    Sun.setScaleX(Sun.getScaleX() - 0.1);
                    Sun.setScaleY(Sun.getScaleY() - 0.1);
                }
            }
            /*
            * Toggle... Alpha
            */
            if (pressedKeys.contains(KeyEvent.VK_Z)){
                if(Sun.getAlpha() >= 0.9f){
                    Sun.setOldAlpha(1.0f);
                    Sun.setAlpha(1.0f);
                }
                else {
                    Sun.setOldAlpha(Sun.getAlpha());
                    Sun.setAlpha(Sun.getAlpha() + 0.1f);
                }
            }
            if (pressedKeys.contains(KeyEvent.VK_X)){
                if(Sun.getAlpha() <= 0.1f){
                    Sun.setOldAlpha(0.1f);
                    Sun.setAlpha(0.1f);
                }
                else {
                    Sun.setOldAlpha(Sun.getAlpha());
                    Sun.setAlpha(Sun.getAlpha() - 0.1f);
                }
            }
            /*
            * Toggle... Visibility
            */
            if (pressedKeys.contains(KeyEvent.VK_V)){
                if (Sun.getVisible()) {
                    Sun.setVisible(false);
                }
                else {
                    Sun.setVisible(true);
                }
            }
            /*
            * Toggle... Rotation
            */
            if (pressedKeys.contains(KeyEvent.VK_A)){
                Sun.setRotation(Sun.getRotation() + 10);
            }

            if (pressedKeys.contains(KeyEvent.VK_S)) {
                Sun.setRotation(Sun.getRotation() - 10);

            }

        }
    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure it gets drawn to
     * the screen, we need to make sure to overridessss this method and call Sun's draw method.
     **/
    @Override
    public void draw(Graphics g) {
		/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
        Graphics2D g2d = (Graphics2D) g;
        Color space = new Color(12, 3, 89);
        g2d.setColor(space);
        g2d.fillRect(0, 0, 1300, 900);

        if (Sun != null) {
            Sun.draw(g);
            //Earth.draw(g);
            //Moon.draw(g);
        }
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabThreeSimulator game = new LabThreeSimulator();
        game.OurSystem();
        game.start();
    }
}

