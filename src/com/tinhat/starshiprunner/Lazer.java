package com.tinhat.starshiprunner;

public class Lazer extends Ballistic {
	public Lazer(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public void update(float deltaTime) {
        stateTime += deltaTime;       
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position);
    }
}
