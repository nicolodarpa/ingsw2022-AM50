package it.polimi.ingsw.model;



public enum PawnColor {
    CYAN, MAGENTA, YELLOW, RED, GREEN;

    static int totalNumberOfPawnColors(){
        return PawnColor.values().length;
    }
}

