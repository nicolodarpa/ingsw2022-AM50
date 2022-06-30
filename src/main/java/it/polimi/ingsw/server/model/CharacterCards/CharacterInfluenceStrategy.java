package it.polimi.ingsw.server.model.CharacterCards;


import it.polimi.ingsw.server.model.PawnColor;

/**
 * Implements the special card that allows to don't consider a selected pawn color on a calculation of influence.
 */
public class CharacterInfluenceStrategy extends CharacterCardStrategy {



    /**
     * It's the character card's constructor. It sets the cost, the effect and the name of the card
     * {@link CharacterCardStrategy}
     * {@link PawnColor}
     */
    public CharacterInfluenceStrategy() {
        setCost(3);
        setEffectOfTheCard("With this card you have to choose one color of the students which will not be considered for the influence. (Select the PawnColor before playing this card)");
        setName("wizard");
    }


    /**
     * CharacterInfluenceStrategy strategy: It sets the Influence Multiplier to 0, in this way that pawnColor hasn't any influence on the island's influence calculation
     */
    @Override
    public void effect() {
        pawnColor.setInfluenceMultiplier(0);
        addCost();
    }


}
