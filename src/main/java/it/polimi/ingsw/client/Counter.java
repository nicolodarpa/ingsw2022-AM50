package it.polimi.ingsw.client;


public class Counter {

    private int counter;


    public Counter() {
        this.counter = 3;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void decrement(){
        this.counter --;
    }

    public void resetCounter(){
        this.counter = 3;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "" + counter;
    }
}


