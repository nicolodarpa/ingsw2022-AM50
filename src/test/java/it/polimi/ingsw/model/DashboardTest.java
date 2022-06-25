package it.polimi.ingsw.model;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for {@link Dashboard}
 */
public class DashboardTest {
    private Game gameTest;

    private final Dashboard testD = new Dashboard();


    /**
     * Test to add students, extracted casually from the student bag, to the hall
     */
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

    /**
     * Test to move students from te hall to the classroom
     */
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

    /**
     * Test the movement of players in every position in the dashboard
     */
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

    /**
     * Test teacher addition to the table
     */
    @Test
    @DisplayName(" Add 1 teacher To TeacherTable")
    public void testAddTeacherToTable() {
        testD.setupHall(2);
        Teacher teacher = new Teacher(PawnColor.RED);
        testD.addTeacherToTable(teacher);
        assertNotNull(testD.getTeacherTable()[1]);
        testD.addTeacherToTable(null);
        testD.drawDashboard();
    }

    @Test
    @DisplayName(" Draw the Dashboard")
    public void drawDashboardTest() {
        testD.setupHall(2);
        testD.addTower(8, TowerColor.black);
        testD.drawDashboard();
    }

    /**
     *We count the students in the classroom of a specified color.
     *
     */
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

    /**
     * Test the distribution of coins when adding a student to the classroom on special positions
     */
    @Test
    @DisplayName("Add Coin to the wallet of the player")
    public void addCoinTest() {
        gameTest = new Game(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        Player p2 = gameTest.getPlist().getPlayers().get(1);
        Dashboard p1dashboard = p1.getDashboard();

      for (int j = 0; j < 4; j++) {
            p1.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
      }
      p1dashboard.addCoin(p1.getWallet());
      p1.getDashboard().drawDashboard();
      p2.getDashboard().addCoin(p2.getWallet());
      p2.getDashboard().drawDashboard();
      System.out.println(p1.getName()+" ha #" + p1.getCoins() + " monete");
      System.out.println(p2.getName()+" ha #" + p2.getCoins() + " monete");

        assertFalse(p1.getDashboard().getCoinPos()[0][2]);
        assertTrue(p2.getDashboard().getCoinPos()[0][2]);
    }

    @Test
    public void getStudentFromClassroomTest() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        p1.moveStudentToClassroom(1, gameTest);
        p1.moveStudentToClassroom(2, gameTest);
        p1.moveStudentToClassroom(3, gameTest);
        p1.moveStudentToClassroom(4, gameTest);
        p1.getDashboard().drawDashboard();
        p1.getDashboard().getStudentFromClassroom(PawnColor.RED);
        p1.getDashboard().drawDashboard();
    }

    /**
     * Tests to insert an invalid input to method "getStudentFromHall)
     */
    @Test
    public void getInvalidStudentFromHallTest(){
        Dashboard dashboardTest = new Dashboard();
        dashboardTest.getStudentFromHall(2);
        dashboardTest.getStudentFromHall(10);
    }

    @Test
    public void getClassroom(){
        Dashboard dashboardTest = new Dashboard();
        assertNotNull(dashboardTest.getClassroom());
    }

    /**
     * tests the removing of a tower from the dashboard, then assert equals that there are 7 towers.
     */
    @Test
    public void removeTower(){
        Dashboard dashboardTest = new Dashboard();
        dashboardTest.setupHall(2);
        dashboardTest.drawDashboard();
        dashboardTest.addTower(8, TowerColor.black);
        dashboardTest.removeTower(7);
        assertEquals(7, dashboardTest.getTowers().size());
        dashboardTest.drawDashboard();
    }

    /**
     * Test to remove an invalid student (null student) from the dashboard.
     * It has to print "Invalid input"
     */
    @Test
    public void removeInvalidStudentFromClassroomTest(){
        Dashboard dashboardTest = new Dashboard();
        dashboardTest.getStudentFromClassroom(PawnColor.RED); // is a null student
        //It has to print "Invalid input"
    }



}
