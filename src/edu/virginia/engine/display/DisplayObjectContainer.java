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
public class DisplayObjectContainer extends DisplayObject {

	/* All DisplayObject have a unique id */
	private String id;

    /*DisplayObjectContainers have children*/
    public ArrayList<DisplayObject> children;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

    public void DisplayObjectContainer(String id) {
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


    public void setChildren(ArrayList<DisplayObject> newChildren) {
        this.children = newChildren;
    }

    public void addChild(DisplayObject child) {
        this.children.add(child);
    }
    public void addChildAtIndex(DisplayObject child, int ind) {
        this.children.add(ind, child);
    }
    public void removeChild(String id) {
        for (int i=0; i<this.children.size(); i++) {
            DisplayObject child = this.children.get(i);
            if (child.getId() == id) {
                this.removeByIndex(i);
            }
    }
    public void removeByIndex(int ind) {
        this.children.remove(ind);
    }
}
