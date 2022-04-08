package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.StudentsBag;

// NOO import junit.framework.TestCase;
// NOOO public class PlayerTest extends TestCase {

import org.junit.jupiter.api.Test;


public class PlayerTest {

    @Test
    public void testMoveStudentToHall(){
        Dashboard dTest = new Dashboard();
        StudentsBag s = new StudentsBag();
        Student student = new Student();
        s.fillBag(120);
        for(int i = 0; i < 7; i++){
            student = s.casualExtraction();
            dTest.addStudentToHall(student);
        }
        dTest.drawDashboard();
    }
}
