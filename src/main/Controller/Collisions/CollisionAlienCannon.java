package main.Controller.Collisions;

import main.GameEntity.Entity;

public class CollisionAlienCannon extends Collision {

	public CollisionAlienCannon() {
		setCollisionImplementor(new AlienCannonCollisionImpl());
	}

	@Override
	public boolean detectCollision(Entity one, Entity two) {
		return getCollisionImplementor().evaluate(one, two);
	}

}
