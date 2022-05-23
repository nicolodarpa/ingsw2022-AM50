package it.polimi.ingsw.model;


/**
 * represent the pawn of the game: the students and the teachers
 */
public class Pawn {
    private PawnColor color;

    public static final String ANSI_RESET = "\u001B[0m";

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    /**
     * draw a pawn represented by "+++" , and it visualizes in terminal in the correspondence color
     */
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
