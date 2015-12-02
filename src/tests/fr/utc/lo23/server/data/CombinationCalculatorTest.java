package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;
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
        try {
            cards.add(new Card(14,'C'));
            cards.add(new Card(13,'C'));
            cards.add(new Card(5,'S'));
            cards.add(new Card(4,'C'));
            cards.add(new Card(3,'C'));
            cards.add(new Card(3,'C'));
            cards.add(new Card(2,'C'));
        } catch (CardFormatInvalidException e) {
            e.printStackTrace();
        }
        
        cardValues = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            cardValues.add(cards.get(i).getValue());
        }
    }

    @Test
    public void testHighCard() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 7, 5}));
        test = calculator.hasHighCard(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{1, 12, 10, 9, 9, 8}));
        assertEquals(test, expected);
    }

    @Test
    public void testOnePair() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 7, 5}));
        test = calculator.hasOnePair(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{2, 9, 9, 12, 10, 8}));
        assertEquals(test, expected);
    }

    @Test
    public void testTwoPair() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 5, 5}));
        test = calculator.hasOnePair(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{3, 9, 9, 5, 5, 12}));
        assertEquals(test, expected);
    }

    @Test
    public void testThree() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 9, 8, 5}));
        test = calculator.hasThree(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{4, 9, 9, 9, 12, 10}));
        assertEquals(expected, test);
    }

    @Test
    public void testStraight() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        ArrayList<Integer> expected = new ArrayList<Integer>();
        // test 1
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 7, 6}));
        test = calculator.hasStraight(test);
        expected.addAll(Arrays.asList(new Integer[]{5, 10, 9, 8, 7, 6}));
        assertEquals(expected, test);
        // test 2
        test.addAll(Arrays.asList(new Integer[]{14, 5, 4, 4, 4, 3, 2}));
        test = calculator.hasStraight(test);
        expected.addAll(Arrays.asList(new Integer[]{5, 5, 4, 3, 2, 1}));
        assertEquals(expected, test);

    }

    @Test
    public void testFlush() throws Exception {
        ArrayList<Integer> test = calculator.hasFlush(cards);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{6, 13, 10, 9, 7, 2}));
        assertEquals(expected, test);

    }

    @Test
    public void testFour() throws Exception {
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 9, 9, 5}));
        test = calculator.hasFour(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{8, 9, 9, 9, 9, 12}));
        assertEquals(expected, test);
    }

    @Test
    public void testStraightFlush() throws Exception {
        ArrayList<Integer> test = calculator.hasFlush(cards);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{9, 5, 4, 3, 2, 1}));
        assertEquals(expected, test);
    }


    @Test
    public void tempTest() throws Exception {
        Collections.sort(cardValues);
        Collections.reverse(cardValues);

    }
}