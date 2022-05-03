package it.polimi.ingsw.model.CharacterCards;


public class AddMoveMN extends SpecialCard {



    public AddMoveMN(){
        setCost(1);
        setEffectOfTheCard(" With this card you have 2 more available move for Mother Nature. ");
        setName("merchant");
    }

    public void effect() {
        int moveAvailable = actualPlayer.getMovesOfMN() + 2;
        actualPlayer.setMovesOfMN(moveAvailable);
        addCost();
    }


}
