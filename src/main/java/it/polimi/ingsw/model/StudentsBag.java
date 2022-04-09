package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

public class StudentsBag {

    private ArrayList<Student> bag;
    private final PawnColor[] colors = {PawnColor.CYAN,PawnColor.MAGENTA,PawnColor.YELLOW,PawnColor.RED,PawnColor.GREEN } ;

    public ArrayList<Student> getBag() {
        return bag;
    }

    /**
     * takes exactly 24 students of each color, to do this it uses j%5 and then fills the bag with the students
     * @param i represent the total number of students in the game (120)
     */
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

