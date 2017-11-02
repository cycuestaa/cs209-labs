package edu.virginia.engine.display;

import java.awt.*;
import java.util.ArrayList;

/**
 * Nothing in this class (yet) because there is nothing specific to a Sprite yet that a DisplayObject
 * doesn't already do. Leaving it here for convenience later. you will see!
 * */
public class Sprite extends DisplayObjectContainer {

	public Sprite(String id) {
		super(id);
	}

	public Sprite(String id, String imageFileName, DisplayObject parent, Point pos) {

		super(id);
		setImage(imageFileName);
		setParent(parent);
		setPosition(pos);
	}g

	public Sprite(String id, String imageFileName, DisplayObject parent, Point pivot, Point pos) {
		super(id);
		setImage(imageFileName);
		setParent(parent);
		setPosition(pos);
		setPivotPoint(pivot);
	}
	
	@Override
	public void update(ArrayList<Integer> pressedKeys) {
		super.update(pressedKeys);
	}
}

