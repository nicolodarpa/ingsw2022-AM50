package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.StudentsBag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveStudentTest {


    @Test
    void effect() {
        Dashboard dashboardTest = new Dashboard();
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(0);
        for (int i = 0; i<3;i++){
            dashboardTest.addStudentToClassroom(new Student(PawnColor.GREEN));
        }
        assertEquals(3,dashboardTest.countStudentByColor(PawnColor.GREEN));
        RemoveStudent removeStudent = new RemoveStudent();
        removeStudent.setPawnColor(PawnColor.GREEN);
        removeStudent.setBag(studentsBag);
        removeStudent.removeStudent(dashboardTest);
        assertEquals(0,dashboardTest.countStudentByColor(PawnColor.GREEN));
        assertEquals(3,studentsBag.getNum());
    }
}