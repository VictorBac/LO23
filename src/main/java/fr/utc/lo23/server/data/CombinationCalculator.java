package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.PlayerHand;

import java.util.ArrayList;

/**
 * Class of combination calculator to find the winner
 * Created by Jianghan on 21/10/2015.
 */
public class CombinationCalculator {

    /**
     * @param listOfPlayer : player list and their hand cards
     * @param cardsOnField : cards on the table
     */
    public ArrayList<PlayerHand> getWinner(ArrayList<PlayerHand> listOfPlayer, ArrayList<Card> cardsOnField){

        return null; //TODO remove this line
    }

    public ArrayList<PlayerHand> getHandType(ArrayList<Card> cardsOnHand, ArrayList<Card> cardsOnField) throws Exception {
        ArrayList<Card> cards = null;
        cards.addAll(cardsOnHand);
        cards.addAll(cardsOnField);
        if(cards.size() != 7) {
            throw new Exception();
        }


        return null; //TODO remove this line
    }

    public ArrayList<Integer> toMap(ArrayList<Integer> cardValues) throws Exception {
        for (int i = 0; i < 7 ; i++) {

        }
        return cardValues;
    }


    public Integer hasHighCard(ArrayList<Integer> cardValues) throws Exception {
        return cardValues.get(0);
    }

    public Integer hasOnePair(ArrayList<Integer> cardValues) throws Exception {
        for (int i = 0; i < cardValues.size() - 1; i++) {
            if (cardValues.get(i) == cardValues.get(i+1)) {
                return cardValues.get(i);
            }
        }
        return 0;
    }

    public Integer hasTwoPair(ArrayList<Integer> cardValues) throws Exception {
        return cardValues.get(0);
    }

    public Integer hasThree(ArrayList<Integer> cardValues) throws Exception {
        for (int i = 0; i < cardValues.size() - 2; i++) {
            if (cardValues.get(i) == cardValues.get(i+2)) {
                return cardValues.get(i);
            }
        }
        return 0;
    }

    public Integer hasStraight(ArrayList<Integer> cardValues) throws Exception {
        return cardValues.get(0);
    }

    public Integer hasFlush (ArrayList<Integer> cardValues) throws Exception {
        return cardValues.get(0);
    }

    public Integer hasFour(ArrayList<Integer> cardValues) throws Exception {
        for (int i = 0; i < cardValues.size() - 3; i++) {
            if (cardValues.get(i) == cardValues.get(i+3)) {
                return cardValues.get(i);
            }
        }
        return 0;
    }

    public Integer hasStraightFlush (ArrayList<Integer> cardValues) throws Exception {
        return cardValues.get(0);
    }

    public Integer hasRoyalFlush(ArrayList<Integer> cardValues) throws Exception {
        return cardValues.get(0);
    }

}
