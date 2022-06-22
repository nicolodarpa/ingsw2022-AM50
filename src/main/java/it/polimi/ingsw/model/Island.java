package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.stream.IntStream;


/**
 * Implements the islands in the game, every island has an id and a flag that indicates if the island is conquered or not.
 * idGroup is used when two or more islands connected together.
 * The dimension indicates how many islands are connected together.
 * TowerColor is the color of the tower on the island.
 * Owner is the player that conquers island.
 * presenceMn and oppositeMn are flags that indicate if motherNature is on the island or not and if the island is in the MN's opposite position.
 */
public class Island {
    private int id;
    private boolean islandConquered = false;
    private int idGroup;
    private int dimension;
    private TowerColor towerColor = null;
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Tower> towerArrayList = new ArrayList<>();
    private Player owner;
    private boolean presenceMN = false; //true if there is Mother Nature on the Island
    private boolean oppositeMN = false; //true if the Island is opposite to The island where there is Mother Nature

    private boolean block = false;
    private int towerMultiplier = 1;

    private boolean towerFinished = false;

    public void setPresenceMN(boolean presenceMN) {
        this.presenceMN = presenceMN;
    }

    public void setOppositeMN(boolean oppositeMN) {
        this.oppositeMN = oppositeMN;
    }

    public void setTowerMultiplier(int towerMultiplier) {
        this.towerMultiplier = towerMultiplier;
    }

    public Island(int id) {
        this.id = id;
        this.idGroup = id;
        this.dimension = 1;
    }

    public void increaseDimension(int i) {
        dimension = dimension + i;
    }

    public int getDimension() {
        return dimension;

    }

    public int getId() {
        return id;
    }


    public int getTowerNumber() {
        return towerArrayList.size();
    }

    public boolean getConquered() {
        return islandConquered;
    }

    public String getOwner() {
        if (owner != null) {
            return owner.getName();
        } else return "free";

    }

    public ArrayList<Student> getStudents() {
        return studentList;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }


    public boolean getPresenceMN() {
        return presenceMN;
    }

    public int getIdGroup() {
        return idGroup;
    }


    public ArrayList<Tower> getTowerArrayList() {
        return towerArrayList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }


    public TowerColor getTowerColor() {
        return towerColor;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean getBlock(){
        return block;
    }


    public void addStudent(Student s) {
        studentList.add(s);
    }

    /**
     * count the number of students of each color
     */

    public int[] countStudentByColor() {
        int cyan = PawnColor.CYAN.ordinal();
        int magenta = PawnColor.MAGENTA.ordinal();
        int yellow = PawnColor.YELLOW.ordinal();
        int red = PawnColor.RED.ordinal();
        int green = PawnColor.GREEN.ordinal();
        int[] color = new int[5];
        for (Student s : studentList) {
            PawnColor pawnColor = s.getColor();
            if (pawnColor.equals(PawnColor.CYAN)) {
                color[cyan]++;
            } else if (pawnColor.equals(PawnColor.MAGENTA)) {
                color[magenta]++;
            } else if (pawnColor.equals(PawnColor.YELLOW)) {
                color[yellow]++;
            } else if (pawnColor.equals(PawnColor.RED)) {
                color[red]++;
            } else if (pawnColor.equals(PawnColor.GREEN)) {
                color[green]++;
            }
        }
        return color;
    }

    public int countStudentOfAColor(PawnColor color) {
        int colorNumber = 0;
        for (Student s : studentList) {
            if (s.getColor() == color)
                colorNumber++;
        }
        return colorNumber;
    }


    /**
     * It takes one tower from the owner's dashboard, then it checks that he has towers available, and it adds the tower to the island.
     * If the player hasn't towers available it calls towerFinished and set towerFinished true.
     */
    public void addTower() {
        try {
            final Dashboard ownerDashboard = owner.getDashboard();
            final int size = ownerDashboard.getTowers().size();
            if(size != 1){
                Tower ownerTower = ownerDashboard.getTowers().get(size - 1); // takes the last tower of the towersList
                ownerDashboard.removeTower(size-1);
                addTowerToIsland(ownerTower);
            }
            else{
                towerFinished();
                Tower ownerTower = ownerDashboard.getTowers().get(0); // takes the last tower of the towersList
                ownerDashboard.removeTower(0);
                addTowerToIsland(ownerTower);
            }


        } catch (Exception e) {
            System.out.println("Tower not available");
        }
    }

    /**
     * It set true if one player finished his tower.
     */
    public void towerFinished(){
        this.towerFinished = true;
    }


    /**
     * @return true if one player finished his tower.
     */
    public boolean getTowerFinished(){
        return towerFinished;
    }


    /**
     * Adds one tower to the island.
     * @param tower is the tower to add.
     */
    public void addTowerToIsland(Tower tower) {
        towerColor = tower.getColor();
        towerArrayList.add(tower);
    }

    /**
     * Calculates the influence of each player on the considered island
     * The influence depends on the number of students of one color and also the towers on the island.
     * towerMultiplier is 1 in normal condition and is 0 when a player play a special card that changes the influence of the towers on the island.
     * @param players are the players in game.
     */

    public void calcInfluence(PlayersList players) {
        int towerInfluencePoint = 0;

        /* set influence point */
        for (Player p : players.getPlayers()) {
            setInfluencePoints(p);
            Dashboard dashboardTemp = p.getDashboard();
            TowerColor towerColorOfPlayer = dashboardTemp.getTowers().get(0).getColor();
            if (towerColor == towerColorOfPlayer && towerArrayList.size() > 0) {
                towerInfluencePoint++;
                p.setInfluencePoint(p.getInfluencePoint() + (towerInfluencePoint*towerMultiplier));
            }
        }

        /* calculate the influence by the influence point of each player*/
        calcInfluencePoints(players);
        /*reset the influence point */
        for (Player p : players.getPlayers())
            p.setInfluencePoint(0);
    }

    /**
     * Sets the point of influence of the player considered.
     * @param p is the player that we are considering.
     */
    private void setInfluencePoints(Player p) {
        int[] colorStudent = new int[PawnColor.numberOfColors];
        Dashboard dashboardTemp = p.getDashboard();
        for (int i = 0; i < dashboardTemp.getTeacherTable().length; i++) {
            if (dashboardTemp.getTeacherTable()[i] != null) {
                colorStudent[i] = countStudentOfAColor(dashboardTemp.getTeacherTable()[i].getColor())*PawnColor.values()[i].getInfluenceMultiplier();
            }
        }
        p.setInfluencePoint(IntStream.of(colorStudent).sum());
    }

    /**
     * Calculates the points of influence of each player, then it assigns the island to respective owner and in that case it adds a tower from owner's dashboard to island.
     * @param playersList are the players in game.
     */
    private void calcInfluencePoints(PlayersList playersList) {
        if (playersList.getPlayers().size() == 2) {
            Player p1 = playersList.getPlayers().get(0);
            Player p2 = playersList.getPlayers().get(1);

            if (p1.getInfluencePoint() > p2.getInfluencePoint()) {
                removePreviousOwnerTower();
                this.owner = p1;
                islandConquered = true;
            } else if (p1.getInfluencePoint() < p2.getInfluencePoint()) {
                removePreviousOwnerTower();
                this.owner = p2;
                islandConquered = true;
            }
        } else if (playersList.getPlayers().size() == 3) {

            Player p1 = playersList.getPlayers().get(0);
            Player p2 = playersList.getPlayers().get(1);
            Player p3 = playersList.getPlayers().get(2);

            if (p1.getInfluencePoint() > p2.getInfluencePoint() && p1.getInfluencePoint() > p3.getInfluencePoint()) {
                removePreviousOwnerTower();
                this.owner = p1;
                islandConquered = true;
            } else if (p2.getInfluencePoint() > p1.getInfluencePoint() && p2.getInfluencePoint() > p3.getInfluencePoint()) {
                removePreviousOwnerTower();
                this.owner = p2;
                islandConquered = true;
            } else if (p3.getInfluencePoint() > p1.getInfluencePoint() && p3.getInfluencePoint() > p2.getInfluencePoint()) {
                removePreviousOwnerTower();
                this.owner = p3;
                islandConquered = true;
            }
        }
        if (owner != null && towerArrayList.size() < dimension)
            addTower();
    }

    /**
     * Remove the previous owner's tower when the island changes its owner.
     * When two or more islands are connected together,
     * there are more than one tower on it, so when the island's owner changes, this method with the for loop removes all the towers
     */
    private void removePreviousOwnerTower(){
        if(towerArrayList.size() > 0){
            for(int towerPosition = 0; towerPosition < towerArrayList.size(); towerPosition++){ //this forLoop removes all the towers.
                owner.getDashboard().addTowerFromIsland(towerColor);
                towerArrayList.remove(towerPosition);
            }

        }
    }


    /**
     * Adds Mother Nature to island.
     */
    public void addMotherNature() {
        presenceMN = true;
    }

    /**
     * @return if the island is the opposite one to the island with Mother Nature.
     */
    public boolean getOppositeMN() {
        return oppositeMN;
    }


}

