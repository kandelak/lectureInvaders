package Controller;

import GameEntity.Cannon;
import GameEntity.Character;
import View.GameBoardUI;

public class GameBoard {
    Character[] characters;
    Cannon[] cannons;

    KeyListener keyListener;
    GameBoardUI gameBoardUI;

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
}
