package it.polimi.ingsw.model.CharacterCards;


public class MotherNatureInfluence extends SpecialCard {

    public MotherNatureInfluence() {
        setCost(3);
        setEffectOfTheCard(" With this card you can chose an island and calculate the influence like when Mother Nature is on that island ");
        setName("ambassador");
    }


    public void effect() {
        System.out.println("Choose an Island : ");
        islands.get(index).calcInfluence(playersList);
        addCost();
    }

}
