package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.CharacterCards.SpecialCard;

public class CharacterCard {
    public String effect;
    public int cost;
    public String name;

    public  CharacterCard(SpecialCard specialCard){
        this.effect = specialCard.getEffectOfTheCard();
        this.cost = specialCard.getCost();
        this.name = specialCard.getName();
    }
}
