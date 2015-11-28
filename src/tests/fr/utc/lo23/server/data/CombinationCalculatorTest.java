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
    ArrayList<Card> cards;
    ArrayList<Integer> cardValues;

    public CombinationCalculatorTest() {
        calculator = new CombinationCalculator();

        cards = new ArrayList<Card>();
        cards.add(new Card(13,'C'));
        cards.add(new Card(10,'C'));
        cards.add(new Card(10,'S'));
        cards.add(new Card(9,'C'));
        cards.add(new Card(7,'C'));
        cards.add(new Card(6,'H'));
        cards.add(new Card(2,'C'));

        cardValues = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            cardValues.add(cards.get(i).getValue());
        }
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
    public void testFlush() throws Exception {
        ArrayList<Integer> test = calculator.hasFlush(cards);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{5, 13, 10, 9, 7, 2}));
        assertEquals(expected, test);

    }



    @Test
    public void tempTest() throws Exception {
        Collections.sort(cardValues);
        Collections.reverse(cardValues);

    }
}