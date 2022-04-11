package it.polimi.ingsw.model;

public class Teacher extends Pawn {

    private PawnColor color;


    public void setColor(PawnColor color) {
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    }



    public Teacher(PawnColor color) {
        super();
        this.color = color;
    }

}
