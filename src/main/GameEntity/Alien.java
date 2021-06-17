package main.GameEntity;

import main.Controller.Dimension2D;
import main.View.GameBoardUI;

public class Alien extends Entity {
    private static final String ALIEN_IMAGE_FILE = "Alien.png";

    private static final double DEFAULT_WIDTH_ALIEN = 55.0;
    private static final double DEFAULT_HEIGHT_ALIEN = 30.0;


    public Alien(Dimension2D gameBoardSize) {
        super(gameBoardSize);
        setIconLocation(ALIEN_IMAGE_FILE);

        setSize(new Dimension2D(DEFAULT_WIDTH_ALIEN, DEFAULT_HEIGHT_ALIEN));
        // Manually updates the start position of the alien
        setStartPositionAlien(gameBoardSize);

    }

    public void moveVertically(double amount) {
        double oldY = getPosition().getY();

        // don't exceed max position to the top and bottom

        if ((amount < 0 && oldY <= -40) ||
                oldY + amount + getSize().getHeight() - 40 >= GameBoardUI.getPreferredSize().getHeight())
            return;

        // oldY + amount moves the cannon vertically; x point remains always the same
        setPosition(getPosition().getX(), oldY + amount);
    }
}
