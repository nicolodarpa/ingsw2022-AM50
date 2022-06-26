package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.Pawn;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for {@link Pawn}
 */
class PawnTest {
    Pawn pawnTest = new Student();
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