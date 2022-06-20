package it.polimi.ingsw.model;


import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * This enum class implements all the 5 color of the pawn: Cyan, Magenta, Yellow, Red, Green.
 */
public enum PawnColor {
    GREEN("\u001B[32m", 1,"green"),RED("\u001B[31m", 1,"red"), YELLOW("\u001B[33m", 1,"yellow"), MAGENTA("\u001B[35m",1,"magenta"),CYAN("\u001B[34m",1,"cyan")  ;

    private final String code;
    private final String name;

    private int influenceMultiplier;

    public static final int numberOfColors = 5;

    PawnColor(String code, int influenceMultiplier, String name) {
        this.code = code;
        this.influenceMultiplier = 1;
        this.name = name;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public int getInfluenceMultiplier() {
        return influenceMultiplier;
    }

    public void setInfluenceMultiplier(int influenceMultiplier) {
        this.influenceMultiplier = influenceMultiplier;
    }

    public static int totalNumberOfPawnColors(){
        return PawnColor.values().length;
    }


}

