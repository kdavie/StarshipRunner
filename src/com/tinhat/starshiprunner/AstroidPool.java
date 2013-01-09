package com.tinhat.starshiprunner;

import java.util.Iterator;
import java.util.Random;

import com.tinhat.android.Pool.PoolObjectFactory;

public class AstroidPool  {

	public static final int MIN_ASTROIDS = 1;
	public static final int MAX_ASTROIDS = 3;
	public static final int SCREEN_LIMIT = 2;
	
	public PoolBufferList<Astroid> pool;
	
	private int count, index, screen, firstEndOrdinal;
	private float minX, maxX, posX, posY;
	private Random rand = new Random();
	private Astroid astroid;
	private Iterator<Astroid> freeing;
	
	public AstroidPool(){
		initialize();
	}
	
	private void initialize(){
		int i;
		
		PoolObjectFactory<Astroid> astroidPoolFactory = new PoolObjectFactory<Astroid>(){
			@Override
			public Astroid createObject(){
				return new Astroid(0,0);
			}
		};
		
		pool = new PoolBufferList<Astroid>(astroidPoolFactory, MAX_ASTROIDS*3);		
		
		for(i=0;i<SCREEN_LIMIT;i++){
			calculatePlacement();
			index++;
		}
	}
	
	public void calculatePlacement(){
		int i;
		
    	count = rand.nextInt(MAX_ASTROIDS - MIN_ASTROIDS + 1) + MIN_ASTROIDS;
    	minX = index == 0 ? World.WORLD_HALF_WIDTH : (int)World.WORLD_WIDTH*screen;
    	maxX = minX + (int)World.WORLD_WIDTH;
    	
    	if(index == 0){
    		firstEndOrdinal = count;	
    	}
    	
    	for(i = 0; i < count; i++){
    		posX = rand.nextFloat() * (maxX - minX) + minX; 
    		posY = rand.nextFloat() * World.WORLD_HEIGHT;
    		astroid = pool.newObject();
    		astroid.setPosition(posX,posY);
    		astroid.textureId = 0; //rand.nextInt(3);
    		
    		if(index < SCREEN_LIMIT){
    			pool.objects.add(astroid);
    		} else {
    			pool.buffer.add(astroid);
    		}
    	}
    	screen++;
	}
	
	public void swapBuffer(){
		int i=0;
		
		freeing = pool.objects.iterator();
		 
		while(freeing.hasNext()){
			astroid = freeing.next();
			pool.free(astroid);
			freeing.remove();
			i++;
			
			if(i==firstEndOrdinal)
				break;
		}

		firstEndOrdinal = pool.objects.size();
		pool.objects.addAll(pool.buffer);
		pool.buffer.clear();
		
	}
	
	
	
}
