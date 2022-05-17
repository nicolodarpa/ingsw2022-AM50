package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveStudentTest {


    @Test
    void removeStudent() {
        Dashboard dashboardTest = new Dashboard();
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(0);
        for (int i = 0; i<3;i++){
            dashboardTest.addStudentToClassroom(new Student(PawnColor.GREEN));
        }
        assertEquals(3,dashboardTest.countStudentByColor(PawnColor.GREEN));
        RemoveStudent removeStudent = new RemoveStudent();
        removeStudent.setPawnColor(PawnColor.GREEN);
        removeStudent.setBag(studentsBag);
        removeStudent.removeStudent(dashboardTest);
        assertEquals(0,dashboardTest.countStudentByColor(PawnColor.GREEN));
        assertEquals(3,studentsBag.getNum());
    }

    @Test
    void effect(){
        Game gameTest;
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        Player ale = gameTest.getPlist().getPlayerByName("ale");
        Dashboard dashboardTest = ale.getDashboard();
        for (int i = 0; i<3;i++){
            dashboardTest.addStudentToClassroom(new Student(PawnColor.GREEN));
        }
        RemoveStudent card = new RemoveStudent();
        card.setPlayersList(gameTest.getPlist());
        card.setPawnColor(PawnColor.GREEN);
        card.setBag(gameTest.getStudentsBag());
        card.effect();
        assertEquals(0,dashboardTest.countStudentByColor(PawnColor.GREEN));
    }
}