package com.tinhat.starshiprunner;

public class SuperLazer extends Ballistic {
	
	
	public SuperLazer(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public void update(float deltaTime) {
        stateTime += deltaTime;       
        if(yDirection == 0)
        	position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        else
        	position.add(velocity.x * deltaTime, -(velocity.y * deltaTime));
       
		bounds.lowerLeft.set(position);
    }
}
