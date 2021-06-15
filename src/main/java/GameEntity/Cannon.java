package GameEntity;

import Controller.KeyPressed;
import Controller.Point2D;

public class Cannon {

    Point2D position;
    /**
     * The direction as degree within a circle, a value between 0 (inclusive) and
     * 360 (exclusive).
     */
    private int direction;

    void shoot() {

    }

    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    void moveHorizontally(double step, KeyPressed direction) {
        if (direction == KeyPressed.LEFT) {
            setPosition(position.getX() - step, position.getY());
        } else {
            setPosition(position.getX() + step, position.getY());
        }
    }
}