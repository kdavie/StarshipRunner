package com.tinhat.starshiprunner;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

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

public class UpgradesScreen extends GLScreen implements OnGestureListener {
	Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle backBounds;
    Rectangle itemBounds;
    
    Vector2 touchPoint;
    Vector2 startTouchPoint;
    int lowerBounds;
    
	public UpgradesScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 480, 320);
        backBounds = new Rectangle(0, 0, 64, 64);
        itemBounds = new Rectangle(80, 240, 380, 40);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 300);
        GLGame glGame = (GLGame)game;
	    glGame.setGestureDetector(this);
	}
	
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            
            if(event.type == TouchEvent.TOUCH_DOWN){
            	startTouchPoint = (Vector2) touchPoint.copy();
            }
            
            if(event.type == TouchEvent.TOUCH_UP) {
                if(CollisionTester.pointInRectangle(backBounds, touchPoint)) {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
                
//                if(CollisionTester.pointInRectangle(itemBounds, touchPoint)) {
//                    if(Settings.coins >= 500){
//                    	Settings.weapon = 1;
//                    	Settings.coins -= 500;
//                    	Settings.save(game.getFileIO());
//                    }
//                    return;
//                }
            }
            
//            if(event.type == TouchEvent.TOUCH_DRAGGED){
//            	if(startTouchPoint.y < touchPoint.y){
//            		if(guiCam.position.y > lowerBounds)
//            			guiCam.position.set(guiCam.position.x, guiCam.position.y - (touchPoint.y - startTouchPoint.y));
//            		else
//            			guiCam.position.set(guiCam.position.x, lowerBounds);
//            	}
//            	else {
//            		if(guiCam.position.y + (startTouchPoint.y+touchPoint.y) <160)
//            		{
//            			guiCam.position.set(guiCam.position.x, guiCam.position.y + (startTouchPoint.y+touchPoint.y));
//            		} else {
//            			guiCam.position.set(guiCam.position.x, 160);
//            		}
//            	}
//            		
//            }
            
            if(event.type == TouchEvent.TOUCH_DRAGGED){
//            	if(startTouchPoint.y < touchPoint.y){
//            		//reduce starting y;
//            		startingY += (touchPoint.y - startTouchPoint.y); 
//            	}
//            	else {
//            		startingY -= (touchPoint.y + startTouchPoint.y); 
//            		//increase starting y;
//            	}
          
            }
        }
    }
	
    int startingY = 280;
    int itemCount = 20;
    int maxY = (itemCount * 41) +31 - 21;
    String text = "CATEGORY 1";
    String slot = "SLOT ";
    String price = "10,000";
    float textWidth;
    
    @Override
    public void render(float deltaTime) {
        GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMartices();
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(240, guiCam.position.y, 480, 320, Assets.backgroundRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.sprites);
        
        Assets.goldFont.drawText(batcher, Integer.toString(Settings.coins), 10, 320-20);

       
        batcher.drawSprite(240, startingY, 380, 21, Assets.upgradeCategory);
        
        textWidth = text.length() * Assets.goldFont.glyphWidth;
        Assets.goldFont.drawText(batcher, text, 240-(textWidth/2), startingY);
        int y = startingY-32;
        for(int i = 0; i < itemCount; i++) {
        	batcher.drawSprite(240, y, 380, 41, Assets.upgradeSlot);
        	Assets.whiteFont.drawText(batcher, slot + Integer.toString(i+1), 240-128, y);
            Assets.redNumberFont.drawText(batcher, price, 430- price.length()*Assets.greenNumberFont.glyphWidth, y);
         
        	y-= 41;
        }
        
        
        batcher.drawSprite(24, 32, 32, 32, Assets.arrow);
        
        
        
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
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
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int one = 1;
		int two = one+one;
		int three = two+one;
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	    startingY += distanceY;
	   
	   
	    if(startingY >maxY ) {
	    	startingY = maxY; 
	    } else if(startingY < 280){
	    	startingY = 280;
	    }
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
