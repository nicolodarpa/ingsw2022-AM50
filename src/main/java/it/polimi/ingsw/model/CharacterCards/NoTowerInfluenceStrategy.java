package it.polimi.ingsw.model.CharacterCards;

/**
 * Towers aren't considered for the calculation of the influence on an island. {@link SpecialCardStrategy}
 * {@link it.polimi.ingsw.model.Island}
 */
public class NoTowerInfluenceStrategy extends SpecialCardStrategy {


    /**
     * It's the character card's constructor. It sets the cost, the effect and the name of the card
     */
    public NoTowerInfluenceStrategy() {
        setCost(3);
        setEffectOfTheCard(" With this card the towers have no influence on the island considered");
        setName("warrior");
    }

    /**
     * sets the tower multiplier on island to 0.
     * It increases the cost by 1 when the card is played.
     */
    @Override
    public void effect() {
        islands.get(index).setTowerMultiplier(0);
        addCost();
    }


}
