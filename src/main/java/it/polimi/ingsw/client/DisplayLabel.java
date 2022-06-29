package it.polimi.ingsw.client;

import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

/**
 * Interface to display information as labels
 */
public interface DisplayLabel {


    /**
     * Display a label
     * @param text is the text we want to show in the label before ":"
     * @param label is the label to show
     * @param textLabel is the text we want to show in the label after ":"
     */
    default void displayLabel(@NotNull String text, Label label, String textLabel){
        label.setText(text + ": " + textLabel);
    }

}
