package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * It's a bag that contains all the students' pawn that there are in the game.
 */
public class StudentsBag {

    private final ArrayList<Student> bag = new ArrayList<>();
    private final PawnColor[] colors = {PawnColor.CYAN,PawnColor.MAGENTA,PawnColor.YELLOW,PawnColor.RED,PawnColor.GREEN } ;

    public ArrayList<Student> getBag() {
        return bag;
    }

    /**
     * takes exactly 24 students of each color, to do this it uses j%5 and then fills the bag with the students
     * @param numberOfStudents represent the total number of students in the game (120)
     */
    public void fillBag(int numberOfStudents){
        final int totalNumberOfPawnColor = PawnColor.numberOfColors;
        for(int j = 1; j < numberOfStudents+1; j++){
            bag.add(new Student(colors[j%totalNumberOfPawnColor]));
        }
    }

    public Student casualExtraction(){
        Random random = new Random();
        Student randomStudent = bag.get(random.nextInt(bag.size()));
        bag.remove(randomStudent);
        return randomStudent;
    }

    public void addStudent(Student student){
        bag.add(student);
    }


    public int getNum(){
        return bag.size();
    }

    public boolean endOfStudents(){
        return bag.size() == 0;
    }


}

