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
        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        Player p2 = gameTest.getPlist().getPlayerByName("jaz");
        p1.getDashboard().addTeacherToTable(new Teacher(PawnColor.RED));
        p2.getDashboard().addTeacherToTable(new Teacher(PawnColor.GREEN));
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.GREEN));
        SpecialInfluenceStrategy card = new SpecialInfluenceStrategy();
        card.setPawnColor(PawnColor.RED);
        island.calcInfluence(gameTest.getPlist());
        assertEquals("ale", island.getOwner() );
        card.effect();
        island.calcInfluence(gameTest.getPlist());
        System.out.println(island.getOwner());
        assertEquals("ale",island.getOwner());
        island.addStudent(new Student(PawnColor.GREEN));
        island.calcInfluence(gameTest.getPlist());
        assertEquals("jaz", island.getOwner());



    }
}