package com.tinhat.starshiprunner;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.tinhat.android.Camera2D;
import com.tinhat.android.FPSCounter;
import com.tinhat.android.GLScreen;
import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.math.CollisionTester;
import com.tinhat.android.math.Rectangle;
import com.tinhat.android.math.Vector2;
import com.tinhat.framework.Game;
import com.tinhat.framework.Input.TouchEvent;
import com.tinhat.starshiprunner.World.WorldListener;



 
 
 
public class GameScreen extends GLScreen {
    static final int GAME_READY = 0;    
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
  
    int state;
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;    
    World world;
    WorldListener worldListener;
    WorldRenderer renderer;    
    String scoreString = "0";
    String coinString = "0";
    int lastScore;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    
    FPSCounter fpsCounter;
    
    public GameScreen(Game game) {
        super(game);
        
        state = GAME_RUNNING;
        guiCam = new Camera2D(glGraphics, 480, 320);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);
        worldListener = new WorldListener() {
            @Override
            public void fly() {            
            }
        };
        
        world = new World(worldListener);
        renderer = new WorldRenderer(glGraphics, batcher, world);     
        pauseBounds = new Rectangle(480-20-15, 320 - 20 - 14, 28, 30);
        resumeBounds = new Rectangle(240 - (105.0f / 2), 160 - 18, 105, 36);
        lastScore = 0;
        fpsCounter = new FPSCounter();
       
    }

	@Override
	public void update(float deltaTime) {
	    if(deltaTime > 0.1f)
	        deltaTime = 0.1f;
	    
	    switch(state) {
	    case GAME_READY:
	        updateReady();
	        break;
	    case GAME_RUNNING:
	        updateRunning(deltaTime);
	        break;
	    case GAME_PAUSED:
	        updatePaused();
	        break;
	    case GAME_LEVEL_END:

	        break;
	    case GAME_OVER:
	        updateGameOver();
	        break;
	    }
	}
	
	private void updateReady() {
	    if(game.getInput().getTouchEvents().size() > 0) {
	        state = GAME_RUNNING;
	    }
	}
	
	private void updateRunning(float deltaTime) {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {
	        TouchEvent event = touchEvents.get(i);
//	        if(event.type != TouchEvent.TOUCH_UP)
//	            continue;
//	        
	        if(event.type == TouchEvent.TOUCH_UP) { 
				 if(world.spaceship.state == Spaceship.SPACESHIP_STATE_FLYING){
					 world.spaceship.weapon.fire();
				 }
			}
	        
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	        
	        if(CollisionTester.pointInRectangle(pauseBounds, touchPoint)) {
	            state = GAME_PAUSED;
	            return;
	        }            
	    }
	    
	  
	    world.update(deltaTime, game.getInput().getAccelY());
	    lastScore = (int)world.spaceship.position.x *3;
	    scoreString = Integer.toString(lastScore);
	    coinString = Integer.toString((int)world.collectedCoins);
	    if(world.state == World.WORLD_STATE_GAME_OVER) {
	        state = GAME_OVER;	        
	        if(lastScore >= Settings.highscores[4]){
	        	Settings.addScore(lastScore);
	        }
	        Settings.addCoins((int)world.collectedCoins);
	        Settings.save(game.getFileIO());
	        
	    }
	    
	   
	}
	
	private void updatePaused() {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) { 
	        TouchEvent event = touchEvents.get(i);
	        if(event.type != TouchEvent.TOUCH_UP)
	            continue;
	        
	        touchPoint.set(event.x, event.y);
	        guiCam.touchToWorld(touchPoint);
	        
	        if(CollisionTester.pointInRectangle(resumeBounds, touchPoint)) {
	            state = GAME_RUNNING;
	            return;
	        }
	    }
	}
	
	 
	
	private void updateGameOver() {
	    List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
	    int len = touchEvents.size();
	    for(int i = 0; i < len; i++) {                   
	        TouchEvent event = touchEvents.get(i);
	        if(event.type != TouchEvent.TOUCH_UP)
	            continue;
	        game.setScreen(new MainMenuScreen(game));
	    }
	}

	@Override
	public void render(float deltaTime) {
	    GL10 gl = glGraphics.getGL();
	    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    gl.glEnable(GL10.GL_TEXTURE_2D);
	    
	    renderer.render();
	    
	    guiCam.setViewportAndMartices();
	    gl.glEnable(GL10.GL_BLEND);
	    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    batcher.beginBatch(Assets.sprites);
	    switch(state) {
	    case GAME_READY:
	        //presentReady();
	        break;
	    case GAME_RUNNING:
	        presentRunning();
	        break;
	    case GAME_PAUSED:
	        presentPaused();
	        break;
	    case GAME_LEVEL_END:
	        //presentLevelEnd();
	        break;
	    case GAME_OVER:
	    	//TODO remove begin and end batch outside of switch once other cases render something
	    	 
	        presentGameOver();
	        
	       
	        break;
	    }
	    batcher.endBatch();
	    gl.glDisable(GL10.GL_BLEND);
	    fpsCounter.logFrame();
	}
	 
    private void presentRunning() {
    	batcher.drawSprite(480-20, 320 - 20, 28, 30, Assets.pause);
     
	    Assets.whiteFont.drawText(batcher, scoreString, 10, 320-20);
	    Assets.goldFont.drawText(batcher, coinString, 10, 320-40);
    }
    
    private void presentPaused(){
    	batcher.drawSprite(240, 160, 105, 36, Assets.paused);        
    }
    
	private void presentGameOver() {
	    batcher.drawSprite(240, 160, 147, 36, Assets.gameOver);        
	    
	   
	    Assets.whiteFont.drawText(batcher, scoreString, 10, 320-20);
	    Assets.goldFont.drawText(batcher, coinString, 10, 320-40);
	}

    @Override
    public void pause() {
        if(state == GAME_RUNNING)
            state = GAME_PAUSED;
    }

    @Override
    public void resume() {        
    }

    @Override
    public void dispose() {       
    }
}
