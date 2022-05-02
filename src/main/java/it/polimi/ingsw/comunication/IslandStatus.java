package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

public class IslandStatus {

    public int id;
    public boolean islandConquered;

    public int dimension;
    public String owner;
    public boolean presenceMN;
    public int idGroup;
    public ArrayList<String> students = new ArrayList<>();
    public int towerNumber;
    public String towerColor;

    public IslandStatus(Island island){
        this.id = island.getId();
        this.idGroup = island.getIdGroup();
        this.islandConquered = island.getConquered();
        this.dimension = island.getDimension();
        this.owner = island.getOwner();
        this.presenceMN = island.getPresenceMN();
        this.towerNumber = island.getTowerNumber();
        this.towerColor = String.valueOf(island.getTowerColor());
        ArrayList<Student> studentArrayList = island.getStudents();
        for (Student student: studentArrayList){
            if(student != null){
                students.add(String.valueOf(student.getColor()));
            } else students.add("EMPTY");
        }

    }


}
