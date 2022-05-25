package it.polimi.ingsw.model;


/**
 * It contains the colors of the towers. White, black and grey.
 */
public enum TowerColor {
    white("white"),black("black"),grey("grey");

    private final String name;

    TowerColor(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
