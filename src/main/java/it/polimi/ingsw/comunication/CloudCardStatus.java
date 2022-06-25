package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.CloudCard;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

/**
 * Template class to encode and decode json with the status of cloud cards
 */
public class CloudCardStatus {
    public int id;

    /**
     * Array List of the color codes of the students in the cloud card
     */
    public ArrayList<String> students = new ArrayList<>();


    /**
     * Array List of the color names of the students in the cloud card
     */
    public ArrayList<String> studentsColors = new ArrayList<>();

    /**
     * Gets the  students list  currently in the cloud card and the relative colors
     * @param cloudCard cloud card to get the value from
     */
    public CloudCardStatus(CloudCard cloudCard) {
        ArrayList<Student> cloudCardStudents = cloudCard.getStudents();
        id = cloudCard.getId();
        for (Student student : cloudCardStudents) {
            if (student != null) {
                studentsColors.add(student.getColor().name().toLowerCase());
                students.add(String.valueOf(student.getColor().getCode()));
            } else {
                studentsColors.add(null);
                students.add(null);
            }
        }
    }

}
