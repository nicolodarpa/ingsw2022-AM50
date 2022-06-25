package it.polimi.ingsw.server.model;


/**
 * It contains the colors of the towers. White, black and grey.
 */
public enum TowerColor {
    white("white"),black("black"),grey("grey");

    /**
     * Is the name of tower color.
     */
    private final String name;

    TowerColor(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
