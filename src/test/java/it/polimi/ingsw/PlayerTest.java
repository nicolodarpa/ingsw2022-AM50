package it.polimi.ingsw;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.StudentsBag;
import junit.framework.TestCase;
import it.polimi.ingsw.model.Player;

public class PlayerTest extends TestCase {

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
