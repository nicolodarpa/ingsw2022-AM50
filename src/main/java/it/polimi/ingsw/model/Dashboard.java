package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;

/**
 * classroom contains your student, hall the ones you con move to classroom
 */

public class Dashboard {
    private Student[][] classroom = new Student[5][10];
    private Student[] hall = new Student[7];
    private Teacher[] teacherTable = new Teacher[5];
    private ArrayList<Tower> towers = new ArrayList<>();
    private boolean [][] coinPos = new boolean[5][10];


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
            } else System.out.print("^^^");
        }
        System.out.println(" ");
        System.out.println(" ");
    }

    public Student getStudentFromHall(int i) {
        try{
            Student student = hall[i];
            hall[i] = null;
            return student;
        }catch (Exception e){
            System.out.println("please, insert a valid value of position, between 0 and 6");
        }
       return null;
    }

    public void addStudentToHall(Student student) {
        for (int i = 0; i < 7; i++) {
            if (hall[i] == null) {
                hall[i] = student;
                return;
            }
        }
    }


    public Dashboard() {
        setCoinPos();

    }

    public void setCoinPos ( ){
        for (int i=0; i < PawnColor.totalNumberOfPawnColors(); i++){
            coinPos[i][2]= true;
            coinPos[i][5]= true;
            coinPos[i][8]=true;
        }
    }


    public Student getStudentFromClassroom ( int x_position, int y_position){
            Student student = classroom[x_position][y_position];
            classroom[x_position][y_position] = null;
            return student;
    }

    public void addStudentToClassroom (Student student){
            PawnColor color = student.getColor();
            for (int i = 0; i < 10; i++) {
                if (classroom[color.ordinal()][i] == null) {
                        classroom[color.ordinal()][i] = student;
                        return;

                }
            }
    }


    public void addCoin (Wallet wallet){
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i <10; i++) {
                if (classroom[j][i] != null && coinPos[j][i] == true) {
                    coinPos [j][i] = false;
                    wallet.addCoins(1);
                }
            }
        }
        return;
    }

    public boolean[][] getCoinPos() {
        return coinPos;
    }

    public void addTeacherToTable (Teacher teacher){
            PawnColor color = teacher.getColor();
            teacherTable[color.ordinal()] = teacher;
        }

        public void removeTeacherFromTable (Teacher teacher){
            PawnColor color = teacher.getColor();
            teacherTable[color.ordinal()] = null;
        }


        public Teacher[] getTeacherTable () {
            return teacherTable;
        }


        /**
         * count the number of students of one color in the classroom
         * @param color is the color of the student, that we search
         * @return the number of the students of one color
         */
        public int countStudentByColor (PawnColor color){
            int numberOfStudent = 0;

            for (int i = 0; i < 10; i++)
                if (classroom[color.ordinal()][i] != null) {
                    numberOfStudent++;
                }
            return numberOfStudent;
        }


    public void removeTower(Tower t){
        towers.remove(t);
    }

    public void addTower(int towerNumber, TowerColor color){
        Tower tower = new Tower(color);
        for(int i = 0; i < towerNumber; i++)
            towers.add(tower);
    }

}

