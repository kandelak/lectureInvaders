package GameEntity;

import Controller.Dimension2D;
import Controller.KeyPressed;
import Controller.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Cannon {
    protected static final int DEFAULT_SHOOT_ANGLE = 90;
    private static final Point2D DEFAULT_START_POSITION = new Point2D(0, 0);
    private static final double STEP_TO_MOVE_HORIZONTALLY = 1.0;
    Point2D position;
    /**
     * The direction as degree within a circle, a value between 0 (inclusive) and
     * 360 (exclusive).
     */
    private int shootAngle = DEFAULT_SHOOT_ANGLE;
    private LaserBolt laserBolt;

    public Cannon() {
        position = DEFAULT_START_POSITION;
        laserBolt = new LaserBolt(position);
    }

    public LaserBolt getLaserBolt() {
        return laserBolt;
    }

    void shoot(KeyPressed action, Dimension2D size) {
        if (action == KeyPressed.SHOOT) {
            laserBolt.move(size);
        }
    }


    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public void setShootAngle(int shootAngle) {
        this.shootAngle = shootAngle;
    }

    void moveHorizontally(KeyPressed action) {
        if (action == KeyPressed.LEFT) {
            setPosition(position.getX() - STEP_TO_MOVE_HORIZONTALLY, position.getY());
        } else if (action == KeyPressed.RIGHT) {
            setPosition(position.getX() + STEP_TO_MOVE_HORIZONTALLY, position.getY());
        }
    }
}
