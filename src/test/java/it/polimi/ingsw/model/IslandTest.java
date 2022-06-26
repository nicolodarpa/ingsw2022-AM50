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


    /**
     * Adds one player of each color to an island and check if the returned value of countStudentsByColor is correct
     */
    @Test
    @DisplayName("Test countStudentsByColor")
    public void testCountStudentByColor() {
        for (int i = 0; i < PawnColor.totalNumberOfPawnColors(); i++) {
            islandTest.addStudent(new Student(PawnColor.values()[i]));
        }
        assertEquals(1, islandTest.countStudentByColor()[PawnColor.CYAN.ordinal()], " must be 1 cyan");
        assertEquals(1, islandTest.countStudentByColor()[PawnColor.YELLOW.ordinal()], "must be 1 yellow");
        assertEquals(1, islandTest.countStudentByColor()[PawnColor.RED.ordinal()], "must be 1 red");
        assertEquals(1, islandTest.countStudentByColor()[PawnColor.MAGENTA.ordinal()], "must be 1 magenta");
        assertEquals(1, islandTest.countStudentByColor()[PawnColor.GREEN.ordinal()], "must be 1 green");
    }


    /**
     * We check that an island is conquered by the player with the most influence on it
     * with no special effects activated influence is calculated from students and tower in an island
     */
    @Test
    @DisplayName("Test calculateInfluence")
    public void calcInfluenceTest() {
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);

        Player plyr_1 = gameTest.getPlist().getPlayers().get(0);
        Player plyr_2 = gameTest.getPlist().getPlayers().get(1);
        Dashboard dashboard_1 = plyr_1.getDashboard();

        dashboard_1.addTeacherToTable(new Teacher(PawnColor.RED));

        islandTest.addStudent(new Student(PawnColor.RED));
        islandTest.addStudent(new Student(PawnColor.RED));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(plyr_1.getName(), islandTest.getOwner());

    }

    /**
     * We check that when an island is conquered a tower of the correct color is placed on it
     * and that tha towers available to a player are decreased
     */
    @Test
    @DisplayName("Test add 1 tower to an island")
    public void testAddTower() {
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);

        Player p = gameTest.getPlist().getPlayerByName("jaz");
        islandTest.setOwner(p);
        islandTest.addTower();
        assertTrue(islandTest.getTowerArrayList().size() > 0);
        assertEquals(7, p.getDashboard().getTowers().size());
        assertEquals(TowerColor.black, p.getDashboard().getTowers().get(0).getColor());
    }

    @Test
    @DisplayName(" add null tower to the island")
    public void testAddNullTower() {
        islandTest.addTower();
    }

    /**
     * We check that towers on an island counts as students for influence calculation
     */
    @Test
    @DisplayName("Test i")
    public void calcInfluenceWithTowerTest() {
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        gameTest.assignTower();
        Player player_1 = gameTest.getPlist().getPlayers().get(0);
        Dashboard dashboard_1 = player_1.getDashboard();
        islandTest.addStudent(new Student(PawnColor.RED));
        dashboard_1.addTeacherToTable(new Teacher(PawnColor.RED));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(player_1.getName(), islandTest.getOwner());
        assertEquals(1, islandTest.getTowerNumber());
        assertEquals(TowerColor.white, islandTest.getTowerColor());//tower is conquered by player_1 who has one RED students with influence on the island

        Player player_2 = gameTest.getPlist().getPlayers().get(1);
        Dashboard dashboard_2 = player_2.getDashboard();
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        dashboard_2.addTeacherToTable(new Teacher(PawnColor.GREEN));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(player_1.getName(), islandTest.getOwner());//tower isn't reassigned, influence on the tower is the same for player_1 and player_2

        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(player_2.getName(), islandTest.getOwner()); //tower is conquered by player_2 who has three GREEN students with influence on the island


    }

    /**
     * We check that the owner of an island is changed when a player has more influence than the previous owner
     */
    @Test
    @DisplayName("Test change owner of an island")
    public void removePreviousOwnerTower() {
        Game gameTest = new Game(3);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        final Player P1 = gameTest.getPlist().getPlayers().get(0);
        final Player P2 = gameTest.getPlist().getPlayers().get(1);
        final Player P3 = gameTest.getPlist().getPlayers().get(2);

        final Dashboard D1 = P1.getDashboard();
        final Dashboard D2 = P2.getDashboard();
        final Dashboard D3 = P3.getDashboard();

        D1.addTeacherToTable(new Teacher(PawnColor.RED));
        islandTest.addStudent(new Student(PawnColor.RED));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(P1.getName(), islandTest.getOwner());
        assertEquals(5, D1.getTowers().size());

        D3.addTeacherToTable(new Teacher(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.addStudent(new Student(PawnColor.GREEN));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(P3.getName(), islandTest.getOwner());
        assertEquals(5, D3.getTowers().size());
        assertEquals(6, D1.getTowers().size());


        D2.addTeacherToTable(new Teacher(PawnColor.CYAN));
        islandTest.addStudent(new Student(PawnColor.CYAN));
        islandTest.addStudent(new Student(PawnColor.CYAN));
        islandTest.addStudent(new Student(PawnColor.CYAN));
        islandTest.addStudent(new Student(PawnColor.CYAN));
        islandTest.addStudent(new Student(PawnColor.CYAN));
        islandTest.calculateInfluence(gameTest.getPlist());
        assertEquals(P2.getName(), islandTest.getOwner());
        assertEquals(5, D2.getTowers().size());
        assertEquals(6, D3.getTowers().size());

    }
}
