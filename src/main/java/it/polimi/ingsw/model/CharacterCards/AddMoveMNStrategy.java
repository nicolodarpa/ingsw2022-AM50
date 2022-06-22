package it.polimi.ingsw.model.CharacterCards;

/**
 * implements the character card that adds 2 more available moves of MN to a player {@link SpecialCardStrategy}
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

    /**
     * It sets 2 more available moves of MN to the player.
     * It increases the cost by 1 when the card is played
     */
    public void effect() {
        int moveAvailable = currentPlayer.getMovesOfMN() + 2;
        currentPlayer.setMovesOfMN(moveAvailable);
        addCost();
    }


}
