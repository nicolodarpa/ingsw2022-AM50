package it.polimi.ingsw;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.StudentsBag;
import junit.framework.TestCase;

public class DashboardTest extends TestCase {

    Dashboard testD = new Dashboard();

    public void testAddStudentToHall() {

        StudentsBag studentsBag = new StudentsBag();

        studentsBag.fillBag(120);
        for (int i = 0; i < 7; i++) {
            Student student = studentsBag.casualExtraction();
            System.out.print(i + ": ");
            student.draw();
            System.out.println("");
            testD.addStudentToHall(student);
        }
        testD.drawDashboard();
    }

    public void testAddStudentToClassroom() {
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        for (int i = 0; i < 7; i++) {
            testD.addStudentToHall(studentsBag.casualExtraction());
        }
        testD.addStudentToClassroom(testD.getStudentFromHall(3));
        testD.addStudentToClassroom(testD.getStudentFromHall(4));
        testD.addStudentToClassroom(testD.getStudentFromHall(1));
        testD.drawDashboard();

    }

    public void testFillClassroom(){
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        for (int i = 0; i < 120; i++) {
            testD.addStudentToClassroom(studentsBag.casualExtraction());
        }
        testD.drawDashboard();
    }


    public void testDrawDashboard() {
    }
}