package it.polimi.ingsw.model;

public class Tower {
    private int id;
    private String color;
    private boolean positionInGame;


    public Tower(int id, String color) {
        this.id = id;
        this.color = color;
        this.positionInGame = true;
    }
}
