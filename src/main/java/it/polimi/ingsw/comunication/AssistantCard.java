package it.polimi.ingsw.comunication;

public class AssistantCard {

    public  int order;
    public int movesMN;

    public AssistantCard(AssistantCard assistantCard){
        this.order = assistantCard.order;
        this.movesMN = assistantCard.movesMN;
    }
}
