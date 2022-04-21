package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandTest {

    private final Island islandTest = new Island(1);
    private final Student cyan = new Student(PawnColor.CYAN);
    private final Student yellow = new Student(PawnColor.YELLOW);
    private final Student red = new Student(PawnColor.RED);
    private final Student green = new Student(PawnColor.GREEN);
    private final Student magenta = new Student(PawnColor.MAGENTA);


    @Test
    @DisplayName(" Count 1 student cyan, 2 students yellow and 1 red")
    public void testCountStudentByColor() {
        islandTest.addStudent(cyan);
        islandTest.addStudent(yellow);
        islandTest.addStudent(yellow);
        islandTest.addStudent(red);
        assertEquals(1, islandTest.countStudentByColor()[PawnColor.CYAN.ordinal()], " must be 1 cyan");
        assertEquals(2, islandTest.countStudentByColor()[PawnColor.YELLOW.ordinal()], "must be 2 yellow");
        assertEquals(1, islandTest.countStudentByColor()[PawnColor.RED.ordinal()], "must be 1 red");
    }

    @Test
    @DisplayName(" Find the most color in the island")
    public void mostColorInfluenceTest() {
        islandTest.addStudent(cyan);
        islandTest.addStudent(cyan);
        islandTest.addStudent(yellow);
        islandTest.addStudent(red);
        islandTest.addStudent(green);
        assertEquals(PawnColor.CYAN, islandTest.mostColorInfluence());
    }

    @Test
    @DisplayName(" Calculate the influence ")
    public void calcInfluenceTest() {
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(3);
        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        Table tableTest = new Table(gameTest.getCloudCards(), gameTest.getIslands());

        Player plyr_1 = PlayersList.getPlayers().get(0);
        Player plyr_2 = PlayersList.getPlayers().get(1);
        Player plyr_3 = PlayersList.getPlayers().get(2);

        Dashboard dashboard_1 = plyr_1.getDashboard();
        Dashboard dashboard_2 = plyr_2.getDashboard();
        Dashboard dashboard_3 = plyr_3.getDashboard();


        PawnColor color = dashboard_1.getHall()[2].getColor();
        plyr_1.moveStudentToClassroom(2);
        gameTest.assignTeacher();
        plyr_2.moveStudentToClassroom(1);
        gameTest.assignTeacher();
        plyr_3.moveStudentToClassroom(4);
        gameTest.assignTeacher();
        plyr_2.moveStudentToIsland(gameTest.getIslands().get(8), 2);
        dashboard_1.drawDashboard();
        dashboard_2.drawDashboard();
        dashboard_3.drawDashboard();
        gameTest.getIslands().get(1).addStudent(new Student(color));
        //plyr_2.moveStudentToIsland(gameTest.getIslands().get(3),2);
        //plyr_3.moveStudentToIsland(gameTest.getIslands().get(2),3);
        gameTest.getIslands().get(1).calcInfluence(gameTest.getPlist());
        try{
            if(gameTest.getIslands().get(1).getOwner() != null)
            assertEquals(plyr_1,gameTest.getIslands().get(1).getOwner());
            System.out.println("The owner is : " + gameTest.getIslands().get(1).getOwner().getName());
        } catch (Exception e){
            System.out.println("No one has the influence on this island");
        }
        tableTest.drawTable();
    }

    @Test
    @DisplayName(" add 1 tower to the island")
    public void testAddTower() {
        Player p = new Player("nic");
        p.getDashboard().addTower(8, TowerColor.white);
        islandTest.setOwner(p);
        islandTest.addTower();
        assertEquals(1, islandTest.getTowerNumber());
        assertEquals(7, p.getDashboard().getTowers().size());

    }
}
