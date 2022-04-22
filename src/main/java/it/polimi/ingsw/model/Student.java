package it.polimi.ingsw.model;

public class Student extends Pawn {

    private PawnColor color;


    public void setColor(PawnColor color) {
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    }


    public Student() {
        this.color = null;
    }

    public Student(PawnColor color) {
        this.color = color;
    }

}
