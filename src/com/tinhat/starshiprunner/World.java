package com.tinhat.starshiprunner;

import java.util.ArrayList;

import com.tinhat.android.Pool.PoolObjectFactory;
import com.tinhat.android.math.CollisionTester;
import com.tinhat.android.math.Vector2;

 


public class World {
	public interface WorldListener {
        public void fly();
    }
	
	public static final float WORLD_WIDTH = 15;
	public static final float WORLD_HEIGHT = 10;
	public static final float WORLD_HALF_WIDTH = WORLD_WIDTH / 2;
	public static final float WORLD_HALF_HEIGHT = WORLD_HEIGHT / 2;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	
	public static final Vector2 gravity = new Vector2(0, -12);
	
	public final Spaceship spaceship;
	public final WorldListener listener;
	
	int state;
	public Astroid[][] astroids;
	public Coin[][] coins;
	 

	
	public int collectedCoins;
	
	public World(WorldListener worldListener){ 
		this.spaceship = new Spaceship(WORLD_HALF_WIDTH,WORLD_HALF_HEIGHT);
		this.listener = worldListener;
		 
	}
	
 
	
	public void update(float deltaTime, float accelY) {
		 
		updateSpaceship(deltaTime, accelY);
	    updateCoins(deltaTime);
	    //updateAstroids(deltaTime);
		if(spaceship.state != Spaceship.SPACESHIP_STATE_CRASHING) {
			checkCollisions();
		}
	   
	} 
	
	public void checkCollisions() {
		//checkAstroidCollisions();
		checkCoinCollisions();
	}
	
	public void checkAstroidCollisions() {
		int j, i, x;
		Ballistic ballistic;
		ArrayList<Integer> remove = new ArrayList<Integer>();
		for(j = 0; j < 2; j++){
			int len = astroids[j].length;
			 
		    for (i = 0; i < len; i++) {
		        Astroid astroid = astroids[j][i];
		        if(astroid != null && astroid.state != Astroid.STATE_EXPLODING){
			        if (spaceship.position.x + Spaceship.SPACESHIP_HALF_WIDTH >= astroid.position.x - Astroid.HALF_WIDTH ) {
			            if (CollisionTester.rectangles(spaceship.bounds, astroid.bounds)) {
			                spaceship.crash();
			                 
			                break;
			            }
			        } 
			        
			        for(x = 0; x < spaceship.ballistics.size();x++){
			        	ballistic = spaceship.ballistics.get(x);
			        	if (ballistic.position.x + 8 >= astroid.position.x - Astroid.HALF_WIDTH ) {
				            if (CollisionTester.rectangles(ballistic.bounds, astroid.bounds)) {
				            	//astroids[j][i] = null;
				            	astroids[j][i].state = Astroid.STATE_EXPLODING;
				            	remove.add(x);
				                break;
				            } else {
				            	if(ballistic.position.x > astroid.position.x &&
				            	   ballistic.bounds.lowerLeft.y < astroid.bounds.lowerLeft.y + astroid.bounds.height &&
			            		   ballistic.bounds.lowerLeft.y + ballistic.bounds.height > astroid.bounds.lowerLeft.y){
				            		//we're on the same y axis, there should have been a collision
				            		astroids[j][i].state = Astroid.STATE_EXPLODING;
					            	remove.add(x);
					            	break;
				            	}
				            }
				        }
			        }
			        
//			        for(x = 0; x < remove.size();x++){
//			        	spaceship.ballistics.remove(x);
//			        }
			        
		        }
		    }
		}
	}
	
	public void checkCoinCollisions() {
		for(int j = 0; j < 2; j++){
			int len = coins[j].length;
			 
		    for (int i = 0; i < len; i++) {
		        Coin coin = coins[j][i];
		        if(coin != null){
			        if (spaceship.position.x + Spaceship.SPACESHIP_HALF_WIDTH >= coin.position.x - Astroid.HALF_WIDTH ) {
			            if (CollisionTester.rectangles(spaceship.bounds, coin.bounds)) {
			            	coins[j][i] = null;
			            	collectedCoins++;
			                break;
			            }
			        } 
		        }
		    }
		}
	}
	
	public void updateSpaceship(float deltaTime, float accelY){ 
		 if (spaceship.state != Spaceship.SPACESHIP_STATE_CRASHING)
			 spaceship.velocity.y = -accelY / 10 * spaceship.SPACESHIP_MOVE_VELOCITY;
		this.spaceship.update(deltaTime);
	}
	
	public void updateCoins(float deltaTime){
		for(int j = 0; j < 2;j++){
			for(int i = 0; i < coins[j].length; i++){
				if(coins[j][i] != null){
					coins[j][i].update(deltaTime);
				}
			}
		}
	}

	public void updateAstroids(float deltaTime){
		for(int j = 0; j < 2;j++){
			for(int i = 0; i < astroids[j].length; i++){
				if(astroids[j][i] != null){
					astroids[j][i].update(deltaTime);
				}
			}
		}
	}
}
