package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialInfluenceTest {

    @Test
    void effect() {
        Game gameTest = new Game(3);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        LoginManager.login("jaz", gameTest);
        Island island = new Island(3);
        Player ale = gameTest.getPlist().getPlayerByName("ale");
        Player jaz = gameTest.getPlist().getPlayerByName("jaz");
        ale.getDashboard().addTeacherToTable(new Teacher(PawnColor.RED));
        jaz.getDashboard().addTeacherToTable(new Teacher(PawnColor.GREEN));
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.GREEN));
        SpecialInfluenceStrategy card = new SpecialInfluenceStrategy();
        card.setPawnColor(PawnColor.RED);
        island.calculateInfluence(gameTest.getPlist());
        assertEquals("ale", island.getOwner() );
        card.effect();
        island.calculateInfluence(gameTest.getPlist());
        System.out.println(island.getOwner());
        assertEquals("ale",island.getOwner());
        island.addStudent(new Student(PawnColor.GREEN));
        island.calculateInfluence(gameTest.getPlist());
        assertEquals("jaz", island.getOwner());



    }
}