package edu.virginia.engine.display;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Shape;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 */
public class DisplayObject{
	/* All DisplayObject have a unique id */
	private String id;
	/* bc we need to know about the parent objects and relation */
	private DisplayObject parent;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	/* describes the x-y position where the object will be drawn*/
	private Point position;

	/*the point that is the origin of the object. the object rotates around this point at a distance indicated*/
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

	private Point centerPoint;

	public Shape hitArea;


	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		this.parent = null;
		this.position = new Point(0, 0);
		this.pivotPoint = new Point(this.getUnscaledHeight()/2,this.getUnscaledWidth()/2);
		this.Rotation = 0;
		this.visible = true;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scaleX = 1.0;
		this.scaleY = 1.0;
		this.setHitArea();

	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		this.parent = null;
		this.position = new Point(0, 0);
		this.pivotPoint = new Point(this.getUnscaledHeight()/2,this.getUnscaledWidth()/2);
		this.Rotation = 0;
		this.visible = true;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scaleX = 1.0;
		this.scaleY = 1.0;
		this.setHitArea();
	}

	/**
	 * Hitbox is for simplifying collision detection of weird-shaped sprites
	 */
	public void setHitArea() {
		int TopX = this.getPosition().x;
		int TopY = this.getPosition().y;
		int Height = this.getHeight();
		int Width = this.getWidth();
		Rectangle2D Bounds = new Rectangle2D.Double(TopX, TopY, Width, Height);
		AffineTransform mod = AffineTransform.getRotateInstance(Math.toRadians(this.getRotation()), this.getPivotPoint().x ,this.getPivotPoint().y);
		Shape rotated = mod.createTransformedShape(Bounds);
		this.hitArea = rotated;
	}
	public Shape getHitArea() {return hitArea;}

	public void setHitBox(Shape s) { this.hitArea = s; }
	public Rectangle getHitBox() {
		Rectangle box = new Rectangle(this.getPosition().x, this.getPosition().y,
				this.getWidth(), this.getHeight());
		return box;
	}

	public Boolean collidesWith(DisplayObject other) {
		Boolean Hit;
		Rectangle OtherObj = other.getHitBox();
		Rectangle currObj = this.getHitBox();
		Hit = currObj.intersects(OtherObj);
		return Hit;
	}

	// remember hierarchy
	public void setParent(DisplayObject parent) {
		this.parent = parent;
	}
	public DisplayObject getParent() {
		return this.parent;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public void setPivotPoint(Point pivotPoint) {
		this.pivotPoint.setLocation(pivotPoint);
	}

	public Point getPivotPoint() {
		return pivotPoint;
	}

	public void setRotation(double Rotation) {
		this.Rotation = Rotation;
	}

	public double getRotation() {
		return Rotation;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean vis) {
		this.visible = vis;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alph) {
		this.alpha = alph;
	}

	public float getOldAlpha() {
		return oldAlpha;
	}

	public void setOldAlpha(float oldalph) {
		this.oldAlpha = oldalph;
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double newX) {
		this.scaleX = newX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double newY) {
		this.scaleY = newY;
	}

	public void setImage(BufferedImage image) {
		if (image == null) {
			return;
		}
		displayImage = image;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}

		if (readImage(imageName) == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " doesn't exist!");
		}
		displayImage = readImage(imageName);
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	/**
	 * Returns the Display Object IMAGE's width and height of this display object
	 */
	public int getUnscaledWidth() {
		BufferedImage currImage = getDisplayImage();
		if (currImage == null) {
			return 0;
		}
		return currImage.getWidth();
	}

	public int getUnscaledHeight() {
		BufferedImage currImage = getDisplayImage();
		if (currImage == null) {
			return 0;
		}
		return currImage.getHeight();
	}

	public int getWidth() {
		BufferedImage currImage = getDisplayImage();
		if (currImage == null) {
			return 0;
		}
		int w = (int) Math.round((displayImage.getWidth() * this.getScaleX()));
		return w;
	}

	public int getHeight() {
		BufferedImage currImage = getDisplayImage();
		if (currImage == null) {
			return 0;
		}
		int h = (int) Math.round(displayImage.getHeight() * this.getScaleY());
		return h;
	}

	// convert local to global coordinate system
	public Point toGlobal(Point localP) {
		if (this.getParent() == null) {
			return localP;
		}
		DisplayObject inObj = getParent();
		int pX = this.getPosition().x;
		int pY = this.getPosition().y;
		Point convertPos = new Point(localP.x + pX, localP.y + pY);
		return inObj.toGlobal(convertPos);
	}

	public Point toLocal(Point globalP) {
		Point pos = toGlobal(globalP);
		Point convert = new Point(globalP.x - pos.x,globalP.y - pos.y);
		return convert;
	}

	// find the center of the image
//	public void setCenterPoint() {
//		int widthCenter = (int) ((this.getUnscaledWidth() * this.getScaleX()) / 2);
//		int heightCenter = (int) ((this.getUnscaledHeight() * this.getScaleY()) / 2);
//		Point center = new Point(widthCenter, heightCenter);
//		this.centerPoint = center;
//	}
//
//	public Point getCenterPoint() {
//		return this.centerPoint;
//	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 */
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



	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 */
	protected void update(ArrayList<Integer> pressedKeys) {
	}



	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(this.position.x, this.position.y);
		g2d.scale(this.scaleX, this.scaleY);
		g2d.rotate(Math.toRadians(this.getRotation()), this.pivotPoint.x, this.pivotPoint.y);



		if (this.visible) {
			g2d.setComposite(AlphaComposite.getInstance(3, 1.0f));
			float curAlpha;
			this.oldAlpha = curAlpha = ((AlphaComposite) g2d.getComposite()).getAlpha();
			g2d.setComposite(AlphaComposite.getInstance(3, curAlpha * this.alpha));
		} else {
			g2d.setComposite(AlphaComposite.getInstance(3, 0.0f));
		}
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(3, this.oldAlpha));
		g2d.rotate(Math.toRadians((-1) * this.getRotation()), -this.pivotPoint.x, -this.pivotPoint.y);
		g2d.scale((1 / this.scaleX), (1 / this.scaleY));
		g2d.translate((-1) * this.position.x, (-1) * this.position.y);
	}


	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 */
	public void draw(Graphics g) {

		if (displayImage != null && getVisible()){

			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			//applyTransformations(g2d);

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

}