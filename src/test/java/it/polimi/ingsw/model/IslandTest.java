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
    public void testCountStudentByColor(){
        islandTest.addStudent(cyan);
        islandTest.addStudent(yellow);
        islandTest.addStudent(yellow);
        islandTest.addStudent(red);
        assertEquals(1,islandTest.countStudentByColor()[PawnColor.CYAN.ordinal()], " must be 1 cyan");
        assertEquals(2,islandTest.countStudentByColor()[PawnColor.YELLOW.ordinal()], "must be 2 yellow");
        assertEquals(1,islandTest.countStudentByColor()[PawnColor.RED.ordinal()], "must be 1 red");
    }

    @Test
    @DisplayName(" Find the most color in the island")
    public void mostColorInfluenceTest(){
        islandTest.addStudent(cyan);
        islandTest.addStudent(cyan);
        islandTest.addStudent(yellow);
        islandTest.addStudent(red);
        islandTest.addStudent(green);
        assertEquals(PawnColor.CYAN,islandTest.mostColorInfluence());
    }

    @Test
    @DisplayName(" Calculate the influence ")
    public void calcInfluenceTest(){
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        gameTest.getPlist().moveStudentsToHall(gameTest.getStudentsBag());

        Player plyr_one = PlayersList.getPlayers().get(0);
        Dashboard dashboard_one = plyr_one.getDashboard();


        PawnColor color = dashboard_one.getHall()[2].getColor();
        plyr_one.moveStudentToClassroom(2);
        gameTest.assignTeacher();
        dashboard_one.drawDashboard();
        gameTest.getIslands().get(1).addStudent(new Student(color));
        gameTest.getIslands().get(1).calcInfluence(gameTest.getPlist());
        assertEquals(plyr_one,gameTest.getIslands().get(1).getOwner());




    }

    @Test
    @DisplayName(" add 1 tower to the island")
    public void testAddTower(){
        Player p = new Player("nic");
        p.getDashboard().addTower(8,TowerColor.white);
        islandTest.setOwner(p);
        islandTest.addTower();
        assertEquals(1,islandTest.getTowerNumber());
        assertEquals(7,p.getDashboard().getTowers().size());

    }
}
