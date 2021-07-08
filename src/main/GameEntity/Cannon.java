package main.GameEntity;

import main.Controller.Dimension2D;
import main.View.GameBoardUI;

public class Cannon extends Entity {

    private static final String CANNON_IMAGE_FILE = "Cannon.png";
    private static final double DEFAULT_WIDTH_CANNON = 110.0;
    private static final double DEFAULT_HEIGHT_CANNON = 80;
    private final static double DEFAULT_CANNON_STEP = 14.0;

    public static final Dimension2D getDefaultCannonSize() {
        return new Dimension2D(DEFAULT_WIDTH_CANNON, DEFAULT_HEIGHT_CANNON);
    }

    public Cannon(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        // TODO: Set the values in the parent class for speed etc. like in Bumpers.
        setIconLocation(CANNON_IMAGE_FILE);
        setSize(new Dimension2D(DEFAULT_WIDTH_CANNON, DEFAULT_HEIGHT_CANNON));
    }

    public void moveHorizontally(boolean left) {
        double oldX = getPosition().getX();

        // don't exceed max position to the left and right
        if ((left && oldX <= 0) ||
                oldX + DEFAULT_CANNON_STEP + getSize().getWidth() >= GameBoardUI.getPreferredSize().getWidth())
            return;

        // oldX + step moves the cannon horizontally; y point remains always the same
        setPosition(left ? oldX - DEFAULT_CANNON_STEP : oldX + DEFAULT_CANNON_STEP, getPosition().getY());
    }

    public void setup() {
        //setPosition(getSize().getWidth() / 2.0, getSize().getHeight() - 200);
    }

}
