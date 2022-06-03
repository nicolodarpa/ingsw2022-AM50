package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.ClientOut;
import it.polimi.ingsw.comunication.DashboardStatus;
import it.polimi.ingsw.comunication.GameInfoStatus;
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

    private int index = -1;

    private ArrayList<Circle> studentsPosition = new ArrayList<>(9);

    private ArrayList<ImageView> towerPosition = new ArrayList<>(8);


    private ArrayList<Circle> professorsPosition = new ArrayList<>(5);

    private ArrayList<Circle> greenPositions = new ArrayList<>(), redPositions = new ArrayList<>(), yellowPositions = new ArrayList<>(), magentaPositions = new ArrayList<>(), cyanPositions = new ArrayList<>();

    private final int numberOfPositionClassroom = 10;

    private Boolean[][] classroomFilled = new Boolean[PawnColor.numberOfColors][numberOfPositionClassroom];


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
        setUpClassroomFilled();
        setUpNameColor();
        setUpClassroom();
        setUpProfessorPositions();
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        readThread.start();
        clientInput.sendString("singleDashboard", "");
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

    public void setUpPlayerInfo() {
        PlayersStatus player = gson.fromJson(message.message, PlayersStatus[].class)[0];
        displayLabel("Username", username, player.getName());
        displayLabel("Moves of MN", movesOfMn, String.valueOf(player.getMovesOfMN()));
        movesOfStudents.setCounter(player.getMovesOfStudents());
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
    private void moveStudentToClassroom(MouseEvent mouseEvent) {
        if (movesOfStudents.getCounter() > 0) {
            if (index != -1) {
                moveToClassroom();
                this.movesOfStudents.decrement();
                displayLabel("Moves of students", movesAvailableCounter, movesOfStudents.toString());
            }

        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, classRoom.getScene().getWindow(),"Error","You have finished you moves");
        }


    }


    private void moveToClassroom() {
        ClientInput.getInstance().sendString("moveStudentToClassroom", String.valueOf(index + 1));
        index = -1;
    }


    public void moveStudentToIsland(ActionEvent actionEvent) throws IOException {
        if (movesOfStudents.getCounter() > 0) {
            setTable(actionEvent);
            this.movesOfStudents.decrement();
            displayLabel("Moves of students", movesAvailableCounter, movesOfStudents.toString());
        } else {
            alertFinishedTurn(actionEvent); //show an alert when you finish the moves
            setWaiting(actionEvent);
        }
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
