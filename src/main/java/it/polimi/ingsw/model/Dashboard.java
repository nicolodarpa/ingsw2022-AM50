package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * classroom contains your student, hall the ones you con move to classroom
 */

public class Dashboard {
    private int id;
    private Student[][] classroom = new Student[5][10];
    private Student[] hall = new Student[7];
    private Teacher[] teacherTable = new Teacher[5];
    private ArrayList<Tower> towers = new ArrayList<>();


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
        System.out.print("TeacherTable: ");
        for (Teacher teacher : teacherTable) {
            System.out.print("-");
            if (teacher != null) {
                teacher.draw();
            } else System.out.print("^^^");
        }
        System.out.println(" ");

        System.out.print("Tower: ");
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
        Student student = hall[i];
        hall[i] = null;
        return student;
    }

    public void addStudentToHall(Student student) {
        for (int i = 0; i < 7; i++) {
            if (hall[i] == null) {
                hall[i] = student;
                return;
            }
        }

    }

    public void addStudentToClassroom(Student student) {
        PawnColor color = student.getColor();

        for (int i = 0; i < 10; i++) {
            if (classroom[color.ordinal()][i] == null) {
                classroom[color.ordinal()][i] = student;
                return;
            }
        }


    }

    public void addTeacherToTable(Teacher teacher) {
        PawnColor color = teacher.getColor();
        teacherTable[color.ordinal()] = teacher;
    }

    public void removeTeacherFromTable(Teacher teacher){
        PawnColor color = teacher.getColor();
        teacherTable[color.ordinal()] = null;
    }


    public Teacher[] getTeacherTable() {
        return teacherTable;
    }


    /**
     * count the number of students of one color in the classroom
     * @param color
     * @return
     */

    public int countStudentByColor(PawnColor color){
        int numberOfStudent = 0;

        for (int i = 0; i < 10; i++) {
            if (classroom[color.ordinal()][i] != null) {
                numberOfStudent++;
            }
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

