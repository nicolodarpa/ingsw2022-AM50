package it.polimi.ingsw.model;

public interface SpecialCard {
     int cost = 0;
    String effectOfTheCard = "";


    default void effect(){

    }


    default void addCost(){
    }


    default int getCost(){
        return cost;
    }

    default String getEffectOfTheCard(){
        return effectOfTheCard;
    }



}
