package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.Random;

public class StudentsBag {

    private ArrayList<Student> bag;
    private final String[] colors = {"CYAN","MAGENTA","YELLOW","RED","GREEN" } ;

    public void fillBag(int i){
        bag = new ArrayList<>(i);
        for (int j = 0; j<i; j++){
            bag.add(new Student(colors[1]));
        }
    }

    public Student casualEstraction(){
        Random random = new Random();
        Student randomStudent = bag.get(random.nextInt(bag.size()));
        bag.remove(randomStudent);
        return randomStudent;
    }


    public int getNum(){
        return bag.size();
    }

}

