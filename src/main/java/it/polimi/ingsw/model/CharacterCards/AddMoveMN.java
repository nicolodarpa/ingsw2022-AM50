package it.polimi.ingsw.model.CharacterCards;

/**
 * implements the character card that adds 2 more available moves of MN to a player
 */
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
