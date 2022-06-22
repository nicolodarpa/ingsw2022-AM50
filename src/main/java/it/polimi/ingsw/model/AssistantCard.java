package it.polimi.ingsw.model;

/**
 * Represent the assistant card in game.
 * @param order is the order of game given by each assistant card.
 * @param movesOfMN is the moves of mn allowed given by each assistant card.
 */
public record AssistantCard(int order, int movesOfMN) {

    public int getOrder() {
        return order;
    }

    public int getMoveOfMN() {
        return movesOfMN;
    }
}
