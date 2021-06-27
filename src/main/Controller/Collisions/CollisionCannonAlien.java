package main.Controller.Collisions;

import main.GameEntity.Alien;
import main.GameEntity.Cannon;
import main.GameEntity.Entity;

public class CollisionCannonAlien extends Collision {

	public CollisionCannonAlien(double border) {
		setCollisionImplementor(new CannonAlienCollisionImpl(border));
	}

	/**
	 * This method tests whether there is a collision between a CannonEntity or
	 * border/limit and an AlienEntity or not. if yes, this method will return true,
	 * which basically means, that the Cannon/Player should lose a life.
	 *
	 * @param one       {@link Cannon}
	 * @param two{@link Alien}
	 */

	@Override
	public boolean detectCollision(Entity one, Entity two) {
		return getCollisionImplementor().evaluate(one, two);
	}

}
