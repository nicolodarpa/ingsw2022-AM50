package it.polimi.ingsw.model;


public class Pawn {
    private PawnColor color;

    public static final String ANSI_RESET = "\u001B[0m";

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    public void draw() {
        PawnColor color = getColor();
        if(color == null)
            System.out.print("^^^");
        else{
            System.out.print(color.getCode() + "+++");
        }
        System.out.print(ANSI_RESET);
    }


}
