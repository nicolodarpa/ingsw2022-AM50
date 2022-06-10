package it.polimi.ingsw.model.CharacterCards;

/**
 * implements the character card that adds 2 more available moves of MN to a player
 */
public class AddMoveMNStrategy extends SpecialCardStrategy {



    /**
     * It's the character card's constructor. It sets the cost, the effect and the name of the card
     */
    public AddMoveMNStrategy(){
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
