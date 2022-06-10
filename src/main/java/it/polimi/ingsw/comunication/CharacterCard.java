package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.CharacterCards.SpecialCardStrategy;

public class CharacterCard {
    public String effect;
    public int cost;
    public String name;

    public  CharacterCard(SpecialCardStrategy specialCardStrategy){
        this.effect = specialCardStrategy.getEffectOfTheCard();
        this.cost = specialCardStrategy.getCost();
        this.name = specialCardStrategy.getName();
    }
}
