package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.ClientOut;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class DashboardController implements Initializable, DisplayLabel  {

    public Pane anchor;
    @FXML
    private Label movesAvailableCounter,movesOfMn, username, roundCounter, actualPlayerLabel, students;

    int movesOfMN, movesOfStudent;

    @FXML
    private Circle studentPosition1, studentPosition2, studentPosition3, studentPosition4, studentPosition5, studentPosition6, studentPosition7, studentPosition8, studentPosition9;
    @FXML
    private ImageView towerPosition1, towerPosition2, towerPosition3, towerPosition4, towerPosition5, towerPosition6, towerPosition7, towerPosition8;
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
    private Rectangle MNIsland1, MNIsland2, MNIsland3, MNIsland4, MNIsland5,MNIsland6, MNIsland7, MNIsland8, MNIsland9, MNIsland10, MNIsland11, MNIsland12;
    @FXML
    private ImageView Island1, Island2, Island3, Island4, Island5, Island6, Island7, Island8, Island9, Island10, Island11, Island12;
    @FXML
    private Circle greenStudentIsland1, greenStudentIsland2, greenStudentIsland3, greenStudentIsland4, greenStudentIsland5, greenStudentIsland6, greenStudentIsland7, greenStudentIsland8, greenStudentIsland9, greenStudentIsland10,greenStudentIsland11, greenStudentIsland12;
    @FXML
    private Circle redStudentIsland1, redStudentIsland2, redStudentIsland3, redStudentIsland4, redStudentIsland5, redStudentIsland6, redStudentIsland7, redStudentIsland8, redStudentIsland9, redStudentIsland10,redStudentIsland11, redStudentIsland12;
    @FXML
    private Circle yellowStudentIsland1, yellowStudentIsland2, yellowStudentIsland3, yellowStudentIsland4, yellowStudentIsland5, yellowStudentIsland6, yellowStudentIsland7, yellowStudentIsland8, yellowStudentIsland9, yellowStudentIsland10,yellowStudentIsland11, yellowStudentIsland12;
    @FXML
    private Circle magentaStudentIsland1, magentaStudentIsland2, magentaStudentIsland3, magentaStudentIsland4, magentaStudentIsland5, magentaStudentIsland6, magentaStudentIsland7, magentaStudentIsland8, magentaStudentIsland9, magentaStudentIsland10,magentaStudentIsland11, magentaStudentIsland12;
    @FXML
    private Circle cyanStudentIsland1, cyanStudentIsland2, cyanStudentIsland3, cyanStudentIsland4, cyanStudentIsland5, cyanStudentIsland6, cyanStudentIsland7, cyanStudentIsland8, cyanStudentIsland9, cyanStudentIsland10,cyanStudentIsland11, cyanStudentIsland12;
    @FXML
    private ImageView MNPosition1, MNPosition2, MNPosition3, MNPosition4, MNPosition5, MNPosition6, MNPosition7, MNPosition8,MNPosition9,MNPosition10, MNPosition11, MNPosition12;

    @FXML
    private ImageView towerIsland1,towerIsland2, towerIsland3, towerIsland4, towerIsland5, towerIsland6, towerIsland7, towerIsland8, towerIsland9, towerIsland10,towerIsland11,towerIsland12;

    @FXML
    private Circle cloudCard1, cloudCard2, cloudCard3;

    @FXML
    private Circle student1CC1, student2CC1, student3CC1, student4CC1;
    @FXML
    private Circle student1CC2, student2CC2, student3CC2, student4CC2;
    @FXML
    private Circle student1CC3, student2CC3, student3CC3, student4CC3;


    private int index, indexIsland, indexMN;
    private int indexCloudCard = -1;

    /**
     * This arrayList contain all the circle position to show the student on the island
     */
    private final ArrayList<Circle> greenStudentIsland= new ArrayList<>();
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
    private final ArrayList<ImageView> MNPositions = new ArrayList<>(12);
    private final ArrayList<ArrayList<Circle>> studentsInEachIsland = new ArrayList<>();
    private final ArrayList<Rectangle> MnIslandPosition = new ArrayList<>(12);

    private final ArrayList<ImageView> towerIslands = new ArrayList<>();
    private final ArrayList<ArrayList<Circle>> nameColor = new ArrayList<>();
    private final ClientInput clientInput = ClientInput.getInstance();
    private final Gson gson = new Gson();
    private static final String[] colors = {"green", "red", "yellow", "magenta", "cyan"};
    private TextMessage message;
    private final HashMap<String, ClientOut.Commd> commandHashMap = new HashMap<>();

    @Override
    public void displayLabel(@NotNull String text, Label label, String textLabel) {
        DisplayLabel.super.displayLabel(text, label, textLabel);
    }

    private void setUpCCArrays(){
        cloudCards.addAll(Arrays.asList(cloudCard1, cloudCard2, cloudCard3));
        setTransparentCircle(cloudCards);
        setUpStudentCC(studentsCC1,student1CC1,student2CC1,student3CC1,student4CC1);
        setUpStudentCC(studentsCC2,student1CC2, student2CC2, student3CC2, student4CC2);
        setUpStudentCC(studentsCC3,student1CC3,student2CC3,student3CC3,student4CC3);
        studentsInEachCloudCard.add(studentsCC1);
        studentsInEachCloudCard.add(studentsCC2);
        studentsInEachCloudCard.add(studentsCC3);
        disableCloudCards();

    }

    /**
     * disables the circle of the cloud cards
     */
    private void disableCloudCards(){
        for(Circle c : cloudCards)
            c.setDisable(true);
    }


    private void setCloudCards(){
        CloudCardStatus[] cloudCardStatus = gson.fromJson(message.message, CloudCardStatus[].class);
        setUpCCImages(cloudCardStatus);
        setUpCloudCards(cloudCardStatus);
    }


    private void setUpCCImages(CloudCardStatus[] cloudCardsStatus){
        int i=0;
        cloudCardDeckImages.extractRandomCloudCard();
        ArrayList<Image> cloudCardsInGame = cloudCardDeckImages.getCloudCardsInGame();

        for (Circle c : cloudCards){
            c.setFill(new ImagePattern(cloudCardsInGame.get(i)));
            if (cloudCardsStatus.length == 2 && i>=2)
                c.setFill(null);
            i++;
            c.setDisable(true);
        }
    }

    private void setUpCloudCards(CloudCardStatus[] cloudCardsStatus){
        int id=0;
        ArrayList<Integer> ordinalColor;

       for (CloudCardStatus cardsStatus : cloudCardsStatus) {
           ordinalColor = cardsStatus.ordinalColorOfStudents;
           setUpStudentsOnTheCC (id, ordinalColor);
           id++;
        }
    }
    private void setUpStudentsOnTheCC(int id, ArrayList<Integer> ordinalColor){
            int i=0;
            for (Integer studentColor : ordinalColor){
                if (ordinalColor != null) {
                    studentsInEachCloudCard.get(id).get(i).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + PawnColor.values()[studentColor].getName() + "_student.png")))));
                } else {
                    studentsInEachCloudCard.get(id).get(i).setFill(null);
                }
                i++;
            }
    }


    private void setUpStudentCC(ArrayList<Circle> cloudCards, Circle student1, Circle student2,Circle student3, Circle student4){
        cloudCards.add(student1);
        cloudCards.add(student2);
        cloudCards.add(student3);
        cloudCards.add(student4);
        setTransparentCircle(cloudCards);
    }

    /**
     * Added to arrayList Island all the island Images.
     */
    private void setUpIslandImageView(){
        Islands.addAll(Arrays.asList(Island1, Island2, Island3, Island4, Island5, Island6, Island7, Island8, Island9, Island10, Island11, Island12));
    }

    private void setUpMNIslandPosition(){
        MnIslandPosition.addAll(Arrays.asList(MNIsland1, MNIsland2, MNIsland3, MNIsland4, MNIsland5, MNIsland6, MNIsland7, MNIsland8, MNIsland9, MNIsland10, MNIsland11, MNIsland12));
        disableMNIslandPosition();
    }

    private void setUpMNPositions(){
        MNPositions.addAll(Arrays.asList(MNPosition1, MNPosition2, MNPosition3, MNPosition4, MNPosition5, MNPosition6, MNPosition7, MNPosition8, MNPosition9, MNPosition10, MNPosition11, MNPosition12));
    }

    private void setUpNameColor() {
        nameColor.addAll(Arrays.asList(greenPositions, redPositions, yellowPositions, magentaPositions, cyanPositions));
    }

    private void setUpClassroomFilled() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                classroomFilled[i][j] = false;
            }
        }
    }

    private void setUpTowerIslands(){
        towerIslands.addAll(Arrays.asList(towerIsland1, towerIsland2, towerIsland3, towerIsland4, towerIsland5, towerIsland6, towerIsland7, towerIsland8, towerIsland9, towerIsland10, towerIsland11, towerIsland12));
    }

    private void setUpTowerDashboard(){
        towerPosition.addAll(Arrays.asList(towerPosition1, towerPosition2, towerPosition3, towerPosition4, towerPosition5, towerPosition6, towerPosition7, towerPosition8));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandHashMap.put("error", this::manageError);
        commandHashMap.put("confirmation", this::manageConfirmation);
        commandHashMap.put("warning", this::printWarning);
        commandHashMap.put("notify", this::printNotify);
        commandHashMap.put("msg", this::printMessage);
        commandHashMap.put("dashboard", this::setDashboard);
        commandHashMap.put("hall", this::setDashboard);
        commandHashMap.put("player",this::setUpPlayerInfo);
        commandHashMap.put("gameInfo", this::setUpGameInfo);
        commandHashMap.put("quit", this::quit);
        commandHashMap.put("islands", this::setIslands);
        commandHashMap.put("singleIsland", this::printIsland);
        commandHashMap.put("cloudCard", this::setCloudCards);

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

    private void refreshGUI(){
        clientInput.sendString("singleDashboard", "");
        clientInput.sendString("islands","");
        clientInput.sendString("player","");
        clientInput.sendString("gameInfo","");
        clientInput.sendString("sendCloudCards", "");
    }

    /**
     * Displays an alert that prints the "notify" type messages sent by the server to the client.
     */
    private void printNotify() {
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, classRoom.getScene().getWindow(), "Notify", message.message);
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
    private void quit(){
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, classRoom.getScene().getWindow(), "Quit", message.message);
    }

    private void printIsland(){
        IslandStatus[] islandStatus = gson.fromJson(message.message, IslandStatus[].class);
        for(IslandStatus island : islandStatus){
            ArrayList<String> studentColors = island.colors;
            String id = String.valueOf(island.id);
            String dimension = String.valueOf(island.dimension);
            String owner = island.owner;
            StringBuilder studentNumber = new StringBuilder();
            int[] numberOfStudentsOfColor = new int[PawnColor.numberOfColors];
            for(int i = 0; i < PawnColor.numberOfColors; i++){
                numberOfStudentsOfColor[i] = countStudentByColor(studentColors, PawnColor.values()[i].getName());
                if(numberOfStudentsOfColor[i] != 0){
                    studentNumber.append("#").append(numberOfStudentsOfColor[i]).append(" ").append(PawnColor.values()[i].getName()).append(" ");
                }
            }
            displayLabel("Students on island#" + id , students,  "\nDimension: " + dimension + "\nOwner: " + owner + "\nStudents: " + studentNumber);
        }
    }

    private void printClassroom(String[][] classroom) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (classroom[i][j] != null) {
                    nameColor.get(i).get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colors[i] + "_student.png")))));
                }
            }
        }
    }


    /**
     * count the number of students of one color
     * @param colorName is the name of the color that we would to count
     * @param studentsColor is an arrayList that contains the strings of the color name of the students
     * @return the number of the students of one color
     */
    private int countStudentByColor(ArrayList<String> studentsColor, String colorName) {
        int numberOfStudents = 0;
        for(String s : studentsColor)
            if (Objects.equals(s, colorName)) {
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



    public void setUpTowerImages(int towersNumber, TowerColor towerColor) {
        String color = towerColor.getName();
        int j = 0;
        Image imgTower = new Image(String.valueOf(getClass().getClassLoader().getResource("images/Tower/" + color + "_tower.png")));
        for (int i = 0; i < towersNumber; i++) {
            towerPosition.get(i).setImage(imgTower);
            j = i;
        }
        j++;
        while(j < towerPosition.size()){
            towerPosition.get(j).setImage(null);
            j++;
        }

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

    public void setUpColorPositionOnTheIslands(){
        setUpColorPositionIslands(greenStudentIsland,greenStudentIsland1,greenStudentIsland2,greenStudentIsland3,greenStudentIsland4,greenStudentIsland5,greenStudentIsland6,greenStudentIsland7,greenStudentIsland8,greenStudentIsland9,greenStudentIsland10, greenStudentIsland11, greenStudentIsland12);
        setUpColorPositionIslands(redStudentIsland,redStudentIsland1,redStudentIsland2,redStudentIsland3,redStudentIsland4,redStudentIsland5,redStudentIsland6,redStudentIsland7,redStudentIsland8,redStudentIsland9,redStudentIsland10, redStudentIsland11, redStudentIsland12);
        setUpColorPositionIslands(yellowStudentIsland,yellowStudentIsland1,yellowStudentIsland2,yellowStudentIsland3,yellowStudentIsland4,yellowStudentIsland5,yellowStudentIsland6,yellowStudentIsland7,yellowStudentIsland8,yellowStudentIsland9,yellowStudentIsland10, yellowStudentIsland11, yellowStudentIsland12);
        setUpColorPositionIslands(magentaStudentIsland,magentaStudentIsland1,magentaStudentIsland2,magentaStudentIsland3,magentaStudentIsland4,magentaStudentIsland5,magentaStudentIsland6,magentaStudentIsland7,magentaStudentIsland8,magentaStudentIsland9,magentaStudentIsland10, magentaStudentIsland11, magentaStudentIsland12);
        setUpColorPositionIslands(cyanStudentIsland,cyanStudentIsland1,cyanStudentIsland2,cyanStudentIsland3,cyanStudentIsland4,cyanStudentIsland5,cyanStudentIsland6,cyanStudentIsland7,cyanStudentIsland8,cyanStudentIsland9,cyanStudentIsland10, cyanStudentIsland11, cyanStudentIsland12);
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

   private void setUpStudentsInEachIsland () {
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

    private void setIslands(){
        IslandStatus[] islands = gson.fromJson(message.message, IslandStatus[].class);
        setUpStudentsInEachIsland();
        setUpIsland(islands);
        setUpMNIslandPosition();

    }

    public void setUpIsland(IslandStatus[] island) {
        int id;
        ArrayList<Integer> studentsColorOrdinal;
        boolean presenceMN;
        String towerColor;
        int towerNumber;

       for (IslandStatus islandStatus : island){
           id = islandStatus.id-1;
           studentsColorOrdinal = islandStatus.studentColorOrdinal;
           System.out.println(studentsColorOrdinal);
           presenceMN = islandStatus.presenceMN;
           towerColor= islandStatus.towerColor;
           towerNumber= islandStatus.towerNumber;
           setUpStudentsOnTheIsland(studentsColorOrdinal , id, presenceMN);
           setUpTowerOnTheIsland(id, towerColor,towerNumber);
       }
    }
    public void setUpStudentsOnTheIsland(ArrayList<Integer> students, int idIsland, boolean presenceMN){
        for (Integer studentColor : students){
            if (studentColor != null){
                studentsInEachIsland.get(studentColor).get(idIsland).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + PawnColor.values()[studentColor].getName() + "_student.png")))));
            } else {
                studentsInEachIsland.get(studentColor).get(idIsland).setFill(null);
            }
        }
        if (presenceMN){
            MNPositions.get(idIsland).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/MotherNature.png"))));
            indexMN=idIsland;
        }else {
            MNPositions.get(idIsland).setImage(null);
        }

    }

    public void setUpTowerOnTheIsland(int idIsland, String towerColor, int towerNumber){
        if ( towerNumber!=0){
            towerIslands.get(idIsland).setImage( new Image(String.valueOf(getClass().getClassLoader().getResource("images/Tower/" + towerColor + "_tower.png"))));

        }
    }

    @FXML
    private void moveMNToIslands (MouseEvent event) {
        indexIsland = MnIslandPosition.indexOf((Rectangle) event.getSource());
        System.out.println(indexIsland + " MnIslandPosition");
        moveMn();
        enableCloudCards();
        System.out.println("Ciao");
        disableMNIslandPosition();
        disableMN();
    }


    private void disableMNIslandPosition() {
        for (Rectangle islands : MnIslandPosition) {
            islands.setDisable(true);
        }
    }

    private void disableMN(){
        for(ImageView mn : MNPositions)
            mn.setDisable(true);
    }

    private void enableCloudCards(){
        for (Circle cloudCard : cloudCards){
            cloudCard.setDisable(false);
        }
    }



    @FXML
    private void showStudentsIsland0(){
        clientInput.sendString("singleIsland", String.valueOf(1));
    }


    @FXML
    private void showStudentsIsland1(){
        clientInput.sendString("singleIsland", String.valueOf(2));
    }

   @FXML
    private void showStudentsIsland2(){
        clientInput.sendString("singleIsland", String.valueOf(3));
    }
    @FXML
    private void showStudentsIsland3(){
        clientInput.sendString("singleIsland", String.valueOf(4));
    }

    @FXML
    private void showStudentsIsland4(){
        clientInput.sendString("singleIsland", String.valueOf(5));
    }

    @FXML
    private void showStudentsIsland5(){
        clientInput.sendString("singleIsland", String.valueOf(6));
    }

    @FXML
    private void showStudentsIsland6(){
        clientInput.sendString("singleIsland", String.valueOf(7));
    }

    @FXML
    private void showStudentsIsland7(){
        clientInput.sendString("singleIsland", String.valueOf(8));
    }

    @FXML
    private void showStudentsIsland8(){
        clientInput.sendString("singleIsland", String.valueOf(9));
    }

    @FXML
    private void showStudentsIsland9(){
        clientInput.sendString("singleIsland", String.valueOf(10));
    }

    @FXML
    private void showStudentsIsland10(){
        clientInput.sendString("singleIsland", String.valueOf(11));
    }

    @FXML
    private void showStudentsIsland11(){
        clientInput.sendString("singleIsland", String.valueOf(12));
    }




    /**
     * takes the position of mother nature, then enable all the islands, except for the island where there is mother nature
     * @param event is the event generated by the "mouseClick" on the motherNature's image.
     */
    @FXML
    private void getIndexMN(MouseEvent event){
        indexMN = MNPositions.indexOf((ImageView) event.getSource());
        System.out.println(indexMN + " it's the position of MN");
        for (int mnPosition = 0 ; mnPosition < 12; mnPosition++){
            MnIslandPosition.get(mnPosition).setDisable(false);
            if (mnPosition == indexMN)
                MnIslandPosition.get(mnPosition).setDisable(true);
        }
    }

    @FXML
    private void moveMn (){
        ClientInput.getInstance().sendString("moveMN", String.valueOf(indexIsland + 1));
        refreshGUI();
    }


    @FXML
    private void chooseCC (MouseEvent event){
        indexCloudCard = cloudCards.indexOf((Circle) event.getSource());
        System.out.println(indexCloudCard + " is the CC chosen");
        if (indexCloudCard != -1){
            chooseCloudCard(event);
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.WARNING, cloudCards.get(indexCloudCard).getScene().getWindow(),"Error","Cloud Card not available" );
        }

    }

    @FXML
    private void chooseCloudCard (MouseEvent event){
        ClientInput.getInstance().sendString("chooseCC", String.valueOf(indexCloudCard+1));
        refreshGUI();
        indexCloudCard=-1;
    }





    @FXML
    private void getIndex(MouseEvent event) {
        index = studentsPosition.indexOf((Circle) event.getSource());
        System.out.println("student Position : " + index);
    }

    @FXML
    private void moveStudentToClassroom() {
        if (movesOfStudent > 0) {
            moveToClassroom();
        }
         else if(movesOfStudent == 0) {
            disableIslandAndHall();
        }
    }

    private void disableIslandAndHall(){
        for (ImageView island : Islands)
            island.setDisable(true);
        for(Circle student : studentsPosition)
            student.setDisable(true);
    }

    private void enableIslandAndHall(){
        for (ImageView island : Islands)
            island.setDisable(false);
        for(Circle student : studentsPosition)
            student.setDisable(false);
    }


    @FXML
    private void moveStudentToIsland (MouseEvent mouseEvent) {
        indexIsland = Islands.indexOf((ImageView) mouseEvent.getSource());
        System.out.println(indexIsland);
        if (movesOfStudent > 0) {
            moveToIsland();
            }
        else {
           disableIslandAndHall();
        }

    }

    @FXML
    private void moveToClassroom() {
        ClientInput.getInstance().sendString("moveStudentToClassroom", String.valueOf(index + 1));
        refreshGUI();
    }

    @FXML
    private void moveToIsland() {
        ClientInput.getInstance().sendString("moveStudentToIsland", String.valueOf(index+1),String.valueOf(indexIsland + 1));
        refreshGUI();
    }

    @FXML
    private void setPhasePage() throws IOException {
        Stage phaseStage = new Stage();
        Scene phaseScene = new Scene(new FXMLLoader(getClass().getResource("assistantCard.fxml")).load());
        phaseStage.setScene(phaseScene);
        phaseStage.showAndWait();
        refreshGUI();
        enableIslandAndHall();
    }


}

