package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
    private Button assistantCardButton;

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
    private Circle professorPosition1, professorPosition2, professorPosition3, professorPosition4, professorPosition5;
    @FXML
    private Circle redPosition1, redPosition2, redPosition3, redPosition4, redPosition5, redPosition6, redPosition7, redPosition8, redPosition9, redPosition10;
    @FXML
    private Circle greenPosition1, greenPosition2, greenPosition3, greenPosition4, greenPosition5, greenPosition6, greenPosition7, greenPosition8, greenPosition9, greenPosition10;
    @FXML
    private Circle yellowPosition1, yellowPosition2, yellowPosition3, yellowPosition4, yellowPosition5, yellowPosition6, yellowPosition7, yellowPosition8, yellowPosition9, yellowPosition10;
    @FXML
    private Circle magentaPosition1, magentaPosition2, magentaPosition3, magentaPosition4, magentaPosition5, magentaPosition6, magentaPosition7, magentaPosition8, magentaPosition9, magentaPosition10;
    @FXML
    private Circle cyanPosition1, cyanPosition2, cyanPosition3, cyanPosition4, cyanPosition5, cyanPosition6, cyanPosition7, cyanPosition8, cyanPosition9, cyanPosition10;
    @FXML
    private Rectangle classRoom;
    @FXML
    private Rectangle MNIsland1, MNIsland2, MNIsland3, MNIsland4, MNIsland5, MNIsland6, MNIsland7, MNIsland8, MNIsland9, MNIsland10, MNIsland11, MNIsland12;
    @FXML
    private ImageView Island1, Island2, Island3, Island4, Island5, Island6, Island7, Island8, Island9, Island10, Island11, Island12;
    @FXML
    private Circle greenStudentIsland1, greenStudentIsland2, greenStudentIsland3, greenStudentIsland4, greenStudentIsland5, greenStudentIsland6, greenStudentIsland7, greenStudentIsland8, greenStudentIsland9, greenStudentIsland10, greenStudentIsland11, greenStudentIsland12;
    @FXML
    private Circle redStudentIsland1, redStudentIsland2, redStudentIsland3, redStudentIsland4, redStudentIsland5, redStudentIsland6, redStudentIsland7, redStudentIsland8, redStudentIsland9, redStudentIsland10, redStudentIsland11, redStudentIsland12;
    @FXML
    private Circle yellowStudentIsland1, yellowStudentIsland2, yellowStudentIsland3, yellowStudentIsland4, yellowStudentIsland5, yellowStudentIsland6, yellowStudentIsland7, yellowStudentIsland8, yellowStudentIsland9, yellowStudentIsland10, yellowStudentIsland11, yellowStudentIsland12;
    @FXML
    private Circle magentaStudentIsland1, magentaStudentIsland2, magentaStudentIsland3, magentaStudentIsland4, magentaStudentIsland5, magentaStudentIsland6, magentaStudentIsland7, magentaStudentIsland8, magentaStudentIsland9, magentaStudentIsland10, magentaStudentIsland11, magentaStudentIsland12;
    @FXML
    private Circle cyanStudentIsland1, cyanStudentIsland2, cyanStudentIsland3, cyanStudentIsland4, cyanStudentIsland5, cyanStudentIsland6, cyanStudentIsland7, cyanStudentIsland8, cyanStudentIsland9, cyanStudentIsland10, cyanStudentIsland11, cyanStudentIsland12;
    @FXML
    private ImageView MNPosition1, MNPosition2, MNPosition3, MNPosition4, MNPosition5, MNPosition6, MNPosition7, MNPosition8, MNPosition9, MNPosition10, MNPosition11, MNPosition12;

    @FXML
    private ImageView towerIsland1, towerIsland2, towerIsland3, towerIsland4, towerIsland5, towerIsland6, towerIsland7, towerIsland8, towerIsland9, towerIsland10, towerIsland11, towerIsland12;

    @FXML
    private Circle cloudCard1, cloudCard2, cloudCard3;

    @FXML
    private Circle student1CC1, student2CC1, student3CC1, student4CC1;
    @FXML
    private Circle student1CC2, student2CC2, student3CC2, student4CC2;
    @FXML
    private Circle student1CC3, student2CC3, student3CC3, student4CC3;


    @FXML
    private final ArrayList<Button> islandInfoButtons = new ArrayList<>();

    @FXML
    private Button info0 = new Button();
    @FXML
    private Button info1 = new Button();
    @FXML
    private Button info2 = new Button();
    @FXML
    private Button info3 = new Button();
    @FXML
    private Button info4 = new Button();
    @FXML
    private Button info5 = new Button();
    @FXML
    private Button info6 = new Button();
    @FXML
    private Button info7 = new Button();
    @FXML
    private Button info8 = new Button();
    @FXML
    private Button info9 = new Button();
    @FXML
    private Button info10 = new Button();
    @FXML
    private Button info11 = new Button();


    private int index, indexIsland, indexMN;
    private int indexCloudCard = -1;

    /**
     * This arrayList contain all the circle position where the students pawn are shown
     */
    private final ArrayList<Circle> greenStudentIsland = new ArrayList<>();
    private final ArrayList<Circle> redStudentIsland = new ArrayList<>();
    private final ArrayList<Circle> yellowStudentIsland = new ArrayList<>();
    private final ArrayList<Circle> magentaStudentIsland = new ArrayList<>();
    private final ArrayList<Circle> cyanStudentIsland = new ArrayList<>();

    private final ArrayList<Circle> cloudCards = new ArrayList<>(3);
    private final CloudCardDeckImages cloudCardDeckImages = new CloudCardDeckImages();
    private final ArrayList<Circle> studentsCC1 = new ArrayList<>();
    private final ArrayList<Circle> studentsCC2 = new ArrayList<>();
    private final ArrayList<Circle> studentsCC3 = new ArrayList<>();
    private final ArrayList<ArrayList<Circle>> studentsInEachCloudCard = new ArrayList<>();


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

    private final Boolean[][] classroomFilled = new Boolean[PawnColor.numberOfColors][numberOfPositionClassroom];

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
    private final ArrayList<ArrayList<Circle>> studentsInEachIsland = new ArrayList<>();


    private final ArrayList<Rectangle> MnIslandPosition = new ArrayList<>(12);

    private CharacterCard[] characterCardList = new CharacterCard[3];
    private final ArrayList<ImageView> towerIslands = new ArrayList<>();
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
        disableCloudCards();

    }

    /**
     * Disables the onMouseClick action of the cloud cards circles
     */
    private void disableCloudCards() {
        for (Circle c : cloudCards)
            c.setDisable(true);
    }


    /**
     *
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
        cloudCardDeckImages.extractRandomCloudCard();

        for (Circle c : cloudCards) {
            c.setFill(new ImagePattern(cloudCardDeckImages.getCloudCardsInGame().get(i)));
            if (cloudCardsStatus.length == 2 && i >= 2)
                c.setFill(null);
            i++;
        }
    }

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

    private void setUpClassroomFilled() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                classroomFilled[i][j] = false;
            }
        }
    }

    /**
     * Adds the towers position on the islands to the arrayList towerIslands
     */
    private void setUpTowerIslands() {
        towerIslands.addAll(Arrays.asList(towerIsland1, towerIsland2, towerIsland3, towerIsland4, towerIsland5, towerIsland6, towerIsland7, towerIsland8, towerIsland9, towerIsland10, towerIsland11, towerIsland12));
    }

    /**
     * Adds the towers position on the dashboard to the arrayList towerPosition
     */
    private void setUpTowerDashboard() {
        towerPosition.addAll(Arrays.asList(towerPosition1, towerPosition2, towerPosition3, towerPosition4, towerPosition5, towerPosition6, towerPosition7, towerPosition8));
    }

    public void setUpHallPosition() {
        studentsPosition.addAll(Arrays.asList(studentPosition1, studentPosition2, studentPosition3, studentPosition4, studentPosition5, studentPosition6, studentPosition7, studentPosition8, studentPosition9));
    }

    public void setUpProfessorPositions() {
        professorsPosition.addAll(Arrays.asList(professorPosition1, professorPosition2, professorPosition3, professorPosition4, professorPosition5));
    }


    public void setUpClassroom() {
        setUpColorPosition(greenPositions, greenPosition1, greenPosition2, greenPosition3, greenPosition4, greenPosition5, greenPosition6, greenPosition7, greenPosition8, greenPosition9, greenPosition10);
        setUpColorPosition(magentaPositions, magentaPosition1, magentaPosition2, magentaPosition3, magentaPosition4, magentaPosition5, magentaPosition6, magentaPosition7, magentaPosition8, magentaPosition9, magentaPosition10);
        setUpColorPosition(yellowPositions, yellowPosition1, yellowPosition2, yellowPosition3, yellowPosition4, yellowPosition5, yellowPosition6, yellowPosition7, yellowPosition8, yellowPosition9, yellowPosition10);
        setUpColorPosition(cyanPositions, cyanPosition1, cyanPosition2, cyanPosition3, cyanPosition4, cyanPosition5, cyanPosition6, cyanPosition7, cyanPosition8, cyanPosition9, cyanPosition10);
        setUpColorPosition(redPositions, redPosition1, redPosition2, redPosition3, redPosition4, redPosition5, redPosition6, redPosition7, redPosition8, redPosition9, redPosition10);
    }

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
        commandHashMap.put("msg", this::printMessage);
        commandHashMap.put("dashboard", this::setDashboard);
        commandHashMap.put("hall", this::setDashboard);
        commandHashMap.put("player", this::setUpPlayerInfo);
        commandHashMap.put("gameInfo", this::setUpGameInfo);
        commandHashMap.put("quit", this::quit);
        commandHashMap.put("islands", this::setIslands);
        commandHashMap.put("singleIsland", this::printIsland);
        commandHashMap.put("cloudCard", this::setCloudCards);
        commandHashMap.put("characterCards", this::setCharacterCards);

        setUpClassroomFilled();
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
     * Refreshes the GUI with the actual game's status sent by the server.
     */
    private void refreshGUI() {
        clientInput.sendString("singleDashboard", "");
        clientInput.sendString("islands", "");
        clientInput.sendString("player", "");
        clientInput.sendString("gameInfo", "");
        clientInput.sendString("sendCloudCards", "");
        clientInput.sendString("sendCharacterCardDeck", "");

    }

    /**
     * Displays an alert that prints the "notify" type messages sent by the server to the client.
     */
    private void printNotify() {
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, classRoom.getScene().getWindow(), "Notify", message.message);
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
     * @param color     is the name of the color that we would to count
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
        displayLabel("Username", username, player.getName());
        displayLabel("Wallet", wallet, String.valueOf(player.wallet));
        movesOfStudent = player.getMovesOfStudents();
        movesOfMN = player.getMovesOfMN();
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
    }

    /**
     * Sets up the towers images on the dashboard
     * @param towersNumber number of towers available to the player
     * @param towerColor color of the towers
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

    private void setUpColorPosition(ArrayList<Circle> colorPositions, Circle colorPosition1, Circle colorPosition2, Circle colorPosition3, Circle colorPosition4, Circle colorPosition5, Circle colorPosition6, Circle colorPosition7, Circle colorPosition8, Circle colorPosition9, Circle colorPosition10) {
        colorPositions.addAll(Arrays.asList(colorPosition1, colorPosition2, colorPosition3, colorPosition4, colorPosition5, colorPosition6, colorPosition7, colorPosition8, colorPosition9, colorPosition10));
        setTransparentCircle(colorPositions);
    }

    private void setUpColorPositionIslands(ArrayList<Circle> colorPositions, Circle colorPosition1, Circle colorPosition2, Circle colorPosition3, Circle colorPosition4, Circle colorPosition5, Circle colorPosition6, Circle colorPosition7, Circle colorPosition8, Circle colorPosition9, Circle colorPosition10, Circle colorPosition11, Circle colorPosition12) {
        colorPositions.addAll(Arrays.asList(colorPosition1, colorPosition2, colorPosition3, colorPosition4, colorPosition5, colorPosition6, colorPosition7, colorPosition8, colorPosition9, colorPosition10, colorPosition11, colorPosition12));
        setTransparentCircle(colorPositions);
    }

    private void setUpStudentsInEachIsland() {
        studentsInEachIsland.addAll(Arrays.asList(greenStudentIsland, redStudentIsland, yellowStudentIsland, magentaStudentIsland, cyanStudentIsland));
    }

    public void setUpProfessorImages(String[] teachers) {
        int i = 0;
        for (String teacher : teachers) {
            if (teacher != null) {
                professorsPosition.get(i).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colors[i] + "_professor.png")))));
            } else professorsPosition.get(i).setFill(null);
            i++;
        }

    }


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

    private void setIslands() {
        IslandStatus[] islands = gson.fromJson(message.message, IslandStatus[].class);
        setUpStudentsInEachIsland();
        setUpIsland(islands);

    }

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
            studentsInEachIsland.get(color.ordinal()).get(idIsland).setFill(null);
        }
    }

    /**
     * sets the students and Mother Nature on the island considerated.
     *
     * @param studentsOrdinal gives us the PawnColor ordinal of the students that are present on the island.
     * @param idIsland        gives us the island that we are considering.
     * @param idIsland        gives us the island that we are considering.
     * @param numberOfIslands give us the number of islands.
     */
    private void setUpStudentsOnTheIsland(ArrayList<Integer> studentsOrdinal, int idIsland, int numberOfIslands) {
        int j = numberOfIslands;
        resetStudentsOnTheIsland(idIsland);

        for (Integer studentColor : studentsOrdinal) {
            Image pawStudent = new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + PawnColor.values()[studentColor].getName() + "_student.png")));
            studentsInEachIsland.get(studentColor).get(idIsland).setFill(new ImagePattern(pawStudent));
        }

        for (Integer studentColor : studentsOrdinal) {
            while (j < studentsInEachIsland.get(studentColor).size()) {
                studentsInEachIsland.get(studentColor).get(j).setFill(null);
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

    @FXML
    public void moveMNToIslands(MouseEvent event) {
        indexIsland = MnIslandPosition.indexOf((Rectangle) event.getSource());
        System.out.println(indexIsland + " MnIslandPosition");
        moveMn();
        enableCloudCards();
        disableMNIslandPosition();
        disableMN();
    }

    private void enableMNIslandPosition() {
        for (Rectangle islands : MnIslandPosition) {
            islands.setDisable(false);
        }
    }

    private void enableMN() {
        for (ImageView mn : MNPositions)
            mn.setDisable(false);
    }


    private void disableMNIslandPosition() {
        for (Rectangle islands : MnIslandPosition) {
            islands.setDisable(true);
        }
    }

    private void disableMN() {
        for (ImageView mn : MNPositions)
            mn.setDisable(true);
    }

    private void enableCloudCards() {
        for (Circle cloudCard : cloudCards) {
            cloudCard.setDisable(false);
        }
    }

    @FXML
    private void showStudentsIsland0() {
        clientInput.sendString("singleIsland", String.valueOf(1));
    }


    @FXML
    private void showStudentsIsland1() {
        clientInput.sendString("singleIsland", String.valueOf(2));
    }

    @FXML
    private void showStudentsIsland2() {
        clientInput.sendString("singleIsland", String.valueOf(3));
    }

    @FXML
    private void showStudentsIsland3() {
        clientInput.sendString("singleIsland", String.valueOf(4));
    }

    @FXML
    private void showStudentsIsland4() {
        clientInput.sendString("singleIsland", String.valueOf(5));
    }

    @FXML
    private void showStudentsIsland5() {
        clientInput.sendString("singleIsland", String.valueOf(6));
    }

    @FXML
    private void showStudentsIsland6() {
        clientInput.sendString("singleIsland", String.valueOf(7));
    }

    @FXML
    private void showStudentsIsland7() {
        clientInput.sendString("singleIsland", String.valueOf(8));
    }

    @FXML
    private void showStudentsIsland8() {
        clientInput.sendString("singleIsland", String.valueOf(9));
    }

    @FXML
    private void showStudentsIsland9() {
        clientInput.sendString("singleIsland", String.valueOf(10));
    }

    @FXML
    private void showStudentsIsland10() {
        clientInput.sendString("singleIsland", String.valueOf(11));
    }

    @FXML
    private void showStudentsIsland11() {
        clientInput.sendString("singleIsland", String.valueOf(12));
    }

    /**
     * takes the position of mother nature, then enable all the islands, except for the island where there is mother nature
     *
     * @param event is the event generated by the "mouseClick" on the motherNature's image.
     */
    @FXML
    public void getIndexMN(MouseEvent event) {
        indexMN = MNPositions.indexOf((ImageView) event.getSource());
        System.out.println(indexMN + " it's the position of MN");
        enableMNIslandPosition();
    }


    @FXML
    public void moveMn() {
        clientInput.sendString("moveMN", String.valueOf(indexIsland + 1));
        refreshGUI();
    }

    @FXML
    public void chooseCC(MouseEvent event) {
        indexCloudCard = cloudCards.indexOf(event.getSource());
        System.out.println(indexCloudCard + " is the CC chosen");
        if (indexCloudCard != -1) {
            chooseCloudCard();
            clientInput.sendString("islands", "");
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, cloudCards.get(indexCloudCard).getScene().getWindow(), "Error", "Cloud Card not available");
        }

        enableIslandAndHall();
        enablePlayAssistantCardButton();

    }

    @FXML
    public void chooseCloudCard() {
        clientInput.sendString("chooseCC", String.valueOf(indexCloudCard + 1));
        refreshGUI();
        indexCloudCard = -1;
    }


    @FXML
    public void getIndex(MouseEvent event) {
        index = studentsPosition.indexOf((Circle) event.getSource());
        System.out.println("student Position : " + index);
    }

    @FXML
    public void moveStudentToClassroom() {
        disableCloudCards();
        if (movesOfStudent > 0) {
            sendMoveToClassroomCommand();
        } else if (movesOfStudent == 0) {
            disableIslandAndHall();
            enableMN();
        }
    }

    private void disableIslandAndHall() {
        for (ImageView island : Islands)
            island.setDisable(true);
        for (Circle student : studentsPosition)
            student.setDisable(true);
    }

    private void enableIslandAndHall() {
        for (ImageView island : Islands)
            island.setDisable(false);
        for (Circle student : studentsPosition)
            student.setDisable(false);
    }

    @FXML
    public void moveStudentToIsland(MouseEvent mouseEvent) {
        indexIsland = Islands.indexOf((ImageView) mouseEvent.getSource());
        disableCloudCards();
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
        ClientInput.getInstance().sendString("moveStudentToIsland", String.valueOf(index + 1), String.valueOf(indexIsland + 1));
        refreshGUI();
    }


    /**
     * It opens a new stage where it shows the assistant card window, then it refreshes the GUI and enables Island and Hall
     */
    @FXML
    private void setPhasePage() throws IOException {
        Stage phaseStage = new Stage();
        Scene phaseScene = new Scene(new FXMLLoader(getClass().getResource("assistantCard.fxml")).load());
        phaseStage.setScene(phaseScene);
        phaseStage.showAndWait();
        refreshGUI();
        enableIslandAndHall();
    }

    @FXML
    private void setDashboardPlayer() throws IOException {
        Stage phaseStage = new Stage();
        Scene phaseScene = new Scene(new FXMLLoader(getClass().getResource("dashboardPlayer.fxml")).load());
        phaseStage.setScene(phaseScene);
        phaseStage.showAndWait();
    }

    private void disablePlayAssistantCardButton() {
        if (clientInput.readLine().type.equals("msg"))
            assistantCardButton.setDisable(true);
    }

    private void enablePlayAssistantCardButton() {
        assistantCardButton.setDisable(false);
    }



    @FXML
    private void setCharacterCardsPage(ActionEvent actionEvent) throws IOException {
        clientInput.sendString("sendCharacterCardDeck", "");
        CharacterCardsController controller = new CharacterCardsController();
        controller.setCards(characterCardList);
        Stage characterCardsStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("characterCards.fxml"));
        loader.setController(controller);
        Scene characterCardsScene = new Scene(loader.load());
        characterCardsStage.setScene(characterCardsScene);
        characterCardsStage.showAndWait();
        refreshGUI();
        enableIslandAndHall();
    }
}

