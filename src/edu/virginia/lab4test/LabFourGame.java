package edu.virginia.lab4test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Animation;
import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.SoundManager;
import java.awt.geom.AffineTransform;

import java.awt.Shape;
import java.awt.Rectangle;
import javax.sound.sampled.*;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */

public class LabFourGame extends Game {
	//good dudes
	Sprite pipe = new Sprite("Pipe", "pipe.png", null, new Point(1000, 750));
	//bad dudes
	Sprite coin1 = new Sprite("Coin1", "piranha1.png", null, new Point(350,350));
	Sprite coin2 = new Sprite("Coin2", "piranha2.png", null, new Point(750,350));

	//main dude
	Sprite mario = new Sprite("Mario", "Mario.png", null, new Point(0,50));
	Rectangle2D marioBox = new Rectangle2D.Double(mario.getPosition().x, mario.getPosition().y, mario.getWidth(),
			mario.getHeight());

	public int score;
	AffineTransform atran = new AffineTransform();
	SoundManager sounds = new SoundManager();


	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabFourGame() {
		super("Lab Four Test Game", 1400, 900);
		score = 1000;
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
				atran.getRotateInstance(Math.toRadians(mario.getRotation()), pivot.x, pivot.y);
			}
			if (pressedKeys.contains(KeyEvent.VK_Q)) {
				mario.setRotation(mario.getRotation() - 10);
				Point pivot = mario.getPivotPoint();
				atran = atran.getRotateInstance(Math.toRadians(mario.getRotation()),pivot.x , pivot.y);
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
				//mario.setHitArea();
			}
			if (pressedKeys.contains(KeyEvent.VK_S)) {
				if (mario.getScaleX() <= 0.2) {
					mario.setScaleX(0.2);
					mario.setScaleY(0.2);
				} else {
					mario.setScaleX(mario.getScaleX() - 0.1);
					mario.setScaleY(mario.getScaleY() - 0.1);
				}
				//mario.setHitArea();
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
			//mario.setHitArea();
			//marioBox.setRect(mario.getPosition().x, mario.getPosition().y, mario.getWidth(),mario.getHeight());


		}
	}


	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Color SkyBlue = new Color(31, 190, 214);
		g2d.setColor(SkyBlue);
		g2d.fillRect(0, 50, 1400, 900);

		Graphics2D text= (Graphics2D) g;
		text.drawString("Score: " + score,400,40);
		text.setColor(Color.BLACK);

		//mario.setHitBox(atran.createTransformedShape(mario.getHitBox()));
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
		Shape box = atran.createTransformedShape(mario.getHitBox());

		mario.setHitBox(box);

		g2d.draw(mario.getHitBox());

		if (mario.collidesWith(pipe)==true) {
			score = score + 1000;
			text.drawString("+1000 points.... YOU WIN!!!!! Final score: " + score,300,50);
			this.sounds.playSoundEffect("end");
			stop();
		}
		if (mario.collidesWith(coin1)==true){
			score = score - 10;
			this.sounds.playSoundEffect("points");
		}
		if (mario.collidesWith(coin2) ==true){
			score = score - 10;
			this.sounds.playSoundEffect("points");
		}


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

