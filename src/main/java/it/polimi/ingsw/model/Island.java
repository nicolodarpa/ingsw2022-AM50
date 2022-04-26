package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;


import java.util.ArrayList;
import java.util.stream.IntStream;



public class Island {
    private int id;
    private boolean islandConquered = false;
    private int idGroup;
    private int towerNumber;
    private TowerColor towerColor;
    private ArrayList<Student> studentList = new ArrayList<>();
    private Player owner;
    private boolean presenceMN = false; //true if there is Mother Nature on the Island
    private boolean oppositeMN = false; //true if the Island is opposite to The island where there is Mother Nature

    public void setPresenceMN(boolean presenceMN) {
        this.presenceMN = presenceMN;
    }

    public void setOppositeMN(boolean oppositeMN) {
        this.oppositeMN = oppositeMN;
    }

    public Island(int id) {
        this.id = id;
        this.idGroup = id;
    }

    public int getId() {
        return id;
    }


    public int getTowerNumber() {
        return towerNumber;
    }

    public boolean getConquered(){
        return islandConquered;
    }

    public String getOwner() {
        if (owner!=null){
            return owner.getName();
        } else return "free";

    }

    public ArrayList<Student> getStudents(){
        return studentList;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public boolean getPresenceMN(){
        return presenceMN;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }



    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }


    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public void addStudent(Student s){
        studentList.add(s);
    }

    /**
     * count the number of students of each color
     *
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

    public int countStudentOfAColor(PawnColor color){
        int colorNumber = 0;
        for(Student s : studentList){
            if(s.getColor() == color)
                colorNumber++;
        }
        return colorNumber;
    }


    public PawnColor mostColorInfluence(){
        PawnColor mostColor = null;
        int[] colors = countStudentByColor();
        int posMax = ArrayMaxPosition.findMaxPosition(colors);
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
        final int numberOfColor = 5;

        for(Player p : players.getPlayers()){
            int [] colorStudent = new int[numberOfColor];
            Dashboard dashboardTemp = p.getDashboard();
            for(int i = 0; i < dashboardTemp.getTeacherTable().length; i++){
                if(dashboardTemp.getTeacherTable()[i] != null){
                    colorStudent[i] = countStudentOfAColor(dashboardTemp.getTeacherTable()[i].getColor());
                }
            }
            p.setInfluencePoint(IntStream.of(colorStudent).sum());
        }

        if (players.getPlayers().size() == 2) {
            Player p1 = players.getPlayers().get(0);
            Player p2 = players.getPlayers().get(1);

            if (p1.getInfluencePoint() > p2.getInfluencePoint()) {
                this.owner = p1;
                islandConquered = true;
            } else if (p1.getInfluencePoint() < p2.getInfluencePoint()) {
                this.owner = p2;
                islandConquered = true;
            } else {
                this.owner = null;
            }
        } else if (players.getPlayers().size() == 3){

                Player p1 = players.getPlayers().get(0);
                Player p2 = players.getPlayers().get(1);
                Player p3 = players.getPlayers().get(2);



            if (p1.getInfluencePoint() > p2.getInfluencePoint() && p1.getInfluencePoint() > p3.getInfluencePoint()) {
                this.owner = p1;
                islandConquered = true;
            } else if (p2.getInfluencePoint() > p1.getInfluencePoint() && p2.getInfluencePoint() > p3.getInfluencePoint()) {
                this.owner = p2;
                islandConquered = true;
            } else if (p3.getInfluencePoint() > p1.getInfluencePoint() && p3.getInfluencePoint() > p2.getInfluencePoint()) {
                this.owner = p3;
                islandConquered = true;
            }
            addTower();
            for(Player p : players.getPlayers()){
                p.setInfluencePoint(0);
            }
        }
    }


    public void addMotherNature(){
        presenceMN = true;
    }

    public boolean getOppositeMN() {
        return oppositeMN;
    }


}

