package main.Controller.Collisions;

import main.GameEntity.Entity;

public class CollisionAlienLaser extends Collision {

	public CollisionAlienLaser() {
		setCollisionImplementor(new AlienLaserCollisionImpl());
	}

	@Override
	public boolean detectCollision(Entity one, Entity two) {
		return getCollisionImplementor().evaluate(one, two);
	}

}
