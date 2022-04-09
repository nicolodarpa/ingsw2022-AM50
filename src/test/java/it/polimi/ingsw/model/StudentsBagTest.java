package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentsBagTest {

    private StudentsBag studentsBagTest = new StudentsBag();
    private PawnColor studentColor = null;

    public int[] countStudentByColor(){
        int cyan = PawnColor.CYAN.ordinal();
        int magenta = PawnColor.MAGENTA.ordinal();
        int yellow = PawnColor.YELLOW.ordinal();
        int red = PawnColor.RED.ordinal();
        int green = PawnColor.GREEN.ordinal();
        int[] color = new int[5];
        for( Student s : studentsBagTest.getBag()){
            PawnColor pawnColor = s.getColor();
            if(pawnColor.equals(PawnColor.CYAN)){
                color[cyan]++;
            }
            else if(pawnColor.equals(PawnColor.MAGENTA)){
                color[magenta]++;
            }
            else if(pawnColor.equals(PawnColor.YELLOW)){
                color[yellow]++;
            }
            else if(pawnColor.equals(PawnColor.RED)){
                color[red]++;
            }
            else if(pawnColor.equals(PawnColor.GREEN)){
                color[green]++;
            }
        }
        return color;
    }

    @Test
    @DisplayName(" Fill the bag whit 120 students ")
    public void fillTheBagTest(){
        studentsBagTest.fillBag(120);
        assertEquals(120,studentsBagTest.getNum());
    }

    @Test
    @DisplayName(" Check that there are the same number of students of each color ")
    public void equalsColorNumberTest(){
        int i = 0;
        studentsBagTest.fillBag(120);
        assertEquals(24,countStudentByColor()[PawnColor.CYAN.ordinal()]);
        assertEquals(24,countStudentByColor()[PawnColor.RED.ordinal()]);
        assertEquals(24,countStudentByColor()[PawnColor.YELLOW.ordinal()]);
        assertEquals(24,countStudentByColor()[PawnColor.GREEN.ordinal()]);
        assertEquals(24,countStudentByColor()[PawnColor.MAGENTA.ordinal()]);

    }
}
