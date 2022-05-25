package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.PlayersStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private Counter movesAvailable = new Counter();

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
    private Circle professorPosition6;
    @FXML
    private Circle professorPosition7;

    private ArrayList<Circle> studentsPosition = new ArrayList<>(7);
    private ArrayList<Image> imgsColorPawn = new ArrayList<>(7);
    private ArrayList<String> colorPawn= new ArrayList<>(7);
    private ArrayList<Student> studentsHall= new ArrayList<>(7);

    private ArrayList<ImageView> towerPosition = new ArrayList<>(8);

    private ArrayList<Circle> professorsPosition = new ArrayList<>(5);


    /**
     * Open a window and show the message : "You run out of available moves"
     * @param actionEvent
     */
    public void alertRunOut(ActionEvent actionEvent){
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Finish moves", "You run out of available moves");
    }

    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roundCounter.setText(String.valueOf(1)); //set the round at the beginning of a new match
        setUpHall();
        setUpTowers();
        setUpProfessor();
        StudentsBag studentsBag = new StudentsBag();
        studentsBag.fillBag(120);
        setUpHallImages(studentsBag);
        Tower tower = new Tower(TowerColor.grey);
        setUpTowerImages(tower);
        Teacher teacher = new Teacher(PawnColor.YELLOW);
        setUpProfessor(teacher);
        //setOrder();
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

    public void moveStudentToClassroom(ActionEvent actionEvent) throws IOException{
        if(movesAvailable.getCounter() > 0){
            this.movesAvailable.decrement();
            movesAvailableCounter.setText(movesAvailable.toString());
        }
        else{
            alertRunOut(actionEvent); //show an alert when you finish the moves
            setWaiting(actionEvent);
        }
    }


    public void addStudentToClassroom(MouseEvent event){

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
