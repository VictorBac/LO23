package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.EnumerationCard;
import fr.utc.lo23.common.data.PlayerHand;
import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jianghan on 26/11/2015.
 */
public class CalculatorTest {
    public CombinationCalculator calculator;
    ArrayList<Card> cards;
    ArrayList<Integer> cardValues;

    ArrayList<PlayerHand> listOfPlayer;
    ArrayList<Card> cardsOnField;

    public CalculatorTest() {
        calculator = new CombinationCalculator();
        cards = new ArrayList<Card>();
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
        test.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 5));
        test = calculator.hasHighCard(test);
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(Arrays.asList(1, 12, 10, 9, 9, 8));
        assertEquals(expected, test);
    }
}