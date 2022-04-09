package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class TowerTest {

    Tower t = new Tower(TowerColor.white);

    @Test
    @DisplayName(" Draw the Tower")
    public void drawTest() {
        t.draw();
    }

}