package it.polimi.ingsw.model;



public enum PawnColor {
    CYAN("\u001B[34m"), MAGENTA("\u001B[35m"), YELLOW("\u001B[33m"), RED("\u001B[31m"), GREEN("\u001B[32m");

    private final String code;

    PawnColor(String code) {
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    static int totalNumberOfPawnColors(){
        return PawnColor.values().length;
    }



}

