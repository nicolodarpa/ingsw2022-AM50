package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Island;

/**
 * Implements the special card that blocks an island from the passage of mother nature. {@link SpecialCardStrategy}
 */
public class BlockCardStrategy extends SpecialCardStrategy {

    /**
     * It's the number of block cards that there is in game.
     */
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
        } else currentPlayer.sendToClient("error","Every block card is already in use");
    }
}
