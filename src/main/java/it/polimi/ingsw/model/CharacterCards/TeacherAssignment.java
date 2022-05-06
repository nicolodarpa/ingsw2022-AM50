package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Teacher;

public class TeacherAssignment extends SpecialCard {
    private final Teacher[] teachers = {new Teacher(PawnColor.CYAN), new Teacher(PawnColor.MAGENTA), new Teacher(PawnColor.YELLOW), new Teacher(PawnColor.RED), new Teacher(PawnColor.GREEN)};


    public TeacherAssignment() {

        setCost(2);
        setEffectOfTheCard(" With this card you take the control of the teacher even if the other player has the same number of student of that color. ");
        setName("hungry man");
    }

    @Override
    public void effect() {
        actualPlayer.setTeacherAssignerModifier(true);
        addCost();
    }
}
