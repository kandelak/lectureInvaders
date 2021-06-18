package main.Controller.Collisions;

import main.GameEntity.Entity;

public abstract class Collision {

	private CollisionImplementor collisionImplementor;

	// The line on the canvas is goes from x:0 to x:1000 (max width of canvas) and
	// is at the height of y:1075 (max height of canvas is 1200). The upper left
	// most point on our canvas is (0|0).

	abstract boolean detectCollision(Entity one, Entity two);

	public CollisionImplementor getCollisionImplementor() {
		return collisionImplementor;
	}

	public void setCollisionImplementor(CollisionImplementor collisionImplementor) {
		this.collisionImplementor = collisionImplementor;
	}

}
