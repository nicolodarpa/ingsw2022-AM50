package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

public class CloudCardStatus {


    public ArrayList<String> students = new ArrayList<>();

    public CloudCardStatus(CloudCard cloudCard){
        ArrayList<Student> cloudCardStudents = cloudCard.getStudents();
        for (Student student: cloudCardStudents){
            students.add(String.valueOf(student.getColor()));
        }

    }
}
