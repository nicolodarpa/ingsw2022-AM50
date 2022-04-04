package it.polimi.ingsw.model;

public class Student {

    private String color = "WHITE";
    private  int id;
    public static final String ANSI_RESET = "\\u001B[0m\t";
    public static final String ANSI_CYAN = "\\u001B[37m\t";
    public static final String ANSI_MAGENTA = "\\u001B[35m";
    public static final String ANSI_YELLOW = "\\u001B[33m";
    public static final String ANSI_RED = "\\u001B[31m";
    public static final String ANSI_GREEN = "\\u001B[32m";

    //private String[] colors = {"CYAN","MAGENTA","YELLOW","RED","GREEN" } ;


    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public Student(){
        this.color = "WHITE";
    }
    public Student(String color){
        this.color = color;
    }

    public void draw(){
        switch (getColor()){
            case "CYAN":
                System.out.print(ANSI_CYAN + "Student" + ANSI_RESET);
            case "MAGENTA":
                System.out.print(ANSI_MAGENTA + "Student" + ANSI_RESET);
            case "YELLOW":
                System.out.print(ANSI_YELLOW + "Student" + ANSI_RESET);
            case "RED":
                System.out.print(ANSI_RED + "Student" + ANSI_RESET);
            case "GREEN":
                System.out.print(ANSI_GREEN + "Student" + ANSI_RESET);
            case "WHITE":
                System.out.print("Empty");
        }


    }
}
