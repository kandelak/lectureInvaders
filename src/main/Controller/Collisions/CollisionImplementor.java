package main.Controller.Collisions;

import main.Controller.Dimension2D;
import main.Controller.Point2D;
import main.GameEntity.Entity;

public abstract class CollisionImplementor {

	public abstract boolean evaluate(Entity one, Entity two);

	boolean detectCollision(Entity one, Entity two) {
		Point2D p1 = one.getPosition();
		Dimension2D d1 = one.getSize();

		Point2D p2 = two.getPosition();
		Dimension2D d2 = two.getSize();

		boolean above = p1.getY() + d1.getHeight() < p2.getY();
		boolean below = p1.getY() > p2.getY() + d2.getHeight();
		boolean right = p1.getX() + d1.getWidth() < p2.getX();
		boolean left = p1.getX() > p2.getX() + d2.getWidth();

		return !above && !below && !right && !left;
	}

}
