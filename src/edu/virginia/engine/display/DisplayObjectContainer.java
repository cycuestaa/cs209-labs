package edu.virginia.engine.display;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends DisplayObject {

	/* All DisplayObject have a unique id */
	private String id;

    /*DisplayObjectContainers have a parent*/
    private DisplayObject parent; 

    /*DisplayObjectContainers have a parent*/
    public ArrayList<DisplayObject> children;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;
}
