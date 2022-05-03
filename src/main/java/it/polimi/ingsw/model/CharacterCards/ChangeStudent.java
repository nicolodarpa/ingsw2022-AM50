package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;

import java.util.Scanner;

public class ChangeStudent extends SpecialCard {
        Scanner scanner = new Scanner(System.in);

    public ChangeStudent(){
        setCost(1);
        setEffectOfTheCard(" With this card you can choose to swap places up to 2 students from the Hall and the Classroom. ");
        setName("joker");
    }





    @Override
    public void effect() {
        PawnColor studentColor = PawnColor.valueOf(scanner.nextLine());
        int position = scanner.nextInt();
        actualPlayer.changeStudent(studentColor, position);
        addCost();
    }

}
