package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Dashboard;

import it.polimi.ingsw.model.Student;

import java.util.ArrayList;

public class HallStatus {
   public String[] students;

    public HallStatus(Dashboard dashboard){
        Student[] hall = dashboard.getHall();
        students = new String[hall.length];

        int i = 0;
        for (Student student : hall) {
            if (student != null) {
                students[i]= String.valueOf(student.getColor().getCode());
            } else students[i] = null;
            i++;
        }
    }
}
