package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandTest {

    private Island islandTest = new Island(1);
    private Student cyan = new Student(PawnColor.CYAN);
    private Student yellow = new Student(PawnColor.YELLOW);
    private Student red = new Student(PawnColor.RED);
    private Student green = new Student(PawnColor.GREEN);
    private Student magenta = new Student(PawnColor.MAGENTA);



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
    @Disabled
    public void calcInfluenceTest(){
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.getStudentsBag().fillBag(120);
        game.setupGame();
        game.moveStudentsToHall();
        PlayersList players = game.getPlist();
        Player p = players.getPlayers().get(0);
        PawnColor studentColor = null;
        studentColor = p.getDashboard().getHall()[2].getColor();
        p.moveStudentToClassroom(2);
        Teacher t = new Teacher(studentColor);
        game.assignTeacher(t);
        Student s = new Student(studentColor);
        game.getIslands().get(1).addStudent(s);
        game.getIslands().get(1).calcInfluence(players);
        assertEquals(p,game.getIslands().get(1).getOwner());
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
