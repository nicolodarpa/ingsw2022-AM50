package it.polimi.ingsw.server.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * It's a bag that contains all the students' pawn that there are in the game.
 */
public class StudentsBag {

    /**
     * It contains all the students in the vag
     */
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

    /**
     * Extracts randomly one student from the bag.
     */
    public Student casualExtraction(){
        Random random = new Random();
        Student randomStudent = bag.get(random.nextInt(bag.size()));
        bag.remove(randomStudent);
        return randomStudent;
    }

    /**
     * Adds one student to bag.
     */
    public void addStudent(Student student){
        bag.add(student);
    }


    /**
     * @return the number of students in the bag
     */
    public int getNum(){
        return bag.size();
    }


    /**
     * @return true if there aren't any students in the bag
     */
    public boolean endOfStudents(){
        return bag.size() == 0;
    }


}

