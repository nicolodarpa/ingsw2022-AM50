package it.polimi.ingsw.comunication;

import it.polimi.ingsw.server.model.CharacterCards.CharacterCardStrategy;

/**
 * Template class to encode and decode json with the values of character cards
 */
public class CharacterCard {
    /**
     * Effect of the card
     */
    public String effect;
    /**
     * Cost of the card
     */
    public int cost;

    /**
     * Name of the card
     */
    public String name;

    /**
     * Populates every field
     *
     * @param characterCardStrategy character card to extract values from
     */

    public CharacterCard(CharacterCardStrategy characterCardStrategy) {
        this.effect = characterCardStrategy.getEffectOfTheCard();
        this.cost = characterCardStrategy.getCost();
        this.name = characterCardStrategy.getName();
    }
}
