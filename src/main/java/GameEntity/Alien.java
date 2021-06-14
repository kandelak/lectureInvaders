package GameEntity;

import Controller.Point2D;

public class Alien {
    Point2D position;

    public void setPosition(double x, double y) {
        this.position = new Point2D(x, y);
    }

    public void moveDown(double step) {
        this.position = new Point2D(position.getX(), position.getY() - step);
    }
}
