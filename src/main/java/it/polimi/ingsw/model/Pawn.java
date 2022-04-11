package it.polimi.ingsw.model;



public class Pawn {
    private PawnColor color;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[34m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";



    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }


    public void draw() {
        PawnColor color = getColor();
        if (color == PawnColor.CYAN) {
            System.out.print(ANSI_CYAN + "+++");
        } else if (color == PawnColor.MAGENTA) {
            System.out.print(ANSI_MAGENTA + "+++");
        } else if (color == PawnColor.YELLOW) {
            System.out.print(ANSI_YELLOW + "+++");
        } else if (color == PawnColor.RED) {
            System.out.print(ANSI_RED + "+++");
        } else if (color == PawnColor.GREEN) {
            System.out.print(ANSI_GREEN + "+++");
        }
        System.out.print(ANSI_RESET);

    }



}
