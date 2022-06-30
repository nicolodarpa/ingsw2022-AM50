package it.polimi.ingsw.server.model;

import java.util.ArrayList;

/**
 * Models the game's dashboard of each player:
 * Classroom contains your student, hall the ones you con move to classroom.
 * Towers contain all the towers in the dashboard.
 * CoinPos is the position of special column of the classroom that gives a coin to the player when he moves a student on it.
 * TeacherTable is the table of the professor, and it fills with professor when a player has more students of one color than the other players.
 * HallCapacity indicates how many students can there be in hall.
 */
public class Dashboard {
    /**
     * Represents the classroom of students in the dashboard with a matrix of 5 row and 10 columns.
     */
    private final Student[][] classroom = new Student[5][10];

    /**
     * Represents the hall in the dashboard with an array.
     */
    private Student[] hall;

    /**
     * Represents the teacherTable in the dashboard with an array of 5 position.
     */
    private final Teacher[] teacherTable = new Teacher[5];

    /**
     * Represents the position of the towers in the dashboard with an array list.
     */
    private ArrayList<Tower> towers = new ArrayList<>();


    private final boolean[][] coinPos = new boolean[5][10];

    /**
     * Is the number of students in the hall.
     */
    private int hallCapacity;




    public ArrayList<Tower> getTowers() {
        return towers;
    }

    public Student[] getHall() {
        return hall;
    }

    public Student[][] getClassroom() {
        return classroom;
    }

    public void setHall(Student[] hall) {
        this.hall = hall;
    }


    /**
     * Dashboard's constructor that sets the special position of the coins in the classroom.
     */
    public Dashboard() {
        setCoinPos();
    }

    /**
     * the coin position determinate the position where the game has to give to a player a coin if he moves his student to the classroom
     */
    public void setCoinPos() {
        for (int i = 0; i < PawnColor.numberOfColors; i++) {
            coinPos[i][2] = true;
            coinPos[i][5] = true;
            coinPos[i][8] = true;
        }
    }


    /**
     * Draws the dashboard, it draws a student, teacher or tower if they aren't null. If one of this previous element is null, it draws "^^^" instead.
     */
    public void drawDashboard() {
        System.out.print("Hall: ");
        for (Student student : hall) {
            System.out.print("-");
            if (student != null) {
                student.draw();
            } else System.out.print("^^^");
        }
        System.out.println(" ");
        System.out.println("Classrooms");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (classroom[i][j] != null) {
                    classroom[i][j].draw();
                } else System.out.print("^^^");
                System.out.print("--");
            }
            System.out.println(" ");
        }
        System.out.print("Teacher Table: ");
        for (Teacher teacher : teacherTable) {
            System.out.print("-");
            if (teacher != null) {
                teacher.draw();
            } else System.out.print("^^^");
        }
        System.out.println(" ");

        System.out.print("Towers: ");
        for (Tower tower : towers) {
            System.out.print("-");
            if (tower != null) {
                tower.draw();
            } else System.out.print("");
        }
        System.out.println(" ");
        System.out.println(" ");
    }

    /**
     * set the capacity of the hall, the number of students in the hall(7 or 9) based on the number of players in the game.
     * @param gameMode is the number of players in the game.
     * \requires gameMode == 2 || gameMode == 3
     */
    public void setupHall(int gameMode) {
        if (gameMode == 2) {
            hallCapacity = 7;
        } else if (gameMode == 3) {
            hallCapacity = 9;
        }
        hall = new Student[hallCapacity];
    }

    /**
     * Extracts a student from the hall, replacing it whit null
     * @param i index of the chosen student to get
     * @return the student in i hall's position or an NullPointerException if the requirements aren't respected.
     */
    public Student getStudentFromHall(int i) {
        try {
            Student student = hall[i];
            hall[i] = null;
            return student;
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Adds one student to tha dashboard's hall.
     * @param student is one to add to the hall.
     */
    public void addStudentToHall(Student student) {
        for (int i = 0; i < hallCapacity; i++) {
            if (hall[i] == null) {
                hall[i] = student;
                return;
            }
        }
    }


    /**
     * remove the last student of the selected color in the classroom
     * @param studentColor the color of the student that we want to remove
     * (\requires (studentColor.equals(CYAN);
     *      || studentColor.equals(RED);
     *          || studentColor.equals(GREEN);
     *               || studentColor.equals(YELLOW);
     *                    || studentColor.equals(MAGENTA) )
     * @return a student of that color
     */
    public Student getStudentFromClassroom(PawnColor studentColor) {
        return findLastStudent(studentColor);
    }



    /**
     * find the last student of the selected color in the classroom
     * @param studentColor the color of the student that we are looking for
     * (\requires (studentColor.equals(CYAN);
     *       || studentColor.equals(RED);
     *          || studentColor.equals(GREEN);
     *              || studentColor.equals(YELLOW);
     *                   || studentColor.equals(MAGENTA) )
     * @return the last student
     */
    public Student findLastStudent(PawnColor studentColor) {
        int position = 0;
        Student lastStudent = null;
        try {
            if (classroom[studentColor.ordinal()][position] != null) {
                while (classroom[studentColor.ordinal()][position] != null)
                    position++;
            }
            lastStudent = classroom[studentColor.ordinal()][position - 1];
            classroom[studentColor.ordinal()][position - 1] = null;
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
        return lastStudent;
    }


    /**
     * add a student in the classroom in the correspondence row, based on this color:
     * <p>
     *     <ul>
     *      <li> Row 1 : Green </li>
     *      <li> Row 2 : Red </li>
     *      <li> Row 3 : Yellow </li>
     *      <li> Row 4 : Magenta </li>
     *      <li> Row 5 : Cyan </li>
     *     </ul>
     * </p>
     */
    public boolean addStudentToClassroom(Student student) {
        PawnColor color = student.getColor();
        for (int i = 0; i < 10; i++) {
            if (classroom[color.ordinal()][i] == null) {
                classroom[color.ordinal()][i] = student;
                return true;

            }
        }
        return false;
    }

    public void moveStudentToClassroom(int index){
        Student student = hall[index];
        if (addStudentToClassroom(student)){
            getStudentFromHall(index);
        }

    }


    /**
     * Add one coin to the player when he moves a student on a coin position of his dashboard.
     * @param wallet is the player's wallet.
     */
    public void addCoin(Wallet wallet) {
        for (int j = 0; j < PawnColor.numberOfColors; j++) {
            for (int i = 0; i < 10; i++) {
                if (classroom[j][i] != null && coinPos[j][i]) {
                    coinPos[j][i] = false;
                    wallet.addCoins(1);
                }
            }
        }
    }

    public boolean[][] getCoinPos() {
        return coinPos;
    }

    /**
     * adds a teacher to dashboard's teachers table corresponding row.
     * @param teacher \requires teacher != null
     */
    public void addTeacherToTable(Teacher teacher) {
        try {
            PawnColor color = teacher.getColor();
            teacherTable[color.ordinal()] = teacher;
        } catch (NullPointerException e) {
            System.out.println("Null teacher");
        }

    }

    /**
     * Removes one teacher from the teacher Table.
     * @param indexOfTeacher is the position of the teacher in teacherTable
     */
    public void removeTeacherFromTable(Teacher indexOfTeacher) {
        teacherTable[indexOfTeacher.getColor().ordinal()] = null;
    }


    public Teacher[] getTeacherTable() {
        return teacherTable;
    }


    /**
     * count the number of students of one color in the classroom
     *
     * @param color is the color of the student, that we search
     * @return the number of the students of one color
     */
    public int countStudentByColor(PawnColor color) {
        int numberOfStudents = 0;

        for (int i = 0; i < 10; i++)
            if (classroom[color.ordinal()][i] != null) {
                numberOfStudents++;
            }

        return numberOfStudents;
    }


    /**
     * remove a tower from the dashboard
     * @param t is the tower to remove
     */
    public void removeTower(int t) {
        towers.remove(t);
    }

    /**
     * adds a tower to the dashboard
     * @param towerNumber is the total number of towers in the dashboard
     * @param color is the color of the towers (white, black or grey).
     */
    public void addTower(int towerNumber, TowerColor color) {
        Tower tower = new Tower(color);
        for (int i = 0; i < towerNumber; i++)
            towers.add(tower);
    }

    /**
     * Adds to the dashboard the towers from the islands.
     * @param color is the tower's color and requires color != null.
     */
    public void addTowerFromIsland(TowerColor color){
        towers.add(new Tower(color));
    }


}

