package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;
import com.tinhat.starshiprunner.DynamicObjectPool.IDynamicObject;

public class Coin extends DynamicGameObject implements IDynamicObject {

	public static final float HEIGHT = 0.6f;
	public static final float WIDTH = 0.6f;
	
	float stateTime;
    int textureIndex;
	
    public boolean collected;
    
    public Coin(){
    	super(0,0,WIDTH,HEIGHT);
    	this.stateTime = (float) Math.random();
    }
    
	public Coin(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		bounds.lowerLeft.set(position);
		this.stateTime = (float) Math.random();
	}

	public void update(float deltaTime) {
        stateTime += deltaTime;
    }

	@Override
	public void setPosition(float x, float y){
		position.set(x,y);
		bounds.lowerLeft.set(position);
	}

	@Override
	public void setTextureIndex(int index) {
		textureIndex = index;
	}

	@Override
	public int getTextureIndex() {
		return textureIndex;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}
	
	@Override
	public void reset() {
		collected = false;
	}
}
