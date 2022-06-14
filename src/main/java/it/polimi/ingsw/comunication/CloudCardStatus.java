package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

public class CloudCardStatus {


    public ArrayList<String> students = new ArrayList<>();
    public ArrayList<Integer> ordinalColorOfStudents = new ArrayList<>();

    public CloudCardStatus(CloudCard cloudCard) {
        ArrayList<Student> cloudCardStudents = cloudCard.getStudents();
        for (Student student : cloudCardStudents) {
            if (student != null) {
                ordinalColorOfStudents.add(student.getColor().ordinal());
                students.add(String.valueOf(student.getColor().getCode()));
            } else {
                ordinalColorOfStudents.add(null);
                students.add(null);
            }
        }
    }

}
