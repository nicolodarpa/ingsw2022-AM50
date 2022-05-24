package it.polimi.ingsw.client;

public class OrderOfPlayer {

    private int order;

    public OrderOfPlayer() {

    }

    public void setOrder(int assistantCardOrder){
        this.order = assistantCardOrder;
    }

    @Override
    public String toString() {
        return "" + order;
    }
}
