package com.tinhat.starshiprunner;


import java.util.Iterator;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;
import com.tinhat.android.Animation;
import com.tinhat.android.Camera2D;
import com.tinhat.android.GLGraphics;
import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.TextureRegion;

 
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
 
    float nextTransition = FRUSTUM_WIDTH/2, viewport;
    int transition = 0;
    TextureRegion keyframe;
	Astroid astroid;
	Coin coin;
	Star star;
	Ballistic ballistic;
	Iterator<Ballistic> ballisticIterator;
	
    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;       
        this.rand = new Random();
        world.initializeObjectPools();
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
    
    private void renderStars() {
    	if(cam.position.x > nextTransition){
    		nextTransition += FRUSTUM_WIDTH/2;
    		transition++;
    		
    		if(transition % 2 == 0){
    			world.stars.calculatePlacement();
    			world.astroids.calculatePlacement();
    			world.coins.calculatePlacement();
    		}
    		else {
    			if(transition > 1){
	    			world.stars.pool.swapBuffer();
	   			    world.astroids.pool.swapBuffer();
	   			    world.coins.pool.swapBuffer();
    			}
    		}	
    	}
	
    	for(int i = 0; i < world.stars.size(); i++)
    	{
    		star = world.stars.pool.objects.get(i);
    		batcher.drawSprite(star.position.x, star.position.y, 0.5f, 0.5f, Assets.stars[star.textureIndex]);
    	}
    	
    	for(int i = 0; i < world.astroids.pool.objects.size();i++){
    		astroid = world.astroids.pool.objects.get(i);
    		if(!astroid.destroyed){
    			batcher.drawSprite(astroid.position.x, astroid.position.y, Astroid.WIDTH, Astroid.HEIGHT, Assets.astroids[astroid.textureIndex]);
    		}
    	}

    	for(int i = 0; i < world.coins.pool.objects.size();i++){
    		coin = world.coins.pool.objects.get(i);
    		if(!coin.collected){
				
	    		keyframe = Assets.spinningCoinAnimation.getKeyFrame(coin.stateTime, Animation.ANIMATION_LOOPING);
	    		batcher.drawSprite(coin.position.x, coin.position.y, Coin.WIDTH, Coin.HEIGHT, keyframe);
    		}
    	}
    	

    }
    
    private void renderBallistics(){
 
         viewport = cam.position.x + cam.frustumWidth/2;
         
         ballisticIterator = world.spaceship.ballistics.iterator();
         
         while(ballisticIterator.hasNext()){
        	 ballistic = ballisticIterator.next();
        	 if(ballistic.position.x < viewport){
    			 batcher.drawSprite(ballistic.bounds.lowerLeft.x, ballistic.bounds.lowerLeft.y, ballistic.bounds.width, ballistic.bounds.height, Assets.arrow);
    			 batcher.drawSprite(ballistic.position.x, ballistic.position.y, 0.5f, 0.25f, Assets.lazer);
    		 } else {
    			 ballisticIterator.remove();
    		 }
         }
    
    }
    
    private void renderSpaceship() {
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
  
        batcher.drawSprite(world.spaceship.position.x, world.spaceship.position.y, Spaceship.SPACESHIP_WIDTH, Spaceship.SPACESHIP_HEIGHT, keyframe);

    }
    
    private void updateCamera() {    	
    	if(world.spaceship.position.x > World.WORLD_HALF_WIDTH){
    		cam.position.set(world.spaceship.position.x, World.WORLD_HALF_HEIGHT);
    	}
    }
    
}
     