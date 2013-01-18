package com.tinhat.starshiprunner;

import com.tinhat.android.DynamicGameObject;
import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.TextureRegion;
import com.tinhat.android.math.Vector2;

public class Particle extends DynamicGameObject {

	private TextureRegion region;
	public int lifespan;
	
	public Particle(float x, float y, float width, float height, TextureRegion region, int lifespan, Vector2 velocity) {
		super(x, y, width, height);
		this.region = region;
		this.lifespan = lifespan;
		this.velocity.set(velocity);		
	}
	
	public void draw(SpriteBatcher batcher) {
		batcher.drawSprite(position.x, position.y, bounds.width, bounds.height, region);
	}
	
	public void update(float deltaTime) {
		lifespan--;
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
	}

}
