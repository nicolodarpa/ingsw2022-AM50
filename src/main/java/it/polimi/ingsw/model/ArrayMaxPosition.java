package it.polimi.ingsw.model;

/**
 * this class find the position of the max number in an array
 */
public class ArrayMaxPosition {


    /**
     * Scans the array and save the index of the max element
     * @param a array of int
     * @return the index of the max element
     */
    public static int findMaxPosition(int[] a) {
        int posMax = 0;
        int max = a[0];
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
                posMax = i;
            }
        }
        return posMax;
    }
}
