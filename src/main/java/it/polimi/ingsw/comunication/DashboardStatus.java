package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.*;

public class DashboardStatus {

    public String nameOwner;
    public String[][] studentsClassroom = new String[5][10];
    public String[] studentsHall;
    public String[] studentsHallColors;

    public String[] teacherTable = new String[5];
    public int towers;
    public TowerColor towerColor = null;


    public DashboardStatus(String nameOwner, Dashboard dashboard) {
        this.nameOwner = nameOwner;
        Student[][] classroom = dashboard.getClassroom();
        Student[] hall = dashboard.getHall();
        towerColor=dashboard.getTowers().get(0).getColor();
        studentsHall = new String[hall.length];
        studentsHallColors = new String[hall.length];
        Teacher[] teachers = dashboard.getTeacherTable();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (classroom[i][j] != null) {
                    studentsClassroom[i][j] = String.valueOf(classroom[i][j].getColor().getCode());
                } else studentsClassroom[i][j] = null;


            }
            System.out.println(" ");
        }
        int i = 0;
        for (Student student : hall) {
            if (student != null) {
                studentsHall[i] = String.valueOf(student.getColor().getCode());
                studentsHallColors[i] = student.getColor().getName().toLowerCase();
            } else {
                studentsHall[i] = null;
                studentsHallColors[i] = null;

            }

            i++;
        }
        i = 0;
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                teacherTable[i] = String.valueOf(teacher.getColor().getCode());
            } else teacherTable[i] = null;
            i++;
        }
        this.towers = dashboard.getTowers().size();


    }

}
