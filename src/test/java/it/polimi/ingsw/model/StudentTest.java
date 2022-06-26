package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link Student}
 */
public class StudentTest {

    private Student red = new Student(PawnColor.RED);
    private Student yellow = new Student(PawnColor.YELLOW);
    private Student cyan = new Student(PawnColor.CYAN);
    private Student green = new Student(PawnColor.GREEN);
    private Student magenta = new Student(PawnColor.MAGENTA);

    /**
     * Tests if the students are correctly draw.
     */
    @Test
    @DisplayName(" Draw the student")
    public void testDraw(){
        red.draw();
        yellow.draw();
        cyan.draw();
        green.draw();
        magenta.draw();
    }

    @Test
    void setColor() {
        Student student = new Student();
        student.setColor(PawnColor.YELLOW);
        assertEquals(student.getColor(),PawnColor.YELLOW);
    }

    @Test
    void getColor() {
        assertEquals(cyan.getColor(), PawnColor.CYAN);
    }
}
