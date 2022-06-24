package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Student;

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
    public ArrayList<String> students = new ArrayList<>();

    public ArrayList<Integer> studentColorOrdinal = new ArrayList<>();

    public ArrayList<String> colors = new ArrayList<>();

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
        ArrayList<Student> studentArrayList = island.getStudents();
        for (Student student : studentArrayList) {
            if (student!=null){
                students.add(String.valueOf(student.getColor().getCode()));
                studentColorOrdinal.add(student.getColor().ordinal());
                colors.add(student.getColor().getName());
            } else{
                students.add(null);
                studentColorOrdinal.add(null);
                colors.add(null);
            }
        }
    }
}
