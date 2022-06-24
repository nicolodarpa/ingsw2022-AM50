package it.polimi.ingsw.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController implements Initializable {

    private String text;
    @FXML
    private Text textLabel;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textLabel.setText(text);
    }
}
