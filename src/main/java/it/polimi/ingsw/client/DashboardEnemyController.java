package it.polimi.ingsw.client;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.server.model.TowerColor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.*;


/**
 * Controller of enemyDashboard.fxml
 */
public class DashboardEnemyController implements  Initializable,DisplayLabel{

    public Pane anchor;
    /**
     * Positions of the student in the hall
     */
    @FXML
    private Circle studentPosition1 = new Circle(), studentPosition2 = new Circle(), studentPosition3 = new Circle(), studentPosition4 = new Circle(), studentPosition5 = new Circle(), studentPosition6 = new Circle(), studentPosition7 = new Circle(), studentPosition8 = new Circle(), studentPosition9 = new Circle();

    /**
     * Positions of the towers on the dashboard
     */
    @FXML
    private ImageView towerPosition1 = new ImageView(), towerPosition2 = new ImageView(), towerPosition3 = new ImageView(), towerPosition4 = new ImageView(), towerPosition5 = new ImageView(), towerPosition6 = new ImageView(), towerPosition7 = new ImageView(), towerPosition8 = new ImageView();

    /**
     * Positions of the teachers on the dashboard
     */
    @FXML
    private Circle professorPosition1 = new Circle(), professorPosition2 = new Circle(), professorPosition3 = new Circle(), professorPosition4 = new Circle(), professorPosition5 = new Circle();

    /**
     * Positions of the green students on the dashboard's classroom.
     */
    @FXML
    private Circle greenPosition1 = new Circle(), greenPosition2 = new Circle(), greenPosition3 = new Circle(), greenPosition4 = new Circle(), greenPosition5 = new Circle(), greenPosition6 = new Circle(), greenPosition7 = new Circle(), greenPosition8 = new Circle(), greenPosition9 = new Circle(), greenPosition10 = new Circle();

    /**
     * Positions of the red students on the dashboard's classroom.
     */
    @FXML
    private Circle redPosition1 = new Circle(), redPosition2 = new Circle(), redPosition3 = new Circle(), redPosition4 = new Circle(), redPosition5 = new Circle(), redPosition6 = new Circle(), redPosition7 = new Circle(), redPosition8 = new Circle(), redPosition9 = new Circle(), redPosition10 = new Circle();

    /**
     * Positions of the yellow students on the dashboard's classroom.
     */
    @FXML
    private Circle yellowPosition1 = new Circle(), yellowPosition2 = new Circle(), yellowPosition3 = new Circle(), yellowPosition4 = new Circle(), yellowPosition5 = new Circle(), yellowPosition6 = new Circle(), yellowPosition7 = new Circle(), yellowPosition8 = new Circle(), yellowPosition9 = new Circle(), yellowPosition10 = new Circle();

    /**
     * Positions of the magenta students on the dashboard's classroom.
     */
    @FXML
    private Circle magentaPosition1 = new Circle(), magentaPosition2 = new Circle(), magentaPosition3 = new Circle(), magentaPosition4 = new Circle(), magentaPosition5 = new Circle(), magentaPosition6 = new Circle(), magentaPosition7 = new Circle(), magentaPosition8 = new Circle(), magentaPosition9 = new Circle(), magentaPosition10 = new Circle();

    /**
     * Positions of the cyan students on the dashboard's classroom.
     */
    @FXML
    private Circle cyanPosition1 = new Circle(), cyanPosition2 = new Circle(), cyanPosition3 = new Circle(), cyanPosition4 = new Circle(), cyanPosition5 = new Circle(), cyanPosition6 = new Circle(), cyanPosition7 = new Circle(), cyanPosition8 = new Circle(), cyanPosition9 = new Circle(), cyanPosition10 = new Circle();

    /**
     * It's a label that show the username of the dashboard
     */
    @FXML
    private Label username = new Label();

    private EnemyDashboardStatus dashboardStatus;



    /**
     * This arrayList contain all the circle position to show the student in the hall of the dashboard
     */
    private final ArrayList<Circle> studentsPosition = new ArrayList<>(9);
    /**
     * This arrayList contain all the ImageView position to show the towers in the dashboard
     */
    private final ArrayList<ImageView> towerPosition = new ArrayList<>(8);
    /**
     * This arrayList contain all the circle position to show the teachers in the teacherTable of the dashboard
     */
    private final ArrayList<Circle> professorsPosition = new ArrayList<>(5);
    /**
     * This arrayList contain all the circle position to show the students based on their color in the row of the classroom
     */
    private final ArrayList<Circle> greenPositions = new ArrayList<>();
    private final ArrayList<Circle> redPositions = new ArrayList<>();
    private final ArrayList<Circle> yellowPositions = new ArrayList<>();
    private final ArrayList<Circle> magentaPositions = new ArrayList<>();
    private final ArrayList<Circle> cyanPositions = new ArrayList<>();
    /**
     * It is the number of column in the classroom
     */
    private final int numberOfPositionClassroom = 10;
    private final ArrayList<ArrayList<Circle>> nameColor = new ArrayList<>();
    private static final String[] colors = {"green", "red", "yellow", "magenta", "cyan"};

    /**
     * Adds all the students positions to the arrayList colorNames
     */
    private  void setUpNameColor() {
        nameColor.addAll(Arrays.asList(greenPositions, redPositions, yellowPositions, magentaPositions, cyanPositions));
    }

    /**
     * Adds the ImageView towerPosition# on the dashboard to the arrayList 'towerPosition'.
     */
    private  void setUpTowerDashboard() {
        towerPosition.addAll(Arrays.asList(towerPosition1, towerPosition2, towerPosition3, towerPosition4, towerPosition5, towerPosition6, towerPosition7, towerPosition8));
    }

    /**
     * It takes the param classroom, and it prints the students on the dashboard's classroom.
     * @param classroom is a matrix of string sent by the EchoServer.
     */
    private  void printClassroom(@NotNull String[][] classroom) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (classroom[i][j] != null) {
                    nameColor.get(i).get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colors[i] + "_student.png")))));
                }
            }
        }
    }

    /**
     * Sets up the towers images on the dashboard
     *
     * @param towersNumber number of towers available to the player
     * @param towerColor   color of the towers
     */
    public  void setUpTowerImages(int towersNumber, TowerColor towerColor) {
        String color = towerColor.getName();
        int j = 0;
        Image imgTower = new Image(String.valueOf(getClass().getClassLoader().getResource("images/Tower/" + color + "_tower.png")));
        for (int i = 0; i < towersNumber; i++) {
            towerPosition.get(i).setImage(imgTower);
            j = i;
        }
        j++;
        while (j < towerPosition.size()) {
            towerPosition.get(j).setImage(null);
            j++;
        }
    }

    /**
     * Adds the Circle studentPosition# to the arrayList 'studentPosition'.
     * 'studentPosition' is the array of the students on the hall of tha dashboard.
     */
    public  void setUpHallPosition() {
        studentsPosition.addAll(Arrays.asList(studentPosition1, studentPosition2, studentPosition3, studentPosition4, studentPosition5, studentPosition6, studentPosition7, studentPosition8, studentPosition9));
    }
    /**
     * Adds the Circle professorPosition# to the arrayList 'professorPosition'.
     * 'professorPosition' is the array of the teachers on the TeacherTable of tha dashboard.
     */
    public  void setUpProfessorPositions() {
        professorsPosition.addAll(Arrays.asList(professorPosition1, professorPosition2, professorPosition3, professorPosition4, professorPosition5));
    }

    /**
     * Sets all the arraylist of the dashboard's classroom.
     */
    public  void setUpClassroom() {
        setUpColorPosition(greenPositions, greenPosition1, greenPosition2, greenPosition3, greenPosition4, greenPosition5, greenPosition6, greenPosition7, greenPosition8, greenPosition9, greenPosition10);
        setUpColorPosition(magentaPositions, magentaPosition1, magentaPosition2, magentaPosition3, magentaPosition4, magentaPosition5, magentaPosition6, magentaPosition7, magentaPosition8, magentaPosition9, magentaPosition10);
        setUpColorPosition(yellowPositions, yellowPosition1, yellowPosition2, yellowPosition3, yellowPosition4, yellowPosition5, yellowPosition6, yellowPosition7, yellowPosition8, yellowPosition9, yellowPosition10);
        setUpColorPosition(cyanPositions, cyanPosition1, cyanPosition2, cyanPosition3, cyanPosition4, cyanPosition5, cyanPosition6, cyanPosition7, cyanPosition8, cyanPosition9, cyanPosition10);
        setUpColorPosition(redPositions, redPosition1, redPosition2, redPosition3, redPosition4, redPosition5, redPosition6, redPosition7, redPosition8, redPosition9, redPosition10);
    }

    /**
     * set to transparent the background of the position of the students in the classroom
     *
     * @param colorPosition indicate the student's color of the row in the classroom
     */
    public  void setTransparentCircle(ArrayList<Circle> colorPosition) {
        for (Circle circle : colorPosition) {
            circle.setFill(null);
            circle.setStroke(null);
        }
    }


    /**
     * It's the function that add to each arraylist, of each students' color, of the classroom every circle of each color.
     * It also set all the circle transparent.
     *
     * @param colorPositions is the arraylist considerated.
     * @param colorPosition1 is the 1st circle of the classroom of each color.
     * @param colorPosition2 is the 2nd circle of the classroom of each color.
     * @param colorPosition3 is the 3rd circle of the classroom of each color.
     * @param colorPosition4 is the 4th circle of the classroom of each color.
     * @param colorPosition5 is the 5th circle of the classroom of each color.
     * @param colorPosition6 is the 6th circle of the classroom of each color.
     * @param colorPosition7 is the 7th circle of the classroom of each color.
     * @param colorPosition8 is the 8th circle of the classroom of each color.
     * @param colorPosition9 is the 9th circle of the classroom of each color.
     * @param colorPosition10 is the 10th circle of the classroom of each color.
     */
    private  void setUpColorPosition(ArrayList<Circle> colorPositions, Circle colorPosition1, Circle colorPosition2, Circle colorPosition3, Circle colorPosition4, Circle colorPosition5, Circle colorPosition6, Circle colorPosition7, Circle colorPosition8, Circle colorPosition9, Circle colorPosition10) {
        colorPositions.addAll(Arrays.asList(colorPosition1, colorPosition2, colorPosition3, colorPosition4, colorPosition5, colorPosition6, colorPosition7, colorPosition8, colorPosition9, colorPosition10));
        setTransparentCircle(colorPositions);
    }


    /**
     * sets the images of the teachers on the dashbaord.
     *
     * @param teachers is string of the teachers that the player has.
     */
    public  void setUpProfessorImages(String[] teachers) {
        int i = 0;
        for (String teacher : teachers) {
            if (teacher != null) {
                professorsPosition.get(i).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colors[i] + "_professor.png")))));
            } else professorsPosition.get(i).setFill(null);
            i++;
        }
    }

    /**
     * sets the images of the student on the hall of the dashboard.
     *
      * @param hall is the string of the students on the hall of the dashbaord.
     */
    public  void setUpHallImages(@NotNull String[] hall) {
        int i = 0;
        for (String colorOfStudent : hall) {
            if (colorOfStudent != null) {
                studentsPosition.get(i).setDisable(false);
                studentsPosition.get(i).setStroke(null);
                studentsPosition.get(i).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colorOfStudent + "_student.png")))));
            } else {
                studentsPosition.get(i).setFill(null);
                studentsPosition.get(i).setDisable(true);
            }
            i++;
        }
    }


    /**
     * Display username of the player.
     * By a Json formatted string sent by the server, it sets the students' and teachers' images in the  dashboard and the tower images.
     */
    public  void setDashboardPlayer() {
        displayLabel("username", username, dashboardStatus.nameOwner);
        String[] hall = dashboardStatus.studentsHallColors;
        String[][] classroom = dashboardStatus.studentsClassroom;
        String[] teachers = dashboardStatus.teacherTable;
        setUpHallImages(hall);
        printClassroom(classroom);
        setUpProfessorImages(teachers);
        setUpTowerImages(dashboardStatus.towers, dashboardStatus.towerColor);
    }



    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public synchronized void initialize(URL location, ResourceBundle resources) {
        setUpNameColor();
        setUpClassroom();
        setUpProfessorPositions();
        setUpTowerDashboard();
        setUpHallPosition();
        setDashboardPlayer();
    }

    public void setDashboardStatus(EnemyDashboardStatus dashboardStatus){
        this.dashboardStatus = dashboardStatus;
    }


}






