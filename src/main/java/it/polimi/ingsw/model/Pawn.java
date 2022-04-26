package it.polimi.ingsw.model;


public class Pawn {
    private PawnColor color;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";


    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }

    public void draw() {
        PawnColor color = getColor();
        if (color == null) {
            System.out.print("^^^");
        } else if (color == PawnColor.CYAN) {
            System.out.print(CYAN + "+++");
        } else if (color == PawnColor.MAGENTA) {
            System.out.print(MAGENTA + "+++");
        } else if (color == PawnColor.YELLOW) {
            System.out.print(YELLOW + "+++");
        } else if (color == PawnColor.RED) {
            System.out.print(RED + "+++");
        } else if (color == PawnColor.GREEN) {
            System.out.print(GREEN + "+++");
        }
        System.out.print(ANSI_RESET);

    }


}
