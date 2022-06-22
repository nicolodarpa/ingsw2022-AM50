package it.polimi.ingsw.model.CharacterCards;

/**
 * Implements the special card that allowed to don't considered the influence given by tower in the calculation of influence. {@link SpecialCardStrategy}
 * {@link it.polimi.ingsw.model.Island}
 */
public class NoTowerInfluenceStrategy extends SpecialCardStrategy {


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
