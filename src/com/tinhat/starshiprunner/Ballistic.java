package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;

public abstract class Ballistic extends DynamicGameObject {

	public int yDirection;
	
	public Ballistic(float x, float y, float width, float height) {
		super(x, y, width, height);
		bounds.lowerLeft.set(position);
	}

	float stateTime;
	
	public abstract void update(float deltaTime);
}
