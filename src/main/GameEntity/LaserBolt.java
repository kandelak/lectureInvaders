package main.GameEntity;

import main.Controller.Dimension2D;


public class LaserBolt extends Entity {
    private static final String LASER_BOLT_IMG = "LaserBolt.png";
    private static final double DEFAULT_WIDTH_BOLT = 10.0;
    private static final double DEFAULT_HEIGHT_BOLT = 24.5;

    public LaserBolt(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        setIconLocation(LASER_BOLT_IMG);

        setSize(new Dimension2D(DEFAULT_WIDTH_BOLT, DEFAULT_HEIGHT_BOLT));

    }

    public void moveVertically(double amount) {
        // oldY + amount moves the cannon vertically; x point remains always the same
        setPosition(getPosition().getX(), getPosition().getY() + amount);
    }
}
