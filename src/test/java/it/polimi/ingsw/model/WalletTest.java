package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Wallet tests
 */
public class WalletTest {
    /**
     * Test if coins count is successfully increased by adding coins
     */
    @Test
    @DisplayName("Add one coin")
    public void testAddCoin(){
        Wallet wTest = new Wallet();
        wTest.addCoins(1);
        assertEquals(2,wTest.getCoins());
    }

    /**
     * Test if coins count is successfully decreased by spending coins
     */
    @Test
    @DisplayName("Spent two coins")
    public void testSpentCoins(){
        Wallet wTest = new Wallet();
        wTest.addCoins(3);
        wTest.removeCoins(2);
        assertEquals(2, wTest.getCoins());
    }
}
