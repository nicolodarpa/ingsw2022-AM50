package it.polimi.ingsw.model;


/**
 * This enum class implements all the 5 color of the pawn: Cyan, Magenta, Yellow, Red, Green.
 */
public enum PawnColor {
    CYAN("\u001B[34m",1), MAGENTA("\u001B[35m",1), YELLOW("\u001B[33m", 1), RED("\u001B[31m", 1), GREEN("\u001B[32m", 1);

    private final String code;

    private int influenceMultiplier;

    public static final int numberOfColors = 5;

    PawnColor(String code, int influenceMultiplier) {
        this.code = code;
        this.influenceMultiplier = 1;
    }

    public String getCode(){
        return code;
    }

    public int getInfluenceMultiplier() {
        return influenceMultiplier;
    }

    public void setInfluenceMultiplier(int influenceMultiplier) {
        this.influenceMultiplier = influenceMultiplier;
    }

    public int totalNumberOfPawnColors(){
        return PawnColor.values().length;
    }



}

