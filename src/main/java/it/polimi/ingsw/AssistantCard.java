package it.polimi.ingsw;

public class AssistantCard {
    private int order;
    private int movesOfMN;

    public AssistantCard(int order, int movesOfMN) {
        this.order = order;
        this.movesOfMN = movesOfMN;
    }

    public int getOrder() {
        return order;
    }

    public int getMovesOfMN() {
        return movesOfMN;
    }
}
