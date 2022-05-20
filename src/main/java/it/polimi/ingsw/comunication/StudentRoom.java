package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

public class StudentRoom {
    public String[] students = new String[4];
    public StudentRoom(ArrayList<Student> studentsRoom){
        int i = 0;
        for (Student student: studentsRoom){
            students[i] = String.valueOf(student.getColor().getCode());
            i++;
        }
    }
}
