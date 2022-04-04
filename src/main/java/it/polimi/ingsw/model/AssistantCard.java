package it.polimi.ingsw.model;

public class AssistantCard {
    private int order;
    private int movesOfMN;
    private boolean hasPlayed;

    public AssistantCard(int order, int movesOfMN) {
        this.order = order;
        this.movesOfMN = movesOfMN;
        this.hasPlayed = false;
    }

    public int getOrder() {
        return order;
    }

    public int getMovesOfMN() {
        return movesOfMN;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }
}
