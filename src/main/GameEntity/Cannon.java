package main.GameEntity;

import main.Controller.Dimension2D;
import main.View.GameBoardUI;

public class Cannon extends Entity {

	/**
	 * The direction as degree within a circle, a value between 0 (inclusive) and
	 * 360 (exclusive).
	 */
	private int direction;

	private static final String CANNON_IMAGE_FILE = "Cannon.png";

	public Cannon(Dimension2D gameBoardSize) {
		super(gameBoardSize);
		// TODO: Set the values in the parent class for speed etc. like in Bumpers.
		setIconLocation(CANNON_IMAGE_FILE);
	}

	void shoot() {

	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	void moveHorizontally(double step /* , KeyPressed direction */) {
		// TODO: This method does not seem finished. We also need to check for the right
		// arrow key, and the else statement should do nothing. This method does also
		// not need a KeyPressed attribute.
		// The subclasses of Entity should not have a position attribute

		double oldX = getPosition().getX();

		if ((step < 0 && oldX == 0) || oldX + getSize().getWidth() >= GameBoardUI.getPreferredSize().getWidth()) {
			return;
		}

		// oldX + step moves the cannon horizontally; y point remains always the same
		setPosition(oldX + step, getPosition().getY());

	}
}
