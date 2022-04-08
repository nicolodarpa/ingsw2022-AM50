package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest{
    @Test
    public void testMoveStudentsToHall() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        game.moveStudentsToHall();
        assertEquals(114-14, game.getStudentsInBag());

    }
    @Test
    public void testSetupGame() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        assertEquals(114, game.getStudentsInBag());
    }

    @Test
    public void testCreateIsland(){
        Game game = new Game();
        game.createIslands();
        assertEquals(12,game.getIslands().size());
    }

    @Test
    public void testCloudcardFill(){
        Game game = new Game();
        game.setNumberOfPlayers(2);
        game.cloudCardCreation();
        assertEquals(2,game.getCloudCards().size());
    }

}