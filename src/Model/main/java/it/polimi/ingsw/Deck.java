package it.polimi.ingsw;

public class Deck {
    private int id;
    private boolean hasChoosen;

    public Deck(int id, boolean hasChoosen) {
        this.id = id;
        this.hasChoosen = hasChoosen;
    }

    public int getId() {
        return id;
    }

    public boolean isHasChoosen() {
        return hasChoosen;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHasChoosen(boolean hasChoosen) {
        this.hasChoosen = hasChoosen;
    }
}
