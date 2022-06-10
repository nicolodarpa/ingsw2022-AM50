package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Island;

public class BlockCardStrategy extends SpecialCardStrategy {

    private int availableBlockCards;

    private Island previousIsland = null;



    public BlockCardStrategy(){
        setCost(2);
        setEffectOfTheCard("Select an island to block");
        setName("princess");
        setAvailableBlockCards(4);
    }

    public void setAvailableBlockCards(int availableBlockCards) {
        this.availableBlockCards = availableBlockCards;
    }

    public int getAvailableBlockCards() {
        return availableBlockCards;
    }

    public void effect() {
        if (previousIsland!=null){
            if (!previousIsland.getBlock()){
                availableBlockCards++;
            }
        }
        if (availableBlockCards>0){
            islands.get(index).setBlock(true);
            previousIsland = islands.get(index);
            availableBlockCards--;
            addCost();
        } else actualPlayer.sendToClient("error","Every block card is already in use");
    }
}
