package it.polimi.ingsw.model;

public class Tower {
    private int id;
    private TowerColor color;
    private boolean positionInGame;


    public Tower(int id, TowerColor color) {
        this.id = id;
        this.color = color;
        this.positionInGame = true;
    }
}
