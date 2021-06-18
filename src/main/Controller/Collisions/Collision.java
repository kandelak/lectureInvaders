package main.Controller.Collisions;

import main.GameEntity.Alien;
import main.GameEntity.Entity;

public abstract class Collision {

	private CollisionImplementor collisionImplementor;

	// The line on the canvas is goes from x:0 to x:1000 (max width of canvas) and
	// is at the height of y:1075 (max height of canvas is 1200). The upper left
	// most point on our canvas is (0|0).

	/**
	 * This method tests wether there is a collision between an Entity and an
	 * AlienEntity or not.
	 * 
	 * If a collision between Laser and Alien is tested, the Alien will lose.
	 * 
	 * If a collision between Cannon and Alien is tested, the cannon will lose.
	 * 
	 * @param one must be an {@link Entity} except Alien; e.g. Cannon or Laser
	 * @param two must be the {@link Alien}
	 */
	public abstract boolean detectCollision(Entity one, Entity two);

	public CollisionImplementor getCollisionImplementor() {
		return collisionImplementor;
	}

	public void setCollisionImplementor(CollisionImplementor collisionImplementor) {
		this.collisionImplementor = collisionImplementor;
	}

}
