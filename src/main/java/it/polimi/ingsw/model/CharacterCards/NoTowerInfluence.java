package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.Island;

public class NoTowerInfluence extends SpecialCard {


    public NoTowerInfluence() {
        setCost(3);
        setEffectOfTheCard(" With this card the towers have no influence on the island considered");

    }


    @Override
    public void effect() {
        islands.get(islandIndex).setTowerMultiplier(0);
        addCost();
    }


}
