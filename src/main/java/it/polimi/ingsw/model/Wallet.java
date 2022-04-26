package it.polimi.ingsw.model;

public class Wallet {
    private  int coins;

    public Wallet() {
        this.coins = 1;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int c){
        coins = coins + c;
    }

    public int getCoins() {
        return coins;
    }
}
