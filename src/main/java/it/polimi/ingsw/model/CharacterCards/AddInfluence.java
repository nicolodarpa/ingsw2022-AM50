package it.polimi.ingsw.model.CharacterCards;


public class AddInfluence extends SpecialCard {


    public AddInfluence() {
        setCost(2);
        setEffectOfTheCard(" With this card you have 2 more points in the calculation of the influence. ");
    }


    public void effect() {
        actualPlayer.setInfluencePoint(2);
        addCost();
    }

}
