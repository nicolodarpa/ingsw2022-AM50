package it.polimi.ingsw.comunication;

import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.Student;

import java.util.ArrayList;


/**
 * Template class to encode and decode json with the state of an island
 */
public class IslandStatus {

    public int id;
    public boolean islandConquered;

    public int dimension;

    public String owner;
    public boolean presenceMN;
    public int idGroup;

    public boolean isBlocked;

    /**
     * is an array of integers where each number inside it is associated with the order (in the PawnColor class) of the student's color {@link it.polimi.ingsw.server.model.PawnColor}
     */
    public ArrayList<Integer> studentColorOrdinal = new ArrayList<>();

    /**
     * The colors of the students on the island.
     */
    public ArrayList<String> colors = new ArrayList<>();

    /**
     * Number of towers on the island.
     */
    public int towerNumber;
    public String towerColor;

    /**
     * Populates every field
     *
     * @param island island to extract values from
     */
    public IslandStatus(Island island) {
        this.id = island.getId();
        this.idGroup = island.getIdGroup();
        this.islandConquered = island.getConquered();
        this.dimension = island.getDimension();
        this.owner = island.getOwner();
        this.presenceMN = island.getPresenceMN();
        this.towerNumber = island.getTowerNumber();
        this.towerColor = String.valueOf(island.getTowerColor());
        this.isBlocked = island.isBlocked();
        ArrayList<Student> studentArrayList = island.getStudents();
        for (Student student : studentArrayList) {
            if (student!=null){
                studentColorOrdinal.add(student.getColor().ordinal());
                colors.add(student.getColor().getName());
            } else{
                studentColorOrdinal.add(null);
                colors.add(null);
            }
        }
    }
}
