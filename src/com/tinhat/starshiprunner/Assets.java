package com.tinhat.starshiprunner;

import com.tinhat.android.Animation;
import com.tinhat.android.Font;
import com.tinhat.android.GLGame;
import com.tinhat.android.Texture;
import com.tinhat.android.TextureRegion;

public class Assets {
	
	public static Texture background;	
	public static Texture mainMenu;	
	public static Texture sprites;
	
	public static TextureRegion backgroundRegion;
	public static TextureRegion mainMenuRegion;
	public static TextureRegion hightlight;
	public static TextureRegion explosion;
	public static TextureRegion gameOver;
	public static TextureRegion pause;
	public static TextureRegion paused;
    public static TextureRegion arrow;
    public static TextureRegion highscoresRegion;
    public static TextureRegion lazer;
    
	public static TextureRegion[] stars;
	public static TextureRegion[] astroids;
	
	public static Animation spawningAnimation; 
	public static Animation flyingAnimation;
	public static Animation crashingAnimation;
	public static Animation spinningCoinAnimation;
	public static Animation astroidExplodingAnimation;
	
	public static Font whiteFont;
	public static Font goldFont;
	
	public static void load(GLGame game) {
		
		mainMenu = new Texture(game, "starship_runner.png");
		mainMenuRegion = new TextureRegion(mainMenu, 0, 0, 480, 320); 
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);
		sprites = new Texture(game, "sprites.png");
		highscoresRegion = new TextureRegion(sprites,128,95,165,36);
	    gameOver = new TextureRegion(sprites, 128,64,147,36);
	    paused = new TextureRegion(sprites, 128,131,105,36);
	    pause = new TextureRegion(sprites,0,128,28,30);
		hightlight = new TextureRegion(sprites, 0, 192, 64, 64);
		explosion = new TextureRegion(sprites, 0, 192, 62, 32);
		arrow = new TextureRegion(sprites,0,160,32,32);
		lazer = new TextureRegion(sprites,2,226,16,8);
		
		stars = new TextureRegion[]{
				new TextureRegion(sprites, 0, 64, 16, 16),
				new TextureRegion(sprites, 16, 64, 16, 16),
				new TextureRegion(sprites, 32, 64, 16, 16),
				new TextureRegion(sprites, 48, 64, 16, 16),
				new TextureRegion(sprites, 0, 80, 16, 16),
				new TextureRegion(sprites, 16, 80, 16, 16),
				new TextureRegion(sprites, 32, 80, 16, 16),
				new TextureRegion(sprites, 48, 80, 16, 16),
				new TextureRegion(sprites, 0, 96, 16, 16),
				new TextureRegion(sprites, 16, 96, 16, 16),
				new TextureRegion(sprites, 32, 96, 16, 16),
				new TextureRegion(sprites, 48, 96, 16, 16), 
				new TextureRegion(sprites, 0, 112, 16, 16),
				new TextureRegion(sprites, 16, 112, 16, 16),
				new TextureRegion(sprites, 32, 112, 16, 16),
				new TextureRegion(sprites, 48, 112, 16, 16)
		};
		
		astroids = new TextureRegion[]{
				new TextureRegion(sprites, 64, 64, 27, 23),
				new TextureRegion(sprites, 96, 64, 32, 32),
				new TextureRegion(sprites, 64, 96, 32, 32),
				new TextureRegion(sprites, 96, 96, 32, 32)
		};
		
		spawningAnimation = new Animation(0.09f,                                 
                new TextureRegion(sprites, 0, 0, 62, 32), 
                new TextureRegion(sprites, 62, 0, 62, 32),
                new TextureRegion(sprites, 124, 0, 62, 32),
                new TextureRegion(sprites, 186, 0, 62, 32)); 
		
		flyingAnimation = new Animation(0.09f,                                 
                new TextureRegion(sprites, 248, 0, 62, 32), 
                new TextureRegion(sprites, 310, 0, 62, 32),
                new TextureRegion(sprites, 372, 0, 62, 32),
                new TextureRegion(sprites, 434, 0, 62, 32)); 
		
		crashingAnimation = new Animation(0.1f,
				new TextureRegion(sprites, 0, 192, 62, 32),
				new TextureRegion(sprites, 62, 192, 62, 32),
				new TextureRegion(sprites, 124, 192, 62, 32),
				new TextureRegion(sprites, 186, 192, 62, 32),
				new TextureRegion(sprites, 248, 192, 62, 32),
				new TextureRegion(sprites, 310, 192, 62, 32),
				new TextureRegion(sprites, 372, 192, 62, 32)); 
		
		spinningCoinAnimation = new Animation(0.2f, 
				new TextureRegion(sprites, 65, 161, 24, 24),
				new TextureRegion(sprites, 89, 161, 24, 24),
				new TextureRegion(sprites, 113, 161, 24, 24)
				);
		
		astroidExplodingAnimation = new Animation(0.1f, 
				new TextureRegion(sprites, 64, 224, 27, 23),
				new TextureRegion(sprites, 96, 224, 27, 23),
				new TextureRegion(sprites, 128, 224, 27, 23),
				new TextureRegion(sprites, 160, 224, 27, 23)
				);
		
		whiteFont = new Font(sprites, 320, 64, 16, 11, 23);
		goldFont = new Font(sprites, 320, 256, 16, 11, 23);
	}
	
	 public static void reload() {
		 mainMenu.reload();
		 sprites.reload();
		 background.reload();
	 }
}
