package it.polimi.ingsw.server.model;

/**
 * represent the towers in the game
 */
public class Tower {
    /**
     * The color of the tower.
     */
    private TowerColor color;

    /**
     * The color code of white
     */
    private static final String ANSI_WHITE = "\u001B[37m";

    /**
     * The color code of black
     */
    private static final String ANSI_BLACK = "\u001B[30m";

    /**
     * The color code of grey
     */
    private static   final String ANSI_RESET = "\u001B[0m";

    public Tower(TowerColor color) {
        this.color = color;
    }

    public void setColor(TowerColor color) {
        this.color = color;
    }

    public TowerColor getColor() {
        return color;
    }

    /**
     * Draws the tower, it represents it by "||" and its color.
     */
    public void draw() {
        TowerColor color = getColor();
        String print = null;
        if (color == TowerColor.white)
            print = ANSI_WHITE + "||";
        else if (color == TowerColor.black)
            print = ANSI_BLACK + "||";
        else if (color == TowerColor.grey)
            print = "||";
        System.out.print(print);
        System.out.print(ANSI_RESET);

    }
}
