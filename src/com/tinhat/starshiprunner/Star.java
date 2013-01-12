package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;
import com.tinhat.starshiprunner.DynamicObjectPool.IDynamicObject;

public class Star extends DynamicGameObject implements IDynamicObject {

	public static final float WIDTH = 0.5f;
	public static final float HEIGHT = 0.5f;
	
	public Star(){
		super(0,0,WIDTH,HEIGHT);
	}
	
	public Star(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
	}

	int textureIndex;
	
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
		return false;
	}
	
	@Override
	public void reset() {
		//do nothing
	}
}
