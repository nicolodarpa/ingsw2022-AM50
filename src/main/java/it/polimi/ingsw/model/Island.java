package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Island {
    private int id;
    private boolean islandConquered;
    private int idGroup;
    private boolean towerStatus;
    private String towerColor;
    private ArrayList<Student> studentList;
    private Player owner;


    public Island(int id, boolean islandConquered, int idGroup, boolean towerStatus, String towerColor, ArrayList<Student> studentList, Player owner) {
        this.id = id;
        this.islandConquered = islandConquered;
        this.idGroup = idGroup;
        this.towerStatus = towerStatus;
        this.towerColor = towerColor;
        this.studentList = studentList;
        this.owner = owner;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public String getTowerColor() {
        return towerColor;
    }

    public int calcInfluence(Student s){
        return 1;
    }
}

