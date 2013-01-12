package com.tinhat.starshiprunner;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.tinhat.android.GLGame;
import com.tinhat.framework.Screen;


 

public class StarshipRunner extends GLGame {
	boolean firstTimeCreate = true;
	
	@Override  
	public Screen getStartScreen(){
	 
		//return new GameScreen(this);
		return new MainMenuScreen(this);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config){
		super.onSurfaceCreated(gl, config);
		if(firstTimeCreate){
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
	 
	}
}