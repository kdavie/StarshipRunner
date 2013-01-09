package com.tinhat.starshiprunner;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.tinhat.android.Camera2D;
import com.tinhat.android.GLScreen;
import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.math.CollisionTester;
import com.tinhat.android.math.Rectangle;
import com.tinhat.android.math.Vector2;
import com.tinhat.framework.Game;
import com.tinhat.framework.Input.TouchEvent;
import com.tinhat.starshiprunner.Assets;
import com.tinhat.starshiprunner.MainMenuScreen;
import com.tinhat.starshiprunner.Settings;

public class HighscoresScreen extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle backBounds;
    Vector2 touchPoint;
    String[] highScores;  
    float xOffset = 0;
    
    public HighscoresScreen(Game game) {
        super(game);
        
        guiCam = new Camera2D(glGraphics, 480, 320);
        backBounds = new Rectangle(0, 0, 64, 64);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 100);
        highScores = new String[5];        
        for(int i = 0; i < 5; i++) {
            highScores[i] = (i + 1) + ". " + Settings.highscores[i];
            xOffset = Math.max(highScores[i].length() * Assets.whiteFont.glyphWidth, xOffset);
        }        
        xOffset = 192 - xOffset / 2 + Assets.whiteFont.glyphWidth / 2;
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
    }

    @Override
    public void render(float deltaTime) {
        GL10 gl = glGraphics.getGL();        
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMartices();
        
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
        batcher.endBatch();
        
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.sprites);
        batcher.drawSprite(240, 214, 165, 36, Assets.highscoresRegion);
        
        float y = 90;
        for(int i = 4; i >= 0; i--) {
            Assets.whiteFont.drawText(batcher, highScores[i], xOffset, y);
            y += Assets.whiteFont.glyphHeight;
        }
        
        batcher.drawSprite(32, 32, 32, 32, Assets.arrow);
        batcher.endBatch();
        
        gl.glDisable(GL10.GL_BLEND);
    }
    
    @Override
    public void resume() {        
    }
    
    @Override
    public void pause() {        
    }

    @Override
    public void dispose() {
    }
}
