package main.Controller.Collisions;

import main.GameEntity.Alien;
import main.GameEntity.Cannon;
import main.GameEntity.Entity;

public class CollisionAlienCannon extends Collision {

	public CollisionAlienCannon() {
		setCollisionImplementor(new AlienCannonCollisionImpl());
	}

	/**
	 * This method tests wether there is a collision between a CannonEntity and an
	 * AlienEntity or not. if yes, this method will return true, which basically
	 * means, that the Cannon/Player should lose a life.
	 * 
	 * @param one must be the {@link Cannon}
	 * @param two must be the {@link Alien}
	 */
	@Override
	public boolean detectCollision(Entity one, Entity two) {
		return getCollisionImplementor().evaluate(one, two);
	}

}
