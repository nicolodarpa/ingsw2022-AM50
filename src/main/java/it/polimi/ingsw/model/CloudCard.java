package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CloudCard {

    private ArrayList<Student> students;
    private int capacity;

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

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> temp = new ArrayList<>(capacity);
        temp.addAll(students);
        students.clear();
        return temp;
    }


}