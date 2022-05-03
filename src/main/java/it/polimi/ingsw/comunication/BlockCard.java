package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.CharacterCards.SpecialCard;

public class BlockCard extends SpecialCard {

    public BlockCard(){
        setCost(2);
        setEffectOfTheCard("Select an island to block");
        setName("princes");
    }

    @Override
    public void effect() {
        //TODO add effect
        addCost();
    }
}
