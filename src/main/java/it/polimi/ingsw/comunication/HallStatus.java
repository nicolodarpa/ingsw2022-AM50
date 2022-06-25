package it.polimi.ingsw.comunication;

import it.polimi.ingsw.server.model.Dashboard;

import it.polimi.ingsw.server.model.Student;

/**
 * Template class to encode and decode json with the state of the dashboard hall
 */
public class HallStatus {
   public String[] students;


    /**
     * Populates every field
     *
     * @param dashboard dashboard to extract values from
     */
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
