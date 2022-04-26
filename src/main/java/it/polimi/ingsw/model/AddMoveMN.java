package it.polimi.ingsw.model;

public class AddMoveMN implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you have 2 more available move for Mother Nature. ");
    private int cost = 1;
    private final Player actualPlayer;

    public AddMoveMN(Player actualPlayer){
        this.actualPlayer = actualPlayer;
    }



    public int getCost() {
        return cost;
    }

    public String getEffectOfTheCard(){
        return effectOfTheCard;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void effect() {
        SpecialCard.super.effect();
        int moveAvailable = actualPlayer.getMovesOfMN() + 2;
        actualPlayer.setMovesOfMN(moveAvailable);
    }


    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }

}
