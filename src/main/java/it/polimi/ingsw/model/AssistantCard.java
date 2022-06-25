package it.polimi.ingsw.model;

/**
 * Represent the assistant card in game.
 * @param order is the order of game given by each assistant card.
 * @param movesOfMN is the moves of mn allowed given by each assistant card.
 */
public record AssistantCard(int order, int movesOfMN) {

    /**
     * Returns the order of the card
     * @return order of the card
     */
    public int getOrder() {
        return order;
    }

    /**
     * Returns the moves of mother nature the card provides
     * @return moves of mother nature
     */
    public int getMoveOfMN() {
        return movesOfMN;
    }
}
