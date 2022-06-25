package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.Island;

/**
 * Freezes the calculation of the influence when mother nature stops on the island
 */
public class BlockCardStrategy extends SpecialCardStrategy {

    /**
     * It's the number of block cards that there is in game.
     */
    private int availableBlockCards;

    private Island previousIsland = null;


    /**
     * It's the character card's constructor. It sets the cost, the effect, the name of the card and the initial number of block cards available
     */
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

    /**
     * Checks if the islands previously blocked is still blocked, if not the available block card counter is increased.
     * Checks if at least one block card is available and blocks the selected island
     */
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
