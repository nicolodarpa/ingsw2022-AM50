package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;

/**
 * implements the character card that remove 3 students of selected color from each player's hall
 * @see SpecialCardStrategy
 */
public class RemoveStudentStrategy extends SpecialCardStrategy {

    public RemoveStudentStrategy() {
        setCost(3);
        setEffectOfTheCard(" With this card you choose a color of the students and every player (even you) has to put in the BagStudents 3 students of that color from each Hall. ");
        setName("thief");
    }


    public void effect() {
        for (Player player : playersList.getPlayers()) {
            Dashboard dashboard = player.getDashboard();
            removeStudent(dashboard);
        }
        addCost();
    }

    public void removeStudent(Dashboard dashboard){
        for (int i = 0; i<3; i++){
            Student student = dashboard.getStudentFromClassroom(pawnColor);
            if (student != null) {
                bag.addStudent(student);
            }
        }

    }
}
