package main.Controller.Collisions;

import main.GameEntity.Alien;
import main.GameEntity.Entity;

public class CollisionLaserAlien extends Collision {

	public CollisionLaserAlien() {
		setCollisionImplementor(new LaserAlienCollisionImpl());
	}

	/**
	 * This method tests whether there is a collision between a LaserEntity and an
	 * AlienEntity or not. if yes, this method will return true, which basically
	 * means, that the alien dies.
	 *
	 * @param one must be the {@link Laser}
	 * @param two must be the {@link Alien}
	 */
	public boolean detectCollision(Entity one, Entity two) {
		return getCollisionImplementor().detectCollision(one, two);
	}
}
