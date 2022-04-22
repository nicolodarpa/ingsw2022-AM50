package it.polimi.ingsw.model;


import it.polimi.ingsw.PlayersList;

import java.util.*;

public class SpecialDeck {
    private Island islandWithMn;
    private PlayersList players;
    private Player actualPlayer;
    private  SpecialCard characterCard1 = new AddInfluence(islandWithMn, players, actualPlayer);
    private  SpecialCard characterCard2 = new AddMoveMN(actualPlayer);
    private  SpecialCard characterCard3 = new ChangeStudent();
    private  SpecialCard characterCard4 = new MotherNatureInfluence();
    private  SpecialCard characterCard5 = new NoTowerInfluence(islandWithMn, players);
    private  SpecialCard characterCard6 = new RemoveStudent();
    private  SpecialCard characterCard7 = new SpecialInfluence();
    private  SpecialCard characterCard8 = new TeacherAssignment();

    private final Map<Integer, SpecialCard> deckMap = new HashMap<>();
    private static final ArrayList<SpecialCard> specialCardsInGame = new ArrayList<>();


    public static ArrayList<SpecialCard> getSpecialCardsInGame() {
            return specialCardsInGame;
        }

        public SpecialDeck(Island islandWithMn, PlayersList players, Player actualPlayer) {
            this.islandWithMn = islandWithMn;
            this.players = players;
            this.actualPlayer = actualPlayer;
            deckMap.put(0, characterCard1);
            deckMap.put(1, characterCard2);
            deckMap.put(2, characterCard3);
            deckMap.put(3, characterCard4);
            deckMap.put(4, characterCard5);
            deckMap.put(5, characterCard6);
            deckMap.put(6, characterCard7);
            deckMap.put(7, characterCard8);
        }

        public SpecialCard getCharacterCard1() {
            return characterCard1;
        }

        public SpecialCard getCharacterCard2() {
            return characterCard2;
        }

        public SpecialCard getCharacterCard3() {
            return characterCard3;
        }

        public SpecialCard getCharacterCard4() {
            return characterCard4;
        }

        public SpecialCard getCharacterCard5() {
            return characterCard5;
        }

        public SpecialCard getCharacterCard6() {
            return characterCard6;
        }

        public Map<Integer, SpecialCard> getDeckMap() {
            return deckMap;
        }

        public void extractRandomCard() {
            ArrayList specialCardToPlay = getRandomNonRepeatingIntegers(3, 0, 7);
            for (int i = 0; i < specialCardToPlay.size(); i++) {
                specialCardsInGame.add(deckMap.get(specialCardToPlay.get(i)));
            }
        }

     public static ArrayList getRandomNonRepeatingIntegers(int size, int min, int max) {
        ArrayList numbers = new ArrayList();
        Random random = new Random();
        while (numbers.size() < size) {
            int randomNumber = random.nextInt((max - min) + 1) + min;
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        return numbers;
    }

}

