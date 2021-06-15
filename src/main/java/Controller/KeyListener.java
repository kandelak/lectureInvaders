package Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    private KeyPressed keyPressed;



    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            keyPressed = KeyPressed.RIGHT;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            keyPressed = KeyPressed.LEFT;
        else {
            keyPressed = KeyPressed.IGNORE;
        }
    }

    KeyPressed listen() {
        //The listen() method returns an integer value, according to the pressed key. Gameboard then evaluates this integers and performs the according action.
        return keyPressed;
    }
}
