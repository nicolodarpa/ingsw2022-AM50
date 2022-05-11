package it.polimi.ingsw.client;
import it.polimi.ingsw.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientGUI extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("input_ip_form.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Eryantis GUI");
        stage.setScene(scene);
        stage.show();




    }

    public static void main(String ip, int port) {
        launch();
    }


}
