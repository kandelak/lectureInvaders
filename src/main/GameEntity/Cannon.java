package main.GameEntity;

import main.Controller.Dimension2D;
import main.View.GameBoardUI;

public class Cannon extends Entity {
	private static final String CANNON_IMAGE_FILE = "Cannon.png";

	public Cannon(Dimension2D gameBoardSize) {
		super(gameBoardSize);
		// TODO: Set the values in the parent class for speed etc. like in Bumpers.
		setIconLocation(CANNON_IMAGE_FILE);
	}

	public void moveHorizontally(double amount) {
		double oldX = getPosition().getX();

		// don't exceed max position to the left and right
		if (	(amount < 0 && oldX <= 0) ||
				oldX + amount + getSize().getWidth() >= GameBoardUI.getPreferredSize().getWidth())
			return;

		// oldX + step moves the cannon horizontally; y point remains always the same
		setPosition(oldX + amount, getPosition().getY());
	}
}
