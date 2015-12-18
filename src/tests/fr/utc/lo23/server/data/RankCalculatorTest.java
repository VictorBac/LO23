package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.EnumerationCard;
import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * The test for every rank checking function.
 * Created by Jianghan on 26/11/2015.
 */
public class RankCalculatorTest {
    public CombinationCalculator calculator;
    ArrayList<Card> cards;
    ArrayList<Integer> cardValues;

    public RankCalculatorTest() {
        calculator = new CombinationCalculator();

        cards = new ArrayList<>();
        try {
            cards.add(new Card(14, EnumerationCard.CLUB));
            cards.add(new Card(13,EnumerationCard.DIAMOND));
            cards.add(new Card(5,EnumerationCard.CLUB));
            cards.add(new Card(4,EnumerationCard.CLUB));
            cards.add(new Card(3,EnumerationCard.HEART));
            cards.add(new Card(3,EnumerationCard.CLUB));
            cards.add(new Card(2,EnumerationCard.CLUB));
        } catch (CardFormatInvalidException e) {
            e.printStackTrace();
        }
        
        cardValues = new ArrayList<>();
        cardValues.addAll(cards.stream().map(Card::getValue).collect(Collectors.toList()));
        Collections.sort(cardValues);
        Collections.reverse(cardValues);
    }


    /**
     * Rank 1: High card
     */
    @Test
    public void testHighCard() {
        System.out.println("Rank 1: High Card");
        ArrayList<Integer> test = new ArrayList<>();
        test.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 5));
        test = calculator.hasHighCard(test);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(1, 12, 10, 9, 9, 8));
        assertEquals(expected, test);
    }

    /**
     * Rank 2: One Pair
     */
    @Test
    public void testOnePair() {
        System.out.println("Rank 2: One Pair");
        ArrayList<Integer> test = new ArrayList<>();
        test.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 5));
        test = calculator.hasOnePair(test);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(2, 9, 9, 12, 10, 8));
        assertEquals(expected, test);
    }

    /**
     * Rank 3: Two Pairs
     */
    @Test
    public void testTwoPair() {
        System.out.println("Rank 3: Two Pairs");
        ArrayList<Integer> test = new ArrayList<>();
        test.addAll(Arrays.asList(12, 10, 9, 9, 8, 5, 5));
        test = calculator.hasTwoPair(test);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(3, 9, 9, 5, 5, 12));
        assertEquals(expected, test);
    }

    /**
     * Rank 4: Three of a kind
     */
    @Test
    public void testThree() {
        System.out.println("Rank 4: Three of a kind");
        ArrayList<Integer> test = new ArrayList<>();
        test.addAll(Arrays.asList(12, 10, 9, 9, 9, 8, 5));
        test = calculator.hasThree(test);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(4, 9, 9, 9, 12, 10));
        assertEquals(expected, test);
    }

    /**
     * Rank 5: Straight
     */
    @Test
    public void testStraight() {
        System.out.println("Rank 5: Straight");
        ArrayList<Integer> test = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        // test 1
        test.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 6));
        test = calculator.hasStraight(test);
        expected.addAll(Arrays.asList(5, 10, 9, 8, 7, 6));
        assertEquals(expected, test);
        // test 2
        test.clear();
        test.addAll(Arrays.asList(14, 5, 4, 4, 4, 3, 2));
        test = calculator.hasStraight(test);
        expected.clear();
        expected.addAll(Arrays.asList(5, 5, 4, 3, 2, 1));
        assertEquals(expected, test);

    }

    /**
     * Rank 6: Flush
     */
    @Test
    public void testFlush() {
        System.out.println("Rank 6: Flush");
        ArrayList<Integer> test = calculator.hasFlush(cards);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(6, 14, 5, 4, 3, 2));
        assertEquals(expected, test);

    }

    /**
     * Rank 7: FullHouse
     */
    @Test
    public void testFullHouse() {
        System.out.println("Rank 5: Straight");
        ArrayList<Integer> test = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        // test 1
        test.addAll(Arrays.asList(12, 9, 9, 8, 3, 3, 3));
        test = calculator.hasFullHouse(test);
        expected.addAll(Arrays.asList(7, 3, 3, 3, 9, 9));
        assertEquals(expected, test);
        // test 2
        test.clear();
        test.addAll(Arrays.asList(10, 10, 10, 7, 3, 3, 2));
        test = calculator.hasFullHouse(test);
        expected.clear();
        expected.addAll(Arrays.asList(7, 10, 10, 10, 3, 3));
        assertEquals(expected, test);

    }

    /**
     * Rank 8: Four of a kind
     */
    @Test
    public void testFour() {
        System.out.println("Rank 8: Four of a kind");
        ArrayList<Integer> test = new ArrayList<>();
        test.addAll(Arrays.asList(12, 10, 9, 9, 9, 9, 5));
        test = calculator.hasFour(test);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(8, 9, 9, 9, 9, 12));
        assertEquals(expected, test);
    }

    /**
     * Rank 9: Straight Flush
     */
    @Test
    public void testStraightFlush() {
        System.out.println("Rank 9: Straight Flush");
        ArrayList<Integer> test = calculator.hasStraightFlush(cards);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(9, 5, 4, 3, 2, 1));
        assertEquals(expected, test);
    }

    /**
     * Rank 10: Royal Flush
     */
    @Test
    public void testRoyalFlush() {
        System.out.println("Rank 10: Royal Flush");
        ArrayList<Integer> test = calculator.hasStraightFlush(cards);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.addAll(Arrays.asList(9, 5, 4, 3, 2, 1));
        assertEquals(expected, test);
    }
}