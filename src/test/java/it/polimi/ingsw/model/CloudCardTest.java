package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.CloudCard;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for {@link CloudCard}
 */
class CloudCardTest {
    CloudCard testCloud = new CloudCard(2,1);


    /**
     * Tests if students are correctly added to a cloud card
     */
    @Test
    void addStudent() {
        for (int i = 0; i<3;i++){
            Student student = new Student(PawnColor.RED);
            testCloud.addStudent(student);
        }
        assertEquals(testCloud.getStudents().get(0).getColor(),PawnColor.RED);
        assertEquals(3,testCloud.getStudents().size());
    }


    @Test
    @DisplayName("Test get student")
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

    /**
     *Test if a cloud card is empty after getting all its students
     */
    @Test
    @DisplayName("Test get and empty cloud card")
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