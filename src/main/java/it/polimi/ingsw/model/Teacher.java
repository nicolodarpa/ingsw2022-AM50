package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Map;

public class Teacher {
    private Color color;

    public Teacher(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void moveTeacherToDashboard(Dashboard d){
    }
}
