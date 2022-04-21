package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CloudCardTest {
    CloudCard testCloud = new CloudCard(2);




    @Test
    void addStudent() {
        for (int i = 0; i<3;i++){
            Student student = new Student();
            testCloud.addStudent(student);
        }
        assertEquals(testCloud.getStudents().get(0).getColor(), PawnColor.WHITE);


    }

    @Test
    void getStudents() {
        for (int i = 0; i<3;i++){
            Student student = new Student();
            testCloud.addStudent(student);
        }
        ArrayList<Student> temp = testCloud.getStudents();
        for (int i = 0; i<3; i++){
           temp.get(i).draw();
        }
        assertNotNull(testCloud.getStudents().get(0));
    }

    @Test
    void getAllStudents() {
        for (int i = 0; i<3;i++){
            Student student = new Student();
            testCloud.addStudent(student);
        }
        ArrayList<Student> temp = testCloud.getAllStudents();
        assertEquals(testCloud.getStudents().size(),0);
        assertEquals(temp.size(),3);
        for (Student student: temp){
            student.draw();
        }
    }
}