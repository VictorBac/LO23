package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jianghan on 26/11/2015.
 */
public class CombinationCalculatorTest {
    public CombinationCalculator calculator;
    ArrayList<Integer> cardValues;

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
    public void testHighCard() {
        //assertEquals(result, 13);
    }

    @Test
    public void testOnePair() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 7, 5}));
        test = calculator.hasOnePair(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{1, 9, 9, 12, 10, 8}));
        assertEquals(test, expected);
    }

    @Test
    public void testThree() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 9, 8, 5}));
        test = calculator.hasThree(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{3, 9, 9, 9, 12, 10}));
        assertEquals(expected, test);
    }

    @Test
    public void testFour() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 9, 9, 5}));
        test = calculator.hasFour(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{7, 9, 9, 9, 9, 12}));
        assertEquals(expected, test);

    }

    @Test
    public void tempTest() throws Exception {
        Collections.sort(cardValues);
        Collections.reverse(cardValues);

    }
}