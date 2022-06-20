package it.polimi.ingsw.model.CharacterCards;

import java.util.Scanner;

public class SpecialInfluenceStrategy extends SpecialCardStrategy {



    /**
     * It's the character card's constructor. It sets the cost, the effect and the name of the card
     */
    public SpecialInfluenceStrategy() {
        setCost(3);
        setEffectOfTheCard("With this card you have to choose one color of the students which will not be considered for the influence.");
        setName("wizard");
    }


    /**
     * SpecialInfluenceStrategy strategy: It sets the Influence Multiplier to 0, in this way that pawnColor hasn't any influence on the island's influence calculation
     */
    @Override
    public void effect() {
        pawnColor.setInfluenceMultiplier(0);
        addCost();
    }


}
