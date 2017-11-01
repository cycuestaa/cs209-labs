package edu.virginia.engine.display;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject {
	/* bc we need to know about the parent objects and relation */
	private DisplayObject parent;
	//public DisplayObject parent;

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	/* describes the x-y position where the object will be drawn*/
	private Point position;

	/*the point that is the origin of the object. the object rotates around this point*/
	private Point pivotPoint;

	/*Rotation: defines the amount in degrees to rotate the object*/
	private double Rotation;

    /*should be true iff this display object is meant to be drawn*/
	private Boolean visible;

    /*float which defines how transparent to draw this object.*/
	private float alpha;
	private float oldAlpha;

	/*double which scales the image up or down. 1.0 would be the actual size*/
	private double scaleX;
	private double scaleY;




	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		this.position= new Point(0,0);
		this.pivotPoint= new Point (0,0);
		this.Rotation=0;
		this.visible=true;
        this.alpha=1.0f;
        this.oldAlpha=0.0f;
        this.scaleX=1.0;
        this.scaleY=1.0;
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		this.position= new Point ( 0, 0);
		this.pivotPoint= new Point (0 , 0);
		this.Rotation=0;
        this.visible=true;
        this.alpha=1.0f;
        this.oldAlpha=0.0f;
        this.scaleX=1.0;
        this.scaleY=1.0;
	}


	// remember hierarchy
	public void setParent(DisplayObject parent) { this.parent = parent; }
	public DisplayObject getParent() { return this.parent; }

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	public void setPosition(Point position) { this.position = position; }
	public Point getPosition() { return position; }

	public void setPivotPoint(Point pivotPoint) { this.pivotPoint = pivotPoint; }
	public Point getPivotPoint() { return pivotPoint; }

	public void setRotation(double Rotation) { this.Rotation = Rotation; }
	public double getRotation() {
		return Rotation;
	}

    public Boolean getVisible() { return visible; }
    public void setVisible(Boolean vis) { this.visible = vis; }

    public float getAlpha() { return alpha; }
    public void setAlpha(float alph) { this.alpha = alph; }

    public float getOldAlpha() { return oldAlpha; }
    public void setOldAlpha(float oldalph) { this.oldAlpha=oldalph; }

    public double getScaleX() { return scaleX; }
    public void setScaleX(double newX) { this.scaleX=newX; }

    public double getScaleY() { return scaleY; }
    public void setScaleY(double newY) { this.scaleY=newY; }

	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) { return 0; }
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) { return 0; }
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " doesn't exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) { return; }
		displayImage = image;
	}

	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) { }

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {

		if (displayImage != null) {

			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;


			/* Actually draw the image, perform the pivot point translation here */
			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);

			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */

			//reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
	    g2d.translate(this.position.x, this.position.y);
	    g2d.rotate(Math.toRadians(this.getRotation()), this.pivotPoint.x, this.pivotPoint.y);
	    g2d.scale(this.scaleX, this.scaleY);


        if(this.visible) {
			g2d.setComposite(AlphaComposite.getInstance(3, 1.0f));
			float curAlpha;
			this.oldAlpha=curAlpha=((AlphaComposite) g2d.getComposite()).getAlpha();
			g2d.setComposite(AlphaComposite.getInstance(3, curAlpha*this.alpha));
        }

        else {
			g2d.setComposite(AlphaComposite.getInstance(3, 0.0f));
        }
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(3,this.oldAlpha));
		g2d.rotate(Math.toRadians((-1) * this.getRotation()), this.pivotPoint.x, this.pivotPoint.y);
		g2d.scale((1/this.scaleX),(1/this.scaleY));
		g2d.translate((-1) * this.position.x,(-1) * this.position.y);
	}

}