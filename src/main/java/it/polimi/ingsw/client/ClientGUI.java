package it.polimi.ingsw.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.stage.Stage;


import java.io.IOException;


public class ClientGUI extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("input_ip_form.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Eryantis GUI");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String ip, int port) {
        launch();
    }


}
