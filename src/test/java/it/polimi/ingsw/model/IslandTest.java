package it.polimi.ingsw.model;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.CharacterCards.NoTowerInfluenceStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * tests for {@link Island}
 */
public class IslandTest {

    private final Island islandTest = new Island(1);


    @Test
    @DisplayName(" Count 1 student cyan, 2 students yellow and 1 red")
    public void testCountStudentByColor(){
        for(int i = 0; i < PawnColor.totalNumberOfPawnColors(); i++){
            islandTest.addStudent(new Student(PawnColor.values()[i]));
        }
        assertEquals(1,islandTest.countStudentByColor()[PawnColor.CYAN.ordinal()], " must be 1 cyan");
        assertEquals(1,islandTest.countStudentByColor()[PawnColor.YELLOW.ordinal()], "must be 1 yellow");
        assertEquals(1,islandTest.countStudentByColor()[PawnColor.RED.ordinal()], "must be 1 red");
        assertEquals(1,islandTest.countStudentByColor()[PawnColor.MAGENTA.ordinal()], "must be 1 magenta");
        assertEquals(1,islandTest.countStudentByColor()[PawnColor.GREEN.ordinal()], "must be 1 green");
    }


    @Test
    @DisplayName(" Calculate the influence ")
    public void calcInfluenceTest(){
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);

        Table tableTest = new Table(gameTest.getIslands());
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
        gameTest.getIslands().get(8).calculateInfluence(gameTest.getPlist());
        assertEquals(plyr_1.getName(), gameTest.getIslands().get(8).getOwner());
        tableTest.drawTable();
        assertEquals(plyr_1.getInfluencePoint(), 0);
        assertEquals(plyr_2.getInfluencePoint(), 0);
    }

    @Test
    @DisplayName(" add 1 tower to the island")
    public void testAddTower(){
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);

        Player p = gameTest.getPlist().getPlayerByName("jaz");
        islandTest.setOwner(p);
        islandTest.addTower();
        assertTrue(islandTest.getTowerArrayList().size()>0);
        assertEquals(7,p.getDashboard().getTowers().size());
        assertEquals(TowerColor.black, p.getDashboard().getTowers().get(0).getColor());
    }

    @Test
    @DisplayName(" add null tower to the island")
    public void testAddNullTower(){
        islandTest.addTower();
    }

    @Test
    @DisplayName(" Calculate the influence with tower")
    public void calcInfluenceWithTowerTest(){
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        gameTest.assignTower();
        ArrayList<Island> islandTest = new ArrayList<>(1);
        islandTest.add(new Island(1));
        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        Dashboard dashboard_1 = gameTest.getPlist().getPlayers().get(0).getDashboard();
        Dashboard dashboard_2 = gameTest.getPlist().getPlayers().get(1).getDashboard();
        islandTest.get(0).addStudent(new Student(PawnColor.RED));
        dashboard_1.addTeacherToTable(new Teacher(PawnColor.RED));
        islandTest.get(0).calculateInfluence(gameTest.getPlist());
        assertEquals(gameTest.getPlist().getPlayers().get(0).getName(), islandTest.get(0).getOwner());
        assertEquals(1,islandTest.get(0).getTowerNumber());
        assertEquals(TowerColor.white, islandTest.get(0).getTowerColor());

        islandTest.get(0).addStudent(new Student(PawnColor.GREEN));
        islandTest.get(0).addStudent(new Student(PawnColor.GREEN));
        dashboard_2.addTeacherToTable(new Teacher(PawnColor.GREEN));
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(),islandTest, PawnColor.CYAN, 0, gameTest.getStudentsBag());
        card.effect();
        islandTest.get(0).calculateInfluence(gameTest.getPlist());
        assertEquals(gameTest.getPlist().getPlayers().get(1).getName(), islandTest.get(0).getOwner());

    }

    @Test
    public void removePreviousOwnerTower(){
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);

        final Island islandTest = gameTest.getIslands().get(0);

        final Player P1 = gameTest.getPlist().getPlayers().get(0);
        final Player P2 = gameTest.getPlist().getPlayers().get(1);

        final Dashboard D1 = P1.getDashboard();
        final Dashboard D2 = P2.getDashboard();

        D1.addTeacherToTable(new Teacher(PawnColor.RED));
        islandTest.addStudent(new Student(PawnColor.RED));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(P1.getName(), islandTest.getOwner());
        assertEquals(7, D1.getTowers().size());

        D2.addTeacherToTable(new Teacher(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(P2.getName(), islandTest.getOwner());
        assertEquals(7, D2.getTowers().size());
        assertEquals(8,D1.getTowers().size());

    }
}
