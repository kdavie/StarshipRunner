package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;

public class Ballistic extends DynamicGameObject {

	public Ballistic(float x, float y, float width, float height) {
		super(x, y, width, height);
		bounds.lowerLeft.set(position);
	}

	float stateTime;
	
	public void update(float deltaTime) {
        stateTime += deltaTime;       
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position);
    }
}
