package it.polimi.ingsw.client;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public interface DisplayLabel {


    default void displayLabel(@NotNull String text, Label label, String textLabel){
        label.setText(text + ": " + textLabel);
    }

}
