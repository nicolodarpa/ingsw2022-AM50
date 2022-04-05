package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

public class StudentsBag {

    private ArrayList<Student> bag;
    private final String[] colors = {"CYAN","MAGENTA","YELLOW","RED","GREEN" } ;

    public void fillBag(int i){
        bag = new ArrayList<>();
        for (int j = 1; j<i+1; j++){
            bag.add(new Student(colors[j%5]));
        }
    }

    public Student casualExtraction(){
        Random random = new Random();
        Student randomStudent = bag.get(random.nextInt(bag.size()));
        bag.remove(randomStudent);
        return randomStudent;
    }


    public int getNum(){
        return bag.size();
    }

}

