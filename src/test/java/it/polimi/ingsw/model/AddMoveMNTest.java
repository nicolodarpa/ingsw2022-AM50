package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class AddMoveMNTest {

    private Game gameTest;

    @Test
    public void effectTest(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        Player p2 = gameTest.getPlist().getPlayers().get(1);
        Deck d1 = new Deck(1);
        Deck d2 = new Deck(2);
        p1.setDeck(d1);
        p2.setDeck(d2);
        p1.playAssistantCard(3);
        p2.playAssistantCard(5);
        gameTest.setActualPlayer();
        SpecialCard cardTest = new AddMoveMN(gameTest.getActualPlayer());
        cardTest.effect();
        assertEquals(4,gameTest.getActualPlayer().getMovesOfMN());
    }

    @Test
    public void testGetCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        NoTowerInfluence card = new NoTowerInfluence(gameTest.getIslandWithMN(), gameTest.getPlist());
        System.out.println(card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        NoTowerInfluence card = new NoTowerInfluence(gameTest.getIslandWithMN(), gameTest.getPlist());
        System.out.println(card.getCost());
        card.addCost();
        System.out.println(card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        NoTowerInfluence card = new NoTowerInfluence(gameTest.getIslandWithMN(), gameTest.getPlist());
        System.out.println(card.getEffectOfTheCard());
    }


}
