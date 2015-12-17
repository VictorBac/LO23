package fr.utc.lo23.server.data;


import fr.utc.lo23.common.data.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jianghan on 26/11/2015.
 */
public class CalculatorTest {
    public CombinationCalculator calculator;
    ArrayList<PlayerHand> listOfPlayer;
    ArrayList<PlayerHand> listWinner;
    ArrayList<Card> cardsOnField;

    public CalculatorTest() {
        calculator = new CombinationCalculator();
        Hand oneHand = new Hand();
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<Card>(), new UserLight("player1")));
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<Card>(), new UserLight("player2")));
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<Card>(), new UserLight("player3")));
        oneHand.getListPlayerHand().add(new PlayerHand(new ArrayList<Card>(), new UserLight("player4")));

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
    }

    /**
     * get winner list test
     */
    @Test
    public void greterThanTest() {
        ArrayList<Integer> left = new ArrayList<Integer>();
        left.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 5));
        ArrayList<Integer> right = new ArrayList<Integer>();
        right.addAll(Arrays.asList(12, 10, 9, 9, 8, 7, 5));
        System.out.println(calculator.greaterThan(left, right));
    }

    /**
     * get handRank test
     */
    @Test
    public void getHandRankTest() {
        calculator.showHand(listOfPlayer, cardsOnField);
        System.out.println("Get rank of the first player " +
                calculator.getHandRank(listOfPlayer.get(0).getListCardsHand(), cardsOnField));
    }
}