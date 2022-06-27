package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.TowerColor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller of dashboard.fxml
 */
public class DashboardController implements Initializable, DisplayLabel {

    public Pane anchor;

    @FXML
    private Label movesAvailableCounter, movesOfMn, username, roundCounter, actualPlayerLabel, students, wallet;

    @FXML
    private Button assistantCardButton = new Button();

    int movesOfMN, movesOfStudent;

    /**
     * Positions of the student in the hall
     */
    @FXML
    private Circle studentPosition1, studentPosition2, studentPosition3, studentPosition4, studentPosition5, studentPosition6, studentPosition7, studentPosition8, studentPosition9;
    /**
     * Positions of the towers on the dashboard
     */
    @FXML
    private ImageView towerPosition1, towerPosition2, towerPosition3, towerPosition4, towerPosition5, towerPosition6, towerPosition7, towerPosition8;
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
     * Positions of the magenta students son the dashboard's classroom.
     */
    @FXML
    private Circle magentaPosition1 = new Circle(), magentaPosition2 = new Circle(), magentaPosition3 = new Circle(), magentaPosition4 = new Circle(), magentaPosition5 = new Circle(), magentaPosition6 = new Circle(), magentaPosition7 = new Circle(), magentaPosition8 = new Circle(), magentaPosition9 = new Circle(), magentaPosition10 = new Circle();

    /**
     * Positions of the cyan students on the dashboard's classroom.
     */
    @FXML
    private Circle cyanPosition1 = new Circle(), cyanPosition2 = new Circle(), cyanPosition3 = new Circle(), cyanPosition4 = new Circle(), cyanPosition5 = new Circle(), cyanPosition6 = new Circle(), cyanPosition7 = new Circle(), cyanPosition8 = new Circle(), cyanPosition9 = new Circle(), cyanPosition10 = new Circle();

    /**
     * It's the area of the classroom.
     */
    @FXML
    private Rectangle classRoom = new Rectangle();

    /**
     * Positions of the islands when MotherNature is moved.
     */
    @FXML
    private Rectangle MNIsland1 = new Rectangle(), MNIsland2 = new Rectangle(), MNIsland3 = new Rectangle(), MNIsland4 = new Rectangle(), MNIsland5 = new Rectangle(), MNIsland6 = new Rectangle(), MNIsland7 = new Rectangle(), MNIsland8 = new Rectangle(), MNIsland9 = new Rectangle(), MNIsland10 = new Rectangle(), MNIsland11 = new Rectangle(), MNIsland12 = new Rectangle();

    /**
     * ImageView of the images of the islands.
     */
    @FXML

    private ImageView Island1 = new ImageView(), Island2 = new ImageView(), Island3 = new ImageView(), Island4 = new ImageView(), Island5 = new ImageView(), Island6 = new ImageView(), Island7 = new ImageView(), Island8 = new ImageView(), Island9 = new ImageView(), Island10 = new ImageView(), Island11 = new ImageView(), Island12 = new ImageView();

    /**
     * Positions of the green students on each island.
     */
    @FXML
    private Circle greenStudentIsland1 = new Circle(), greenStudentIsland2 = new Circle(), greenStudentIsland3 = new Circle(), greenStudentIsland4 = new Circle(), greenStudentIsland5 = new Circle(), greenStudentIsland6 = new Circle(), greenStudentIsland7 = new Circle(), greenStudentIsland8 = new Circle(), greenStudentIsland9 = new Circle(), greenStudentIsland10 = new Circle(), greenStudentIsland11 = new Circle(), greenStudentIsland12 = new Circle();

    /**
     * Positions of the red students on each island.
     */
    @FXML
    private Circle redStudentIsland1 = new Circle(), redStudentIsland2 = new Circle(), redStudentIsland3 = new Circle(), redStudentIsland4 = new Circle(), redStudentIsland5 = new Circle(), redStudentIsland6 = new Circle(), redStudentIsland7 = new Circle(), redStudentIsland8 = new Circle(), redStudentIsland9 = new Circle(), redStudentIsland10 = new Circle(), redStudentIsland11 = new Circle(), redStudentIsland12 = new Circle();

    /**
     * Positions of the yellow students on each island.
     */
    @FXML
    private Circle yellowStudentIsland1 = new Circle(), yellowStudentIsland2 = new Circle(), yellowStudentIsland3 = new Circle(), yellowStudentIsland4 = new Circle(), yellowStudentIsland5 = new Circle(), yellowStudentIsland6 = new Circle(), yellowStudentIsland7 = new Circle(), yellowStudentIsland8 = new Circle(), yellowStudentIsland9 = new Circle(), yellowStudentIsland10 = new Circle(), yellowStudentIsland11 = new Circle(), yellowStudentIsland12 = new Circle();

    /**
     * Positions of the magenta students on each island.
     */
    @FXML
    private Circle magentaStudentIsland1 = new Circle(), magentaStudentIsland2 = new Circle(), magentaStudentIsland3 = new Circle(), magentaStudentIsland4 = new Circle(), magentaStudentIsland5 = new Circle(), magentaStudentIsland6 = new Circle(), magentaStudentIsland7 = new Circle(), magentaStudentIsland8 = new Circle(), magentaStudentIsland9 = new Circle(), magentaStudentIsland10 = new Circle(), magentaStudentIsland11 = new Circle(), magentaStudentIsland12 = new Circle();

    /**
     * Positions of the cyan students on each island.
     */
    @FXML
    private Circle cyanStudentIsland1 = new Circle(), cyanStudentIsland2 = new Circle(), cyanStudentIsland3 = new Circle(), cyanStudentIsland4 = new Circle(), cyanStudentIsland5 = new Circle(), cyanStudentIsland6 = new Circle(), cyanStudentIsland7 = new Circle(), cyanStudentIsland8 = new Circle(), cyanStudentIsland9 = new Circle(), cyanStudentIsland10 = new Circle(), cyanStudentIsland11 = new Circle(), cyanStudentIsland12 = new Circle();

    /**
     * Positions of Mother Nature on each island.
     */
    @FXML
    private ImageView MNPosition1 = new ImageView(), MNPosition2 = new ImageView(), MNPosition3 = new ImageView(), MNPosition4 = new ImageView(), MNPosition5 = new ImageView(), MNPosition6 = new ImageView(), MNPosition7 = new ImageView(), MNPosition8 = new ImageView(), MNPosition9 = new ImageView(), MNPosition10 = new ImageView(), MNPosition11 = new ImageView(), MNPosition12 = new ImageView();

    /**
     * Positions of the towers on each island.
     */
    @FXML
    private ImageView towerIsland1 = new ImageView(), towerIsland2 = new ImageView(), towerIsland3 = new ImageView(), towerIsland4 = new ImageView(), towerIsland5 = new ImageView(), towerIsland6 = new ImageView(), towerIsland7 = new ImageView(), towerIsland8 = new ImageView(), towerIsland9 = new ImageView(), towerIsland10 = new ImageView(), towerIsland11 = new ImageView(), towerIsland12 = new ImageView();

    /**
     * Positions of the CloudCard.
     */
    @FXML
    private Circle cloudCard1 = new Circle(), cloudCard2 = new Circle(), cloudCard3 = new Circle();

    /**
     * Positions of the students on the CloudCard 1.
     */
    @FXML
    private Circle student1CC1 = new Circle(), student2CC1 = new Circle(), student3CC1 = new Circle(), student4CC1 = new Circle();

    /**
     * Positions of the students on the CloudCard 2.
     */
    @FXML
    private Circle student1CC2 = new Circle(), student2CC2 = new Circle(), student3CC2 = new Circle(), student4CC2 = new Circle();

    /**
     * Positions of the students on the CloudCard 3.
     */
    @FXML
    private Circle student1CC3 = new Circle(), student2CC3 = new Circle(), student3CC3 = new Circle(), student4CC3 = new Circle();

    /**
     * It's an array of all the button of the islands.
     */
    @FXML
    private final ArrayList<Button> islandInfoButtons = new ArrayList<>();

    /**
     * Button to show the info of island 1.
     */
    @FXML
    private Button info0 = new Button();

    /**
     * Button shows the info of island 2.
     */
    @FXML
    private Button info1 = new Button();

    /**
     * Button shows the info of island 3.
     */
    @FXML
    private Button info2 = new Button();

    /**
     * Button shows the info of island 4.
     */
    @FXML
    private Button info3 = new Button();

    /**
     * Button shows the info of island 5.
     */
    @FXML
    private Button info4 = new Button();

    /**
     * Button shows the info of island 6.
     */
    @FXML
    private Button info5 = new Button();

    /**
     * Button shows the info of island 7.
     */
    @FXML
    private Button info6 = new Button();

    /**
     * Button shows the info of island 8.
     */
    @FXML
    private Button info7 = new Button();

    /**
     * Button shows the info of island 9.
     */
    @FXML
    private Button info8 = new Button();

    /**
     * Button shows the info of island 10.
     */
    @FXML
    private Button info9 = new Button();

    /**
     * Button shows the info of island 11.
     */
    @FXML
    private Button info10 = new Button();

    /**
     * Button shows the info of island 12.
     */
    @FXML
    private Button info11 = new Button();


    private int index = -1, indexIsland, indexMN;

    /**
     * This arrayList contain all the circle position where the red students are shown on each island.
     */
    private final ArrayList<Circle> redStudentIsland = new ArrayList<>();

    /**
     * This arrayList contain all the circle position where the green students are shown on each island.
     */
    private final ArrayList<Circle> greenStudentIsland = new ArrayList<>();

    /**
     * This arrayList contain all the circle position where the yellow students are shown on each island.
     */
    private final ArrayList<Circle> yellowStudentIsland = new ArrayList<>();

    /**
     * This arrayList contain all the circle position where the magenta students are shown on each island.
     */
    private final ArrayList<Circle> magentaStudentIsland = new ArrayList<>();

    /**
     * This arrayList contain all the circle position where the cyan students are shown on each island.
     */
    private final ArrayList<Circle> cyanStudentIsland = new ArrayList<>();

    /**
     * This arrayList contain all the circle of the CloudCards.
     */
    private final ArrayList<Circle> cloudCards = new ArrayList<>(3);

    /**
     * This arrayList contain all the circle position of the students shown on the CloudCard 1.
     */
    private final ArrayList<Circle> studentsCC1 = new ArrayList<>();

    /**
     * This arrayList contain all the circle position of the students shown on the CloudCard 2.
     */
    private final ArrayList<Circle> studentsCC2 = new ArrayList<>();

    /**
     * This arrayList contain all the circle position of the students shown on the CloudCard 3.
     */
    private final ArrayList<Circle> studentsCC3 = new ArrayList<>();

    /**
     * This arrayList contain all the circle position of the students shown on the  CloudCards.
     */
    private final ArrayList<ArrayList<Circle>> studentsInEachCloudCard = new ArrayList<>();


    /**
     * This arrayList contain all the circle position that show the student in the hall of the dashboard.
     */
    private final ArrayList<Circle> studentsPosition = new ArrayList<>(9);

    /**
     * This arrayList contain all the ImageView position that show the towers in the dashboard.
     */
    private final ArrayList<ImageView> towerPosition = new ArrayList<>(8);

    /**
     * This arrayList contain all the circle position that show the teachers in the teacherTable of the dashboard.
     */
    private final ArrayList<Circle> professorsPosition = new ArrayList<>(5);

    /**
     * This arrayList contain all the circle position that show the students based on their color in the row of the classroom
     */
    private final ArrayList<Circle> greenPositions = new ArrayList<>();
    private final ArrayList<Circle> redPositions = new ArrayList<>();
    private final ArrayList<Circle> yellowPositions = new ArrayList<>();
    private final ArrayList<Circle> magentaPositions = new ArrayList<>();
    private final ArrayList<Circle> cyanPositions = new ArrayList<>();

    /**
     * ArrayList that contain all the ImageView to show the islands
     */
    private final ArrayList<ImageView> Islands = new ArrayList<>(12);

    /**
     * Holds the positions of mother nature on every island
     */
    private final ArrayList<ImageView> MNPositions = new ArrayList<>(12);

    /**
     * Holds the positions of students on every island
     */
    private final ArrayList<ArrayList<Circle>> studentsOnEachIsland = new ArrayList<>();

    /**
     * An arrayList that contain the island where Mother nature is moved.
     */
    private final ArrayList<Rectangle> MnIslandPosition = new ArrayList<>(12);

    /**
     * A button that allow you to watch the second player's dashboard.
     */
    @FXML
    private Button dashboardButton1;

    /**
     * A button that allow you to watch the third player's dashboard.
     */
    @FXML
    private Button dashboardButton2;

    /**
     * An array of the CharacterCards in the game.
     */
    private CharacterCard[] characterCardList = new CharacterCard[3];

    /**
     * An array of the dashboards of the other players.
     */
    private EnemyDashboardStatus[] enemyDashboard = new EnemyDashboardStatus[2];

    /**
     * An arraylist of the ImageView of the towers on each island.
     */
    private final ArrayList<ImageView> towerIslands = new ArrayList<>();

    /**
     * An arraylist of the arraylist of the students in the classroom.
     */
    private final ArrayList<ArrayList<Circle>> colorNames = new ArrayList<>();
    private final ClientInput clientInput = ClientInput.getInstance();
    private final Gson gson = new Gson();
    private static final String[] colors = {"green", "red", "yellow", "magenta", "cyan"};
    private TextMessage message;
    private final HashMap<String, Command> commandHashMap = new HashMap<>();

    @Override
    public void displayLabel(@NotNull String text, Label label, String textLabel) {
        DisplayLabel.super.displayLabel(text, label, textLabel);
    }


    /**
     * Sets up  the arrayList of cloud cards images and positions, sets the positions of students pawn on each cloud card
     */
    private void setUpCCArrays() {
        cloudCards.addAll(Arrays.asList(cloudCard1, cloudCard2, cloudCard3));
        setTransparentCircle(cloudCards);
        setUpStudentCC(studentsCC1, student1CC1, student2CC1, student3CC1, student4CC1);
        setUpStudentCC(studentsCC2, student1CC2, student2CC2, student3CC2, student4CC2);
        setUpStudentCC(studentsCC3, student1CC3, student2CC3, student3CC3, student4CC3);
        studentsInEachCloudCard.add(studentsCC1);
        studentsInEachCloudCard.add(studentsCC2);
        studentsInEachCloudCard.add(studentsCC3);


    }

    /**
     *By a Json formatted string sent from the server, it sets the CloudCards .
     */
    private void setCloudCards() {
        CloudCardStatus[] cloudCardStatus = gson.fromJson(message.message, CloudCardStatus[].class);
        setUpCCImages(cloudCardStatus);
        setUpCloudCards(cloudCardStatus);
    }

    /**
     * Sets up the students pawns on cloud cards.
     *
     * @param cloudCardsStatus list of cloud cards statuses
     */
    public void setUpCloudCards(CloudCardStatus[] cloudCardsStatus) {
        int id = 0;
        int i = 0;
        for (CloudCardStatus cardsStatus : cloudCardsStatus) {
            if (cardsStatus.students.size() == 0) {
                for (Circle student : studentsInEachCloudCard.get(id)) {
                    student.setFill(null);
                }
            } else {
                for (String student : cardsStatus.studentsColors) {
                    studentsInEachCloudCard.get(id).get(i).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + student + "_student.png")))));
                    i++;
                }
            }
            i = 0;
            id++;
        }
    }


    /**
     * Sets the images of the cloud cards
     *
     * @param cloudCardsStatus list of cloud cards statuses
     */

    private void setUpCCImages(CloudCardStatus[] cloudCardsStatus) {
        int i = 0;
        int id;

        for (Circle c : cloudCards) {
            if (i <cloudCardsStatus.length) {
                id = cloudCardsStatus[i].id;
                c.setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/cloudcard/cc" + (id+1)+ ".png")))));
            }
            else if (cloudCardsStatus.length == 2 && i >= 2)
                c.setFill(null);
            i++;
        }
    }

    /**
     * It's the function that add every circle of the students on each CloudCard.
     * @param cloudCards is the arraylist of the CloudCard considerated..
     * @param student1 represents the 1st student on the CloudCard.
     * @param student2 represents the 2nd student on the CloudCard.
     * @param student3 represents the 3rd student on the CloudCard.
     * @param student4 represents the 4th student on the CloudCard.
     */
    private void setUpStudentCC(ArrayList<Circle> cloudCards, Circle student1, Circle student2, Circle student3, Circle student4) {
        cloudCards.add(student1);
        cloudCards.add(student2);
        cloudCards.add(student3);
        cloudCards.add(student4);
        setTransparentCircle(cloudCards);
    }

    /**
     * Adds to arrayList Island all the island Images.
     */
    private void setUpIslandImageView() {
        Islands.addAll(Arrays.asList(Island1, Island2, Island3, Island4, Island5, Island6, Island7, Island8, Island9, Island10, Island11, Island12));
    }

    /**
     * Adds al the Info buttons of the islands to the arrayList islandInfoButton
     */
    private void setUpIslandInfoButton() {
        islandInfoButtons.addAll(Arrays.asList(info0, info1, info2, info3, info4, info5, info6, info7, info8, info9, info10, info11));
    }

    /**
     * Adds to the arrayList MNIslandPosition all the Mn Rectangle.
     */
    private void setUpMNIslandPosition() {
        MnIslandPosition.addAll(Arrays.asList(MNIsland1, MNIsland2, MNIsland3, MNIsland4, MNIsland5, MNIsland6, MNIsland7, MNIsland8, MNIsland9, MNIsland10, MNIsland11, MNIsland12));
        disableMNIslandPosition();
    }

    /**
     * Adds to arrayList MNIslandPosition all the Mn Images.
     */
    private void setUpMNPositions() {
        MNPositions.addAll(Arrays.asList(MNPosition1, MNPosition2, MNPosition3, MNPosition4, MNPosition5, MNPosition6, MNPosition7, MNPosition8, MNPosition9, MNPosition10, MNPosition11, MNPosition12));
    }


    /**
     * Adds all the students positions to the arrayList colorNames
     */
    private void setUpNameColor() {
        colorNames.addAll(Arrays.asList(greenPositions, redPositions, yellowPositions, magentaPositions, cyanPositions));
    }

    /**
     * Adds the towers position on the islands to the arrayList towerIslands
     */
    private void setUpTowerIslands() {
        towerIslands.addAll(Arrays.asList(towerIsland1, towerIsland2, towerIsland3, towerIsland4, towerIsland5, towerIsland6, towerIsland7, towerIsland8, towerIsland9, towerIsland10, towerIsland11, towerIsland12));
    }

    /**
     * Adds the ImageView towerPosition# on the dashboard to the arrayList 'towerPosition'.
     */
    private void setUpTowerDashboard() {
        towerPosition.addAll(Arrays.asList(towerPosition1, towerPosition2, towerPosition3, towerPosition4, towerPosition5, towerPosition6, towerPosition7, towerPosition8));
    }

    /**
     * Adds the Circle studentPosition# to the arrayList 'studentPosition'.
     * 'studentPosition' is the array of the students on the hall of tha dashboard.
     */
    public void setUpHallPosition() {
        studentsPosition.addAll(Arrays.asList(studentPosition1, studentPosition2, studentPosition3, studentPosition4, studentPosition5, studentPosition6, studentPosition7, studentPosition8, studentPosition9));
    }

    /**
     * Adds the Circle professorPosition# to the arrayList 'professorPosition'.
     * 'professorPosition' is the array of the teachers on the TeacherTable of tha dashboard.
     */
    public void setUpProfessorPositions() {
        professorsPosition.addAll(Arrays.asList(professorPosition1, professorPosition2, professorPosition3, professorPosition4, professorPosition5));
    }

    /**
     * Sets all the arraylist of the dashboard's classroom.
     */
    public void setUpClassroom() {
        setUpColorPosition(greenPositions, greenPosition1, greenPosition2, greenPosition3, greenPosition4, greenPosition5, greenPosition6, greenPosition7, greenPosition8, greenPosition9, greenPosition10);
        setUpColorPosition(magentaPositions, magentaPosition1, magentaPosition2, magentaPosition3, magentaPosition4, magentaPosition5, magentaPosition6, magentaPosition7, magentaPosition8, magentaPosition9, magentaPosition10);
        setUpColorPosition(yellowPositions, yellowPosition1, yellowPosition2, yellowPosition3, yellowPosition4, yellowPosition5, yellowPosition6, yellowPosition7, yellowPosition8, yellowPosition9, yellowPosition10);
        setUpColorPosition(cyanPositions, cyanPosition1, cyanPosition2, cyanPosition3, cyanPosition4, cyanPosition5, cyanPosition6, cyanPosition7, cyanPosition8, cyanPosition9, cyanPosition10);
        setUpColorPosition(redPositions, redPosition1, redPosition2, redPosition3, redPosition4, redPosition5, redPosition6, redPosition7, redPosition8, redPosition9, redPosition10);
    }

    /**
     * Sets all th arraylist of each color of the students on each island.
     */
    public void setUpColorPositionOnTheIslands() {
        setUpColorPositionIslands(greenStudentIsland, greenStudentIsland1, greenStudentIsland2, greenStudentIsland3, greenStudentIsland4, greenStudentIsland5, greenStudentIsland6, greenStudentIsland7, greenStudentIsland8, greenStudentIsland9, greenStudentIsland10, greenStudentIsland11, greenStudentIsland12);
        setUpColorPositionIslands(redStudentIsland, redStudentIsland1, redStudentIsland2, redStudentIsland3, redStudentIsland4, redStudentIsland5, redStudentIsland6, redStudentIsland7, redStudentIsland8, redStudentIsland9, redStudentIsland10, redStudentIsland11, redStudentIsland12);
        setUpColorPositionIslands(yellowStudentIsland, yellowStudentIsland1, yellowStudentIsland2, yellowStudentIsland3, yellowStudentIsland4, yellowStudentIsland5, yellowStudentIsland6, yellowStudentIsland7, yellowStudentIsland8, yellowStudentIsland9, yellowStudentIsland10, yellowStudentIsland11, yellowStudentIsland12);
        setUpColorPositionIslands(magentaStudentIsland, magentaStudentIsland1, magentaStudentIsland2, magentaStudentIsland3, magentaStudentIsland4, magentaStudentIsland5, magentaStudentIsland6, magentaStudentIsland7, magentaStudentIsland8, magentaStudentIsland9, magentaStudentIsland10, magentaStudentIsland11, magentaStudentIsland12);
        setUpColorPositionIslands(cyanStudentIsland, cyanStudentIsland1, cyanStudentIsland2, cyanStudentIsland3, cyanStudentIsland4, cyanStudentIsland5, cyanStudentIsland6, cyanStudentIsland7, cyanStudentIsland8, cyanStudentIsland9, cyanStudentIsland10, cyanStudentIsland11, cyanStudentIsland12);
    }


    /**
     * Maps the commands to the relative method, sets up the scene view.
     * Starts the readThread to read incoming messages from the server and to execute the commands
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandHashMap.put("error", this::manageError);
        commandHashMap.put("confirmation", this::manageConfirmation);
        commandHashMap.put("warning", this::printWarning);
        commandHashMap.put("notify", this::printNotify);
        commandHashMap.put("endGame", this::endGame);
        commandHashMap.put("msg", this::printMessage);
        commandHashMap.put("dashboard", this::setDashboard);
        commandHashMap.put("hall", this::setUpHallPosition);
        commandHashMap.put("player", this::setUpPlayerInfo);
        commandHashMap.put("gameInfo", this::setUpGameInfo);
        commandHashMap.put("quit", this::quit);
        commandHashMap.put("islands", this::setIslands);
        commandHashMap.put("singleIsland", this::printIsland);
        commandHashMap.put("cloudCard", this::setCloudCards);
        commandHashMap.put("characterCards", this::setCharacterCards);
        commandHashMap.put("enemyDashboard", this::setEnemyDashboard);


        setUpNameColor();
        setUpClassroom();
        setUpProfessorPositions();
        setUpColorPositionOnTheIslands();
        setUpIslandImageView();
        setUpMNPositions();
        setUpMNIslandPosition();
        setUpTowerIslands();
        setUpTowerDashboard();
        setUpHallPosition();
        setUpCCArrays();
        setUpIslandInfoButton();


        Thread readThread = new Thread(() -> {
            while (true) {
                message = clientInput.readLine();
                if (message != null) {
                    Platform.runLater(() -> {
                        try {
                            commandHashMap.get(message.type).runCommand();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
                try {
                    Thread.sleep(400);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        readThread.start();
        refreshGUI();
    }

    /**
     * Decode the content of the message received as the list of available character cards
     */
    private void setCharacterCards() {
        characterCardList = gson.fromJson(message.message, CharacterCard[].class);
    }

    /**
     * It takes the status of enemy's dashboard sent by server, then it sets the name of the dashboard on the button.
     *
     * @throws RuntimeException if the nameOwner is null.
     */
    private void setEnemyDashboard() {
        enemyDashboard = gson.fromJson(message.message, EnemyDashboardStatus[].class);
        try {
            setDashboardButtonText(dashboardButton1, enemyDashboard[0].nameOwner);
            if (enemyDashboard.length > 1)
                setDashboardButtonText(dashboardButton2, enemyDashboard[1].nameOwner);
        } catch (NullPointerException exception) {
            throw new RuntimeException();
        }

    }

    /**
     * Refreshes the GUI with the current game's status sent by the server.
     */
    private void refreshGUI() {
        for (String s : Arrays.asList("singleDashboard", "islands", "player", "gameInfo", "sendCloudCards", "sendCharacterCardDeck", "enemyDashboard")) {
            clientInput.sendString(s, "");
        }
    }

    /**
     * Displays an alert that prints the "notify" type messages sent by the server to the client and refreshes the GUI.
     */
    private void printNotify() {
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, classRoom.getScene().getWindow(), "Notify", message.message);
        if (Objects.equals(message.message, "Action phase")) {
            disablePlayAssistantCardButton();
        } else if (Objects.equals(message.message, "Planning phase")) {
            enablePlayAssistantCardButton();
        }
        refreshGUI();
    }

    /**
     * Displays an alert that prints the "error" type messages sent by the server to the client.
     */
    private void manageError() {
        AlertHelper.showAlert(Alert.AlertType.ERROR, classRoom.getScene().getWindow(), "Error", message.message);
    }

    /**
     * Displays an alert that prints the "confirmation" type messages sent by the server to the client.
     */
    private void manageConfirmation() {
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, classRoom.getScene().getWindow(), "Confirmation", message.message);
    }

    /**
     * Displays an alert that prints the "warning" type messages sent by the server to the client.
     */
    private void printWarning() {
        AlertHelper.showAlert(Alert.AlertType.WARNING, classRoom.getScene().getWindow(), "Warning", message.message);
    }

    /**
     * Displays an alert that prints the messages sent by the server to the client.
     */
    private void printMessage() {
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, classRoom.getScene().getWindow(), "Message", message.message);
    }

    /**
     * Displays an alert that prints "quit" type messages sent by the server to the client.
     */
    private void quit() {

        AlertHelper.showAlert(Alert.AlertType.INFORMATION, classRoom.getScene().getWindow(), "Quit", message.message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
        //System.exit(0); exit program after closing the alert
    }


    /**
     * Displays all the islands and sets up the relative info box with the status received by the server
     */
    private void printIsland() {
        IslandStatus[] islandStatus = gson.fromJson(message.message, IslandStatus[].class);
        for (IslandStatus island : islandStatus) {
            ArrayList<String> studentColors = island.colors;
            String id = String.valueOf(island.id);
            String dimension = String.valueOf(island.dimension);
            String owner = island.owner;
            String towerNumber = String.valueOf(island.towerNumber);
            StringBuilder studentNumber = new StringBuilder();
            int[] numberOfStudentsOfColor = new int[PawnColor.numberOfColors];
            for (int i = 0; i < PawnColor.numberOfColors; i++) {
                numberOfStudentsOfColor[i] = countStudentByColor(studentColors, PawnColor.values()[i].getName());
                if (numberOfStudentsOfColor[i] != 0) {
                    studentNumber.append("#").append(numberOfStudentsOfColor[i]).append(" ").append(PawnColor.values()[i].getName()).append(" ");
                }
            }
            displayLabel("Students on island#" + id, students, "\nDimension: " + dimension + "\nOwner: " + owner + "\nStudents: " + studentNumber + "\nTower: #" + towerNumber);
        }
    }

    /**
     * Displays the students on the dashboard's classroom.
     *
     * @param classroom is a matrix of student in the classroom, represented by a string
     */
    private void printClassroom(String[][] classroom) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (classroom[i][j] != null) {
                    colorNames.get(i).get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colors[i] + "_student.png")))));
                }
            }
        }
    }


    /**
     * Counts the number of students of one color
     *
     * @param color         is the name of the color that we would to count
     * @param studentsColor is an arrayList that contains the strings of the color name of the students
     * @return the number of the students of one color
     */
    private int countStudentByColor(ArrayList<String> studentsColor, String color) {
        int numberOfStudents = 0;
        for (String s : studentsColor)
            if (Objects.equals(s, color)) {
                numberOfStudents++;
            }
        return numberOfStudents;
    }

    /**
     * Displays in a label some info of player like his username, his moves of mother nature and his available moves in the action phase.
     */
    private void setUpPlayerInfo() {
        PlayersStatus player = gson.fromJson(message.message, PlayersStatus[].class)[0];
        displayLabel("Username", username, player.name);
        displayLabel("Wallet", wallet, String.valueOf(player.wallet));
        movesOfStudent = player.movesOfStudents;
        movesOfMN = player.movesOfMN;
        displayLabel("Moves of MN", movesOfMn, String.valueOf(movesOfMN));
        displayLabel("Moves of students", movesAvailableCounter, String.valueOf(movesOfStudent));
    }


    /**
     * Displays in a label some info of game like the # of round and the actual player.
     */
    private void setUpGameInfo() {
        GameInfoStatus gameInfoStatus = gson.fromJson(message.message, GameInfoStatus[].class)[0];
        displayLabel("Round #", roundCounter, gameInfoStatus.round);
        displayLabel("Actual Player", actualPlayerLabel, gameInfoStatus.actualPlayer);
        if (gameInfoStatus.numberOfPlayer < 3) {
            dashboardButton2.setDisable(true);
            dashboardButton2.setVisible(false);
        }

    }

    /**
     * Sets up the towers images on the dashboard
     *
     * @param towersNumber number of towers available to the player
     * @param towerColor   color of the towers
     */
    public void setUpTowerImages(int towersNumber, TowerColor towerColor) {
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
     * set to transparent the background of the position of the students in the classroom
     *
     * @param colorPosition indicate the student's color of the row in the classroom
     */
    public void setTransparentCircle(ArrayList<Circle> colorPosition) {
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
    private void setUpColorPosition(ArrayList<Circle> colorPositions, Circle colorPosition1, Circle colorPosition2, Circle colorPosition3, Circle colorPosition4, Circle colorPosition5, Circle colorPosition6, Circle colorPosition7, Circle colorPosition8, Circle colorPosition9, Circle colorPosition10) {
        colorPositions.addAll(Arrays.asList(colorPosition1, colorPosition2, colorPosition3, colorPosition4, colorPosition5, colorPosition6, colorPosition7, colorPosition8, colorPosition9, colorPosition10));
        setTransparentCircle(colorPositions);
    }

    /**
     * It's the function that add to each arraylist of each students' color on each island, every circle of each color.
     * It also set all the circle transparent.
     *
     * @param colorPositions is the arraylist considerated.
     * @param colorPosition1 is the 1st circle of each color that is on the island 1.
     * @param colorPosition2 is the 2nd circle of each color that is on the island 2.
     * @param colorPosition3 is the 3rd circle of each color that is on the island 3.
     * @param colorPosition4 is the 4th circle of each color that is on the island 4.
     * @param colorPosition5 is the 5th circle of each color that is on the island 5.
     * @param colorPosition6 is the 6th circle of each color that is on the island 6.
     * @param colorPosition7 is the 7th circle of each color that is on the island 7.
     * @param colorPosition8 is the 8th circle of each color that is on the island 8.
     * @param colorPosition9 is the 9th circle of each color that is on the island 9.
     * @param colorPosition10 is the 10th circle of each color that is on the island 10.
     * @param colorPosition11 is the 11th circle of each color that is on the island 11.
     * @param colorPosition12 is the 12th circle of each color that is on the island 12.
     */
    private void setUpColorPositionIslands(ArrayList<Circle> colorPositions, Circle colorPosition1, Circle colorPosition2, Circle colorPosition3, Circle colorPosition4, Circle colorPosition5, Circle colorPosition6, Circle colorPosition7, Circle colorPosition8, Circle colorPosition9, Circle colorPosition10, Circle colorPosition11, Circle colorPosition12) {
        colorPositions.addAll(Arrays.asList(colorPosition1, colorPosition2, colorPosition3, colorPosition4, colorPosition5, colorPosition6, colorPosition7, colorPosition8, colorPosition9, colorPosition10, colorPosition11, colorPosition12));
        setTransparentCircle(colorPositions);
    }

    /**
     * Sets all the arraylists of each students' color, related to the islands, in only one arraylist 'studentsOnEachIsland'.
     */
    private void setUpStudentsInEachIsland() {
        studentsOnEachIsland.addAll(Arrays.asList(greenStudentIsland, redStudentIsland, yellowStudentIsland, magentaStudentIsland, cyanStudentIsland));
    }


    /**
     * Sets the images of the teachers on the TableTeacher of the dashboard.
     * @param teachers is a string with all the player's teacher.
     */
    public void setUpProfessorImages(String[] teachers) {
        int i = 0;
        for (String teacher : teachers) {
            if (teacher != null) {
                professorsPosition.get(i).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colors[i] + "_professor.png")))));
            } else professorsPosition.get(i).setFill(null);
            i++;
        }
    }

    /**
     * Sets the images of the students on the hall of the dashboard.
     * @param hall is a string with all the students on the hall.
     */
    public void setUpHallImages(String[] hall) {
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
     *By a Json formatted string sent from the server, it sets the dashboard of the player.
     */
    private void setDashboard() {
        DashboardStatus dashboardStatus = gson.fromJson(message.message, DashboardStatus[].class)[0];
        String[] hall = dashboardStatus.studentsHallColors;
        String[][] classroom = dashboardStatus.studentsClassroom;
        String[] teachers = dashboardStatus.teacherTable;
        setUpHallImages(hall);
        printClassroom(classroom);
        setUpProfessorImages(teachers);
        setUpTowerImages(dashboardStatus.towers, dashboardStatus.towerColor);
    }

    /**
     *By a Json formatted string sent from the server, it sets all the islands of the game.
     */
    private void setIslands() {
        IslandStatus[] islands = gson.fromJson(message.message, IslandStatus[].class);
        setUpStudentsInEachIsland();
        setUpIsland(islands);

    }

    /**
     * Sets the images of the island.
     * @param numberOfIslands is the number of island that we have during the game.
     */
    public void setIslandImages(int numberOfIslands) {

        int j = 0;
        int k = 1;

        for (int i = 0; i < numberOfIslands; i++) {
            Image imgIsland = new Image(String.valueOf(getClass().getClassLoader().getResource("images/islands/Island" + k + ".png")));
            Islands.get(i).setImage(imgIsland);
            k++;
            if (k > 3)
                k = 1;
            System.out.println(i + " island");
            j = i;
        }
        j++;
        while (j < Islands.size()) {
            Islands.get(j).setImage(null);
            islandInfoButtons.get(j).setVisible(false);
            islandInfoButtons.get(j).setDisable(true);
            j++;
        }


    }

    /**
     * Sets every information of each island.
     * @param island is an array of the islands in the game.
     */
    public void setUpIsland(IslandStatus[] island) {
        ArrayList<Integer> studentsColorOrdinal;
        boolean presenceMN;
        String towerColor;
        int towerNumber;
        int numberOfIslands = island.length;
        setIslandImages(numberOfIslands);

        for (int i = 0; i < numberOfIslands; i++) {

            studentsColorOrdinal = island[i].studentColorOrdinal;
            System.out.println(studentsColorOrdinal);
            towerColor = island[i].towerColor;
            towerNumber = island[i].towerNumber;
            presenceMN = island[i].presenceMN;
            setUpStudentsOnTheIsland(studentsColorOrdinal, i, numberOfIslands);
            setUpMNOnTheIsland(presenceMN, i);
            setUpTowerOnTheIsland(i, towerColor, towerNumber);

        }

    }

    /**
     * sets all the student on the island to null.
     *
     * @param idIsland gives us the index of the island that we are considering.
     */
    private void resetStudentsOnTheIsland(int idIsland) {
        for (PawnColor color : PawnColor.values()) {
            studentsOnEachIsland.get(color.ordinal()).get(idIsland).setFill(null);
        }
    }

    /**
     * sets the students and Mother Nature on the island considered.
     *
     * @param studentsOrdinal gives us the PawnColor ordinal of the students that are present on the island.
     * @param idIsland        gives us the island that we are considering.
     * @param numberOfIslands give us the number of islands.
     */
    private void setUpStudentsOnTheIsland(ArrayList<Integer> studentsOrdinal, int idIsland, int numberOfIslands) {
        int j;
        resetStudentsOnTheIsland(idIsland);

        for (Integer studentColor : studentsOrdinal) {
            Image pawStudent = new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + PawnColor.values()[studentColor].getName() + "_student.png")));
            studentsOnEachIsland.get(studentColor).get(idIsland).setFill(new ImagePattern(pawStudent));
        }

        for (PawnColor studentColor : PawnColor.values()) {
            j = numberOfIslands;
            while (j < studentsOnEachIsland.get(studentColor.ordinal()).size()) {
                studentsOnEachIsland.get(studentColor.ordinal()).get(j).setFill(null);
                j++;
            }
        }


    }

    /**
     * sets/remove the image of Mather Nature respectively if she is on the island or not.
     *
     * @param presenceMN gives us the information of the presence of Mother Nature.
     * @param idIsland   gives us the index of the island that we are considering.
     */
    public void setUpMNOnTheIsland(boolean presenceMN, int idIsland) {

        int i = idIsland;

        if (presenceMN) {
            MNPositions.get(idIsland).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/MotherNature.png"))));
            indexMN = idIsland;
        } else {
            MNPositions.get(idIsland).setImage(null);
        }

        i++;

        while (i < MNPositions.size()) {   //it set all the MNPosition, that don't have an Island, null.
            MNPositions.get(i).setImage(null);
            i++;
        }

    }

    /**
     * sets the image of the tower on the island.
     *
     * @param idIsland    gives us the index of the island that we are considering.
     * @param towerColor  gives us the color of the tower of the island's owner.
     * @param towerNumber gives us the information if there are any tower.
     */

    public void setUpTowerOnTheIsland(int idIsland, String towerColor, int towerNumber) {
        resetTowerOnTheIsland(idIsland);

        int j = idIsland;

        if (towerNumber != 0) {
            towerIslands.get(idIsland).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Tower/" + towerColor + "_tower.png"))));
        }

        j++;

        while (j < towerIslands.size()) { //it set all the towerIslands, that don't have an Island, null.
            towerIslands.get(j).setImage(null);
            j++;
        }

    }


    /**
     * Removes the tower image from an island
     *
     * @param idIsland index of the island where the tower needs to be removed
     */
    public void resetTowerOnTheIsland(int idIsland) {
        towerIslands.get(idIsland).setImage(null);
    }

    /**
     * Moves Mother Nature.
     * Gets the index of the clicked island and sends a command to the server to move Mother Nature to the destination island
     *
     * @param event used to identify the clicked object
     */
    @FXML
    public void moveMNToIslands(MouseEvent event) {
        indexIsland = MnIslandPosition.indexOf((Rectangle) event.getSource());
        System.out.println(indexIsland + " MnIslandPosition");
        clientInput.sendString("moveMN", String.valueOf(indexIsland + 1));
        if (movesOfStudent != 0) {
            disableMNIslandPosition();
            enableMN();
        }
        else {
            index = -1;
            refreshGUI();
        }
    }

    /**
     * Enables click on Islands where is possible to move mother nature
     */
    private void enableMNIslandPosition() {
        for (Rectangle islands : MnIslandPosition) {
            islands.setDisable(false);
        }
    }

    /**
     * Enables click on Mother Nature image
     */
    private void enableMN() {
        for (ImageView mn : MNPositions)
            mn.setDisable(false);
    }

    /**
     * Disables click on Islands where is possible to move mother nature
     */
    private void disableMNIslandPosition() {
        for (Rectangle islands : MnIslandPosition) {
            islands.setDisable(true);
        }
    }

    /**
     * Disables click on Mother Nature image
     */
    private void disableMN() {
        for (ImageView mn : MNPositions)
            mn.setDisable(true);
    }

    /**
     * gives us the information of the island 1.
     */
    @FXML
    private void showStudentsIsland0() {
        clientInput.sendString("singleIsland", String.valueOf(1));
    }

    /**
     * gives us the information of the island 2.
     */
    @FXML
    private void showStudentsIsland1() {
        clientInput.sendString("singleIsland", String.valueOf(2));
    }

    /**
     * gives us the information of the island 3.
     */
    @FXML
    private void showStudentsIsland2() {
        clientInput.sendString("singleIsland", String.valueOf(3));
    }

    /**
     * gives us the information of the island 4.
     */
    @FXML
    private void showStudentsIsland3() {
        clientInput.sendString("singleIsland", String.valueOf(4));
    }

    /**
     * gives us the information of the island 5.
     */
    @FXML
    private void showStudentsIsland4() {
        clientInput.sendString("singleIsland", String.valueOf(5));
    }

    /**
     * gives us the information of the island 6.
     */
    @FXML
    private void showStudentsIsland5() {
        clientInput.sendString("singleIsland", String.valueOf(6));
    }

    /**
     * gives us the information of the island 7.
     */
    @FXML
    private void showStudentsIsland6() {
        clientInput.sendString("singleIsland", String.valueOf(7));
    }

    /**
     * gives us the information of the island 8.
     */
    @FXML
    private void showStudentsIsland7() {
        clientInput.sendString("singleIsland", String.valueOf(8));
    }

    /**
     * gives us the information of the island 9.
     */
    @FXML
    private void showStudentsIsland8() {
        clientInput.sendString("singleIsland", String.valueOf(9));
    }

    /**
     * gives us the information of the island 10.
     */
    @FXML
    private void showStudentsIsland9() {
        clientInput.sendString("singleIsland", String.valueOf(10));
    }

    /**
     * gives us the information of the island 11.
     */
    @FXML
    private void showStudentsIsland10() {
        clientInput.sendString("singleIsland", String.valueOf(11));
    }

    /**
     * gives us the information of the island 12.
     */
    @FXML
    private void showStudentsIsland11() {
        clientInput.sendString("singleIsland", String.valueOf(12));
    }

    /**
     * takes the position of mother nature, then enable all the islands.
     *
     * @param event used to identify the clicked object
     */

    public void getIndexMN(MouseEvent event) {
        int indexMNClicked = MNPositions.indexOf((ImageView) event.getSource());
        if (indexMNClicked == indexMN){
            System.out.println(indexMN + " it's the position of MN");
            enableMNIslandPosition();
            disableMN();
        }
    }
    /**
     * Gets the index of the clicked cloud card and sends a command to the server to select the cloud card to get the students from
     *
     * @param event used to identify the clicked object
     */
    @FXML
    public void chooseCC(MouseEvent event) {
        int indexCloudCard = cloudCards.indexOf((Circle) event.getSource());
        System.out.println(indexCloudCard + " is the CC chosen");
        if (indexCloudCard != -1) {
            clientInput.sendString("chooseCC", String.valueOf(indexCloudCard + 1));
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, cloudCards.get(indexCloudCard).getScene().getWindow(), "Error", "Cloud Card not available");
        }
        enableMN();
        enableIslandAndHall();
        disableMNIslandPosition();
        refreshGUI();
    }


    /**
     * Gets the index of the clicked student on the hall
     *
     * @param event used to identify the clicked object
     */
    @FXML
    public void getIndex(MouseEvent event) {
        index = studentsPosition.indexOf((Circle) event.getSource());
        System.out.println("student Position : " + index);
    }

    /**
     * Moves a student to the classroom.
     * If the player has available students moves it sends a command to the server
     * else disables all the islands and hall actions
     */
    @FXML
    public void moveStudentToClassroom() {
        if (movesOfStudent > 0) {
            sendMoveToClassroomCommand();
        } else if (movesOfStudent == 0) {
            disableIslandAndHall();
            enableMN();
        }
    }

    /**
     * Disables the mouse click on islands and hall
     */
    private void disableIslandAndHall() {
        for (ImageView island : Islands)
            island.setDisable(true);
        for (Circle student : studentsPosition)
            student.setDisable(true);
    }

    /**
     * Enables the mouse click on islands and hall
     */
    private void enableIslandAndHall() {
        for (ImageView island : Islands)
            island.setDisable(false);
        for (Circle student : studentsPosition)
            student.setDisable(false);
    }


    /**
     * Moves a student to an island.
     * Get the destination island from the clicked island index int the Islands array
     * If the player has available students moves it sends a command to the server
     * else disables all the islands and hall actions
     *
     * @param mouseEvent used to identify the clicked object
     */
    @FXML
    public void moveStudentToIsland(MouseEvent mouseEvent) {
        indexIsland = Islands.indexOf((ImageView) mouseEvent.getSource());
        System.out.println(indexIsland);
        if (movesOfStudent > 0) {
            sendMoveToIslandCommand();
        } else {
            disableIslandAndHall();
            enableMN();
        }

    }

    /**
     * sends to echoServer the command "moveStudentToClassroom" and the index of the students in the hall.
     */
    @FXML
    public void sendMoveToClassroomCommand() {
        clientInput.sendString("moveStudentToClassroom", String.valueOf(index + 1));
        refreshGUI();
    }


    /**
     * sends to echoServer the command "moveStudentToIsland" and the index of the students in the hall, and the index of the student's destination island.
     */
    @FXML
    public void sendMoveToIslandCommand() {
        clientInput.sendString("moveStudentToIsland", String.valueOf(index + 1), String.valueOf(indexIsland + 1));
        refreshGUI();
    }


    /**
     * Opens a new stage where it shows the assistant card window, then it refreshes the GUI and enables Island and Hall
     */
    @FXML
    private void setAssistantCardsPage() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(new FXMLLoader(getClass().getResource("assistantCard.fxml")).load());
        stage.setScene(scene);
        stage.showAndWait();
        refreshGUI();
        enableIslandAndHall();
    }

    /**
     * Show the page where to view dashboard of the first enemy.
     * Asks the server the enemyDashboard, instantiate a new DashboardEnemyController
     * loads the fxml file and shows the scene
     *
     * @throws IOException If loading the scene an exception occurred.
     */
    @FXML
    private void setPlayerDashboardOne() throws IOException {
        clientInput.sendString("enemyDashboard", "");
        DashboardEnemyController controller = new DashboardEnemyController();
        controller.setDashboardStatus(enemyDashboard[0]);
        Stage enemyDashboardStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardPlayer.fxml"));
        loader.setController(controller);
        Scene enemyDashboardScene = new Scene(loader.load());
        enemyDashboardStage.setScene(enemyDashboardScene);
        enemyDashboardStage.showAndWait();
        refreshGUI();
    }

    /**
     * Show the page where to view dashboard of the second enemy.
     * Asks the server the enemyDashboard, instantiate a new DashboardEnemyController
     * loads the fxml file and shows the scene
     *
     * @throws IOException If loading the scene an exception occurred
     */
    @FXML
    private void setPlayerDashboardTwo() throws IOException {
        clientInput.sendString("enemyDashboard", "");
        DashboardEnemyController controller = new DashboardEnemyController();
        controller.setDashboardStatus(enemyDashboard[1]);
        Stage enemyDashboardStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardPlayer.fxml"));
        loader.setController(controller);
        Scene enemyDashboardScene = new Scene(loader.load());
        enemyDashboardStage.setScene(enemyDashboardScene);
        enemyDashboardStage.showAndWait();
        refreshGUI();
    }


    /**
     * Enables the play assistant card button.
     */
    private void enablePlayAssistantCardButton() {
        assistantCardButton.setDisable(false);
    }

    /**
     * Disables the play assistant card button.
     */
    private void disablePlayAssistantCardButton() {
        assistantCardButton.setDisable(true);
    }


    /**
     * Shows the page where to play character cards.
     * Asks the server the list of character cards, instantiate a new CharacterCardsController,
     * load the fxml file and shows the scene
     *
     * @throws IOException If an exception occurred loading the scene
     */
    @FXML
    private void setCharacterCardsPage() throws IOException {
        clientInput.sendString("sendCharacterCardDeck", "");
        CharacterCardsController controller = new CharacterCardsController();
        controller.setCards(characterCardList);
        Stage characterCardsStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("characterCards.fxml"));
        fxmlLoader.setController(controller);
        Scene characterCardsScene = new Scene(fxmlLoader.load());
        characterCardsStage.setScene(characterCardsScene);
        characterCardsStage.showAndWait();
        refreshGUI();
        enableIslandAndHall();
    }

    /**
     * Sets the text of dashboard button
     *
     * @param button is the button that where we want sets the text
     * @param name   is the dashboard's owner,
     */
    @FXML
    public void setDashboardButtonText(Button button, String name) {
        button.setText("view " + name + " dashboard");
    }

    /**
     * Shows the end game page
     *
     * @throws IOException If an exception occurred loading the scene
     */
    private void endGame() throws IOException {
        EndGameController controller = new EndGameController();
        controller.setText(message.message);
        Stage stage = (Stage) anchor.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("endGame.fxml"));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}

