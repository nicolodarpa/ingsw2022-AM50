package it.polimi.ingsw;

public class Wallet {
    private  int coins;

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int c){
        coins = coins +c;

    }

    public int getCoins() {
        return coins;
    }
}
