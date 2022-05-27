package it.polimi.ingsw.client.view;

import com.google.gson.Gson;
import it.polimi.ingsw.client.AlertHelper;
import it.polimi.ingsw.client.ClientInput;
import it.polimi.ingsw.comunication.TextMessage;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class AlertThread extends Thread {
    private final Gson gson = new Gson();

    private final BufferedReader socketIn;

    public AlertThread() {
        this.socketIn = ClientInput.getInstance().getSocketIn();
    }

    @Override
    public void run() {
        while (true) {
            String socketLine;
            try {
                socketLine = socketIn.readLine();
                if (socketLine != null) {
                    Platform.runLater(() -> {
                        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
                        TextMessage message = gson.fromJson(socketLine, TextMessage.class);
                        if (Objects.equals(message.type, "msg")) {
                            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Message", message.message);
                        } else if (Objects.equals(message.type, "error")) {
                            System.out.println("error with alert");
                        }
                    });

                }
            } catch (IOException e) {
                System.out.println("No connection to the server");
                break;
            }


        }

    }
}
