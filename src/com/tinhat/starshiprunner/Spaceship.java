package com.tinhat.starshiprunner;

import java.util.ArrayList;

 

import com.tinhat.android.DynamicGameObject;

 
public class Spaceship extends DynamicGameObject {
	
	public static final int SPACESHIP_STATE_SPAWNING = 0;
	public static final int SPACESHIP_STATE_FLYING = 1;
	public static final int SPACESHIP_STATE_CRASHING = 2;
	public static final float SPACESHIP_FLYING = 0.09f;
	public static final float SPACESHIP_SPAWNING_VELOCITY = 2.5f;
	public static final float SPACESHIP_FLYING_VELOCITY = 3.75f;
	public static final float SPACESHIP_MOVE_VELOCITY = 50;
	public static final float SPACESHIP_WIDTH = 1.5f; 
	public static final float SPACESHIP_HEIGHT = 1.0f;
	public static final float SPACESHIP_HALF_HEIGHT = SPACESHIP_HEIGHT / 2; 
	public static final float SPACESHIP_HALF_WIDTH = SPACESHIP_WIDTH / 2;
	
	int state;
	float stateTime = 0; 
	Ballistic ballistic;
	
	public Weapon weapon;
	public ArrayList<Ballistic> ballistics;
	
	public Spaceship(float x, float y) { 
		super(x, y, SPACESHIP_WIDTH, SPACESHIP_HEIGHT);
		weapon = new Weapon();
		ballistics = new ArrayList<Ballistic>();
		state = SPACESHIP_STATE_SPAWNING;  
		velocity.set(SPACESHIP_SPAWNING_VELOCITY, SPACESHIP_FLYING); 
		
		position.set(1, position.y);
		bounds.lowerLeft.set(position);
	}
	
	 float averageDelta;
	 int averageCount;
	 public void update(float deltaTime1) {     
		 //TODO: get rid of this average here and average deltas in GLGame
		 //using http://stackoverflow.com/questions/10648325/android-smooth-game-loop
		 //for guidance.
		 if(averageCount < 10){
			 averageCount++;
			 averageDelta+=deltaTime1;
		 }
		 
		 float deltaTime = averageDelta/averageCount;
		 
	 
		 if(state == SPACESHIP_STATE_SPAWNING) { 
			 if(position.x > World.WORLD_HALF_WIDTH/2){
	 	    	 state = Spaceship.SPACESHIP_STATE_FLYING;
	 	    	 velocity.set(SPACESHIP_FLYING_VELOCITY, SPACESHIP_FLYING); 
			 } else {
				 position.add(velocity.x * deltaTime, velocity.y * deltaTime);
			 }
 	     }
		 
		 if(state == SPACESHIP_STATE_FLYING){
			 velocity.add(.005f, 0);
			 position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		 }
		 
		 if(state == SPACESHIP_STATE_SPAWNING || state == SPACESHIP_STATE_FLYING) {
			 
	
			 if(position.y < 0.5f){
				 position.set(position.x, 0.5f);
			 } else if(position.y > World.WORLD_HEIGHT - 0.5f) {
				 position.set(position.x, World.WORLD_HEIGHT - 0.5f);
			 }
			 bounds.lowerLeft.set(position);
		 }
	     
		 
	     for(int i = 0; i < ballistics.size(); i++){
	    	 ballistic = ballistics.get(i);
	    	 ballistic.update(deltaTime);
	    	 
	     }
		 
			 
	     stateTime += deltaTime;  
	   
	 }
	
	 public void crash(){
		 state = SPACESHIP_STATE_CRASHING;
         stateTime = 0;
         velocity.x = 0;
        
	 }
	 
	 public void fire() {
		 Ballistic ballistic = new Ballistic(this.position.x+Spaceship.SPACESHIP_HALF_WIDTH, this.position.y-.25f, 0.5f, .025f);
 		 ballistic.velocity.set(this.velocity.x + 20, 0);
 		 ballistics.add(ballistic);
	 }
	
}