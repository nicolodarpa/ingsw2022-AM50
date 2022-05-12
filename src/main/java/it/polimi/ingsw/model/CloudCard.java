package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class models the Cloud Cards
 */
public class CloudCard {

    private ArrayList<Student> students;
    private int capacity;


    /**
     * By the param numberOfPlayers, the constructor set the number of students(capacity) on the cloud cards (3 or 4)
     * @param numberOfPlayers
     */
    public CloudCard(int numberOfPlayers) {

        if (numberOfPlayers == 2) {
            capacity = 3;
            students = new ArrayList<>(capacity);
        } else if (numberOfPlayers == 3) {
            capacity = 4;
            students = new ArrayList<>(capacity);
        }

    }


    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * remove all the students that there are on the cloud card
     * @return all the students
     */
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> temp = new ArrayList<>(capacity);
        temp.addAll(students);
        students.clear();
        return temp;
    }


}