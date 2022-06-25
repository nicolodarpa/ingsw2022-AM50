package it.polimi.ingsw.server.model.CharacterCards;

import it.polimi.ingsw.server.model.Player;


/**
 * Changes the method of assignment of teacher in game.
 * {@link SpecialCardStrategy} {@link Player}
 */
public class TeacherAssignmentStrategy extends SpecialCardStrategy {


    /**
     * It's the character card's constructor. It sets the cost, the effect and the name of the card
     */
    public TeacherAssignmentStrategy() {
        setCost(2);
        setEffectOfTheCard(" With this card you take the control of the teacher even if the other player has the same number of student of that color. ");
        setName("hungry man");
    }

    /**
     * It changes the TeacherAssignerModifier flag of the current player.
     * It increases the cost by 1 when the card is played.
     */
    @Override
    public void effect() {
        currentPlayer.setTeacherAssignerModifier(true);
        addCost();
    }
}
