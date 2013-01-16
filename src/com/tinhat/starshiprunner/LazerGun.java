package com.tinhat.starshiprunner;

public class LazerGun extends Weapon   {
	public LazerGun(Spaceship spaceship) {
		super(spaceship);
	}

	public void fire() {
		Ballistic ballistic = new Lazer(spaceship.position.x+Spaceship.SPACESHIP_HALF_WIDTH, spaceship.position.y-.25f, 0.5f, .025f);
		ballistic.velocity.set(spaceship.velocity.x+ 20, 0);
		ballistics.add(ballistic);
	 }
	
	public void update(float deltaTime){
		for(int i = 0; i < ballistics.size(); i++){
	    	 ballistic = ballistics.get(i);
	    	 ballistic.update(deltaTime);
	     }
	}
}
