package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.TowerColor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class DashboardEnemyController implements Initializable, DisplayLabel{

        public Pane anchor;

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
        private Label username = new Label();





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



        private final ArrayList<ArrayList<Circle>> nameColor = new ArrayList<>();
        private final ClientInput clientInput = ClientInput.getInstance();
        private final Gson gson = new Gson();
        private static final String[] colors = {"green", "red", "yellow", "magenta", "cyan"};








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


        private void setUpTowerDashboard() {
            towerPosition.addAll(Arrays.asList(towerPosition1, towerPosition2, towerPosition3, towerPosition4, towerPosition5, towerPosition6, towerPosition7, towerPosition8));
        }


        /**
         * @param location  The location used to resolve relative paths for the root object, or
         *                  {@code null} if the location is not known.
         * @param resources The resources used to localize the root object, or {@code null} if
         *                  the root object was not localized.
         */
        @Override
        public void initialize(URL location, ResourceBundle resources) {
            sendCommand();
            setUpClassroomFilled();
            setUpNameColor();
            setUpClassroom();
            setUpProfessorPositions();
            setUpTowerDashboard();
            setUpHallPosition();
            setDashboard();
        }


        /**
         * Send to server singleDashboard command
         */
        private void sendCommand() {
            ClientInput.getInstance().sendString("singleDashboard", "");
        }


        /**
         * It takes the param classroom, and it prints the students on the dashboard's classroom.
         * @param classroom is a matrix of string sent by the EchoServer.
         */
        private void printClassroom(String[][] classroom) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 10; j++) {
                    if (classroom[i][j] != null) {
                        nameColor.get(i).get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colors[i] + "_student.png")))));
                    }
                }
            }
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
            while (j < towerPosition.size()) {
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
            ClientInput.getInstance().sendString("player", "");
            PlayersStatus player = gson.fromJson(ClientInput.getInstance().readLine().message, PlayersStatus[].class)[0];
            String name = player.name;
            ClientInput.getInstance().sendString("dashboard", "");
            DashboardStatus[] dashboardStatuses = gson.fromJson(ClientInput.getInstance().readLine().message, DashboardStatus[].class);
            for(DashboardStatus dashboardStatus : dashboardStatuses){
                if(!Objects.equals(dashboardStatus.nameOwner, name)){
                    displayLabel("username", username, name);
                    String[] hall = dashboardStatus.studentsHallColors;
                    String[][] classroom = dashboardStatus.studentsClassroom;
                    String[] teachers = dashboardStatus.teacherTable;
                    setUpHallImages(hall);
                    printClassroom(classroom);
                    setUpProfessorImages(teachers);
                    setUpTowerImages(dashboardStatus.towers, dashboardStatus.towerColor);
                }
            }

        }




}
