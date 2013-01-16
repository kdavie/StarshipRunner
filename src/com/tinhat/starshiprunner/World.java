package com.tinhat.starshiprunner;

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
    static final int MIN_STARS = 10;
    static final int MAX_STARS = 25;
    static final int MIN_ASTROIDS = 1;
    static final int MAX_ASTROIDS = 3;
    static final int MIN_COINS = 1;
    static final int MAX_COINS = 5;
	public static final Vector2 gravity = new Vector2(0, -12);
	
	public final Spaceship spaceship;
	public final WorldListener listener;
	
	int state;
	Ballistic ballistic;
	Astroid astroid;
	Coin coin;
	 
	public DynamicObjectPool<Astroid> astroids;
	public DynamicObjectPool<Coin> coins;
	public DynamicObjectPool<Star> stars;
	
	public int collectedCoins;
	
	public World(WorldListener worldListener){ 
		this.spaceship = new Spaceship(WORLD_HALF_WIDTH,WORLD_HALF_HEIGHT);
		this.listener = worldListener;
		 
	}
	
    public void initializeObjectPools(){
    	initializeStarPool();
    	initializeAstroidPool();
    	initializeCoinPool();
    }
    
    private void initializeStarPool(){
    	PoolObjectFactory<Star> starPoolFactory = new PoolObjectFactory<Star>() {
			@Override
			public Star createObject() {
				return new Star();
			}
		};
		stars = new DynamicObjectPool<Star>(MIN_STARS,MAX_STARS,15,starPoolFactory);
    }
    
    private void initializeAstroidPool(){
    	PoolObjectFactory<Astroid> astroidPoolFactory = new PoolObjectFactory<Astroid>() {
			@Override
			public Astroid createObject() {
				return new Astroid();
			}
		};
		astroids = new DynamicObjectPool<Astroid>(MIN_ASTROIDS,MAX_ASTROIDS,1,astroidPoolFactory);
    }
    
    private void initializeCoinPool(){
    	PoolObjectFactory<Coin> coinPoolFactory = new PoolObjectFactory<Coin>() {
			@Override
			public Coin createObject() {
				return new Coin();
			}
		};
		coins = new DynamicObjectPool<Coin>(MIN_COINS,MAX_COINS,1,coinPoolFactory);
    }
	
	public void update(float deltaTime, float accelY) {
		 
		updateSpaceship(deltaTime, accelY);
	    updateCoins(deltaTime);
	    updateAstroids(deltaTime);
	    
		if(spaceship.state != Spaceship.SPACESHIP_STATE_CRASHING) {
			checkCollisions();
		}
	   
	} 
	
	public void checkCollisions() {
		checkAstroidCollisions();
		checkCoinCollisions();
	}
	
	public void checkAstroidCollisions() {
		int i, x;
		
	    for (i = 0; i < astroids.size(); i++) {
	        astroid = astroids.pool.objects.get(i);
	        if(astroid.isCollidable() && !astroid.destroyed && astroid.state != Astroid.STATE_EXPLODING){
		        if (spaceship.position.x + Spaceship.SPACESHIP_HALF_WIDTH >= astroid.position.x - Astroid.HALF_WIDTH ) {
		            if (CollisionTester.rectangles(spaceship.bounds, astroid.bounds)) {
		                spaceship.crash();
		                 
		                break;
		            }
		        } 
		        
		        for(x = 0; x < spaceship.weapon.ballistics.size();x++){
		        	ballistic = spaceship.weapon.ballistics.get(x);
		        	if (ballistic.position.x + 8 >= astroid.position.x - Astroid.HALF_WIDTH ) {
			            if (CollisionTester.rectangles(ballistic.bounds, astroid.bounds)) {
			            	astroid.state = Astroid.STATE_EXPLODING;
			            	astroid.destroyed = true;
			                break;
			            } else {
			            	if(ballistic.position.x > astroid.position.x &&
			            	   ballistic.bounds.lowerLeft.y < astroid.bounds.lowerLeft.y + astroid.bounds.height &&
		            		   ballistic.bounds.lowerLeft.y + ballistic.bounds.height > astroid.bounds.lowerLeft.y){
			            		//we're on the same y axis, there should have been a collision
			            		astroid.state = Astroid.STATE_EXPLODING;
			            		astroid.destroyed = true;
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
	
	public void checkCoinCollisions() {
		
		
		for(int i = 0; i < coins.size(); i++){
			coin = coins.pool.objects.get(i);
			if (!coin.collected && spaceship.position.x + Spaceship.SPACESHIP_HALF_WIDTH >= coin.position.x - Astroid.HALF_WIDTH ) {
	            if (CollisionTester.rectangles(spaceship.bounds, coin.bounds)) {
	            	coin.collected = true;
	            	collectedCoins++;
	                break;
	            }
	        } 
		}
	}
	
	public void updateSpaceship(float deltaTime, float accelY){ 
		 if (spaceship.state != Spaceship.SPACESHIP_STATE_CRASHING)
			 spaceship.velocity.y = -accelY / 10 * Spaceship.SPACESHIP_MOVE_VELOCITY;
		this.spaceship.update(deltaTime);
	}
	
	public void updateCoins(float deltaTime){
		for(int i = 0; i < coins.size(); i++){
			if(coins.pool.objects.get(i) != null){
				coins.pool.objects.get(i).update(deltaTime);
			}
		}
	}
	
	public void updateAstroids(float deltaTime){
		for(int i = 0; i < astroids.size(); i++){
			if(astroids.pool.objects.get(i) != null){
				astroids.pool.objects.get(i).update(deltaTime);
			}
		}
	}

 
}
