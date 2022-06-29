package it.polimi.ingsw.server.model.CharacterCards;

import it.polimi.ingsw.server.model.Island;

import java.util.ArrayList;

/**
 * Freezes the calculation of the influence when mother nature stops on the island
 */
public class BlockCardStrategy extends CharacterCardStrategy {

    /**
     * It's the number of block cards that there is in game.
     */
    private int availableBlockCards;

    private final ArrayList<Island> previousIsland = new ArrayList<>(4);


    /**
     * It's the character card's constructor. It sets the cost, the effect, the name of the card and the initial number of block cards available
     */
    public BlockCardStrategy() {
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
        for (Island island : previousIsland) {
            if (!island.isBlocked()) {
                availableBlockCards++;
            }
        }
        previousIsland.removeIf(island -> !island.isBlocked());
        if (availableBlockCards > 0) {
            Island island = islands.get(index);
            if (island.isBlocked()) {
                currentPlayer.sendToClient("error", "Island already blocked");
            } else {
                island.setBlock(true);
                previousIsland.add(island);
                availableBlockCards--;
                addCost();
            }

        } else currentPlayer.sendToClient("error", "Every block card is already in use");
    }
}
