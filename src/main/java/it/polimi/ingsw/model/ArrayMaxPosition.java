package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.OptionalInt;

/**
 * this class find the position of the max number in an array
 */

public class ArrayMaxPosition {

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

    public static int findMaxOfArray(int[] a1) {
        OptionalInt max_1;
        int max_a1;
        max_1 = Arrays.stream(a1).max();
        max_a1 = max_1.getAsInt();
        return max_a1;
    }
}
