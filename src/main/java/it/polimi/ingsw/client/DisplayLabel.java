package it.polimi.ingsw.client;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

/**
 * Class to display information as labels
 */
public interface DisplayLabel {


    default void displayLabel(@NotNull String text, Label label, String textLabel){
        label.setText(text + ": " + textLabel);
    }

}
