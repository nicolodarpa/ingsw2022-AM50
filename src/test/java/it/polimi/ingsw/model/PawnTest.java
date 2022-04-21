package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {
    Pawn pawnTest = new Pawn();
    @Test
    void getColor() {
        assertNull(pawnTest.getColor());
    }

    @Test
    void setColor() {
        pawnTest.setColor(PawnColor.CYAN);
        assertEquals(pawnTest.getColor(),PawnColor.CYAN);
    }

}