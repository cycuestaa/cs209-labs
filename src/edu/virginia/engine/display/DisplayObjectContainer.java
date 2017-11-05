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
    public boolean contains(DisplayObject child) {
        for(int i=0; i < this.children.size(); i++) {
            if(this.children.get(i) == child ) {
                return true;
            }
        }
        return false;
    }

    public void setChildren(ArrayList<DisplayObject> newChildren) { this.children = newChildren; }
    public ArrayList<DisplayObject> getChildren(DisplayObjectContainer c) {
        return c.children;
    }
    public ArrayList<DisplayObject> getChildren() {
        return this.children;
    }


    //Add container methods
    public DisplayObject getChild(int index) {
        return children.get(index);
    }

    public DisplayObject getChild(String id) {
        for (int i = 0; i < this.children.size(); i++) {
            DisplayObject child = this.children.get(i);
            String ch = child.getId();
            if (id == ch) {
                return child;
            }
        }
        return null;
    }
    public void addChild(DisplayObject child) {
        if(child.getParent()==this && this.contains(child)==false) {
            this.children.add(child);
        }
    }

    public void addChildAtIndex(DisplayObject child, int i) {
        if(child.getParent()==this && this.contains(child)==false) {
            this.children.add(i, child);
        }
    }

    /*
    * Remove container methods
    * */
    public void removeByIndex(int i) {
        this.children.remove(i);
    }

    public void removeChild(DisplayObject child) {
        if(this.children.contains(child)) {
            this.children.remove(child);
        }
    }


    public void removeAll() {
        for (int i = 0; i < getChildren().size(); i++) {
            removeByIndex(i);
        }
    }



    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (getVisible()) {
            applyTransformations(g2);
            super.draw(g2);
            if (getChildren() != null ) {
                for (int i=0; i < this.children.size(); i++){
                    this.children.get(i).draw(g);
                }
            }
            reverseTransformations(g2);
            }
        }
    }
