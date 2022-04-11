package it.polimi.ingsw.model;

public record AssistantCard(int order, int movesOfMN) {

    public int getOrder() {
        return order;
    }

    public int getMoveOfMN() {
        return movesOfMN;
    }
}
