package it.polimi.ingsw.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;


public class ClientGUI extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("input_ip_form.fxml")));

        Scene scene = new Scene(root);
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("images/LOGO CRANIO CREATIONS_bianco.png")));

        stage.getIcons().add(icon);
        stage.setTitle("Eryantis GUI");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String ip, int port) {
        launch();
    }


}
