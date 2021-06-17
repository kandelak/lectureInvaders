package main.GameEntity;

import main.Controller.Dimension2D;
import main.View.GameBoardUI;

public class Alien extends Entity {
    private static final String ALIEN_IMAGE_FILE = "Alien.png";

    public Alien(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        setIconLocation(ALIEN_IMAGE_FILE);
    }

    public void moveVertically(double amount) {
        double oldY = getPosition().getY();

        // don't exceed max position to the top and bottom
        if (	(amount < 0 && oldY <= 0) ||
                oldY + amount + getSize().getHeight() >= GameBoardUI.getPreferredSize().getHeight())
            return;

        // oldY + amount moves the cannon vertically; x point remains always the same
        setPosition(getPosition().getX(), oldY + amount);
    }
}
