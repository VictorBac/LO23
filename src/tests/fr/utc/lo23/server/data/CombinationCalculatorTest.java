package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jianghan on 26/11/2015.
 */
public class CombinationCalculatorTest {
    public CombinationCalculator calculator;
    ArrayList<Integer> cardValues;
    ArrayList<Integer> map;

    public CombinationCalculatorTest() {
        calculator = new CombinationCalculator();
        cardValues = new ArrayList<Integer>();
        cardValues.add(13);
        cardValues.add(10);
        cardValues.add(10);
        cardValues.add(9);
        cardValues.add(9);
        cardValues.add(9);
        cardValues.add(3);
    }

    @Test
    public void testHighCard() throws Exception {
        int result = calculator.hasHighCard(cardValues);
        assertEquals(result, 13);
    }

    @Test
    public void testOnePair() throws Exception {
        int result = calculator.hasOnePair(cardValues);
        assertEquals(result, 10);
    }

    @Test
    public void testThree() throws Exception {
        int result = calculator.hasThree(cardValues);
        assertEquals(result, 9);
    }

    @Test
    public void testFour() throws Exception {
        int result = calculator.hasFour(cardValues);
        assertEquals(result, 0);
    }

    @Test
    public void tempTest() throws Exception {
        Collections.sort(cardValues);
        Collections.reverse(cardValues);

    }
}