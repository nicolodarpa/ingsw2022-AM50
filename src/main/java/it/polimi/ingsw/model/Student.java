package it.polimi.ingsw.model;

public class Student {

    private String color;
    private int id;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[34m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    //private String[] colors = {"CYAN","MAGENTA","YELLOW","RED","GREEN" } ;


    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public Student() {
        this.color = "WHITE";
    }

    public Student(String color) {
        this.color = color;
    }

    public void draw() {
        String color = getColor();
        if (color == "CYAN") {
            System.out.print(ANSI_CYAN + "+++");
        } else if (color == "MAGENTA") {
            System.out.print(ANSI_MAGENTA + "+++");
        } else if (color == "YELLOW") {
            System.out.print(ANSI_YELLOW + "+++");
        } else if (color == "RED") {
            System.out.print(ANSI_RED + "+++");
        } else if (color == "GREEN") {
            System.out.print(ANSI_GREEN + "+++");
        }
        System.out.print(ANSI_RESET);


    }
}
