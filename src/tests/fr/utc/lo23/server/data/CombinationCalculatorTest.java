package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Created by Jianghan on 26/11/2015.
 */
public class CombinationCalculatorTest {
    public CombinationCalculator calculator = new CombinationCalculator();
    ArrayList<Integer> cardValues;

    public CombinationCalculatorTest() {
        cardValues = new ArrayList<Integer>();
        cardValues.add(13);
        cardValues.add(10);
        cardValues.add(10);
        cardValues.add(9);
        cardValues.add(8);
        cardValues.add(8);
        cardValues.add(8);
    }

    @Test
    public void testHighCard() throws Exception {
        int result = calculator.hasHighCard(cardValues);
        assertEquals(result, 13);
    }
}