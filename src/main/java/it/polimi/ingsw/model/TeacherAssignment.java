package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

public class TeacherAssignment implements SpecialCard {
    private final String effectOfTheCard = new String(" With this card you take the control of the teacher even if the other player has more student of that color. ");
    private int cost = 2;
    private final Teacher[] teachers = {new Teacher(PawnColor.CYAN), new Teacher(PawnColor.MAGENTA), new Teacher(PawnColor.YELLOW), new Teacher(PawnColor.RED), new Teacher(PawnColor.GREEN)};
    private final PlayersList players;

    public TeacherAssignment(PlayersList players){
        this.players = players;
    }

    @Override
    public void effect() {
        SpecialCard.super.effect();
        if (players.getCurrentNumberOfPlayers() == 2) {
            Dashboard d1 = players.getPlayers().get(0).getDashboard();
            Dashboard d2 = players.getPlayers().get(1).getDashboard();

            for(int i = 0; i < 5; i++){
                if (d1.countStudentByColor(teachers[i].getColor()) >= d2.countStudentByColor(teachers[i].getColor())) {
                    d1.addTeacherToTable(d1.getTeacherTable()[i]);
                    if(d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                }

                else if(d2.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor())){
                    d2.addTeacherToTable(teachers[i]);
                    if(d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                }
            }

        }

        else if (players.getCurrentNumberOfPlayers() == 3) {
            Dashboard d1 = players.getPlayers().get(0).getDashboard();
            Dashboard d2 = players.getPlayers().get(1).getDashboard();
            Dashboard d3 = players.getPlayers().get(2).getDashboard();
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




    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getEffectOfTheCard(){
        return effectOfTheCard;
    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }
}
