package com.tinhat.starshiprunner;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.tinhat.android.Camera2D;
import com.tinhat.android.GLScreen;
import com.tinhat.android.ParticleSystem;
import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.TextureRegion;
import com.tinhat.android.math.CollisionTester;
import com.tinhat.android.math.Rectangle;
import com.tinhat.android.math.Vector2;
import com.tinhat.framework.Game;
import com.tinhat.framework.Input.TouchEvent;

public class AchievementsScreen extends GLScreen {
	Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle backBounds;
    Vector2 touchPoint;
    ParticleSystem particleSystem;
    Vector2 position;
   
	public AchievementsScreen(Game game) {
		super(game);
		guiCam = new Camera2D(glGraphics, 480, 320);
        backBounds = new Rectangle(0, 0, 64, 64);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 400);
        
        ArrayList<TextureRegion> regions = new ArrayList<TextureRegion>();
        
//        regions.add(new TextureRegion(Assets.lightning, 0, 0, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 32, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 64, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 96, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 128, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 160, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 192, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 224, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 256, 120, 32));
//        regions.add(new TextureRegion(Assets.lightning, 0, 288, 120, 32));
        
        regions.add(Assets.exhaustParticle1);
        regions.add(Assets.exhaustParticle2);
       
        
        position = new Vector2(240, 2);
        //particleEngine = new ParticleEngine(regions, position);
        Vector2 velocity = new Vector2(3,15);
        particleSystem = new ParticleSystem(Assets.sprites, regions, position, 12, 24, 12, 24, velocity, 75, 40);
        
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
            
            if(event.type == TouchEvent.TOUCH_UP) {
                if(CollisionTester.pointInRectangle(backBounds, touchPoint)) {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
        
        particleSystem.update(deltaTime);
        //particleEngine.update(deltaTime, position);
    }
	
    @Override
    public void render(float deltaTime) {
        GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMartices();
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
        batcher.endBatch();
        
         
        //particleEngine.Draw(batcher, gl);
        particleSystem.render(batcher, gl);
        
//         batcher.beginBatch(Assets.lightning);
//        
//        keyframe = Assets.lightningAnimation.getKeyFrame(stateTime, Animation.ANIMATION_LOOPING);
//        
//        batcher.drawSprite(240, 160, 480, 32, keyframe);
        
        
        batcher.beginBatch(Assets.sprites);
       
        
        batcher.drawSprite(32, 32, 32, 32, Assets.arrow);
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
 

	
}

