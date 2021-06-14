package Controller;

import GameEntity.Alien;
import GameEntity.Cannon;
import GameEntity.Character;
import View.GameBoardUI;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    Character[] characters;
    Cannon[] cannons;
    private static final int NUMBER_OF_ALIENS = 15;
    KeyListener keyListener;
    GameBoardUI gameBoardUI;
    private final Dimension2D size;
    List<Alien> aliens = new ArrayList<>();

    public GameBoard(Dimension2D size) {
        this.size = size;
        createAliens();
    }

    private void createAliens() {
        for(int i = 0;i<NUMBER_OF_ALIENS;i++){
            aliens.add(new Alien());
        }
    }

    void start() {

    }

    void stop() {

    }

    void isSynchronized() {

    }

    void move() {

    }

    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }

    public Cannon[] getCannons() {
        return cannons;
    }

    public void setCannons(Cannon[] cannons) {
        this.cannons = cannons;
    }

    public Dimension2D getSize() {
        return size;
    }
}
