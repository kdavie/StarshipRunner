package com.tinhat.starshiprunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.tinhat.android.SpriteBatcher;
import com.tinhat.android.TextureRegion;
import com.tinhat.android.math.Vector2;

public class ParticleEngine {
	private Random random = new Random();
	private Vector2 emitterLocation;
	private List<Particle> particles;
	private ArrayList<TextureRegion> regions;
	
	public ParticleEngine(ArrayList<TextureRegion> regions, Vector2 location){
		this.emitterLocation = location;
		this.regions = regions;
		this.particles = new ArrayList<Particle>();
		
	}
	
	private Particle generateNewParticle(){
		TextureRegion region = regions.get(random.nextInt(regions.size()));
		Vector2 position = emitterLocation;
		Vector2 velocity = new Vector2();
		velocity.x = 10.0f * random.nextFloat()  * 2 - 1;
		velocity.y = 20.0f * random.nextFloat()  * 2 - 1;
		int lifespan = random.nextInt(60)+50;
		int size = random.nextInt(24 - 16 + 1) + 16;
		return new Particle(position.x, position.y, size,size, region, lifespan, velocity);
	}
	
	int total = 50;
	
	
	public void addParticles(){
		
		
	}
	
	public void update(float deltaTime){
		
		if(particles.size() < 100){
		int total = 10;
		for(int i = 0; i < total; i++){
			particles.add(generateNewParticle());
			
		}
		}
		Iterator<Particle> iterator = particles.iterator();
		Particle particle;
		 
		while(iterator.hasNext()){
			particle = iterator.next();
			particle.update(deltaTime);
			if(particle.lifespan <= 0){
				iterator.remove();
				
			}
		 
		}
		
 
	}
	
	public void Draw(SpriteBatcher batcher, GL10 gl){
		batcher.beginBatch(Assets.sprites);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
        
        for(int i = 0; i < particles.size();i++){
        	particles.get(i).draw(batcher);
        }
        
        
        batcher.endBatch();
        
	}
	
}
