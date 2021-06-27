package main.Controller.Collisions;

import main.GameEntity.Alien;
import main.GameEntity.Entity;
import main.GameEntity.LaserBolt;

public class LaserAlienCollisionImpl extends CollisionImplementor {

	/**
	 * This method tests whether there is a collision between a LaserEntity and an
	 * AlienEntity or not. if yes, this method will return true and crunch the
	 * alien.
	 *
	 * @param one must be the {@link LaserBolt}
	 * @param two must be the {@link Alien}
	 */
	@Override
	public boolean evaluate(Entity one, Entity two) {
		if (detectCollision(one, two)) {
			two.crunch();
			return true;
		}

		return false;
	}

}
