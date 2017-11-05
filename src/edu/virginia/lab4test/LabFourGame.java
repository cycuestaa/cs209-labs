package edu.virginia.lab4test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.SoundManager;
//import edu.virginia.engine.display.AnimatedSprite;
//import edu.virginia.engine.display.Animation;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import java.awt.geom.AffineTransform;

import java.awt.Shape;
import java.awt.Rectangle;
import javax.sound.sampled.*;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */

public class LabFourGame extends Game {

	Sprite coin1 = new Sprite("Coin1", "coin1.png", null, new Point(200, 150));
//	Rectangle c1box = coin1.getHitBox().getBounds();

	Sprite coin2 = new Sprite("Coin2", "coin2.png", null, new Point(300, 150));
//	Rectangle c2box = coin2.getHitBox().getBounds();

	Sprite pipe = new Sprite("Pipe", "pipe.png", null, new Point(550, 880));
//	Rectangle exitbox = pipe.getHitBox().getBounds();

	Sprite mario = new Sprite("Mario", "Mario.png", null, new Point(0, 0));
	Shape box = new Rectangle(mario.getPosition().x, mario.getPosition().y, mario.getUnscaledWidth(),
			mario.getUnscaledHeight());
	Rectangle marioBox = box.getBounds();

	SoundManager sounds = new SoundManager();




	//	Sprite c3 = new Sprite("Coin3", "coin3.png", null, new Point(700,250));
//	Sprite plant = new Sprite("Plant", "piranha.png", null, new Point(500,450));

	public int score;

	public AffineTransform atran = new AffineTransform();
	//SoundManager sound = new SoundManager();

	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabFourGame() {
		super("Lab Four Test Game", 1000, 700);
		score = 100;
		sounds.LoadSoundEffect("points","ding.wav");
		sounds.LoadSoundEffect("die","end.wav");
		sounds.LoadMusic("main", "main_music.wav");
	}

	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);

		/* Make sure mario is not null. Swing may auto cause an extra frame to go before everything is initialized */

		if(mario != null) {
			mario.update(pressedKeys);


			// MOVE in direction to different positions
			if (pressedKeys.contains(KeyEvent.VK_UP)) {
				mario.setPosition(new Point(mario.getPosition().x,mario.getPosition().y - 5));
				mario.setHitArea();
			}
			if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
				mario.setPosition(new Point(mario.getPosition().x, mario.getPosition().y + 5));
				mario.setHitArea();
			}
			if (pressedKeys.contains(KeyEvent.VK_LEFT)){
				mario.setPosition(new Point(mario.getPosition().x-5, mario.getPosition().y));
				mario.setHitArea();
			}
			if (pressedKeys.contains(KeyEvent.VK_RIGHT)){
				mario.setPosition(new Point(mario.getPosition().x+5, mario.getPosition().y));
				mario.setHitArea();
			}
			// MOVE in direction to different positions
			if (pressedKeys.contains(KeyEvent.VK_I)){
				mario.setPivotPoint(new Point(mario.getPivotPoint().x, mario.getPivotPoint().y-5));
			}
			if (pressedKeys.contains(KeyEvent.VK_J)){
				mario.setPivotPoint(new Point(mario.getPivotPoint().x-5, mario.getPivotPoint().y));
			}
			if (pressedKeys.contains(KeyEvent.VK_K)){
				mario.setPivotPoint(new Point(mario.getPivotPoint().x, mario.getPivotPoint().y+5));
			}
			if (pressedKeys.contains(KeyEvent.VK_L)){
				mario.setPivotPoint(new Point(mario.getPivotPoint().x+5, mario.getPivotPoint().y));
			}
			// ROTATE clockwise , counter-clockwise
			if (pressedKeys.contains(KeyEvent.VK_W)) {
				mario.setRotation(mario.getRotation() + 10);
				Point pivot = mario.getPivotPoint();
				atran.rotate(Math.toRadians(mario.getRotation()), pivot.x, pivot.y);
			}
			if (pressedKeys.contains(KeyEvent.VK_Q)) {
				mario.setRotation(mario.getRotation() - 10);
				Point pivot = mario.getPivotPoint();
				atran.rotate(Math.toRadians(mario.getRotation()),pivot.x , pivot.y);
			}
			// SCALE up , down
			if (pressedKeys.contains(KeyEvent.VK_A)){
				if (mario.getScaleX() >= 1.8){
					mario.setScaleX(1.8);
					mario.setScaleY(1.8);
				}
				else{
					mario.setScaleX(mario.getScaleX() + 0.1);
					mario.setScaleY(mario.getScaleY() + 0.1);
				}
			}
			if (pressedKeys.contains(KeyEvent.VK_S)) {
				if (mario.getScaleX() <= 0.2) {
					mario.setScaleX(0.2);
					mario.setScaleY(0.2);
				} else {
					mario.setScaleX(mario.getScaleX() - 0.1);
					mario.setScaleY(mario.getScaleY() - 0.1);
				}
			}
			// TRANSPARENCY more , less
			if (pressedKeys.contains(KeyEvent.VK_Z)){
				if(mario.getAlpha() >= 0.9f){
					mario.setOldAlpha(1.0f);
					mario.setAlpha(1.0f);
				}
				else {
					mario.setOldAlpha(mario.getAlpha());
					mario.setAlpha(mario.getAlpha() + 0.1f);
				}
			}
			if (pressedKeys.contains(KeyEvent.VK_X)){
				if(mario.getAlpha() <= 0.1f){
					mario.setOldAlpha(0.1f);
					mario.setAlpha(0.1f);
				}
				else {
					mario.setOldAlpha(mario.getAlpha());
					mario.setAlpha(mario.getAlpha() - 0.1f);
				}
			}
			// VISIBILITY
			if (pressedKeys.contains(KeyEvent.VK_V)){
				if (mario.getVisible()) {
					mario.setVisible(false);
				}
				else {
					mario.setVisible(true);
				}

			}
			/*
			if (pressedKeys.contains(KeyEvent.VK_R)) {
				mario.setAnimationSpeed(mario.getAnimationSpeed() + 200);
			}

			if (pressedKeys.contains(KeyEvent.VK_T)) {
				mario.setAnimationSpeed(mario.getAnimationSpeed() - 200);
			}
			*/

			// Making sure our HitAreas are correct!
			marioBox.setRect(mario.getPosition().x, mario.getPosition().y, mario.getUnscaledWidth()*getScaleX(),
					mario.getUnscaledHeight()*mario.getScaleY());
			mario.setHitBox(marioBox);

		}
	}


	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Graphics2D text= (Graphics2D) g;

		Color SkyBlue = new Color(31, 190, 214);
		g2d.setColor(SkyBlue);
		g2d.fillRect(0, 0, 1000, 700);


		System.out.println(mario.hitArea);

		Shape s = mario.getHitBox();

		mario.setHitBox(atran.createTransformedShape(s));
	/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
		if (mario != null) {
			mario.draw(g);
		}
		if (coin1 != null) {
			coin1.draw(g);
		}
		if (coin2 != null) {
			coin2.draw(g);
		}
		if (pipe != null) {
			pipe.draw(g);
		}
		box = atran.createTransformedShape(marioBox);
		marioBox = box.getBounds();
		mario.setHitBox(marioBox);
		g2d.draw(marioBox);

		if (mario.collidesWith(pipe)==true) {
			score = score + 20;
			g.drawString("YOU WIN!!!!! Final score: " + score,300,50);
			stop();
			this.sounds.playSoundEffect("end");
		}
		if (mario.collidesWith(coin1)==true){
			this.sounds.playSoundEffect("points");
			score = score + 40;
			//sm.PlaySoundEffect("se1");
		}
		if (mario.collidesWith(coin2) ==true){
			this.sounds.playSoundEffect("points");
			score = score + 40;
			//sm.PlaySoundEffect("se1");
		}
		text.drawString("Score: " + score,400,20);
		text.setColor(Color.BLACK);

	}


	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */

	public static void main(String[] args) {
		LabFourGame game = new LabFourGame();
		game.sounds.playMusic("main");
		game.start();

	}
}

