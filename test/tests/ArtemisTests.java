package tests;

import main.Controller.Collisions.AlienLaserCollisionImpl;
import main.Controller.Dimension2D;
import main.Controller.GameBoard;
import main.Controller.Point2D;
import main.GameEntity.*;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static main.View.GameBoardUI.getPreferredSize;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
public class ArtemisTests {

    @Mock
    TestInterface testMock;

    @TestSubject
    Alien alien = new Alien(new Dimension2D(1000, 1000));

    @org.junit.jupiter.api.Test
    public void testMockObject() {

        alien.setSpeed(3.0);
        // The method t1 should check, whether the actual speed can be incremented or not
        expect(testMock.testSpeed(alien.getSpeed(), 8)).andReturn(true);
        replay(testMock);

        alien.incSpeed(testMock);

        assertEquals(4.0, alien.getSpeed());
    }

    /**
     * Tests if the Cannon horizontal movement to the right is correctly implemented
     */
    @Test
    public void testCannonMoveRight() {
        Cannon testCannon = new Cannon(Cannon.getDefaultCannonSize());
        Point2D actPos = testCannon.getPosition();
        testCannon.moveHorizontally(2.0);
        Assertions.assertEquals(testCannon.getPosition().getX(), actPos.getX() + 2.0);
        Assertions.assertEquals(testCannon.getPosition().getY(), actPos.getY());
    }

    /**
     * Tests if Cannon doesn't move if it is positioned at the edge of the Window
     */
    @Test
    public void testCannonMoveLeftIgnore() {
        Cannon testCannon = new Cannon(Cannon.getDefaultCannonSize());
        Point2D actPos = testCannon.getPosition();
        testCannon.moveHorizontally(-2.0);
        Assertions.assertEquals(testCannon.getPosition().getX(), actPos.getX());
        Assertions.assertEquals(testCannon.getPosition().getY(), actPos.getY());
    }

    /**
     * Tests if the Cannon horizontal movement to the left is correctly implemented
     */
    @Test
    public void testCannonMoveLeft() {
        Cannon testCannon = new Cannon(Cannon.getDefaultCannonSize());
        Dimension2D gameBoardSize = getPreferredSize();
        testCannon.setPosition(gameBoardSize.getWidth() / 2.0, gameBoardSize.getHeight() - 200);
        Point2D actPos = testCannon.getPosition();
        testCannon.moveHorizontally(-2.0);
        Assertions.assertEquals(testCannon.getPosition().getX(), actPos.getX() - 2.0);
        Assertions.assertEquals(testCannon.getPosition().getY(), actPos.getY());
    }

    /**
     * Tests if the Invalid Arguments for the Gameboard are handled correctly
     */
    @Test
    public void illegalArgumentForGameBoard() {
        Dimension2D illegalDim = new Dimension2D(-43, -50);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new GameBoard(illegalDim));
    }


    /**
     * Tests LaserBolt Movement
     */
    @Test
    public void LaserBoltMove() {
        Dimension2D gameBoardSize = getPreferredSize();
        LaserBolt laserBolt = new LaserBolt(gameBoardSize);

        laserBolt.setPosition(gameBoardSize.getWidth() / 2.0, gameBoardSize.getHeight() - 200);
        Point2D actPos = laserBolt.getPosition();
        laserBolt.moveVertically(2.0);
        Assertions.assertEquals(laserBolt.getPosition().getY(), actPos.getY() + 2.0);

    }

    /**
     * Tests if Player life points are decremented correctly
     */
    @Test
    public void playerLifePoints() {
        Player player = new Player(new Cannon(getPreferredSize()));
        int oldLife = player.getPlayerLifePoints();
        player.decrementLifePoints();
        assertEquals(player.getPlayerLifePoints(), oldLife - 1);
    }

    /**
     * Tests if the Player Always will instantiated with a Cannon
     */
    @Test
    public void playerHasCannon() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Player(null));
    }

    /**
     * Tests correct Evaluation of the Collision
     */
    @Test
    public void noCollision() {
        Alien alien = new Alien(getPreferredSize());
        LaserBolt laserBolt = new LaserBolt(getPreferredSize());
        //Place below the Alien so no collision occurs
        laserBolt.setPosition(new Point2D(alien.getPosition().getX(),
                alien.getPosition().getY() - laserBolt.getSize().getHeight() - 0.01));
        AlienLaserCollisionImpl alienLaserCollision = new AlienLaserCollisionImpl();
        Assertions.assertFalse(alienLaserCollision.detectCollision(laserBolt, alien));
    }

    /**
     * Tests correct Evaluation of the Collision
     */
    @Test
    public void yesCollision() {
        Alien alien = new Alien(getPreferredSize());
        LaserBolt laserBolt = new LaserBolt(getPreferredSize());
        //the pick of the laserBolt is exactly on the bottom line of the Alien
        laserBolt.setPosition(new Point2D(alien.getPosition().getX(),
                alien.getPosition().getY() - laserBolt.getSize().getHeight()));
        AlienLaserCollisionImpl alienLaserCollision = new AlienLaserCollisionImpl();
        Assertions.assertTrue(alienLaserCollision.detectCollision(laserBolt, alien));
    }


}
