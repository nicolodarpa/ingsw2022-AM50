package it.polimi.ingsw.server.model.CharacterCards;


/**
 * Calculate the influence on an island like mother nature stopped on it. {@link CharacterCardStrategy}
 */
public class MotherNatureInfluenceStrategy extends CharacterCardStrategy {


    /**
     * It's the character card's constructor. It sets the cost, the effect and the name of the card
     */
    public MotherNatureInfluenceStrategy() {
        setCost(3);
        setEffectOfTheCard(" With this card you can chose an island and calculate the influence like when Mother Nature is on that island. (Select the island before playing this card)");
        setName("ambassador");
    }


    /**
     * Calculates the influence on the selected island.
     * It increases the cost by 1 when the card is played.
     */
    public void effect() {
        islands.get(index).calculateInfluence(playersList);
        addCost();
    }

}
