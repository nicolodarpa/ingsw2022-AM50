package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.CharacterCards.AddMoveMNStrategy;
import it.polimi.ingsw.server.model.CharacterCards.CharacterCardStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests for {@link Game}
 */
public class GameTest {

    private Game gameTest;

    /**
     * Checks that after setup there are 100 students left in the student bag because 14 are in the dashboard classroom and 6 on the cloud card
     */
    @Test
    @DisplayName("Test student bag size")
    public void testMoveStudentsToHall() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        assertEquals(100, gameTest.getStudentsInBag());
    }

    /**
     * We test the game setup for 2 players.
     * We check that cloudCard, sudent Bag and players dashboards are of the correct size
     */
    @Test
    @DisplayName("testing the setup of a new match")
    public void testSetupGame() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        assertEquals(100, gameTest.getStudentsInBag());
        assertEquals(2, gameTest.getCloudCards().size());
        for (int i = 0; i < 2; i++)
            assertEquals(3, gameTest.getCloudCards().get(i).getStudents().size());
        assertEquals(12, gameTest.getIslands().size());
        Table t = new Table(gameTest.getIslands());
        t.drawTable();
        for (Player p : gameTest.getPlist().getPlayers())
            assertEquals(8, p.getDashboard().getTowers().size());
        assertEquals(3, gameTest.getCardsInGame().size());
    }


    @Test
    @DisplayName(" Test that there are 12 Islands")
    public void createIslandTest() {
        gameTest = new Game();
        gameTest.createIslands();
        assertEquals(12, gameTest.getIslands().size());
    }

    @Test
    @DisplayName(" Test the assignment of the Teacher")
    public void assignTeacherTest() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        PawnColor color = player_one.getDashboard().getHall()[2].getColor();
        player_one.moveStudentToClassroom(2, gameTest);
        assertNotNull(player_one.getDashboard().getTeacherTable()[color.ordinal()]);
        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();

    }

    @Test
    @DisplayName(" add student on the islands at the beginning of a new match")
    public void addStudentToIslandTest() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        for (Island i : gameTest.getIslands()) {
            if (!i.getPresenceMN() && !i.getOppositeMN()) {
                assertEquals(1, i.getStudentList().size());
            }
        }
    }


    @Test
    @DisplayName(" Connect two island with the same owner")
    public void connectIslandTest() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p = gameTest.getPlist().getPlayers().get(0);
        Player q = gameTest.getPlist().getPlayers().get(1);
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - dimension: " + i.getDimension() + ", owner: " + i.getOwner());
        }
        System.out.println(" ");
        gameTest.getIslands().get(2).setOwner(p);
        gameTest.getIslands().get(3).setOwner(p);
        gameTest.getIslands().get(4).setOwner(p);
        gameTest.connectIsland();
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - dimension: " + i.getDimension() + ", owner: " + i.getOwner());
        }

        System.out.println(" ");
        gameTest.getIslands().get(5).setOwner(q);
        gameTest.getIslands().get(6).setOwner(q);
        gameTest.getIslands().get(7).setOwner(q);
        gameTest.connectIsland();
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - dimension: " + i.getDimension() + ", owner: " + i.getOwner());
        }


    }

    /**
     * We test the connection of the first and the last island in the islands array
     */
    @Test
    @DisplayName("connect island 1 and 12")
    public void connectExtremeIslands() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p = gameTest.getPlist().getPlayers().get(0);
        Player q = gameTest.getPlist().getPlayers().get(1);
        gameTest.getIslands().get(0).setOwner(q);
        gameTest.getIslands().get(11).setOwner(q);
        gameTest.connectIsland();
        gameTest.getIslands().get(0).setOwner(q);
        gameTest.connectIsland();
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - dimension: " + i.getDimension() + ", owner: " + i.getOwner());
        }

        assertEquals(q.getName(), gameTest.getIslands().get(9).getOwner());


    }

    @Test
    @DisplayName("connect island 1 and 2")
    public void connectFirstSecondIslands() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p = gameTest.getPlist().getPlayers().get(0);
        Player q = gameTest.getPlist().getPlayers().get(1);
        gameTest.getIslands().get(0).setOwner(q);
        gameTest.getIslands().get(1).setOwner(q);
        gameTest.connectIsland();
        gameTest.getIslands().get(2).setOwner(q);
        gameTest.getIslands().get(3).setOwner(q);
        gameTest.connectIsland();
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - dimension: " + i.getDimension() + ", owner: " + i.getOwner());
        }
        gameTest.getIslands().get(9).setOwner(q);
        gameTest.getIslands().get(2).setOwner(p);
        gameTest.getIslands().get(3).setOwner(p);
        gameTest.connectIsland();
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - dimension: " + i.getDimension() + ", owner: " + i.getOwner());
        }

        //assertEquals(q.getName(), gameTest.getIslands().get(9).getOwner());
    }

    /**
     * Tests that the game ends when the limit of groups is reached
     */
    @Test
    @DisplayName("End game for islands connection")
    public void endGameIslandsConnection() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p = gameTest.getPlist().getPlayers().get(0);
        Player q = gameTest.getPlist().getPlayers().get(1);
        System.out.println("p: " + p.getDashboard().getTowers().size() + "\n" + "q: " + q.getDashboard().getTowers().size());
        gameTest.getIslands().get(0).setOwner(p);
        gameTest.getIslands().get(1).setOwner(p);
        gameTest.getIslands().get(2).setOwner(p);
        gameTest.connectIsland();
        gameTest.getIslands().get(1).setOwner(p);
        gameTest.getIslands().get(2).setOwner(p);
        gameTest.getIslands().get(3).setOwner(p);
        gameTest.connectIsland();
        gameTest.getIslands().get(5).setOwner(q);
        gameTest.getIslands().get(6).setOwner(q);
        gameTest.getIslands().get(2).setOwner(q);
        gameTest.getIslands().get(3).setOwner(q);
        gameTest.getIslands().get(4).setOwner(q);
        for (int i = 7; i >= 0; i--) {
            p.getDashboard().removeTower(i);
        }
        for (int i = 7; i > 3; i--) {
            q.getDashboard().removeTower(i);
        }
        gameTest.connectIsland();
        System.out.println("--------");
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - dimension: " + i.getDimension() + ", owner: " + i.getOwner());
        }

        System.out.println("p: " + p.getDashboard().getTowers().size() + "\n" + "q: " + q.getDashboard().getTowers().size());

        assertEquals(p, gameTest.getWinner());
        assertEquals("ENDED", gameTest.getGameStatus());


    }

    /**
     * Testing the end of the match when a player finishes his towers
     */
    @Test
    @DisplayName("Test towers game end")
    public void endOfGameTowers() {
        gameTest = new Game(2);
        Student s = new Student(PawnColor.RED);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p = gameTest.getPlist().getPlayers().get(0);
        Player q = gameTest.getPlist().getPlayers().get(1);

        System.out.println("p: " + p.getDashboard().getTowers().size() + "\n" + "q: " + q.getDashboard().getTowers().size());

        p.getDashboard().addTeacherToTable(new Teacher(PawnColor.RED));

        gameTest.getIslands().get(0).addStudent(s);
        gameTest.getIslands().get(1).addStudent(s);
        gameTest.getIslands().get(2).addStudent(s);
        gameTest.getIslands().get(3).addStudent(s);
        gameTest.getIslands().get(4).addStudent(s);
        gameTest.getIslands().get(5).addStudent(s);
        gameTest.getIslands().get(6).addStudent(s);
        gameTest.getIslands().get(7).addStudent(s);
        gameTest.getIslands().get(8).addStudent(s);

        gameTest.getIslands().get(0).calculateInfluence(gameTest.getPlist());
        gameTest.getIslands().get(1).calculateInfluence(gameTest.getPlist());
        gameTest.getIslands().get(2).calculateInfluence(gameTest.getPlist());
        gameTest.getIslands().get(3).calculateInfluence(gameTest.getPlist());
        gameTest.getIslands().get(4).calculateInfluence(gameTest.getPlist());
        gameTest.getIslands().get(5).calculateInfluence(gameTest.getPlist());
        gameTest.getIslands().get(6).calculateInfluence(gameTest.getPlist());
        assertEquals(1, p.getDashboard().getTowers().size());
        p.setMovesOfMN(12);

        if (gameTest.getIslands().get(7).getPresenceMN())
            gameTest.moveMN(p, 8);
        else
            gameTest.moveMN(p, 7);

        assertEquals(0, p.getDashboard().getTowers().size());
        assertEquals(p, gameTest.getWinner());
        assertEquals("ENDED", gameTest.getGameStatus());
    }


    @Test
    @DisplayName("Test set current player")
    public void setCurrentPlayerTest() {
        gameTest = new Game(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Deck deck1 = new Deck(1, "BLUE");
        Deck deck2 = new Deck(2, "PINK");
        Player playerOne = gameTest.getPlist().getPlayerByName("jaz");
        Player playerTwo = gameTest.getPlist().getPlayerByName("nic");
        playerOne.setDeck(deck1);
        playerTwo.setDeck(deck2);
        playerOne.playAssistantCard(3);
        playerTwo.playAssistantCard(6);
        gameTest.calculateCurrentPlayer();
        assertEquals(playerOne, gameTest.getCurrentPlayer());
    }

    @Test
    @DisplayName("Test special card extraction")
    public void extractSpecialCardTest() {
        gameTest = new Game(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        assertEquals(3, gameTest.getCardsInGame().size());
        for (CharacterCardStrategy card : gameTest.getCardsInGame()) {
            System.out.println("==" + card.getEffectOfTheCard());
        }

    }

    /**
     * Test that students' hall is refilled after choosing a cloud card
     */
    @Test
    @DisplayName("Test cloud card selection")
    public void chooseCloudCard() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("jaz");
        p1.moveStudentToClassroom(3, gameTest);
        p1.moveStudentToIsland(4, gameTest.getIslandWithMNIndex(), gameTest);
        p1.moveStudentToClassroom(5, gameTest);
        assertNull(p1.getDashboard().getStudentFromHall(3));
        assertNull(p1.getDashboard().getStudentFromHall(4));
        assertNull(p1.getDashboard().getStudentFromHall(5));
        gameTest.chooseCloudCard(1, p1);
        assertNotNull(p1.getDashboard().getStudentFromHall(3));
        assertNotNull(p1.getDashboard().getStudentFromHall(4));
        assertNotNull(p1.getDashboard().getStudentFromHall(5));
    }


    /**
     * Test the movement of mother nature
     */
    @Test
    @DisplayName("Test moveMN")
    public void moveMN() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("jaz");
        p1.setMovesOfMN(12);
        int i = gameTest.getIslandWithMNIndex();
        int destination = i + 5;
        if (destination > 11) destination = destination - 6;
        gameTest.moveMN(p1, destination);
        assertFalse(gameTest.getIslands().get(i).getPresenceMN());
        assertTrue(gameTest.getIslands().get(destination).getPresenceMN());

    }

    /**
     * Tests that mother nature need to be moved of 1 step minimum
     */
    @Test
    @DisplayName("Test minimum moveMN")
    public void moveMNOne() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("jaz");
        p1.setMovesOfMN(12);
        int i = gameTest.getIslandWithMNIndex();
        assertFalse(gameTest.moveMN(p1, i));


    }


    /**
     * Test the assignment of the Teacher for 3 players
     */
    @Test
    @DisplayName("test teacher assignment 3 players ")
    public void assignTeacherForThreePlayersTest() {
        gameTest = new Game(3);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        LoginManager.login("jaz", gameTest);
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        Player player_three = gameTest.getPlist().getPlayers().get(2);

        //adds two CYAN students of the same color to the classroom of player_one.
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));

        //adds one GREEN students to the classroom of player_one.
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        gameTest.assignTeacher(PawnColor.CYAN);
        gameTest.assignTeacher(PawnColor.GREEN);


        //checks that CYAN, GREEN teachers have been assigned to player_one
        assertNotNull(player_one.getDashboard().getTeacherTable()[PawnColor.CYAN.ordinal()]);
        assertNotNull(player_one.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);


        //add three students of the same color to the classroom of the second player.
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        gameTest.assignTeacher(PawnColor.CYAN);

        //checks that CYAN teacher  has been moved from player_one to player_two
        assertNull(player_one.getDashboard().getTeacherTable()[PawnColor.CYAN.ordinal()]);
        assertNotNull(player_two.getDashboard().getTeacherTable()[PawnColor.CYAN.ordinal()]);

        //add four students of the same color to the classroom of the third player.
        player_three.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        player_three.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        player_three.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        player_three.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        gameTest.assignTeacher(PawnColor.CYAN);

        //checks that CYAN teacher has been moved from player_one to player_two
        assertNotNull(player_three.getDashboard().getTeacherTable()[PawnColor.CYAN.ordinal()]);

        // player two and three have the same number of cyan students
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.CYAN));
        gameTest.assignTeacher(PawnColor.CYAN);

        //checks that CYAN teacher hasn't moved from player_one to player_two
        assertNull(player_two.getDashboard().getTeacherTable()[PawnColor.CYAN.ordinal()]);
        assertNotNull(player_three.getDashboard().getTeacherTable()[PawnColor.CYAN.ordinal()]);


        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();
        player_three.getDashboard().drawDashboard();
    }

    /**
     * Test assignment of teacher with same number of students
     * We check that with the same number of students of a color in the classroom CYAN
     * teacher isn't reassigned to player_three after being assigned to player_one.ù
     * Teacher is reassigned when a player has more students than other players
     */
    @Test
    @DisplayName("Test Teacher assignment")
    public void assignTeacherSameNumber() {
        gameTest = new Game(3);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        LoginManager.login("jaz", gameTest);
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        Player player_three = gameTest.getPlist().getPlayers().get(2);

        player_one.getDashboard().getHall()[0] = new Student(PawnColor.CYAN);
        player_two.getDashboard().getHall()[0] = new Student(PawnColor.CYAN);
        player_three.getDashboard().getHall()[0] = new Student(PawnColor.CYAN);

        player_one.moveStudentToClassroom(0, gameTest);
        player_two.moveStudentToClassroom(0, gameTest);
        player_three.moveStudentToClassroom(0, gameTest);

        assertNotNull(player_one.getDashboard().getTeacherTable()[4]);
        assertNull(player_three.getDashboard().getTeacherTable()[4]);

        player_three.getDashboard().addStudentToHall(new Student(PawnColor.CYAN));

        player_three.moveStudentToClassroom(0, gameTest);//second CYAN student is moved to classroom
        player_three.getDashboard().drawDashboard();
        player_one.getDashboard().drawDashboard();
        assertNull(player_one.getDashboard().getTeacherTable()[4]);
        assertNotNull(player_three.getDashboard().getTeacherTable()[4]);
    }


    /**
     * we test that a player chooses a cloud card and the remaining students on that card are 0
     */
    @Test
    @DisplayName("The player choose a cloud card")
    public void chooseCloudCardTest() {
        gameTest = new Game(2);


        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player playerTest = gameTest.getPlist().getPlayerByName("ale");
        playerTest.moveStudentToClassroom(2, gameTest);
        playerTest.moveStudentToClassroom(3, gameTest);
        playerTest.moveStudentToClassroom(4, gameTest);
        gameTest.chooseCloudCard(1, playerTest);
        assertEquals(7, playerTest.getDashboard().getHall().length);
        assertEquals(0, gameTest.getCloudCards().get(0).getAllStudents().size());
        assertEquals(3, gameTest.getCloudCards().get(1).getAllStudents().size());
    }

    /**
     * Test that island status is correctly generated
     */
    @Test
    @DisplayName("Test send islandStatus")
    public void sendIslandTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);
        int id = 1;
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        IslandStatus[] statuses = gson.fromJson(gameTest.sendIslands(), IslandStatus[].class);
        for (IslandStatus islandStatus : statuses) {
            assertEquals(islandStatus.id, id++);
            assertEquals(islandStatus.owner, "free");
            assertEquals(islandStatus.dimension, 1);
            assertEquals(islandStatus.towerNumber, 0);
            assertFalse(islandStatus.islandConquered);
            if (islandStatus.id == gameTest.getIslandWithMN().getId())
                assertTrue(islandStatus.presenceMN);
        }
    }

    /**
     * Tests sendSingleIsland with an island with mother nature
     */
    @Test
    @DisplayName("Test sendSingleIsland")
    public void sendSingleIslandTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Island islandTest = gameTest.getIslandWithMN();

        IslandStatus[] statuses = gson.fromJson(gameTest.sendSingleIsland(islandTest), IslandStatus[].class);
        for (IslandStatus status : statuses) {
            assertTrue(status.presenceMN, " must be true ");
            assertEquals(status.id, islandTest.getId());
        }

    }


    /**
     * Test that hall status is correctly generated
     */
    @Test
    @DisplayName("Test sendHall")
    public void sendHallTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player playerTest = gameTest.getPlist().getPlayerByName("nic");

        HallStatus[] statuses = gson.fromJson(gameTest.sendHall(playerTest), HallStatus[].class);
        for (HallStatus status : statuses) {
            assertEquals(status.students.length, 7);
        }
    }


    /**
     * Test that dashboard status is correctly generated
     */
    @Test
    @DisplayName("Test sendDashboard")
    public void sendDashboardTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);


        DashboardStatus[] statuses = gson.fromJson(gameTest.sendDashboard(), DashboardStatus[].class);
        for (DashboardStatus status : statuses) {
            assertEquals(status.studentsHall.length, 7);
            assertEquals(status.towers, 8);
            assertEquals(status.teacherTable.length, 5);
        }
    }

    @Test
    @DisplayName("Test sendPlayerDashboard")
    public void sendPlayerDashboardTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player playerTest = gameTest.getPlist().getPlayerByName("nic");


        DashboardStatus[] statuses = gson.fromJson(gameTest.sendPlayerDashboard(playerTest), DashboardStatus[].class);
        for (DashboardStatus status : statuses) {
            assertEquals(status.studentsHall.length, 7);
            assertEquals(status.towers, 8);
            assertEquals(status.teacherTable.length, 5);
            assertEquals(status.nameOwner, playerTest.getName());
        }
    }

    @Test
    public void sendCloudCards() {
        Gson gson = new Gson();
        gameTest = new Game(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);


        CloudCardStatus[] statuses = gson.fromJson(gameTest.sendCloudCards(), CloudCardStatus[].class);
        for (CloudCardStatus status : statuses) {
            assertEquals(status.students.size(), 3);
        }
    }

    @Test
    public void sendCharactersCardDeck() {
        Gson gson = new Gson();
        gameTest = new Game(2);

        int specialCardIndex = 0;
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        CharacterCard[] statuses = gson.fromJson(gameTest.sendCharacterCardsDeck(), CharacterCard[].class);
        for (CharacterCard status : statuses) {
            assertEquals(status.effect, gameTest.getCardsInGame().get(specialCardIndex++).getEffectOfTheCard());
        }

    }

    @Test
    public void sendPlayerTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("ale");

        gameTest.chooseDeck(1, p1);


        PlayersStatus[] statuses = gson.fromJson(gameTest.sendPlayer(p1), PlayersStatus[].class);
        for (PlayersStatus status : statuses) {
            assertEquals(status.name, p1.getName());
            assertEquals(status.movesOfMN, p1.getMovesOfMN());
            assertEquals(status.wallet, p1.getWallet().getCoins());
            assertEquals(status.movesOfStudents, p1.getMovesOfStudents());
            assertEquals(status.order, p1.getOrder());
            assertEquals(status.hasPlayed, p1.getHasPlayed());
        }
    }

    @Test
    public void sendAllPlayerTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        Player p2 = gameTest.getPlist().getPlayerByName("nic");

        gameTest.chooseDeck(1, p1);
        gameTest.chooseDeck(2, p2);


        PlayersStatus[] statuses = gson.fromJson(gameTest.sendAllPlayers(), PlayersStatus[].class);
        for (PlayersStatus status : statuses) {
            for (Player playerTest : gameTest.getPlist().getPlayers()) {
                assertEquals(status.movesOfMN, playerTest.getMovesOfMN());
                assertEquals(status.wallet, playerTest.getWallet().getCoins());
                assertEquals(status.movesOfStudents, playerTest.getMovesOfStudents());
                assertEquals(status.order, playerTest.getOrder());
                assertEquals(status.hasPlayed, playerTest.getHasPlayed());
            }
        }
    }

    @Test
    public void sendGameInfoTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        gameTest.calculateCurrentPlayer();

        GameInfoStatus[] statuses = gson.fromJson(gameTest.sendGameInfo(), GameInfoStatus[].class);
        for (GameInfoStatus status : statuses) {
            assertEquals(status.phase, "Planning phase");
            assertEquals(status.actualPlayer, gameTest.getCurrentPlayer().getName());
            assertEquals(status.round, String.valueOf(gameTest.getRound()));
        }
    }

    @Test
    public void sendDeckTest() {
        Gson gson = new Gson();
        gameTest = new Game(2);

        int idDeck = 1;

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        DeckStatus[] statuses = gson.fromJson(gameTest.sendDeck(), DeckStatus[].class);
        for (DeckStatus status : statuses) {
            assertEquals(status.id, idDeck++);
        }
    }

    @Test
    @DisplayName("Test choose deck")
    public void chooseDeckTest() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        Player p2 = gameTest.getPlist().getPlayerByName("nic");

        //player1 choose deck with id 1
        gameTest.chooseDeck(1, p1);
        assertEquals(p1.getDeck().getId(), 1);

        //player2 choose same deck of player1, but it's not admissible so the method doesn't assign him a deck
        gameTest.chooseDeck(1, p2);
        assertNull(p2.getDeck());

        //player2 choose deck with id 2
        gameTest.chooseDeck(2, p2);
        assertEquals(p2.getDeck().getId(), 2);
    }

    /**
     * Tests that the game enter the last round when a player plays his last assistant card
     */
    @Test
    @DisplayName("Test endGame after lastAssistant")
    public void playAssistantCardTest() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        Player p2 = gameTest.getPlist().getPlayerByName("nic");

        p1.setDeck(new Deck(1, "blue"));

        //removes 9 out of 10 cards from the deck
        p1.playAssistantCard(1);
        p1.playAssistantCard(2);
        p1.playAssistantCard(3);
        p1.playAssistantCard(4);
        p1.playAssistantCard(5);
        p1.playAssistantCard(6);
        p1.playAssistantCard(7);
        p1.playAssistantCard(8);
        p1.playAssistantCard(9);

        //play the last card from the deck
        gameTest.playAssistantCard(p1, 10);

        assertTrue(gameTest.isLastRound());

    }

    /**
     * We check that a removed player is correctly flagged as inactive, with no active player the game ends
     */
    @Test
    @DisplayName("Test removePlayer")
    public void removePlayerTest() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        Player p2 = gameTest.getPlist().getPlayerByName("nic");
        assertTrue(p1.isActive());
        assertTrue(p2.isActive());

        gameTest.removePlayer(p1);
        assertFalse(p1.isActive());

        gameTest.removePlayer(p2);
        assertEquals("ENDED", gameTest.getGameStatus());
    }


    /**
     * We test that the selected character card is correctly played by the current player if he has enough coins,
     * and the effect is activated
     * If the player hasn't enough coin the card isn't played
     */
    @Test
    @DisplayName("Test play character card")
    public void testCharacterCard() {

        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        Player p2 = gameTest.getPlist().getPlayerByName("nic");

        gameTest.setCurrentPlayer(p1);
        CharacterCardStrategy characterCardStrategy = new AddMoveMNStrategy();

        assertEquals(1, p1.getCoins());
        assertEquals(0, p1.getMovesOfMN());
        assertTrue(gameTest.playCharacterCard(characterCardStrategy, 0, null));
        assertEquals(2, p1.getMovesOfMN());
        assertEquals(0, p1.getCoins());//check cost is deducted from p1 wallet

        p1.addCoin(1); //add 1 coin to p1 wallet
        assertFalse(gameTest.playCharacterCard(characterCardStrategy, 0, null));// card cost 2 coins the second time it's played, cannot be played by p1


    }


    /**
     *
     */
    @Test
    @DisplayName("Test play assistant card")
    public void testPlayAssistant() {

        gameTest = new Game(3);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        LoginManager.login("jaz", gameTest);
        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        Player p2 = gameTest.getPlist().getPlayerByName("nic");
        Player p3 = gameTest.getPlist().getPlayerByName("jaz");
        gameTest.chooseDeck(1, p1);
        gameTest.chooseDeck(2, p2);
        gameTest.chooseDeck(3, p3);

        assertEquals(0, gameTest.getPhase());
        assertTrue(gameTest.playAssistantCard(p1, 1));
        assertEquals(1, p1.getMovesOfMN());
        assertFalse(gameTest.playAssistantCard(p2, 1));
        assertTrue(gameTest.playAssistantCard(p2, 2));
        assertFalse(gameTest.playAssistantCard(p3, 2));
        assertEquals(0, p3.getMovesOfMN());
        assertTrue(gameTest.playAssistantCard(p2, 4));


    }

    @Test
    @DisplayName("Test phase change")
    public void testNextPhase() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        gameTest.nextPhase();
        assertEquals(1, gameTest.getPhase());
        gameTest.nextPhase();
        assertEquals(0, gameTest.getPhase());
    }


}