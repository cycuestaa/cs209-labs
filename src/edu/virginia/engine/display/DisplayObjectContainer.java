package edu.virginia.engine.display;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 *
 * */
public class DisplayObjectContainer extends DisplayObject {
    /* DisplayObjectContainers have children */
    private ArrayList<DisplayObject> children;


    /*
     * Constructors: can pass in the id OR the id and image's file path and
     * position OR the id and a buffered image and position
     */
    public DisplayObjectContainer(String id) {
        super(id);
        this.children = new ArrayList<DisplayObject>();
    }

    public DisplayObjectContainer(String id, String fileName) {
        super(id, fileName);
        this.children = new ArrayList<DisplayObject>();
    }

    /*
     * Setters & Getters
     */
    // Returns true if D.O. is already a child of this container.
    public boolean contains(DisplayObject child) { return children.contains(child); }

    public void setChildren(ArrayList<DisplayObject> newChildren) { this.children = newChildren; }
    public ArrayList<DisplayObject> getChildren() {
        return this.children;
    }

    //Add container methods
    public DisplayObject getChild(int index) {
        return children.get(index);
    }

    public DisplayObject getChild(String id) {
        for (int i = 0; i < getChildren().size(); i++) {
            DisplayObject child = getChild(i);
            String ch = child.getId();
            if (Objects.equals(ch, id)) {
                return child;
            }
        }
        return null;
    }
    public void addChild(DisplayObject child) {
        if(!contains(child)) {
            getChildren().add(child);
        }
    }

    public void addChildAtIndex(DisplayObject child, int ind) {
        if(!contains(child)) {
            children.add(ind, child);
        }
    }

    /*
    * Remove container methods
    * */
    public void removeByIndex(int ind) {
        this.children.remove(ind);
    }

    public void removeChild(String id) {
        if(contains(getChild(id))) {
            for (int i = 0; i < getChildren().size(); i++) {
                DisplayObject child = getChild(i);
                if (Objects.equals(child.getId(), id)) {
                    this.removeByIndex(i);
                }
            }
        }
    }


    public void removeAll() {
        for (int i = 0; i < getChildren().size(); i++) {
            this.removeByIndex(i);
        }
    }


    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (getVisible()) {
            applyTransformations(g2);
            super.draw(g2);
            if (children != null ) {
                for (DisplayObject child : children) {
                    child.draw(g);
                }
            }
            reverseTransformations(g2);
            }
        }
    }
