package it.polimi.ingsw.server.model.CharacterCards;

import it.polimi.ingsw.server.model.Dashboard;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Student;

/**
 * Removes 3 students of one color from each player's dashboard
 * @see SpecialCardStrategy
 */
public class RemoveStudentStrategy extends SpecialCardStrategy {

    /**
     * It's the character card's constructor. It sets the cost, the effect and the name of the card
     */
    public RemoveStudentStrategy() {
        setCost(3);
        setEffectOfTheCard(" With this card you choose a color of the students and every player (even you) has to put in the BagStudents 3 students of that color from each Hall. ");
        setName("thief");
    }


    /**
     * For every player removes 3 students of the selected color.
     * Increase the cost by 1.
     */
    public void effect() {
        for (Player player : playersList.getPlayers()) {
            Dashboard dashboard = player.getDashboard();
            removeStudent(dashboard);
        }
        addCost();
    }

    /**
     * Removes 3 student from the dashboard
     * @param dashboard dashboard to remove the students from
     */
    public void removeStudent(Dashboard dashboard){
        int j= 0, i = 0;
        Student student;
        while(j < dashboard.getHall().length && i < 3 ){
            if (dashboard.getHall()[j].getColor() == pawnColor) {
                student = dashboard.getStudentFromHall(j);
                bag.addStudent(student);
                i++;
            }
            j++;
        }
    }
}
