package tests;

import main.Controller.Dimension2D;
import main.GameEntity.Alien;
import main.GameEntity.TestInterface;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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

    @Test
    public void test() {
        //TODO: Write 9 Test cases!


    }
}
