package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.*;

import java.util.Scanner;
import java.util.stream.IntStream;

public class SpecialInfluence extends SpecialCard {

    private final Scanner scanner = new Scanner(System.in);


    public SpecialInfluence() {
        setCost(3);
        setEffectOfTheCard("With this card you have to choose one color of the students which will not be considered for the influence.");
        setName("wizard");
    }


    @Override
    public void effect() {
        pawnColor.setInfluenceMultiplier(0);
        addCost();
    }


}
