package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.AlertThread;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.Optional;


import java.io.IOException;
import java.util.Objects;


public class ClientGUI extends Application {


    Stage mainStage = null;

    @Override
    public void start(Stage stage) throws IOException {
        this.mainStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("input_ip_form.fxml")));

        Scene scene = new Scene(root);
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("images/LOGO CRANIO CREATIONS_bianco.png")));

        stage.getIcons().add(icon);
        stage.setTitle("Eryantis GUI");
        stage.setScene(scene);
        stage.show();

        mainStage.setOnCloseRequest(evt -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to left the game ? ", ButtonType.YES, ButtonType.NO);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            if(ButtonType.NO.equals(result)){
                evt.consume();
            }else
                try{
                    ClientInput.getInstance().sendString("quit", "");
                }
                catch (Exception e){
                    stage.close();
                }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }


    public static void main(String ip, int port) {
        launch();
    }
}


