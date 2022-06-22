package it.polimi.ingsw.model.CharacterCards;


public class AddInfluenceStrategy extends SpecialCardStrategy {


    public AddInfluenceStrategy() {
        setCost(2);
        setEffectOfTheCard(" With this card you have 2 more points in the calculation of the influence. ");
        setName("knight");
    }


    public void effect() {
        currentPlayer.setInfluencePoint(2);
        addCost();
    }

}
