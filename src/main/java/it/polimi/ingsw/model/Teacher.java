package it.polimi.ingsw.model;

public class Teacher {
    private ColorPawn color;

    public Teacher(ColorPawn color) {
        this.color = color;
    }

    public ColorPawn getColor() {
        return color;
    }

    public void setColor(ColorPawn color) {
        this.color = color;
    }

    public void moveTeacherToDashboard(Dashboard d){
    }
}
