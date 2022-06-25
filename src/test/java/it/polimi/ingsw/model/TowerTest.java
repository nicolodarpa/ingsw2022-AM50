package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tower test
 */
public class TowerTest {

    Tower t = new Tower(TowerColor.white);


    /**
     * Tests if a tower is correctly drawn
     */
    @Test
    @DisplayName(" Draw the Tower")
    public void drawTest() {
        t.draw();
    }

    /**
     * Gets the color of the tower
     */
    @Test
    @DisplayName(" Get the name of the tower's color")
    public void testGetColorName(){
        assertEquals(t.getColor().getName(), "white");
    }
}