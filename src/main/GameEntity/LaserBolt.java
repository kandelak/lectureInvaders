package main.GameEntity;

import main.Controller.Dimension2D;

public class LaserBolt  extends Entity {
    private static final String LASER_BOLT_IMG = "LaserBolt.png";

    public LaserBolt(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        setIconLocation(LASER_BOLT_IMG);
        setSize(new Dimension2D(5.0,12));
    }

    public void moveVertically(double amount) {
        // oldY + amount moves the cannon vertically; x point remains always the same
        setPosition(getPosition().getX(), getPosition().getY() + amount);
    }
}
