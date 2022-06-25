package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Teachers test
 */
public class TeacherTest {

    private final Teacher cyan = new Teacher(PawnColor.CYAN);
    private final Teacher magenta = new Teacher(PawnColor.MAGENTA);
    private final Teacher yellow = new Teacher(PawnColor.YELLOW);
    private final Teacher red = new Teacher(PawnColor.RED);
    private final Teacher green = new Teacher(PawnColor.GREEN);

    @Test
    @DisplayName(" Draw the teachers")
    public void testDraw(){
        cyan.draw();
        magenta.draw();
        yellow.draw();
        red.draw();
        green.draw();
    }

    @Test
    @DisplayName(" Set the color ")
    public void testSetColor(){
        Teacher teacherTest = new Teacher(PawnColor.RED);
        teacherTest.setColor(PawnColor.CYAN);
        assertEquals(PawnColor.CYAN, teacherTest.getColor() );
    }
}
