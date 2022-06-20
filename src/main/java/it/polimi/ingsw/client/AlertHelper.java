package it.polimi.ingsw.client;

import javafx.scene.control.Alert;
import javafx.stage.Window;


/**
 * Creates and display a pop-up alert.
 */

public class AlertHelper {

    /**
     * Instantiates a new Alert and shows it on screen
     * @param alertType the type of alert: notify, confirmation, error, waring
     * @param owner javafx window where the alert is shown
     * @param title title of the alert
     * @param message message of the alert
     */
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
