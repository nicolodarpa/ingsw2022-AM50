package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * classroom contains your student, hall the ones you con move to classroom
 */

public class Dashboard {
    private Student[][] classroom = new Student[5][10];
    private Student[] hall;
    private Teacher[] teacherTable = new Teacher[5];
    private ArrayList<Tower> towers = new ArrayList<>();
    private boolean[][] coinPos = new boolean[5][10];
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
     * set the capacity of the hall, the number of students in the hall(7 or 9) based on the number of players in the game
     * @param gameMode
     */
    public void setupHall(int gameMode) {
        if (gameMode == 2) {
            hallCapacity = 7;
        } else if (gameMode == 3) {
            hallCapacity = 9;
        }
        hall = new Student[hallCapacity];
    }

    public Student getStudentFromHall(int i) {
        try {
            Student student = hall[i];
            hall[i] = null;
            return student;
        } catch (Exception ignored) {
        }
        return null;
    }

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
     * @return a student
     */
    public Student getStudentFromClassroom(PawnColor studentColor) {
        Student studentToRemove = null;
        try {
            studentToRemove = findLastStudent(studentColor);
        } catch (Exception ignored) {
        }
        return studentToRemove;
    }

    /**
     * find the last student of the selected color in the classroom
     * @param studentColor the color of the student that we are looking for
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
    public void addStudentToClassroom(Student student) {
        PawnColor color = student.getColor();
        for (int i = 0; i < 10; i++) {
            if (classroom[color.ordinal()][i] == null) {
                classroom[color.ordinal()][i] = student;
                return;

            }
        }
    }


    /**
     * Add one coin to the player when he moves a student on a coin position of his dashboard
     * @param wallet
     */
    public void addCoin(Wallet wallet) {
        for (int j = 0; j < 5; j++) {
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

    public void addTeacherToTable(Teacher teacher) {
        try {
            PawnColor color = teacher.getColor();
            teacherTable[color.ordinal()] = teacher;
        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void removeTeacherFromTable(Teacher teacher) {
        PawnColor color = teacher.getColor();
        teacherTable[color.ordinal()] = null;
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


    public void removeTower(Tower t) {
        towers.remove(t);
    }

    public void addTower(int towerNumber, TowerColor color) {
        Tower tower = new Tower(color);
        for (int i = 0; i < towerNumber; i++)
            towers.add(tower);
    }

}

