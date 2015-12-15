package fr.utc.lo23.server.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
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
    ArrayList<PlayerHand> listWinner;
    ArrayList<Card> cardsOnField;

    public CalculatorTest() {
        calculator = new CombinationCalculator();
        Hand oneHand = new Hand();
        oneHand.getListPlayerHand().add(new PlayerHand());
        oneHand.getListPlayerHand().add(new PlayerHand());
        //oneHand.getListPlayerHand().add(new PlayerHand());
        //oneHand.getListPlayerHand().add(new PlayerHand());

        oneHand.distributeCard();
        Console.log("after distrib cards \n");
        for(Card c : oneHand.getListCardField()){
            Console.log("field cards :" + c.getId());
        }
        Console.log("");
        for(PlayerHand p : oneHand.getListPlayerHand()){
            for(Card c : p.getListCardsHand()){
                Console.log("player " + p.toString() + " cards :" + c.getId());
            }
            Console.log("");
        }

        listOfPlayer = oneHand.getListPlayerHand();
        cardsOnField = oneHand.getListCardField();
/*        cards = new ArrayList<Card>();
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
        listOfPlayer.add(new PlayerHand(cards, new UserLight()));
        cardValues = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            cardValues.add(cards.get(i).getValue());
        }
        Collections.sort(cardValues);
        Collections.reverse(cardValues);*/

    }


    /**
     * get Winner Test
     */
    @Test
    public void getWinnerTest() {



        listWinner = calculator.getWinner(listOfPlayer, cardsOnField);
//        for(PlayerHand p : listWinner){
//            for(Card c : p.getListCardsHand()){
//                Console.log("win player " + p.toString() + " cards :" + c.getId());
//            }
//            Console.log("");
//        }
    }

    /**
     * get Winner Test
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
     * get Winner Test
     */
    @Test
    public void getHandRankTest() {
        System.out.println(calculator.getHandRank(listOfPlayer.get(0).getListCardsHand(),cardsOnField));
    }
}