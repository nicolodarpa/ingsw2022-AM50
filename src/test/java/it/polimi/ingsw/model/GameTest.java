package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.CharacterCards.SpecialCard;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class GameTest{

    private Game gameTest;

    @Test
    public void testMoveStudentsToHall() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        assertEquals(100, gameTest.getStudentsInBag() );
    }

    @Test
    @DisplayName("testing the setup of a new match")
    public void testSetupGame() {
        boolean presenceOfMN = false;
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        assertEquals(100, gameTest.getStudentsInBag());
        assertEquals(2,gameTest.getCloudCards().size());
        for(int i = 0; i < 2; i++)
            assertEquals(3,gameTest.getCloudCards().get(i).getStudents().size());
        assertEquals(12,gameTest.getIslands().size());
        Table t = new Table(gameTest.getCloudCards(), gameTest.getIslands());
        t.drawTable();
        for(Player p : gameTest.getPlist().getPlayers())
            assertEquals(8,p.getDashboard().getTowers().size());
        assertEquals(3, gameTest.getCardsInGame().size());
    }

    @Test
    @DisplayName(" Test that there are 12 Islands")
    public void createIslandTest() {
        gameTest = new Game();
        gameTest.createIslands();
        assertEquals(12,gameTest.getIslands().size());
    }

    @Test
    @DisplayName(" Test the assignment of the Teacher")
    public void assignTeacherTest(){
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

        for(Island i : gameTest.getIslands()){
            if(!i.getPresenceMN() && !i.getOppositeMN()){
                assertEquals(1,i.getStudentList().size());
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
            System.out.println("Numero isola: " + i.getId()+ " - dimension: " + i.getDimension() +", owner: " + i.getOwner());
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

    @Test
    @DisplayName("connect island 1 an 12")
    public  void connectExtremeIslands(){
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

        assertEquals(q.getName(),gameTest.getIslands().get(9).getOwner());



    }

    @Test
    public void setActualPlayerTest(){
        gameTest = new Game(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Deck deck1 = new Deck(1,"BLUE");
        Deck deck2 = new Deck(2,"PINK");
        Player playerOne = gameTest.getPlist().getPlayerByName("jaz");
        Player playerTwo = gameTest.getPlist().getPlayerByName("nic");
        playerOne.setDeck(deck1);
        playerTwo.setDeck(deck2);
        playerOne.playAssistantCard(3);
        playerTwo.playAssistantCard(6);
        gameTest.setActualPlayer();
        assertEquals(playerOne, gameTest.getActualPlayer());
    }

    @Test
    public void extractSpecialCardTest(){
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        assertEquals(3, gameTest.getCardsInGame().size());
        for(SpecialCard card : gameTest.getCardsInGame()){
            System.out.println("==" + card.getEffectOfTheCard());
        }

    }

    @Test
    public void chooseCloudCard(){
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("jaz");
        p1.moveStudentToClassroom(3, gameTest);
        p1.moveStudentToIsland(gameTest.getIslandWithMN(), 4);
        p1.moveStudentToClassroom(5, gameTest);
        p1.getDashboard().drawDashboard();
    }


    @Test
    @DisplayName("move MN")
    public void moveMN(){
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("jaz");
        p1.setMovesOfMN(12);
        int i = gameTest.getIslandWithMNIndex();
        System.out.println(i);
        if(i != 8)
            assertTrue(gameTest.moveMN(p1, 8));
        else
            assertFalse(gameTest.moveMN(p1, 8));
        if (i!=8){
            assertFalse(gameTest.getIslands().get(i).getPresenceMN());
        } else assertTrue(gameTest.getIslands().get(i).getPresenceMN());
    }




    @Test
    @DisplayName(" Test the assignment of the Teacher for 3 players")
    public void assignTeacherForThreePlayersTest(){
        gameTest = new Game(3);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        LoginManager.login("jaz",gameTest);

        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        Player player_three= gameTest.getPlist().getPlayers().get(2);

        player_one.moveStudentToClassroom(1, gameTest);
        for (int i = 0; i<2; i++){
            Student studentColor = new Student(PawnColor.CYAN);
            player_one.getDashboard().addStudentToHall(studentColor);
            player_one.moveStudentToClassroom(1,gameTest);
        }

        player_two.moveStudentToClassroom(1,gameTest);
        for (int i = 0; i<4; i++){
            Student studentColor = new Student(PawnColor.CYAN);
            player_two.getDashboard().addStudentToHall(studentColor);
            player_two.moveStudentToClassroom(1,gameTest);
        }

        player_three.moveStudentToClassroom(0,gameTest);


        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();
        player_three.getDashboard().drawDashboard();
        assertEquals(player_two.getDashboard().getTeacherTable()[0].getColor(),PawnColor.CYAN);

    }



    @Test
    @DisplayName("The player choose a cloud card")
    public void chooseCloudCardTest(){
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player playerTest = gameTest.getPlist().getPlayerByName("ale");
        playerTest.moveStudentToClassroom(2,gameTest);
        playerTest.moveStudentToClassroom(3,gameTest);
        playerTest.moveStudentToClassroom(4,gameTest);
        gameTest.chooseCloudCard(1, playerTest);
        assertEquals(7,playerTest.getDashboard().getHall().length);
        assertEquals(0, gameTest.getCloudCards().get(1).getAllStudents().size());
    }

}