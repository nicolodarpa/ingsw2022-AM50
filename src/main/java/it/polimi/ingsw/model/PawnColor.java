package it.polimi.ingsw.model;



public enum PawnColor {
    CYAN("\u001B[34m"), MAGENTA("\u001B[35m"), YELLOW("\u001B[33m"), RED("\u001B[31m"), GREEN("\u001B[32m");

    private final String code;

    public static final int numberOfColors = 5;

    PawnColor(String code) {
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public int totalNumberOfPawnColors(){
        return PawnColor.values().length;
    }



}

