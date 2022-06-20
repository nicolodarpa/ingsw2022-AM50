package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TowerTest {

    Tower t = new Tower(TowerColor.white);

    @Test
    @DisplayName(" Draw the Tower")
    public void drawTest() {
        t.draw();
    }

    @Test
    @DisplayName(" Get the name of the tower's color")
    public void testGetColorName(){
        assertEquals(t.getColor().getName(), "white");
    }
}