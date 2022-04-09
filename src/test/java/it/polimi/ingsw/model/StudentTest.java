package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {

    private Student red = new Student(PawnColor.RED);
    private Student yellow = new Student(PawnColor.YELLOW);
    private Student cyan = new Student(PawnColor.CYAN);
    private Student green = new Student(PawnColor.GREEN);
    private Student magenta = new Student(PawnColor.MAGENTA);

    @Test
    @DisplayName(" Draw the student")
    public void testDraw(){
        red.draw();
        yellow.draw();
        cyan.draw();
        green.draw();
        magenta.draw();
    }
}
