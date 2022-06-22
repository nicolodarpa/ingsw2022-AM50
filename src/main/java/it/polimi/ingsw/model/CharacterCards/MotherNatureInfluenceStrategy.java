package it.polimi.ingsw.model.CharacterCards;


/**
 * Implements the special card that calculate the influence on an island even Mother Nature isn't on it. {@link SpecialCardStrategy}
 */
public class MotherNatureInfluenceStrategy extends SpecialCardStrategy {

    public MotherNatureInfluenceStrategy() {
        setCost(3);
        setEffectOfTheCard(" With this card you can chose an island and calculate the influence like when Mother Nature is on that island ");
        setName("ambassador");
    }


    /**
     * Calculates the influence on a selected island.
     * It increases the cost by 1 when the card is played.
     */
    public void effect() {
        islands.get(index).calcInfluence(playersList);
        addCost();
    }

}
