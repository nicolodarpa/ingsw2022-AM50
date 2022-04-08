package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest {
    @Test
    @DisplayName("Add one coin")
    public void testAddCoin(){
        Wallet wTest = new Wallet();
        wTest.addCoins(1);
        assertEquals(2,wTest.getCoins());
    }
}
