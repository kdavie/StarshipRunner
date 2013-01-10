package com.tinhat.starshiprunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.tinhat.android.Animation;
import com.tinhat.android.Camera2D;
import com.tinhat.android.GLGraphics;
import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.TextureRegion;
import com.tinhat.android.Pool.PoolObjectFactory;
import com.tinhat.framework.Input.KeyEvent;
 
public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 15;
    static final float FRUSTUM_HEIGHT = 10;    
    static final int MIN_STARS = 10;
    static final int MAX_STARS = 25;
    static final int MIN_COMETS = 1;
    static final int MAX_COMETS = 3;
    static final int MIN_COINS = 0;
    static final int MAX_COINS = 5;
    
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;    
    Random rand;
    float[][] stars;
    DynamicObjectPool<Astroid> astroidPool;
    
    
    float nextTransition = FRUSTUM_WIDTH/2;
    int transition = 0;
    int currentScreen = 0;
     
    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;       
        this.rand = new Random();
        this.stars = new float[3][];
        initializeObjectPools();
        //world.astroids = new Astroid[3][];
        world.coins = new Coin[3][];
        this.calculateStars(0);
        //this.calculateAstroids(0);
        this.calculateCoins(0);
        currentScreen++;
        this.calculateStars(1);        
        //this.calculateAstroids(1);
        this.calculateCoins(1);
        currentScreen++;
    }
    
    public void initializeObjectPools(){
    	PoolObjectFactory<Astroid> astroidPoolFactory = new PoolObjectFactory<Astroid>() {
			@Override
			public Astroid createObject() {
				return new Astroid();
			}
		};
		
		astroidPool = new DynamicObjectPool<Astroid>(MIN_COMETS,MAX_COMETS,4,astroidPoolFactory);
    }
    
    public void render() {
    	updateCamera();
        cam.setViewportAndMartices();
        renderBackground();
        renderObjects();        
    }
    
    public void renderBackground() {
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(cam.position.x, cam.position.y,
                           FRUSTUM_WIDTH, FRUSTUM_HEIGHT, 
                           Assets.backgroundRegion);
        batcher.endBatch();
    }
    
    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.sprites);
       
        renderStars();
        renderBallistics();
        renderSpaceship();
       
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
        
    }
    
 
    private int calculateStars(int index) {
    	int i;  	
    	int numberOfStars = rand.nextInt(MAX_STARS - MIN_STARS + 1) + MIN_STARS;
    	int min = (int)FRUSTUM_WIDTH*currentScreen;
    	int max = min + (int)FRUSTUM_WIDTH;
    	int size = numberOfStars * 3;
    	this.stars[index] = new float[size];
    	
    	for(i = 0; i < size;i+=3){
    		this.stars[index][i] = rand.nextInt(15);
    		this.stars[index][i+1] = rand.nextFloat() * (max - min) + min; 
    		this.stars[index][i+2] = rand.nextFloat() * FRUSTUM_HEIGHT; 
    	}
    	
    	
    	return numberOfStars;
    }
    
    private void calculateAstroids(int index){
    	int i, count;
    	float minX, maxX;
    	float x, y;
    	Astroid astroid;
    	 
    	count = rand.nextInt(MAX_COMETS - MIN_COMETS + 1) + MIN_COMETS;
    	minX = index == 0 ? FRUSTUM_WIDTH/2 : (int)FRUSTUM_WIDTH*currentScreen;
    	maxX = minX + (int)FRUSTUM_WIDTH;

    	
    	world.astroids[index] = new Astroid[count];
    	
    	for(i = 0; i < count;i++){
    		x = rand.nextFloat() * (maxX - minX) + minX; 
    		y = rand.nextFloat() * FRUSTUM_HEIGHT;
    		//y = world.spaceship.position.y -.25f; //align with ship for testing
    		astroid = new Astroid(x,y);
    		astroid.setTextureIndex(0); //rand.nextInt(3);
    		world.astroids[index][i] = astroid; 
    	}
    }
    
    private void calculateCoins(int index){
    	int i, count, minX, maxX;
    	float x, y;
    	Coin coin;
    	 
    	count = rand.nextInt(MAX_COINS - MIN_COINS + 1) + MIN_COINS;
    	minX = (int)FRUSTUM_WIDTH*currentScreen;
    	maxX = minX + (int)FRUSTUM_WIDTH;

    	
    	world.coins[index] = new Coin[count];
    	
    	for(i = 0; i < count;i++){
    		x = rand.nextFloat() * (maxX - minX) + minX; 
    		y = rand.nextFloat() * FRUSTUM_HEIGHT;
    	 
    		coin = new Coin(x,y);
    	 
    		world.coins[index][i] = coin; 
    	}
    }
     
    private void renderStars() {
    	float x = cam.position.x;
    	
    	if(x > nextTransition){
    		nextTransition += FRUSTUM_WIDTH/2;
    		transition++;
    		
    		if(transition % 2 == 0){
    			calculateStars(2);
    			//calculateAstroids(2);
    			calculateCoins(2);
    			currentScreen++;
    			astroidPool.calculatePlacement();
    		}
    		else {
    			if(transition > 1){
	    			stars[0] = stars[1];
	   			    stars[1] = stars[2];
	   			    stars[2] = null;
	   			    
//	   			    world.astroids[0] = world.astroids[1];
//	   			    world.astroids[1] = world.astroids[2];
//	   			    world.astroids[2] = null;
	   			    
	   			    world.coins[0] = world.coins[1];
	   			    world.coins[1] = world.coins[2];
	   			    world.coins[2] = null;
	   			    
	   			    astroidPool.pool.swapBuffer();
    			}
    		}	
    	}
        
    	int max;    	
    	for(int j = 0; j < 2; j++){
	    	max = stars[j].length;
	    	
	    	for(int i = 0; i < max; i+=3)
	    	{
	    		batcher.drawSprite(stars[j][i+1], stars[j][i+2], 0.5f, 0.5f, Assets.stars[(int)stars[j][i]]);
	    	}
    	}
    	TextureRegion keyframe;
    	Astroid astroid;
//    	for(int j = 0; j < 2; j++){
//	    	max = world.astroids[j].length;
//	    	
//	    	for(int i = 0; i < max; i++)
//	    	{
//	    		astroid = world.astroids[j][i];
//	    		if(astroid != null){
//	    			if(astroid.state == Astroid.STATE_EXPLODING){
//	    				  				 
//	    				keyframe = Assets.astroidExplodingAnimation.getKeyFrame(astroid.stateTime, Animation.ANIMATION_LOOPING);
//	    				batcher.drawSprite(astroid.position.x, astroid.position.y, Astroid.WIDTH, Astroid.HEIGHT, keyframe);
//	            		if(keyframe.equals(Assets.astroidExplodingAnimation.getKeyFrame(Assets.astroidExplodingAnimation.frameCount-1, Animation.ANIMATION_LOOPING))){
//	            			world.astroids[j][i] =  null;
//	            		}
//	    			} else {
//	    				batcher.drawSprite(astroid.position.x, astroid.position.y, Astroid.WIDTH, Astroid.HEIGHT, Assets.astroids[astroid.textureId]);
//	    			}
//	    		}
//	    	}
//    	}
    	
    	for(int j = 0; j < astroidPool.pool.objects.size();j++){
    		astroid = astroidPool.pool.objects.get(j);
    		batcher.drawSprite(astroid.position.x, astroid.position.y, Astroid.WIDTH, Astroid.HEIGHT, Assets.astroids[astroid.getTextureIndex()]);
    	}
    	
    	for(int j = 0; j < 2; j++){
	    	max = world.coins[j].length;
	    	
	    	for(int i = 0; i < max; i++)
	    	{
	    		if(world.coins[j][i] != null){
	    					
		    		keyframe = Assets.spinningCoinAnimation.getKeyFrame(world.coins[j][i].stateTime, Animation.ANIMATION_LOOPING);
		    		batcher.drawSprite(world.coins[j][i].position.x, world.coins[j][i].position.y, Coin.WIDTH, Coin.HEIGHT, keyframe);
	    		}
	    		
	    	}
    	}
    }
    
    private void renderBallistics(){
         int size = world.spaceship.ballistics.size();
         float viewport = cam.position.x + cam.frustumWidth/2;
         Ballistic ballistic;
         ArrayList<Integer> remove = new ArrayList<Integer>(); 
    	 for(int i = 0; i < size;i++){
    		 ballistic = world.spaceship.ballistics.get(i);    		 
    		 if(ballistic.position.x < viewport){
    			 batcher.drawSprite(ballistic.bounds.lowerLeft.x, ballistic.bounds.lowerLeft.y, ballistic.bounds.width, ballistic.bounds.height, Assets.arrow);
    			 batcher.drawSprite(ballistic.position.x, ballistic.position.y, 0.5f, 0.25f, Assets.lazer);
    		 } else {
    			 remove.add(i);
    		 }
    		 
    	 }
    	 for(int x = 0; x < remove.size();x++){
    		 world.spaceship.ballistics.remove(x);
    	 }
    }
    
    private void renderSpaceship() {
        TextureRegion keyframe;
        switch(world.spaceship.state){
        	case Spaceship.SPACESHIP_STATE_SPAWNING:
        		keyframe = Assets.spawningAnimation.getKeyFrame(world.spaceship.stateTime, Animation.ANIMATION_NONLOOPING);
        		break;
        	case Spaceship.SPACESHIP_STATE_FLYING:
        		keyframe = Assets.flyingAnimation.getKeyFrame(world.spaceship.stateTime, Animation.ANIMATION_LOOPING);
        		break;
        	case Spaceship.SPACESHIP_STATE_CRASHING:
        		keyframe = Assets.crashingAnimation.getKeyFrame(world.spaceship.stateTime, Animation.ANIMATION_NONLOOPING);
        	
        		if(keyframe.equals(Assets.crashingAnimation.getKeyFrame(Assets.crashingAnimation.frameCount-1, Animation.ANIMATION_NONLOOPING))){
        			world.state = World.WORLD_STATE_GAME_OVER;
        		}
        		break;
        	default:
        		keyframe = Assets.spawningAnimation.getKeyFrame(world.spaceship.stateTime, Animation.ANIMATION_NONLOOPING);
        		break;
        }
  
        batcher.drawSprite(world.spaceship.position.x, world.spaceship.position.y, world.spaceship.SPACESHIP_WIDTH, world.spaceship.SPACESHIP_HEIGHT, keyframe);

    }
    
    private void updateCamera() {    	
    	if(world.spaceship.position.x > World.WORLD_HALF_WIDTH){
    		cam.position.set(world.spaceship.position.x, World.WORLD_HALF_HEIGHT);
    	}
    }
    
}
     