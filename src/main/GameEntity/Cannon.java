package main.GameEntity;

import main.Controller.Dimension2D;
import main.View.GameBoardUI;

public class Cannon extends Entity {

    private static final String CANNON_IMAGE_FILE = "Cannon.png";
    private static final double DEFAULT_WIDTH_CANNON = 110.0;
    private static final double DEFAULT_HEIGHT_CANNON = 80;

    public static final Dimension2D getDefaultCannonSize() {
        return new Dimension2D(DEFAULT_WIDTH_CANNON, DEFAULT_HEIGHT_CANNON);
    }

    public Cannon(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        // TODO: Set the values in the parent class for speed etc. like in Bumpers.
        setIconLocation(CANNON_IMAGE_FILE);
        setSize(new Dimension2D(DEFAULT_WIDTH_CANNON, DEFAULT_HEIGHT_CANNON));
    }

    public void moveHorizontally(double amount) {
        double oldX = getPosition().getX();

        // don't exceed max position to the left and right
        if ((amount < 0 && oldX <= 0) ||
                oldX + amount + getSize().getWidth() >= GameBoardUI.getPreferredSize().getWidth())
            return;

        // oldX + step moves the cannon horizontally; y point remains always the same
        setPosition(oldX + amount, getPosition().getY());
    }

    public void setup() {
        //setPosition(getSize().getWidth() / 2.0, getSize().getHeight() - 200);
    }

}
