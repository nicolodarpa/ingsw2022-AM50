package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * classroom contains your student, hall the ones you con move to classroom
 */

public class Dashboard {
    private int id;
    private Student[][] classroom = new Student[5][10];
    private ArrayList<Student> hall = new ArrayList<>(7);
    private int towerNumber;
    private ArrayList<Teacher> teacherList;



    public void drawDashboard() {
        System.out.print("Hall: ");
        for (Student student: hall) {
            System.out.print("-");
            if (student != null) {
                student.draw();
            } else System.out.print("Empty");
            System.out.print("-");

        }
        System.out.println("");
        System.out.println("Classrooms");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (classroom[i][j] != null) {
                    classroom[i][j].draw();
                } else System.out.print("Empty");
                System.out.print("-");
            }
            System.out.println("");

        }

    }

    public void addStudentToHall(Student student) {
        hall.add(student);
    }

    public void addStudentToClassroom(int row, int col, Student student) {
        classroom[row][col] = student;
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void addTeacher(Teacher t){
        teacherList.add(t);
    }

}
