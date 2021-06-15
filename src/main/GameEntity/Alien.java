package main.GameEntity;

import main.Controller.Dimension2D;

public class Alien extends Entity {

    private static final String ALIEN_IMAGE_FILE = "Alien.png";

    public Alien(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        // TODO: Set the values in the parent class for speed etc. like in Bumpers.
        setIconLocation(ALIEN_IMAGE_FILE);
    }


    public void moveDown(double step, Dimension2D size) {

        //The subclasses of Entity should not have a position attribute

        //this.position = new Point2D(position.getX(), position.getY() + step);
    }
}
