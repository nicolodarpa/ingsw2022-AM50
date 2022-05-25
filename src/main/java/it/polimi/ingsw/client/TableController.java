package it.polimi.ingsw.client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TableController implements Initializable {

    @FXML
    private Circle circle;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;

    @FXML
    private ImageView island1;
    @FXML
    private ImageView island2;
    @FXML
    private ImageView island3;
    @FXML
    private ImageView island4;
    @FXML
    private ImageView island5;
    @FXML
    private ImageView island6;
    @FXML
    private ImageView island7;
    @FXML
    private ImageView island8;
    @FXML
    private ImageView island9;
    @FXML
    private ImageView island10;
    @FXML
    private ImageView island11;
    @FXML
    private ImageView island12;
    @FXML
    private ImageView student;

    @FXML
    private ImageView dashboard1;
    @FXML
    private ImageView dashboard2;
    @FXML
    private ImageView dashboard3;


    @FXML
    private Text txt1;
    @FXML
    private Text txt2;
    @FXML
    private Text txt3;

    private ArrayList<Circle> cloudcardCircle = new ArrayList<>(3);
    private CloudCardDeckImages cloudCardDeck = new CloudCardDeckImages();
    private ArrayList<Image> cloudCardsInGame = new ArrayList<>(3);

    public ArrayList<Image> getCloudCardsInGame() {
        return cloudCardsInGame;
    }

    @Override
    public void initialize (URL url, ResourceBundle rb) {
            cloudcardCircle.add(circle);
            cloudcardCircle.add(circle2);
            cloudcardCircle.add(circle3);
            cloudCardDeck.extractRandomCloudCard();
            cloudCardsInGame = cloudCardDeck.getCloudCardsInGame();

            int i=0;
            for (Circle c : cloudcardCircle){
                c.setStroke(Color.WHITE);
                c.setFill(new ImagePattern(cloudCardsInGame.get(i)));
                i++;
            }

        /*}
        else {
            circle.setStroke(Color.WHITE);
            Image cc1 = new Image("C:\\Users\\zarle\\ingsw2022-AM50\\src\\main\\resources\\images\\Assistenti\\cloudcard\\cc1.png", false);
            circle.setFill(new ImagePattern(cc1));
            circle.setCenterX(100);

            circle2.setStroke(null);
            circle2.setFill(null);

            circle3.setFill(Color.WHITE);
            Image cc3 = new Image("C:\\Users\\zarle\\ingsw2022-AM50\\src\\main\\resources\\images\\Assistenti\\cloudcard\\cc3.png", false);
            circle3.setFill(new ImagePattern(cc3));
            circle3.setCenterX(-100);

            dashboard2.setImage(null);

            txt2.setText("");
            txt3.setText("Dashboard Player 2");
            txt3.setX(-100);
            txt1.setX(100);
            dashboard1.setX(100);
            dashboard3.setX(-100);


        }*/
    }

    @FXML
    public void seeDashboard (MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }



    public void addYellowStudent(MouseEvent event)  {
        Image yellowStudent = new Image("@../../../../images/Pawn/yellow_student.png",false);
        student.setImage(yellowStudent);

    }

    public void addRedStudent (MouseEvent event){
        Image redStudent = new Image("@../../../../images/Pawn/red_student.png",false);
        student.setImage(redStudent);

    }

    public void addGreenStudent (MouseEvent event){
        Image greenStudent = new Image("@../../../../images/Pawn/green_student.png",false);
        student.setImage(greenStudent);
    }

    public void addCyanoStudent (MouseEvent event){
        Image cyanoStudent = new Image("@../../../../images/Pawn/cyano_student.png",false);
        student.setImage(cyanoStudent);
    }

    public void addMagentaStudent (MouseEvent event){
        Image magentaStudent = new Image("@../../../../images/Pawn/magenta_student.png",false);
        student.setImage(magentaStudent);
    }


}
