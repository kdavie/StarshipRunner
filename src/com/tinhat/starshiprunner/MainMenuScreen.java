package com.tinhat.starshiprunner;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;

import com.tinhat.android.Camera2D;
import com.tinhat.android.GLGame;
import com.tinhat.android.GLScreen;
import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.math.CollisionTester;
import com.tinhat.android.math.Rectangle;
import com.tinhat.android.math.Vector2;
import com.tinhat.framework.Game;
import com.tinhat.framework.Input.TouchEvent;
 

public class MainMenuScreen extends GLScreen  {
	Camera2D guiCam;
    SpriteBatcher batcher;
	Rectangle playBounds;
	Rectangle highscoresBounds;
    Rectangle helpBounds;
    Rectangle upgradesBounds;
    Rectangle achievementsBounds;
    Vector2 touchPoint;
    
	public MainMenuScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        touchPoint = new Vector2();  
		playBounds = new Rectangle(170,161,59,36);
		highscoresBounds = new Rectangle(170,128,165,36); 
		upgradesBounds = new Rectangle(170,97,137,36);
		achievementsBounds = new Rectangle(170,64,192,36);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float deltaTime) {
		GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMartices();
        gl.glEnable(GL10.GL_TEXTURE_2D);        
        batcher.beginBatch(Assets.mainMenu);
        batcher.drawSprite(240,160, 480, 320, Assets.mainMenuRegion);
        
        batcher.endBatch();
        
        batcher.beginBatch(Assets.sprites);
        Assets.whiteFont.drawText(batcher, "HMMM, WHAT IS GOING ON? SHOULD WORK RIGHT NOW", 30, 30);
        Assets.greenNumberFont.drawText(batcher, "1,000", 30, 10);
        
        batcher.endBatch();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);                        
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint); 
                if(CollisionTester.pointInRectangle(playBounds, touchPoint)) {
                	game.setScreen(new GameScreen(game));
                    return;
                }
                if(CollisionTester.pointInRectangle(highscoresBounds, touchPoint)) {
                	game.setScreen(new HighscoresScreen(game));
                    return;
                }
                if(CollisionTester.pointInRectangle(upgradesBounds, touchPoint)) {
                	game.setScreen(new UpgradesScreen(game));
                    return;
                }
                if(CollisionTester.pointInRectangle(achievementsBounds, touchPoint)) {
                	game.setScreen(new AchievementsScreen(game));
                    return;
                }
            }
        }
		
	}
	
	
}