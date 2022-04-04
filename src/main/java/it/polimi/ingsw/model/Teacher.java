package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Teacher {
    private String color;


    public Teacher(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void moveTeacherToDashboard(Teacher t, Dashboard d){
        d.addTeacher(t);
    }
}
