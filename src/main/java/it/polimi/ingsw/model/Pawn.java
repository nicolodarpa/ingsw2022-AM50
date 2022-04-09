package it.polimi.ingsw.model;

/**
 * NOT IMPLEMENTED YET
 */

public class Pawn {
    private PawnColor color;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[34m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";


    public Pawn(PawnColor color){
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }



}
