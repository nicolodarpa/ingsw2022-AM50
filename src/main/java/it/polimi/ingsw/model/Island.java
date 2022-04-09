package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;
import java.util.Arrays;

public class Island {
    private int id;
    private boolean islandConquered;
    private int idGroup;
    private int towerNumber;
    private TowerColor towerColor;
    private ArrayList<Student> studentList = new ArrayList<>();
    private Player owner;


    public Island(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isIslandConquered() {
        return islandConquered;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    public Player getOwner() {
        return owner;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIslandConquered(boolean islandConquered) {
        this.islandConquered = islandConquered;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public void setTowerNumber(int towerNumber) {
        this.towerNumber = towerNumber;
    }

    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int calcInfluence(Player player){
        return 1;
    }

    public void addStudent(Student s){
        studentList.add(s);
    }

    /**
     * count the number of students of each color
     * @return
     */

    public int[] countStudentByColor(){
        int cyan = PawnColor.CYAN.ordinal();
        int magenta = PawnColor.MAGENTA.ordinal();
        int yellow = PawnColor.YELLOW.ordinal();
        int red = PawnColor.RED.ordinal();
        int green = PawnColor.GREEN.ordinal();
        int[] color = new int[5];
        for( Student s : studentList){
            PawnColor pawnColor = s.getColor();
            if(pawnColor.equals(PawnColor.CYAN)){
                color[cyan]++;
            }
            else if(pawnColor.equals(PawnColor.MAGENTA)){
                color[magenta]++;
            }
            else if(pawnColor.equals(PawnColor.YELLOW)){
                color[yellow]++;
            }
            else if(pawnColor.equals(PawnColor.RED)){
                color[red]++;
            }
            else if(pawnColor.equals(PawnColor.GREEN)){
                color[green]++;
            }
        }
        return color;
    }


    public PawnColor mostColorInfluence(){
        PawnColor mostColor = null;
        int[] colors = new int[5];
        int posMax = 0;
        colors = countStudentByColor();
        posMax = ArrayMaxPosition.findMaxPosition(colors);
        if(posMax == PawnColor.CYAN.ordinal()){
            mostColor = PawnColor.CYAN;
        }
        else if(posMax == PawnColor.RED.ordinal()){
            mostColor = PawnColor.RED;
        }
        else if(posMax == PawnColor.MAGENTA.ordinal()){
            mostColor = PawnColor.MAGENTA;
        }
        else if(posMax == PawnColor.YELLOW.ordinal()){
            mostColor = PawnColor.YELLOW;
        }
        else if(posMax == PawnColor.GREEN.ordinal()){
            mostColor = PawnColor.GREEN;
        }
        return mostColor;
    }


    public void addTower(){
        towerNumber++;
        Dashboard ownerDashboard = owner.getDashboard();
        int size = ownerDashboard.getTowers().size();
        Tower t = ownerDashboard.getTowers().get(size-1);
        ownerDashboard.removeTower(t);
    }


    public void calcInfluence(PlayersList players){
        PawnColor mostColor = null;
        mostColor = mostColorInfluence();
        for(Player p : PlayersList.getPlayers()){
            if(p.getDashboard().getTeacherTable()[mostColor.ordinal()] != null)
                this.owner = p;
        }
        addTower();
    }
}

