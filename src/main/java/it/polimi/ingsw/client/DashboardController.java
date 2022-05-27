package it.polimi.ingsw.client;

import com.google.gson.Gson;
import com.sun.javafx.stage.EmbeddedWindow;
import it.polimi.ingsw.comunication.DashboardStatus;
import it.polimi.ingsw.comunication.PlayersStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private Counter movesAvailable = new Counter();

    @FXML
    private Button moveStudentToClassroom;

    @FXML
    private Label movesAvailableCounter;

    @FXML
    private Label roundCounter;

    @FXML
    private Label order;

    private Student[] students = new Student[7];

    private String username;

    @FXML
    private Circle studentPosition1;
    @FXML
    private Circle studentPosition2;
    @FXML
    private Circle studentPosition3;
    @FXML
    private Circle studentPosition4;
    @FXML
    private Circle studentPosition5;
    @FXML
    private Circle studentPosition6;
    @FXML
    private Circle studentPosition7;

    @FXML
    private ImageView towerPosition1;
    @FXML
    private ImageView towerPosition2;
    @FXML
    private ImageView towerPosition3;
    @FXML
    private ImageView towerPosition4;
    @FXML
    private ImageView towerPosition5;
    @FXML
    private ImageView towerPosition6;
    @FXML
    private ImageView towerPosition7;
    @FXML
    private ImageView towerPosition8;

    @FXML
    private Circle professorPosition1;
    @FXML
    private Circle professorPosition2;
    @FXML
    private Circle professorPosition3;
    @FXML
    private Circle professorPosition4;
    @FXML
    private Circle professorPosition5;


    @FXML
    private Circle redPosition1;
    @FXML
    private Circle redPosition2;
    @FXML
    private Circle redPosition3;
    @FXML
    private Circle redPosition4;
    @FXML
    private Circle redPosition5;
    @FXML
    private Circle redPosition6;
    @FXML
    private Circle redPosition7;
    @FXML
    private Circle redPosition8;
    @FXML
    private Circle redPosition9;
    @FXML
    private Circle redPosition10;

    @FXML
    private Circle greenPosition1;
    @FXML
    private Circle greenPosition2;
    @FXML
    private Circle greenPosition3;
    @FXML
    private Circle greenPosition4;
    @FXML
    private Circle greenPosition5;
    @FXML
    private Circle greenPosition6;
    @FXML
    private Circle greenPosition7;
    @FXML
    private Circle greenPosition8;
    @FXML
    private Circle greenPosition9;
    @FXML
    private Circle greenPosition10;

    @FXML
    private Circle yellowPosition1;
    @FXML
    private Circle yellowPosition2;
    @FXML
    private Circle yellowPosition3;
    @FXML
    private Circle yellowPosition4;
    @FXML
    private Circle yellowPosition5;
    @FXML
    private Circle yellowPosition6;
    @FXML
    private Circle yellowPosition7;
    @FXML
    private Circle yellowPosition8;
    @FXML
    private Circle yellowPosition9;
    @FXML
    private Circle yellowPosition10;

    @FXML
    private Circle magentaPosition1;
    @FXML
    private Circle magentaPosition2;
    @FXML
    private Circle magentaPosition3;
    @FXML
    private Circle magentaPosition4;
    @FXML
    private Circle magentaPosition5;
    @FXML
    private Circle magentaPosition6;
    @FXML
    private Circle magentaPosition7;
    @FXML
    private Circle magentaPosition8;
    @FXML
    private Circle magentaPosition9;
    @FXML
    private Circle magentaPosition10;

    @FXML
    private Circle cyanPosition1;
    @FXML
    private Circle cyanPosition2;
    @FXML
    private Circle cyanPosition3;
    @FXML
    private Circle cyanPosition4;
    @FXML
    private Circle cyanPosition5;
    @FXML
    private Circle cyanPosition6;
    @FXML
    private Circle cyanPosition7;
    @FXML
    private Circle cyanPosition8;
    @FXML
    private Circle cyanPosition9;
    @FXML
    private Circle cyanPosition10;

    @FXML
    private Rectangle classRoom;








    private ArrayList<Circle> studentsPosition = new ArrayList<>(9);
    private ArrayList<Image> imgsColorPawn = new ArrayList<>(7);
    private ArrayList<String> colorPawn= new ArrayList<>(7);
    private ArrayList<Student> studentsHall= new ArrayList<>(7);

    private ArrayList<ImageView> towerPosition = new ArrayList<>(8);

    private ArrayList<Circle> professorsPosition = new ArrayList<>(5);

    private ArrayList<Circle> greenPositions = new ArrayList<>();
    private ArrayList<Circle> redPositions = new ArrayList<>();
    private ArrayList<Circle> yellowPositions = new ArrayList<>();
    private ArrayList<Circle> magentaPositions = new ArrayList<>();
    private ArrayList<Circle> cyanPositions = new ArrayList<>();

    private Boolean [][] classroomFilled = new Boolean[5][10];


    private ArrayList<ArrayList> nameColor = new ArrayList<>();
    private EmbeddedWindow stage;

    public void setUpNameColor(){
        nameColor.add(greenPositions);
        nameColor.add(redPositions);
        nameColor.add(yellowPositions);
        nameColor.add(magentaPositions);
        nameColor.add(cyanPositions);
    }

    public void setUpClassroomFilled(){
        for (int i=0;i<5;i++){
            for (int j=0 ; j<10 ; j++){
                classroomFilled[i][j] = false;
            }
        }
    }


    /**
     * Open a window and show the message : "You run out of available moves"
     * @param actionEvent
     */

    public void alertRunOut(ActionEvent actionEvent){
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Finish moves", "You run out of available moves");
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void alertFinishedTurn(ActionEvent actionEvent){
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Finished turn", "You finished your turn, now you have to move Mother Nature");
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roundCounter.setText(String.valueOf(1)); //set the round at the beginning of a new match
        setUpClassroomFilled();

        setUpNameColor();
        setUpRedPositions();
        setUpCyanPositions();
        setUpGreenPositions();
        setUpYellowPositions();
        setUpMagentaPositions();
        setUpHall();
        setUpTowers();
        setUpProfessor();
        setUpRectangle();


        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        setUpHallImages(studentsBag);
        Tower tower = new Tower(TowerColor.grey);
        setUpTowerImages(tower);
        Teacher teacher = new Teacher(PawnColor.YELLOW);
        setUpProfessor(teacher);
        //setOrder();
    }
    public void setUpRectangle(){
        classRoom.setDisable(true);
    }

    public void setOrder(){
        ClientInput.getInstance().sendString("player", "");
        TextMessage message = ClientInput.getInstance().readLine();
        Gson gson = new Gson();
        PlayersStatus[] playersStatuses = gson.fromJson(message.message, PlayersStatus[].class);
        for(PlayersStatus playersStatus : playersStatuses){
            if(Objects.equals(username, playersStatus.name))
                order.setText(String.valueOf(playersStatus.order));
        }
    }

    public void setUpTowerImages(Tower tower){
        String color = tower.getColor().getName();
        Image imgTower = new Image(String.valueOf(getClass().getClassLoader().getResource("images/Tower/"+color+"_tower.png")));
        for(ImageView tow: towerPosition){
            tow.setImage(imgTower);
        }
    }

    public void setUpHall (){

        studentsPosition.add(studentPosition1);
        studentsPosition.add(studentPosition2);
        studentsPosition.add(studentPosition3);
        studentsPosition.add(studentPosition4);
        studentsPosition.add(studentPosition5);
        studentsPosition.add(studentPosition6);
        studentsPosition.add(studentPosition7);

    }

    public void setUpTowers(){
        towerPosition.add(towerPosition1);
        towerPosition.add(towerPosition2);
        towerPosition.add(towerPosition3);
        towerPosition.add(towerPosition4);
        towerPosition.add(towerPosition5);
        towerPosition.add(towerPosition6);
        towerPosition.add(towerPosition7);
        towerPosition.add(towerPosition8);
    }

    public void setUpProfessor(){
        professorsPosition.add(professorPosition1);
        professorsPosition.add(professorPosition2);
        professorsPosition.add(professorPosition3);
        professorsPosition.add(professorPosition4);
        professorsPosition.add(professorPosition5);
    }

    public void setUpRedPositions(){
        redPositions.add(redPosition1);
        redPositions.add(redPosition2);
        redPositions.add(redPosition3);
        redPositions.add(redPosition4);
        redPositions.add(redPosition5);
        redPositions.add(redPosition6);
        redPositions.add(redPosition7);
        redPositions.add(redPosition8);
        redPositions.add(redPosition9);
        redPositions.add(redPosition10);
        for (Circle circle : redPositions){
            circle.setFill(null);
            circle.setStroke(null);
        }

    }

    public void setUpGreenPositions(){
        greenPositions.add(greenPosition1);
        greenPositions.add(greenPosition2);
        greenPositions.add(greenPosition3);
        greenPositions.add(greenPosition4);
        greenPositions.add(greenPosition5);
        greenPositions.add(greenPosition6);
        greenPositions.add(greenPosition7);
        greenPositions.add(greenPosition8);
        greenPositions.add(greenPosition9);
        greenPositions.add(greenPosition10);
        for (Circle circle : greenPositions){
            circle.setFill(null);
            circle.setStroke(null);
        }


    }
    public void setUpMagentaPositions(){
        magentaPositions.add(magentaPosition1);
        magentaPositions.add(magentaPosition2);
        magentaPositions.add(magentaPosition3);
        magentaPositions.add(magentaPosition4);
        magentaPositions.add(magentaPosition5);
        magentaPositions.add(magentaPosition6);
        magentaPositions.add(magentaPosition7);
        magentaPositions.add(magentaPosition8);
        magentaPositions.add(magentaPosition9);
        magentaPositions.add(magentaPosition10);
        for (Circle circle : magentaPositions){
            circle.setFill(null);
            circle.setStroke(null);
        }

    }
    public void setUpYellowPositions(){
        yellowPositions.add(yellowPosition1);
        yellowPositions.add(yellowPosition2);
        yellowPositions.add(yellowPosition3);
        yellowPositions.add(yellowPosition4);
        yellowPositions.add(yellowPosition5);
        yellowPositions.add(yellowPosition6);
        yellowPositions.add(yellowPosition7);
        yellowPositions.add(yellowPosition8);
        yellowPositions.add(yellowPosition9);
        yellowPositions.add(yellowPosition10);
        for (Circle circle : yellowPositions){
            circle.setFill(null);
            circle.setStroke(null);
        }

    }
    public void setUpCyanPositions(){
        cyanPositions.add(cyanPosition1);
        cyanPositions.add(cyanPosition2);
        cyanPositions.add(cyanPosition3);
        cyanPositions.add(cyanPosition4);
        cyanPositions.add(cyanPosition5);
        cyanPositions.add(cyanPosition6);
        cyanPositions.add(cyanPosition7);
        cyanPositions.add(cyanPosition8);
        cyanPositions.add(cyanPosition9);
        cyanPositions.add(cyanPosition10);
        for (Circle circle : cyanPositions){
            circle.setFill(null);
            circle.setStroke(null);
        }
    }

    public void setUpProfessor(Teacher teacher){
        for (Circle c: professorsPosition){
            c.setStroke(null);
            c.setFill(null);
        }
        professorsPosition.get(teacher.getColor().ordinal()).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/"+teacher.getColor().getName()+"_professor.png")))));
    }



    public void setUpHallImages(StudentsBag studentsBag){
        for (int i=0; i<7; i++){
            Student s =studentsBag.casualExtraction();
            studentsHall.add(s);
        }
        for (Student student : studentsHall){
            String colorOfStudent = new String();
            colorOfStudent= student.getColor().getName();
            colorPawn.add(colorOfStudent);
        }
        for (String color : colorPawn){
            Image image = new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + color +"_student.png") ));
            imgsColorPawn.add(image);
        }
        int i=0;
        for (Circle c : studentsPosition){
            c.setStroke(null);
            c.setFill(new ImagePattern(imgsColorPawn.get(i)));
            i++;
        }
    }



    public void moveStudentToClassroom(MouseEvent mouseEvent) throws IOException {
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(),mouseEvent.getTarget());
        classRoom.setDisable(false);

       if (mouseEvent.isConsumed() != true){
                classRoom.setOnMouseClicked(event -> {
                   try {
                       moveToClassroom(mouseEvent);
                       if (movesAvailable.getCounter() > 0) {
                           this.movesAvailable.decrement();
                           movesAvailableCounter.setText(movesAvailable.toString());
                           if (movesAvailable.getCounter() == 0) {
                               alertFinishedTurn(ae);
                               for (Circle c : studentsPosition)
                                   c.setDisable(true);
                           }
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               );
       }

    }

    public void moveToClassroom(MouseEvent event) throws IOException {

        String idStudentPosition = event.getPickResult().getIntersectedNode().getId();
        int i = 0;
        for (int j = 0; j <7 ; j++){
            if (idStudentPosition == studentsPosition.get(j).getId())
                 i=j;
        }

        if (colorPawn.get(i)== "red"){
            for (int j=0 ; j<10 ; j++){
                if (classroomFilled[1][j]== false) {
                    redPositions.get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colorPawn.get(i) + "_student.png")))));
                    classroomFilled[1][j] = true;
                    studentsPosition.get(i).setFill(null);
                    colorPawn.set(i,null);
                    classRoom.setDisable(true);

                    return;
                }
            }
        }
        else if (colorPawn.get(i)== "green"){
            for (int j=0 ; j<10 ; j++){
                if (classroomFilled[0][j]== false) {
                    greenPositions.get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colorPawn.get(i) + "_student.png")))));
                    classroomFilled[0][j] = true;
                    studentsPosition.get(i).setFill(null);
                    colorPawn.set(i,null);
                    classRoom.setDisable(true);
                    return;
                }
            }
        }
        else if (colorPawn.get(i)== "yellow"){
            for (int j=0 ; j<10 ; j++){
                if (classroomFilled[2][j]== false) {
                    yellowPositions.get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colorPawn.get(i) + "_student.png")))));
                    classroomFilled[2][j] = true;
                    studentsPosition.get(i).setFill(null);
                    colorPawn.set(i,null);
                    classRoom.setDisable(true);
                    return;
                }
            }
        }
        else if (colorPawn.get(i)== "magenta"){
            for (int j=0 ; j<10 ; j++){
                if (classroomFilled[3][j]== false) {
                    magentaPositions.get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colorPawn.get(i) + "_student.png")))));
                    classroomFilled[3][j] = true;
                    studentsPosition.get(i).setFill(null);
                    colorPawn.set(i,null);
                    classRoom.setDisable(true);
                    return;
                }
            }
        }
        else if (colorPawn.get(i)== "cyan"){
            for (int j=0 ; j<10 ; j++){
                if (classroomFilled[4][j]== false) {
                    cyanPositions.get(j).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colorPawn.get(i) + "_student.png")))));
                    classroomFilled[4][j] = true;
                    studentsPosition.get(i).setFill(null);
                    colorPawn.set(i,null);
                    classRoom.setDisable(true);
                    return;
                }
            }
        }
    }




    public void moveStudentToIsland(ActionEvent actionEvent) throws IOException {
        if(movesAvailable.getCounter() > 0){
            this.movesAvailable.decrement();
            movesAvailableCounter.setText(movesAvailable.toString());
            setTable(actionEvent);
        }
        else{
            alertRunOut(actionEvent); //show an alert when you finish the moves
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


    public void setWaiting(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("waiting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
