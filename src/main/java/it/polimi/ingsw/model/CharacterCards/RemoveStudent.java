package it.polimi.ingsw.model.CharacterCards;

public class RemoveStudent extends SpecialCard {

    public RemoveStudent(){
        setCost(3);
        setEffectOfTheCard(" With this card you choose a color of the students and every player (even you) has to put in the BagStudents 3 students of that color from each Hall. ");
    }


    public void effect() {
        addCost();

    }
}
