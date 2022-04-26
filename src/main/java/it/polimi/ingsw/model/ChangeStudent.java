package it.polimi.ingsw.model;

import java.util.Scanner;

public class ChangeStudent implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you can choose to swap places up to 2 students from the Hall and the Classroom. ");
    private int cost = 1;
    private final Player actualPlayer;
    Scanner scanner = new Scanner(System.in);

    public ChangeStudent(Player actualPlayer){
        this.actualPlayer = actualPlayer;
    }

    public String getEffectOfTheCard(){
        return effectOfTheCard;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void effect() {
        SpecialCard.super.effect();
        int pos_x = 0;
        int pos_y = 0;
        int pos_hall = 0;
        int numberOfStudentToChange = 0;
        final Dashboard actualPlayerDashboard = actualPlayer.getDashboard();
        System.out.println(" Insert the number of students to switch, 1 or 2: ");
        numberOfStudentToChange = scanner.nextInt();
        if(numberOfStudentToChange <= 2){
            for(int i = 0; i < numberOfStudentToChange; i++){
                System.out.println("Insert the x_position of the student in the classroom: ");
                pos_x = scanner.nextInt();
                System.out.println("Insert the y_position of the student in the classroom: ");
                pos_y = scanner.nextInt();
                Student studentFromClassroom = actualPlayerDashboard.getStudentFromClassroom(pos_x,pos_y);
                System.out.println("Insert the position of the student in the hall");
                pos_hall = scanner.nextInt();
                Student studentFromHall = actualPlayerDashboard.getStudentFromHall(pos_hall);
                actualPlayerDashboard.addStudentToClassroom(studentFromHall);
                actualPlayerDashboard.addStudentToHall(studentFromClassroom);
            }
        }





    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }
}
