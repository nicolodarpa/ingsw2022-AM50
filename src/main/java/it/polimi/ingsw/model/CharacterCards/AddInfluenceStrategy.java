package it.polimi.ingsw.model.CharacterCards;


/**
 * Adds 2 more influence point to a player for this round
 */
public class AddInfluenceStrategy extends SpecialCardStrategy {


    /**
     * Constructor method for the class, sets cost, effect and name of the card
     */
    public AddInfluenceStrategy() {
        setCost(2);
        setEffectOfTheCard(" With this card you have 2 more points in the calculation of the influence. ");
        setName("knight");
    }


    /**
     * Adds 2 points to teh current player influence points
     */
    public void effect() {
        currentPlayer.setInfluencePoint(currentPlayer.getInfluencePoint() + 2);
        addCost();
    }

}
