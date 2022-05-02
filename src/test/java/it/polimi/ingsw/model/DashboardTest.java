package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DashboardTest {
    private Game gameTest;

    Dashboard testD = new Dashboard();


    @Test
    @DisplayName(" Add Student to Hall")
    public void testAddStudentToHall() {
        testD.setupHall(2);
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        for (int i = 0; i < 7; i++) {
            Student student = studentsBag.casualExtraction();
            System.out.print(i + ": ");
            student.draw();
            System.out.println(" ");
            testD.addStudentToHall(student);
        }
        testD.drawDashboard();
    }

    @Test
    @DisplayName(" Add 3 Student to Classroom ")
    public void testAddStudentToClassroom() {
        testD.setupHall(2);
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        for (int i = 0; i < 7; i++) {
            testD.addStudentToHall(studentsBag.casualExtraction());
        }
        testD.addStudentToClassroom(testD.getStudentFromHall(3));
        testD.addStudentToClassroom(testD.getStudentFromHall(4));
        testD.addStudentToClassroom(testD.getStudentFromHall(1));
        testD.drawDashboard();

    }

    @Test
    @DisplayName(" Fill the Classroom ")
    public void testFillClassroom() {
        testD.setupHall(2);
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        for (int i = 0; i < 120; i++) {
            testD.addStudentToClassroom(studentsBag.casualExtraction());
        }
        testD.drawDashboard();
        assertEquals(10, testD.countStudentByColor(PawnColor.CYAN));
        assertEquals(10, testD.countStudentByColor(PawnColor.MAGENTA));
        assertEquals(10, testD.countStudentByColor(PawnColor.YELLOW));
        assertEquals(10, testD.countStudentByColor(PawnColor.RED));
        assertEquals(10, testD.countStudentByColor(PawnColor.GREEN));
    }

    @Test
    @DisplayName(" Add 1 teacher To TeacherTable")
    public void testAddTeacherToTable() {
        testD.setupHall(2);
        Teacher teacher = new Teacher(PawnColor.RED);
        testD.addTeacherToTable(teacher);
        assertNotNull(testD.getTeacherTable()[3]);
        testD.drawDashboard();
    }

    @Test
    @DisplayName(" Draw the Dashboard")
    public void drawDashboardTest() {
        testD.setupHall(2);
        testD.addTower(8, TowerColor.black);
        testD.drawDashboard();
    }

    @Test
    @DisplayName(" Count the students by their color ")
    public void countStudentByColorTest() {
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        // fill the classroom
        for (int i = 0; i < 120; i++) {
            testD.addStudentToClassroom(studentsBag.casualExtraction());
        }
        assertEquals(10, testD.countStudentByColor(PawnColor.RED));
    }

    @Test
    @DisplayName("Add Coin to the wallet of the player")

    public void addCoinTest() {

        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();

        Player p1 = gameTest.getPlist().getPlayers().get(0);
        for (int i = 0; i < 7; i++) {
            p1.moveStudentToClassroom(i, gameTest);
        }

        Student s = new Student(PawnColor.CYAN);
        for (int j = 0; j < 4; j++) {
            p1.getDashboard().addStudentToHall(s);
        }
        for (int i = 0; i < 4; i++) {
            p1.moveStudentToClassroom(i, gameTest);
        }
        p1.getDashboard().drawDashboard();
        //p1.getDashboard().addCoin(p1.getWallet());
        System.out.println("Il player ha #" + p1.getWallet().getCoins() + " monete");
        assertEquals(false, p1.getDashboard().getCoinPos()[0][2]);
    }

    @Test
    public void getStudentFromClassroomTest(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        p1.moveStudentToClassroom(1, gameTest);
        p1.moveStudentToClassroom(2, gameTest);
        p1.moveStudentToClassroom(3,gameTest);
        p1.moveStudentToClassroom(4,gameTest);
        p1.getDashboard().drawDashboard();
        p1.getDashboard().getStudentFromClassroom(PawnColor.RED);
        p1.getDashboard().drawDashboard();

    }


}
