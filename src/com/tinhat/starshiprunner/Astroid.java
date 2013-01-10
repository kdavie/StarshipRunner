package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;
import com.tinhat.starshiprunner.DynamicObjectPool.IDynamicObject;
 

public class Astroid extends DynamicGameObject implements IDynamicObject{
	
	public static final int STATE_NORMAL = 0;
	public static final int STATE_EXPLODING = 1;
	
	public static final float WIDTH = 0.75f;
	public static final float HEIGHT = 0.6f;
	public static final float HALF_WIDTH = Astroid.HEIGHT /2;
	
	float stateTime;
    int textureIndex;
    
	public int state;
	public int frame;
	public boolean destroyed;
	
	public Astroid(){
		super(0,0,WIDTH,HEIGHT);
	}
	
	public Astroid(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		bounds.lowerLeft.set(position);
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

	
}
