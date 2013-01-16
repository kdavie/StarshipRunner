package com.tinhat.starshiprunner;

import java.util.ArrayList;

public abstract class Weapon {
	public static final int STATE_IDLE = 0;
	public static final int STATE_FIRING = 1;
	
	Ballistic ballistic;
	Spaceship spaceship;
	
	public ArrayList<Ballistic> ballistics;
	
	
	public Weapon(Spaceship spaceship){
		this.ballistics = new ArrayList<Ballistic>();
		this.spaceship = spaceship;
	}
	
	public abstract void fire();
	public abstract void update(float deltaTime);
	
	 
}
