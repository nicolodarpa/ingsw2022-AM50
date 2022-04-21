package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;


import java.util.ArrayList;



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

    public boolean isIslandConquered() {
        return islandConquered;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    public String getOwner() {
        if (owner!=null){
            return owner.getName();
        } else return "free";

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

    public void setId(int id) {
        this.id = id;
    }


    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
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


    public void calcInfluence(PlayersList playersList){
        //PawnColor mostColor = mostColorInfluence();
        int[] colorStudentOne = new int[5], colorStudentTwo = new int[5];
        int max_1, max_2;
        Player p1 = PlayersList.getPlayers().get(0);
        Player p2 = PlayersList.getPlayers().get(1);
        Teacher[] teacherColorOne = p1.getDashboard().getTeacherTable();
        Teacher[] teacherColorTwo = p2.getDashboard().getTeacherTable();

        for(int i = 0; i < teacherColorOne.length; i++){
            if(teacherColorOne[i] != null){
                colorStudentOne[i] = countStudentOfAColor(teacherColorOne[i].getColor());
            }
        }
        for(int i = 0; i < teacherColorTwo.length; i++){
            if(teacherColorTwo[i] != null){
                colorStudentTwo[i] = countStudentOfAColor(teacherColorTwo[i].getColor());
            }
        }
        max_1 = ArrayMaxPosition.findMaxOfArray(colorStudentOne);
        max_2 = ArrayMaxPosition.findMaxOfArray(colorStudentTwo);
        if(max_1 > max_2){
            this.owner = p1;
            islandConquered = true;
        }
        else if(max_1 < max_2){
            this.owner = p2;
            islandConquered = true;
        }
        else {
            this.owner = null;
        }
    }


    public void addMotherNature(){
        presenceMN = true;
    }

    public boolean getOppositeMN() {
        return oppositeMN;
    }

    public void connectIsland(){

    }
}

