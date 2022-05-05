package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.CharacterCards.NoTowerInfluence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IslandTest {

    private final Island islandTest = new Island(1);
    private final Student cyan = new Student(PawnColor.CYAN);
    private final Student yellow = new Student(PawnColor.YELLOW);
    private final Student red = new Student(PawnColor.RED);
    private final Student green = new Student(PawnColor.GREEN);
    private final Student magenta = new Student(PawnColor.MAGENTA);



    private Game gameTest;

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
    @DisplayName(" Calculate the influence ")
    public void calcInfluenceTest(){
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        gameTest.setupGame();
        Table tableTest = new Table(gameTest.getCloudCards(), gameTest.getIslands());
        Player plyr_1 = gameTest.getPlist().getPlayers().get(0);
        Player plyr_2 = gameTest.getPlist().getPlayers().get(1);
        Dashboard dashboard_1 = plyr_1.getDashboard();
        Dashboard dashboard_2 = plyr_2.getDashboard();
        PawnColor color = dashboard_1.getHall()[2].getColor();
        plyr_1.moveStudentToClassroom(2, gameTest);
        gameTest.assignTeacher();
        dashboard_1.drawDashboard();
        dashboard_2.drawDashboard();
        gameTest.getIslands().get(8).addStudent(new Student(color));
        gameTest.getIslands().get(8).addStudent(new Student(color));
        gameTest.getIslands().get(8).calcInfluence(gameTest.getPlist());
        assertEquals(plyr_1.getName(), gameTest.getIslands().get(8).getOwner());
        tableTest.drawTable();
        assertEquals(plyr_1.getInfluencePoint(), 0);
        assertEquals(plyr_2.getInfluencePoint(), 0);
    }

    @Test
    @DisplayName(" add 1 tower to the island")
    public void testAddTower(){
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        gameTest.setupGame();
        Player p = gameTest.getPlist().getPlayerByName("jaz");
        islandTest.setOwner(p);
        islandTest.addTower();
        assertTrue(islandTest.getTowerArrayList().size()>0);
        assertEquals(7,p.getDashboard().getTowers().size());
        assertEquals(TowerColor.black, p.getDashboard().getTowers().get(0).getColor());

    }

    @Test
    @DisplayName(" Calculate the influence with tower")
    public void calcInfluenceWithTowerTest(){
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        gameTest.assignTower();
        ArrayList<Island> islandTest = new ArrayList<>(1);
        islandTest.add(new Island(1));
        NoTowerInfluence card = new NoTowerInfluence();
        Dashboard dashboard_1 = gameTest.getPlist().getPlayers().get(0).getDashboard();
        Dashboard dashboard_2 = gameTest.getPlist().getPlayers().get(1).getDashboard();
        islandTest.get(0).addStudent(new Student(PawnColor.RED));
        dashboard_1.addTeacherToTable(new Teacher(PawnColor.RED));
        islandTest.get(0).calcInfluence(gameTest.getPlist());
        assertEquals(gameTest.getPlist().getPlayers().get(0).getName(), islandTest.get(0).getOwner());
        assertEquals(1,islandTest.get(0).getTowerNumber());
        assertEquals(TowerColor.white, islandTest.get(0).getTowerColor());

        islandTest.get(0).addStudent(new Student(PawnColor.GREEN));
        islandTest.get(0).addStudent(new Student(PawnColor.GREEN));
        dashboard_2.addTeacherToTable(new Teacher(PawnColor.GREEN));
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(),islandTest, PawnColor.CYAN, 0, gameTest.getStudentsBag());
        card.effect();
        islandTest.get(0).calcInfluence(gameTest.getPlist());
        assertEquals(gameTest.getPlist().getPlayers().get(1).getName(), islandTest.get(0).getOwner());

    }
}
