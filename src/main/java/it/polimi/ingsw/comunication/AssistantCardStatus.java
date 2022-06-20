package it.polimi.ingsw.comunication;

public class AssistantCardStatus {

    public  int order;
    public int movesMN;

    public AssistantCardStatus(AssistantCardStatus assistantCard){
        this.order = assistantCard.order;
        this.movesMN = assistantCard.movesMN;
    }
}
