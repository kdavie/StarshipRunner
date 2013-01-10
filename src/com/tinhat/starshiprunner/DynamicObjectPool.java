package com.tinhat.starshiprunner;

import java.util.Random;

import com.tinhat.android.Pool.PoolObjectFactory;

public class DynamicObjectPool<T>  {

	 
	public static final int SCREEN_LIMIT = 2;
	
	public PooledBufferedList<T> pool;
	
	private int count, index, screen, minObjects, maxObjects, textures, textureIndex;
	private float minX, maxX, posX, posY;
	private Random rand = new Random();
	private T object;
	private IDynamicObject dynamicObject;
	
	public DynamicObjectPool(int min, int max, int availableTextures, PoolObjectFactory<T> factory){
		int i;
		
		minObjects = min;
		maxObjects = max;
		textures  = availableTextures;
		pool = new PooledBufferedList<T>(factory, 100);		
		
		for(i=0;i<SCREEN_LIMIT;i++){
			calculatePlacement();
			index++;
		}
	}
	
	public void calculatePlacement(){
		int i;
		
    	count = rand.nextInt(maxObjects - minObjects + 1) + minObjects;
    	minX = (int)World.WORLD_WIDTH*screen;
    	maxX = minX + (int)World.WORLD_WIDTH;
    	
    	if(index == 0){
    		 pool.setFirstEndOrdinal(count);
    	}
    	
    	for(i = 0; i < count; i++){
    		object = pool.newObject();
    		dynamicObject = (IDynamicObject)object;
    		
    		if(index == 0 && dynamicObject.isCollidable()){
    			minX = World.WORLD_HALF_WIDTH;
    		}
    		
    		posX = rand.nextFloat() * (maxX - minX) + minX; 
    		posY = rand.nextFloat() * World.WORLD_HEIGHT;
    		textureIndex = textures > 1 ? rand.nextInt(textures) : 0;
    		
    		dynamicObject.setPosition(posX,posY);
    		dynamicObject.setTextureIndex(textureIndex); 
    		
    		if(index < SCREEN_LIMIT){
    			pool.objects.add(object);
    		} else {
    			pool.buffer.add(object);
    		}
    	}
    	screen++;
	}
	
	public int size(){
		return pool.objects.size();
	}
	
	public interface IDynamicObject {
		void setPosition(float x, float y);
		void setTextureIndex(int index);
		int getTextureIndex();
		boolean isCollidable();
	}
	
	
}
