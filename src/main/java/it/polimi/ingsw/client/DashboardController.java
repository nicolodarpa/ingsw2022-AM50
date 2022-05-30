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
    private Label movesAvailableCounter, roundCounter, order, movesOfMn, Username;

    private Student[] students = new Student[7];
    @FXML
    private Circle studentPosition1, studentPosition2, studentPosition3, studentPosition4, studentPosition5, studentPosition6, studentPosition7;
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







    private ArrayList<Circle> studentsPosition = new ArrayList<>(9);
    private ArrayList<Image> imgsColorPawn = new ArrayList<>(7);
    private ArrayList<String> colorPawn= new ArrayList<>(7);
    private ArrayList<Student> studentsHall= new ArrayList<>(7);

    private ArrayList<ImageView> towerPosition = new ArrayList<>(8);

    private ArrayList<Circle> professorsPosition = new ArrayList<>(5);

    private ArrayList<Circle> greenPositions = new ArrayList<>(), redPositions = new ArrayList<>(), yellowPositions = new ArrayList<>(), magentaPositions = new ArrayList<>(), cyanPositions = new ArrayList<>();

    private Boolean [][] classroomFilled = new Boolean[5][10];


    private ArrayList<ArrayList> nameColor = new ArrayList<>();
    private EmbeddedWindow stage;
    private final ClientInput clientInput = ClientInput.getInstance();
    private final Gson gson = new Gson();

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
        Username.setText(username);
    }

    public void setMovesOfMn(int movesOfMN){
        movesOfMn.setText(String.valueOf(movesOfMN));
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
        setUpHall();
        setUpClassroom();
        setUpTowers();
        setUpProfessor();
        setUpRectangle();
        setDashboard();
        Tower tower = new Tower(TowerColor.grey);
        setUpTowerImages(tower);
        Teacher teacher = new Teacher(PawnColor.YELLOW);
        setUpProfessor(teacher);
    }
    public void setUpRectangle(){
        classRoom.setDisable(true);
    }

    public void setOrder(int orderOfPlayer){
        order.setText(String.valueOf(orderOfPlayer));
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

    /**
     * set to transparent the background of the position of the students in the classroom
     * @param colorPosition indicate the student's color of the row in the classroom
     */
    public void setTransparentCircle(ArrayList<Circle> colorPosition) {
        for (Circle circle : colorPosition) {
            circle.setFill(null);
            circle.setStroke(null);
        }
    }


    public void setUpClassroom(){
        setUpColorPosition(greenPositions, greenPosition1, greenPosition2, greenPosition3, greenPosition4, greenPosition5, greenPosition6, greenPosition7, greenPosition8, greenPosition9, greenPosition10);
        setUpColorPosition(magentaPositions, magentaPosition1, magentaPosition2, magentaPosition3, magentaPosition4, magentaPosition5, magentaPosition6, magentaPosition7, magentaPosition8, magentaPosition9, magentaPosition10);
        setUpColorPosition(yellowPositions, yellowPosition1, yellowPosition2, yellowPosition3, yellowPosition4, yellowPosition5, yellowPosition6, yellowPosition7, yellowPosition8, yellowPosition9, yellowPosition10);
        setUpColorPosition(cyanPositions, cyanPosition1, cyanPosition2, cyanPosition3, cyanPosition4, cyanPosition5, cyanPosition6, cyanPosition7, cyanPosition8, cyanPosition9, cyanPosition10);
        setUpColorPosition(redPositions, redPosition1, redPosition2, redPosition3, redPosition4, redPosition5, redPosition6, redPosition7, redPosition8, redPosition9, redPosition10);
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

    public void setUpProfessor(Teacher teacher){
        for (Circle c: professorsPosition){
            c.setStroke(null);
            c.setFill(null);
        }
        professorsPosition.get(teacher.getColor().ordinal()).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/"+teacher.getColor().getName()+"_professor.png")))));
    }



    public void setUpHallImages(PawnColor[] hall){
        for (PawnColor student: hall){
            Student s = new Student(student);
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

    private void setDashboard() {
        clientInput.sendString("singleDashboard", "");
        TextMessage response = clientInput.readLine();
        while (!Objects.equals(response.type, "dashboard")) {
            response = clientInput.readLine();
        }
        DashboardStatus dashboardStatus = gson.fromJson(response.message, DashboardStatus[].class)[0];
        PawnColor[] hall = dashboardStatus.studentsHallColors;
        setUpHallImages(hall);

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


    public void setHallNull(int positionOfTheHall){
        studentsPosition.get(positionOfTheHall).setFill(null);
        colorPawn.set(positionOfTheHall,null);
        classRoom.setDisable(true);
    }

    public void setImages(ArrayList<Circle> colorPositions, int position, int positionHall){
        colorPositions.get(position).setFill(new ImagePattern(new Image(String.valueOf(getClass().getClassLoader().getResource("images/Pawn/" + colorPawn.get(positionHall) + "_student.png")))));
    }

    public void moveToClassroom(MouseEvent event) throws IOException {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        String idStudentPosition = event.getPickResult().getIntersectedNode().getId();
        int positionHall = 0;
        for (int positionClicked = 0; positionClicked < 7 ; positionClicked++){
            if (Objects.equals(idStudentPosition, studentsPosition.get(positionClicked).getId()))
                positionHall = positionClicked;
                ClientInput.getInstance().sendString("moveStudentToClassroom", String.valueOf(positionHall+1));
                TextMessage message = ClientInput.getInstance().readLine();
                if(Objects.equals(message.type, "error"))
                    AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid position", message.message);
        }
        PawnColor color = studentsHall.get(positionHall).getColor();
        for ( int classroomPosition = 0 ; classroomPosition < 10 ; classroomPosition++ ){
            if (!classroomFilled[color.ordinal()][classroomPosition]) {
                setImages(nameColor.get(color.ordinal()), classroomPosition, positionHall);
                classroomFilled[color.ordinal()][classroomPosition] = true;
                setHallNull(positionHall);
                return;
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
