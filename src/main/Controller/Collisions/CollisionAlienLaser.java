package main.Controller.Collisions;

import main.GameEntity.Alien;
import main.GameEntity.Entity;
import main.GameEntity.LaserBolt;

public class CollisionAlienLaser extends Collision {

	public CollisionAlienLaser() {
		setCollisionImplementor(new AlienLaserCollisionImpl());
	}

	/**
	 * This method tests wether there is a collision between a LaserEntity and an
	 * AlienEntity or not. if yes, this method will return true and crunch the
	 * alien.
	 * 
	 * @param one must be the {@link LaserBolt}
	 * @param two must be the {@link Alien}
	 */
	@Override
	public boolean detectCollision(Entity one, Entity two) {
		return getCollisionImplementor().evaluate(one, two);
	}

}
