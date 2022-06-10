package it.polimi.ingsw.model.CharacterCards;

public class NoTowerInfluenceStrategy extends SpecialCardStrategy {


    public NoTowerInfluenceStrategy() {
        setCost(3);
        setEffectOfTheCard(" With this card the towers have no influence on the island considered");
        setName("warrior");
    }


    @Override
    public void effect() {
        islands.get(index).setTowerMultiplier(0);
        addCost();
    }


}
