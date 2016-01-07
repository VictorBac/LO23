package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.*;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * The test for some main function in Calculator.
 * You can take the test as an example of function usage.
 * Created by Jianghan on 26/11/2015.
 */
public class CalculatorTest {
    CombinationCalculator calculator;
    ArrayList<PlayerHand> listOfPlayer;
    ArrayList<PlayerHand> listWinner;
    ArrayList<Card> cardsOnField;

    public CalculatorTest() {
        calculator = new CombinationCalculator();
        Hand oneHand = new Hand(new Timestamp(Calendar.getInstance().getTime().getTime()));
        // Add 4 player in this test.
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<>(), new UserLight("Thibault")));
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<>(), new UserLight("Bastien")));
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<>(), new UserLight("Paul")));
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<>(), new UserLight("Victor")));
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<>(), new UserLight("Marouane")));

        oneHand.distributeCard();
        listOfPlayer = oneHand.getListPlayerHand();
        cardsOnField = oneHand.getListCardField();
    }


    /**
     * get Winner Test
     */
    @Test
    public void getWinnerTest() {
        listWinner = calculator.getWinner(listOfPlayer, cardsOnField);
        for (PlayerHand p : listOfPlayer) {
            System.out.println("Get rank of " + p.getPlayer().getPseudo() + " : " +
                    calculator.getHandRank(p.getListCardsHand(), cardsOnField));
        }
    }

    /**
     * get winner list test
     */
    @Test
    public void greterThanTest() {
        ArrayList<Integer> left = new ArrayList<>();
        left.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 5));
        ArrayList<Integer> right = new ArrayList<>();
        right.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 5));
        //System.out.println(calculator.greaterThan(left, right));
    }

    /**
     * get handRank test
     */
    @Test
    public void getHandRankTest() {
        calculator.showHand(listOfPlayer, cardsOnField);
        for (PlayerHand p : listOfPlayer) {
            System.out.println("Get rank of " + p.getPlayer().getPseudo() + " : " +
                    calculator.getHandRank(p.getListCardsHand(), cardsOnField));
        }

    }
}