package it.polimi.ingsw.model;

import java.util.ArrayList;

public record Table(ArrayList<CloudCard> cloudCards, ArrayList<Island> islands) {


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

