package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;
 

public class Astroid extends DynamicGameObject {
	
	public static final int STATE_NORMAL = 0;
	public static final int STATE_EXPLODING = 1;
	
	public static final float WIDTH = 0.75f;
	public static final float HEIGHT = 0.6f;
	public static final float HALF_WIDTH = Astroid.HEIGHT /2;
	
	float stateTime;
	public int textureId;
	public int state;
	public int frame;
	
	public Astroid(float x, float y) {
		super(x, y, WIDTH, HEIGHT);
		bounds.lowerLeft.set(position);
		// TODO Auto-generated constructor stub
	}
	
	public void setPosition(float x, float y){
		position.set(x,y);
		bounds.lowerLeft.set(position);
	}
	
	public void update(float deltaTime) {
        stateTime += deltaTime;
    }

	
}
