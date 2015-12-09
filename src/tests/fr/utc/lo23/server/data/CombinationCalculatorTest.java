package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.EnumerationCard;
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
            cards.add(new Card(14, EnumerationCard.CLUB));
            cards.add(new Card(13,EnumerationCard.CLUB));
            cards.add(new Card(5,EnumerationCard.SPADE));
            cards.add(new Card(4,EnumerationCard.CLUB));
            cards.add(new Card(3,EnumerationCard.CLUB));
            cards.add(new Card(3,EnumerationCard.CLUB));
            cards.add(new Card(2,EnumerationCard.CLUB));
        } catch (CardFormatInvalidException e) {
            e.printStackTrace();
        }
        
        cardValues = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            cardValues.add(cards.get(i).getValue());
        }
        Collections.sort(cardValues);
        Collections.reverse(cardValues);
    }


    /**
     * Rank 1: High card
     */
    @Test
    public void testHighCard() {
        System.out.println("Rank 1: High Card");
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 7, 5}));
        test = calculator.hasHighCard(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{1, 12, 10, 9, 9, 8}));
        assertEquals(test, expected);
    }

    /**
     * Rank 2: One Pair
     */
    @Test
    public void testOnePair() {
        System.out.println("Rank 2: One Pair");
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 7, 5}));
        test = calculator.hasOnePair(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{2, 9, 9, 12, 10, 8}));
        assertEquals(test, expected);
    }

    /**
     * Rank 3: Two Pairs
     */
    @Test
    public void testTwoPair() {
        System.out.println("Rank 3: Two Pairs");
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 8, 5, 5}));
        test = calculator.hasOnePair(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{3, 9, 9, 5, 5, 12}));
        assertEquals(test, expected);
    }

    /**
     * Rank 4: Three of a kind
     */
    @Test
    public void testThree() {
        System.out.println("Rank 4: Three of a kind");
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 9, 8, 5}));
        test = calculator.hasThree(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{4, 9, 9, 9, 12, 10}));
        assertEquals(expected, test);
    }

    /**
     * Rank 5: Straight
     * @throws Exception
     */
    @Test
    public void testStraight() {
        System.out.println("Rank 5: Straight");
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

    /**
     * Rank 6: Flush
     */
    @Test
    public void testFlush() {
        System.out.println("Rank 6: Flush");
        ArrayList<Integer> test = calculator.hasFlush(cards);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{6, 13, 10, 9, 7, 2}));
        assertEquals(expected, test);

    }

    /**
     * Rank 7: FullHouse TODO
     */
    @Test
    public void testFullHouse() {

    }

    /**
     * Rank 8: Four of a kind
     */
    @Test
    public void testFour() {
        System.out.println("Rank 8: Four of a kind");
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.addAll(Arrays.asList(new Integer[]{12, 10, 9, 9, 9, 9, 5}));
        test = calculator.hasFour(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{8, 9, 9, 9, 9, 12}));
        assertEquals(expected, test);
    }

    /**
     * Rank 9: Straight Flush
     */
    @Test
    public void testStraightFlush() {
        System.out.println("Rank 9: Straight Flush");
        ArrayList<Integer> test = calculator.hasFlush(cards);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{9, 5, 4, 3, 2, 1}));
        assertEquals(expected, test);
    }

    /**
     * Rank 10: Royal Flush
     */
    @Test
    public void testRoyalFlush() {
        System.out.println("Rank 10: Royal Flush");
        ArrayList<Integer> test = calculator.hasFlush(cards);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(new Integer[]{10, 14, 13, 12, 11, 10}));
        assertEquals(expected, test);
    }
}