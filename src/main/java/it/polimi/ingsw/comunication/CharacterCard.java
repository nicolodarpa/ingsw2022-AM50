package it.polimi.ingsw.comunication;

import it.polimi.ingsw.server.model.CharacterCards.SpecialCardStrategy;

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
     * @param specialCardStrategy character card to extract values from
     */

    public CharacterCard(SpecialCardStrategy specialCardStrategy) {
        this.effect = specialCardStrategy.getEffectOfTheCard();
        this.cost = specialCardStrategy.getCost();
        this.name = specialCardStrategy.getName();
    }
}
