package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * classroom contains your student, hall the ones you con move to classroom
 */

public class Dashboard {
    private int id;
    private Student[][] classroom = new Student[5][10];
    private Student[] hall = new Student[7];
    private int towerNumber;
    private ArrayList<Teacher> teacherList = new ArrayList<Teacher>();


    public void drawDashboard() {
        System.out.print("Hall: ");
        for (Student student : hall) {
            System.out.print("-");
            if (student != null) {
                student.draw();
            } else System.out.print("^^^");


        }
        System.out.println("");
        System.out.println("Classrooms");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (classroom[i][j] != null) {
                    classroom[i][j].draw();
                } else System.out.print("^^^");
                System.out.print("--");
            }
            System.out.println("");

        }

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
        String color = student.getColor();
        int row=0;
        if (color == "CYAN") {
            row = 0;
        } else if (color == "MAGENTA") {
            row = 1;
        } else if (color == "YELLOW") {
            row = 2;
        } else if (color == "RED") {
            row = 3;
        } else if (color == "GREEN") {
            row = 4;
        }

        for (int i = 0; i < 10; i++) {
            if (classroom[row][i] == null) {
                classroom[row][i] = student;
                return;
            }
        }


    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void addTeacher(Teacher teacher) {
        teacherList.add(teacher);
    }
}
