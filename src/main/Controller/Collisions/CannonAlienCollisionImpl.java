package main.Controller.Collisions;

import main.Controller.Dimension2D;
import main.Controller.Point2D;
import main.GameEntity.Alien;
import main.GameEntity.Cannon;
import main.GameEntity.Entity;

public class CannonAlienCollisionImpl extends CollisionImplementor {

	private final double border;

	public CannonAlienCollisionImpl(double border) {
		this.border = border;
	}

	/**
	 * This method tests whether there is a collision between a CannonEntity and an
	 * AlienEntity or not. if yes, this method will return true, which basically
	 * means, that the Cannon/Player should lose a life.
	 *
	 * @param one must be the {@link Cannon}
	 * @param two must be the {@link Alien}
	 */
	@Override
	public boolean evaluate(Entity one, Entity two) {
		return detectCollision(one, two);
	}

	/**
	 * If alien passes the border, it counts as if an alien hit the cannon and it
	 * evaluates to true!
	 * 
	 * @param one {@link Cannon}
	 * @param two {@link Alien}
	 */
	@Override
	boolean detectCollision(Entity one, Entity two) {

		final double limit = border - one.getSize().getHeight();

		Point2D p2 = two.getPosition();
		Dimension2D d2 = two.getSize();

		return p2.getY() + d2.getHeight() >= limit ? true : false;
	}

}
