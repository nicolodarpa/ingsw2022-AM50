package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Models the table of the game with all the islands.
 * @param islands are the islands in the game.
 */
public record Table(ArrayList<Island> islands) {


    /**
     * Draw all the 12 island and show the students on it.
     */
    public void drawTable() {
        System.out.println(" Islands: ");
        for (Island i : islands) {
            System.out.print("Island #" + i.getId() + " : ");
            for (Student s : i.getStudentList()) {
                if (s != null) {
                    s.draw();
                    System.out.print("-");
                }
            }
            System.out.println(" ");
        }
    }
}

