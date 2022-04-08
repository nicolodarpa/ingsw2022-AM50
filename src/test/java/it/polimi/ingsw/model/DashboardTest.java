package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.StudentsBag;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;


public class DashboardTest{

        Dashboard testD = new Dashboard();
        @Test
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
        @Test
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
        @Test
        public void testFillClassroom(){
            StudentsBag studentsBag = new StudentsBag();
            studentsBag.fillBag(120);
            for (int i = 0; i < 120; i++) {
                testD.addStudentToClassroom(studentsBag.casualExtraction());
            }
            testD.drawDashboard();
        }

        @Test
        @Disabled("Not implemented yet")
        public void testDrawDashboard() {
        }
}
