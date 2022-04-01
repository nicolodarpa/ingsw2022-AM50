package it.polimi.ingsw;

import java.util.ArrayList;

public class CloudCard {

    private ArrayList<Student> students;

    public CloudCard(int numberOfPlayers) {
        if (numberOfPlayers == 2) {
            students = new ArrayList<>(3);
        } else if (numberOfPlayers == 3) {
            students = new ArrayList<>(4);
        }

    }


    public void addStudent(Student student) {
        students.add(student);

    }

}