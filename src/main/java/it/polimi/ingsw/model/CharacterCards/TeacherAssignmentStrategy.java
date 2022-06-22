package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Teacher;


/**
 * Implements the special card that change the assignment of teacher in game.
 * {@link SpecialCardStrategy} {@link it.polimi.ingsw.model.Player}
 */
public class TeacherAssignmentStrategy extends SpecialCardStrategy {


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
