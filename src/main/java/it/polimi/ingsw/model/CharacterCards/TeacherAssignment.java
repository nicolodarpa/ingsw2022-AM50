package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.PlayersList;
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
        if (playersList.getCurrentNumberOfPlayers() == 2) {
            Dashboard d1 = playersList.getPlayers().get(0).getDashboard();
            Dashboard d2 = playersList.getPlayers().get(1).getDashboard();

            for (int i = 0; i < 5; i++) {
                if (d1.countStudentByColor(teachers[i].getColor()) >= d2.countStudentByColor(teachers[i].getColor())) {
                    d1.addTeacherToTable(d1.getTeacherTable()[i]);
                    if (d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                } else if (d2.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor())) {
                    d2.addTeacherToTable(teachers[i]);
                    if (d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                }
            }

        } else if (playersList.getCurrentNumberOfPlayers() == 3) {
            Dashboard d1 = playersList.getPlayers().get(0).getDashboard();
            Dashboard d2 = playersList.getPlayers().get(1).getDashboard();
            Dashboard d3 = playersList.getPlayers().get(2).getDashboard();
            for (int i = 0; i < 5; i++) {
                if (d1.countStudentByColor(teachers[i].getColor()) > d2.countStudentByColor(teachers[i].getColor()) && d1.countStudentByColor(teachers[i].getColor()) > d3.countStudentByColor(teachers[i].getColor())) {
                    d1.addTeacherToTable(teachers[i]);
                    if (d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                    else if (d3.getTeacherTable()[i] != null)
                        d3.removeTeacherFromTable(teachers[i]);
                } else if (d2.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor()) && d2.countStudentByColor(teachers[i].getColor()) > d3.countStudentByColor(teachers[i].getColor())) {
                    d2.addTeacherToTable(teachers[i]);
                    if (d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                    else if (d3.getTeacherTable()[i] != null)
                        d3.removeTeacherFromTable(teachers[i]);
                } else if (d3.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor()) && d3.countStudentByColor(teachers[i].getColor()) > d2.countStudentByColor(teachers[i].getColor())) {
                    d3.addTeacherToTable(teachers[i]);
                    if (d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                    else if (d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                }

            }
        }
    }
}
