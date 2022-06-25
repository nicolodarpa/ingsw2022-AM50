package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.ArrayMaxPosition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayMaxPositionTest {

    private final int[] a = {0,2,27,34,-2};

    @Test
    @DisplayName("Find max position")
    public void findMaxPosition(){
        int posMax = 0;
        posMax = ArrayMaxPosition.findMaxPosition(a);
        assertEquals(3,posMax);
    }
}
