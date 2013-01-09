package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;

public class Coin extends DynamicGameObject {

	public static final float HEIGHT = 0.6f;
	public static final float WIDTH = 0.6f;
	
	float stateTime;
	
	public Coin(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		bounds.lowerLeft.set(position);
		this.stateTime = (float) Math.random();
	}

	public void update(float deltaTime) {
        stateTime += deltaTime;
    }
	
}
