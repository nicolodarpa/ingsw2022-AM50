package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Island {
    private int id;
    private boolean islandConquered;
    private int idGroup;
    private boolean towerStatus;
    private String towerColor;
    private ArrayList<Student> studentList = new ArrayList<>();
    private Player owner;


    public Island(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isIslandConquered() {
        return islandConquered;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public boolean isTowerStatus() {
        return towerStatus;
    }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public String getTowerColor() {
        return towerColor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIslandConquered(boolean islandConquered) {
        this.islandConquered = islandConquered;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public void setTowerStatus(boolean towerStatus) {
        this.towerStatus = towerStatus;
    }

    public void setTowerColor(String towerColor) {
        this.towerColor = towerColor;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int calcInfluence(Player player){
        return 1;
    }

    public void addStudent(Student s){
        studentList.add(s);
    }

    public int countStudentColor(){
        String color;
        for( Student s : studentList){
            s.getColor();
        }
        return 1;
    }
}

