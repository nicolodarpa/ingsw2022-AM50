package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.ClientOut;
import it.polimi.ingsw.comunication.DashboardStatus;
import it.polimi.ingsw.comunication.GameInfoStatus;
import it.polimi.ingsw.comunication.IslandStatus;
import it.polimi.ingsw.comunication.PlayersStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import javafx.stage.Window;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.net.URL;

import java.util.*;

public class DashboardController implements Initializable, DisplayLabel {

    public Button characterCard;
    public Button moveToIsland;
    public Pane anchor;
    @FXML
    private Label movesAvailableCounter,movesOfMn, username, roundCounter, actualPlayerLabel;

    private Counter movesOfStudents = new Counter();
    private Counter mnMoves = new Counter();

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

    private int index = -1;
    private int indexIsland = -1;
    private int indexMN = -1;





    private ArrayList<Circle> greenStudentIsland= new ArrayList<>(), redStudentIsland = new ArrayList<>(), yellowStudentIsland = new ArrayList<>(), magentaStudentIsland = new ArrayList<>(), cyanStudentIsland = new ArrayList<>();


    private ArrayList<Circle> studentsPosition = new ArrayList<>(9);

    private ArrayList<ImageView> towerPosition = new ArrayList<>(8);


    private ArrayList<Circle> professorsPosition = new ArrayList<>(5);

    private ArrayList<Circle> greenPositions = new ArrayList<>(), redPositions = new ArrayList<>(), yellowPositions = new ArrayList<>(), magentaPositions = new ArrayList<>(), cyanPositions = new ArrayList<>();

    private final int numberOfPositionClassroom = 10;

    private Boolean[][] classroomFilled = new Boolean[PawnColor.numberOfColors][numberOfPositionClassroom];

    private ArrayList<ImageView> Islands = new ArrayList<>(12);
    private ArrayList<ImageView> MNPositions = new ArrayList<>(12);
    private ArrayList<ArrayList<Circle>> studentsInEachIsland = new ArrayList<>();
    private ArrayList<Rectangle> MnIslandPosition = new ArrayList<>(12);


    private ArrayList<ArrayList<Circle>> nameColor = new ArrayList<>();

    private final ClientInput clientInput = ClientInput.getInstance();
    private final Gson gson = new Gson();

    private static final String[] colors = {"green", "red", "yellow", "magenta", "cyan"};
    private TextMessage message;

    private final HashMap<String, ClientOut.Commd> commandHashMap = new HashMap<>();

    @Override
    public void displayLabel(@NotNull String text, Label label, String textLabel) {
        DisplayLabel.super.displayLabel(text, label, textLabel);
    }
/*
    public Label getMovesOfMnLabel() {
        return movesOfMn;
    }

    public Label getOrderLabel() {
        return order;
    }

    public Label getUsernameLabel() {
        return username;
    }*/

    public void setUpIslandImageView(){
        Islands.add(Island1);
        Islands.add(Island2);
        Islands.add(Island3);
        Islands.add(Island4);
        Islands.add(Island5);
        Islands.add(Island6);
        Islands.add(Island7);
        Islands.add(Island8);
        Islands.add(Island9);
        Islands.add(Island10);
        Islands.add(Island11);
        Islands.add(Island12);

       // for (ImageView island : Islands){
         //   island.setDisable(true);
        //}
    }

    public void setUpMNIslandPosition(){
        MnIslandPosition.add(MNIsland1);
        MnIslandPosition.add(MNIsland2);
        MnIslandPosition.add(MNIsland3);
        MnIslandPosition.add(MNIsland4);
        MnIslandPosition.add(MNIsland5);
        MnIslandPosition.add(MNIsland6);
        MnIslandPosition.add(MNIsland7);
        MnIslandPosition.add(MNIsland8);
        MnIslandPosition.add(MNIsland9);
        MnIslandPosition.add(MNIsland10);
        MnIslandPosition.add(MNIsland11);
        MnIslandPosition.add(MNIsland12);

        for (Rectangle rectangle : MnIslandPosition){
            rectangle.setDisable(true);
        }
    }

    public void setUpMNPositions(){
        MNPositions.add(MNPosition1);
        MNPositions.add(MNPosition2);
        MNPositions.add(MNPosition3);
        MNPositions.add(MNPosition4);
        MNPositions.add(MNPosition5);
        MNPositions.add(MNPosition6);
        MNPositions.add(MNPosition7);
        MNPositions.add(MNPosition8);
        MNPositions.add(MNPosition9);
        MNPositions.add(MNPosition10);
        MNPositions.add(MNPosition11);
        MNPositions.add(MNPosition12);
   }

    public void setUpNameColor() {
        nameColor.add(greenPositions);
        nameColor.add(redPositions);
        nameColor.add(yellowPositions);
        nameColor.add(magentaPositions);
        nameColor.add(cyanPositions);
    }

    public void setUpClassroomFilled() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                classroomFilled[i][j] = false;
            }
        }
    }


    /**
     * Open a window and show the message : "You finished your turn, now you have to move Mother Nature"
     *
     * @param actionEvent
     */
    public void alertFinishedTurn(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Finished turn", "You finished your turn, now you have to move Mother Nature");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commandHashMap.put("error", this::manageError);
        commandHashMap.put("confirmation", this::manageConfirmation);
        commandHashMap.put("warning", this::printWarning);
        commandHashMap.put("notify", this::printNotify);
        commandHashMap.put("msg", this::printMessage);
        commandHashMap.put("dashboard", this::setDashboard);
        commandHashMap.put("player",this::setUpPlayerInfo);
        commandHashMap.put("gameInfo", this::setUpGameInfo);
        commandHashMap.put("quit", this::quit);
        commandHashMap.put("islands", this::setIslands);

        setUpClassroomFilled();
        setUpNameColor();
        setUpClassroom();
        setUpProfessorPositions();
        setUpColorPositionOnTheIslands();
        setUpIslandImageView();
        setUpMNPositions();
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    message = clientInput.readLine();
                    if (message != null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                     commandHashMap.get(message.type).runCommand();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        readThread.start();
        clientInput.sendString("singleDashboard", "");
        clientInput.sendString("islands","");
        clientInput.sendString("player","");
        clientInput.sendString("gameInfo","");

    }



    private void printNotify() {
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, classRoom.getScene().getWindow(), "Notify", message.message);
    }

    private void manageError() {
        AlertHelper.showAlert(Alert.AlertType.ERROR, classRoom.getScene().getWindow(), "Error", message.message);
    }


    private void manageConfirmation() {
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, classRoom.getScene().getWindow(), "Confirmation", message.message);
    }

    private void printWarning() {
        AlertHelper.showAlert(Alert.AlertType.WARNING, classRoom.getScene().getWindow(), "Warning", message.message);
    }

    private void printMessage() {
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, classRoom.getScene().getWindow(), "Message", message.message);
    }

    private void quit(){
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, classRoom.getScene().getWindow(), "Quit", message.message);
    }


    public void setUpTowerImages(DashboardStatus dashboard) {
        towerPosition.add(towerPosition1);
        towerPosition.add(towerPosition2);
        towerPosition.add(towerPosition3);
        towerPosition.add(towerPosition4);
        towerPosition.add(towerPosition5);
        towerPosition.add(towerPosition6);
        if (dashboard.towers > 7) {
            towerPosition.add(towerPosition7);
            towerPosition.add(towerPosition8);
        }
        String color = dashboard.towerColor.getName();

        Image imgTower = new Image(String.valueOf(getClass().getClassLoader().getResource("images/Tower/" + color + "_tower.png")));
        for (ImageView tow : towerPosition) {
            tow.setImage(imgTower);
        }

    }

    public void setUpHall() {
        studentsPosition.add(studentPosition1);
        studentsPosition.add(studentPosition2);
        studentsPosition.add(studentPosition3);
        studentsPosition.add(studentPosition4);
        studentsPosition.add(studentPosition5);
        studentsPosition.add(studentPosition6);
        studentsPosition.add(studentPosition7);
        studentsPosition.add(studentPosition8);
        studentsPosition.add(studentPosition9);

    }

    public void setUpProfessorPositions() {
        professorsPosition.add(professorPosition1);
        professorsPosition.add(professorPosition2);
        professorsPosition.add(professorPosition3);
        professorsPosition.add(professorPosition4);
        professorsPosition.add(professorPosition5);
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
        colorPositions.add(colorPosition1);
        colorPositions.add(colorPosition2);
        colorPositions.add(colorPosition3);
        colorPositions.add(colorPosition4);
        colorPositions.add(colorPosition5);
        colorPositions.add(colorPosition6);
        colorPositions.add(colorPosition7);
        colorPositions.add(colorPosition8);
        colorPositions.add(colorPosition9);
        colorPositions.add(colorPosition10);
        setTransparentCircle(colorPositions);
    }

    private void setUpColorPositionIslands(ArrayList<Circle> colorPositions, Circle colorPosition1, Circle colorPosition2, Circle colorPosition3, Circle colorPosition4, Circle colorPosition5, Circle colorPosition6, Circle colorPosition7, Circle colorPosition8, Circle colorPosition9, Circle colorPosition10, Circle colorPosition11, Circle colorPosition12) {
        colorPositions.add(colorPosition1);
        colorPositions.add(colorPosition2);
        colorPositions.add(colorPosition3);
        colorPositions.add(colorPosition4);
        colorPositions.add(colorPosition5);
        colorPositions.add(colorPosition6);
        colorPositions.add(colorPosition7);
        colorPositions.add(colorPosition8);
        colorPositions.add(colorPosition9);
        colorPositions.add(colorPosition10);
        colorPositions.add(colorPosition11);
        colorPositions.add(colorPosition12);
        setTransparentCircle(colorPositions);
    }

   private void setUpStudentsInEachIsland () {
       studentsInEachIsland.add(greenStudentIsland);
       studentsInEachIsland.add(redStudentIsland);
       studentsInEachIsland.add(yellowStudentIsland);
       studentsInEachIsland.add(magentaStudentIsland);
       studentsInEachIsland.add(cyanStudentIsland);
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

    public void setUpStudentsOnTheIsland(ArrayList<Integer> students, int idIsland){
        for (Integer studentColor : students){
            if (studentColor != null){
                studentsInEachIsland.get(studentColor).get(idIsland).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + PawnColor.values()[studentColor].getName() + "_student.png")))));
            } else {
                studentsInEachIsland.get(studentColor).get(idIsland).setFill(null);
            }
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

    private void setDashboard() {
        while (!Objects.equals(message.type, "dashboard")) {
            message = clientInput.readLine();
        }
        DashboardStatus dashboardStatus = gson.fromJson(message.message, DashboardStatus[].class)[0];
        String[] hall = dashboardStatus.studentsHallColors;
        String[][] classroom = dashboardStatus.studentsClassroom;
        String[] teachers = dashboardStatus.teacherTable;
        setUpHall();
        setUpHallImages(hall);
        printClassroom(classroom);
        setUpProfessorImages(teachers);
        setUpTowerImages(dashboardStatus);
    }

    private void setIslands(){
       while (!Objects.equals(message.type, "islands")) {
            message = clientInput.readLine();
       }
        IslandStatus[] islands = gson.fromJson(message.message, IslandStatus[].class);
        setUpStudentsInEachIsland();
        setUpIsland(islands);
        setUpMNIslandPosition();
    }

    public void setUpIsland(IslandStatus[] island){

        int id = 0;
        ArrayList<Integer> studentsColorOrdinal = null;
        boolean presenceMN = false;

       for (IslandStatus islandStatus : island){
           islandStatus.id= id;
           studentsColorOrdinal = islandStatus.studentColorOrdinal;
           System.out.println(studentsColorOrdinal);
           presenceMN = islandStatus.presenceMN;
           if (presenceMN == true ){
               MNPositions.get(id).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/MotherNature.png"))));
               indexMN=id;
           }
           setUpStudentsOnTheIsland(studentsColorOrdinal , id);
           id++;
       }
    }


    @FXML
    private void moveMNToIslands (MouseEvent event){
        indexMN = MNPositions.indexOf(event.getSource());
        if (mnMoves.getCounter() > 0) {
            if (indexMN != -1) {
                for (Rectangle islandLanded : MnIslandPosition)
                islandLanded.setOnMouseClicked(e -> {
                    indexIsland = MnIslandPosition.indexOf(e.getSource());
                    moveMn(e);
                    MNPositions.get(indexMN).setImage(null);
                    MNPositions.get(indexIsland).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/MotherNature.png"))));
                    this.mnMoves.decrement();
                    displayLabel("Moves of MN", movesOfMn, mnMoves.toString());
                });
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, classRoom.getScene().getWindow(),"Error","You have finished you moves");
        }
    }


    @FXML
    private void getIndexMN(MouseEvent event){
        indexMN = MNPositions.indexOf(event.getSource());
        System.out.println(indexMN);
    }

    @FXML
    private void moveMn ( MouseEvent event){
        ClientInput.getInstance().sendString("moveMN", String.valueOf(indexIsland + 1));
        indexIsland = -1;
    }


    public void setUpPlayerInfo() {
        PlayersStatus player = gson.fromJson(message.message, PlayersStatus[].class)[0];
        displayLabel("Username", username, player.getName());
        movesOfStudents.setCounter(player.getMovesOfStudents());
        mnMoves.setCounter(player.getMovesOfMN());
        displayLabel("Moves of MN", movesOfMn, String.valueOf(player.getMovesOfMN()));
        displayLabel("Moves of students", movesAvailableCounter, String.valueOf(movesOfStudents.getCounter()));
    }

    public void setUpGameInfo() {
        GameInfoStatus gameInfoStatus = gson.fromJson(message.message, GameInfoStatus[].class)[0];
        displayLabel("Round #", roundCounter, gameInfoStatus.round);
        displayLabel("Actual Player", actualPlayerLabel, gameInfoStatus.actualPlayer);
    }


    @FXML
    private void getIndex(MouseEvent event) {
        index = studentsPosition.indexOf(event.getSource());
        System.out.println(index);
    }

    @FXML
    private void moveStudentToClassroom(MouseEvent mouseEvent) throws IOException {
        if (movesOfStudents.getCounter() > 0) {
            if (index != -1) {
                moveToClassroom(mouseEvent);
                this.movesOfStudents.decrement();
                displayLabel("Moves of students", movesAvailableCounter, movesOfStudents.toString());
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, classRoom.getScene().getWindow(),"Error","You have finished you moves");
            for (Rectangle rectangle : MnIslandPosition){
                rectangle.setDisable(true);
            }

        }
    }


    @FXML
    private void moveStudentToIsland (MouseEvent mouseEvent) throws IOException {
        indexIsland = Islands.indexOf(mouseEvent.getSource());
        System.out.println(indexIsland);
        if (movesOfStudents.getCounter() > 0) {
            if ( index!=-1){
                studentsPosition.get(index).setFill(null);
                studentsPosition.get(index).setDisable(true);
                moveToIsland(mouseEvent);
                this.movesOfStudents.decrement();
                displayLabel("Moves of students", movesAvailableCounter, movesOfStudents.toString());

            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, Islands.get(indexIsland).getScene().getWindow(),"Error","You have finished you moves");
            for (Rectangle rectangle : MnIslandPosition){
                rectangle.setDisable(true);
            }
            for (ImageView island : Islands){
                island.setDisable(true);
            }
        }

    }



    @FXML
    private void moveToClassroom(MouseEvent event) {
        ClientInput.getInstance().sendString("moveStudentToClassroom", String.valueOf(index + 1));
        index = -1;
    }


    @FXML
    private void moveToIsland(MouseEvent event) throws IOException {
        ClientInput.getInstance().sendString("moveStudentToIsland", String.valueOf(index+1),String.valueOf(indexIsland + 1));
        indexIsland = -1;
        index = -1;
    }


    public void playCharacterCard(ActionEvent actionEvent) {
    }

    public void setTable(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void setWaiting(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("waiting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
