package it.polimi.ingsw.server.model;


/**
 * This enum class implements all the 5 color of the pawn: Cyan, Magenta, Yellow, Red, Green.
 */
public enum PawnColor {
    GREEN("\u001B[32m", 1,"green"),RED("\u001B[31m", 1,"red"), YELLOW("\u001B[33m", 1,"yellow"), MAGENTA("\u001B[35m",1,"magenta"),CYAN("\u001B[34m",1,"cyan")  ;

    /**
     * It is java color code of the pawn colors
     */
    private final String code;

    /**
     * It is the name of pawnColor
     */
    private final String name;

    /**
     * It is used when a player play the special card that doesn't consider the influence given by a selected pawnColor.
     * It's set at 1 by default
     */
    private int influenceMultiplier;

    /**
     * The total number of pawn colors.
     */
    public static final int numberOfColors = 5;

    PawnColor(String code, int influenceMultiplier, String name) {
        this.code = code;
        this.influenceMultiplier = 1;
        this.name = name;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public int getInfluenceMultiplier() {
        return influenceMultiplier;
    }

    public void setInfluenceMultiplier(int influenceMultiplier) {
        this.influenceMultiplier = influenceMultiplier;
    }

    public static int totalNumberOfPawnColors(){
        return PawnColor.values().length;
    }


}

