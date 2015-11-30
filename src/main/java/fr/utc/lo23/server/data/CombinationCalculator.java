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

    public ArrayList<Integer> getHandType(ArrayList<Card> cardsOnHand, ArrayList<Card> cardsOnField) throws Exception {
        ArrayList<Card> cards = null;
        cards.addAll(cardsOnHand);
        cards.addAll(cardsOnField);
        if (cards.size() != 7) {
            throw new Exception();
        }
        ArrayList<Integer> cardValues = new ArrayList<Integer>();

        ArrayList<Integer> cardRank = hasRoyalFlush(cardValues);
        if (cardRank == null) cardRank = hasRoyalFlush(cardValues);
        if (cardRank == null) cardRank = hasStraightFlush(cardValues);
        if (cardRank == null) cardRank = hasFullHouse(cardValues);
        if (cardRank == null) cardRank = hasFour(cardValues);
        if (cardRank == null) cardRank = hasFlush(cards);
        if (cardRank == null) cardRank = hasStraight(cardValues);
        if (cardRank == null) cardRank = hasThree(cardValues);
        if (cardRank == null) cardRank = hasTwoPair(cardValues);
        if (cardRank == null) cardRank = hasOnePair(cardValues);
        if (cardRank == null) cardRank = hasHighCard(cardValues);
        return cardRank;
    }

    /**
     * Rank 1: High card TODO
     * @param cardValues
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasHighCard(ArrayList<Integer> cardValues) throws Exception {
        return cardValues;
    }

    /**
     * Rank 2: One pair
     * @param cardValues
     * @return cardRank if has one pair. null if not.
     * @throws Exception
     */
    public ArrayList<Integer> hasOnePair(ArrayList<Integer> cardValues) throws Exception {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 1; i < 7; i++) {
            if (cardValues.get(i - 1) == cardValues.get(i)) {
                break;
            }
        }
        if (i < 7) {
            // move the pair to the start
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(1, 1);
            // remove the last two card values
            cardRank.remove(6);
            cardRank.remove(6);
            return cardRank;
        }  else {
            return null;
        }
    }

    /**
     * Rank 3: Two pairs TODO
     * @param cardValues
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasTwoPair(ArrayList<Integer> cardValues) throws Exception {
        this.hasOnePair(cardValues);
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 1; i < 7; i++) {
            if (cardValues.get(i -1) == cardValues.get(i)) {
                break;
            }
        }

        return null;
    }

    /**
     * Rank 4: Three of a kind
     * @param cardValues
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasThree(ArrayList<Integer> cardValues) throws Exception {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 2; i < 7; i++) {
            if (cardValues.get(i - 2) == cardValues.get(i)) {
                break;
            }
        }
        if (i < 7) {
            // move the three of a kind to the start
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, 3);
            // remove the last two card values
            cardRank.remove(6);
            cardRank.remove(6);
            return cardRank;
        }  else {
            return null;
        }
    }

    /**
     * Rank 5: Straight TODO
     * @param cardValues
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasStraight(ArrayList<Integer> cardValues) throws Exception {
        return null;
    }

    /**
     * Rank 6: Flush
     * @param cards
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasFlush (ArrayList<Card> cards) throws Exception {
        ArrayList<Integer> spade = new ArrayList<Integer>();
        ArrayList<Integer> heart = new ArrayList<Integer>();
        ArrayList<Integer> diamond = new ArrayList<Integer>();
        ArrayList<Integer> club = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            char symbol = cards.get(i).getSymbol();
            Integer value = cards.get(i).getValue();
            if (symbol == 'S') spade.add(value);
            if (symbol == 'H') heart.add(value);
            if (symbol == 'D') diamond.add(value);
            if (symbol == 'C') club.add(value);
        }
        if (spade.size() >= 5) {
            while (spade.size() > 5) spade.remove(5);
            spade.add(0,6);
            return spade;
        }
        if (heart.size() >= 5) {
            while (heart.size() > 5) heart.remove(5);
            heart.add(0,6);
            return heart;
        }
        if (diamond.size() >= 5) {
            while (diamond.size() > 5) diamond.remove(5);
            diamond.add(0,6);
            return diamond;
        }
        if (club.size() >= 5) {
            while (club.size() > 5) club.remove(5);
            club.add(0,6);
            return club;
        }
        return null;
    }

    public ArrayList<Integer> hasFullHouse (ArrayList<Integer> cardValues) throws Exception {
        return null;
    }

    /**
     * Rank 7: Four of a kind
     * @param cardValues
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasFour(ArrayList<Integer> cardValues) throws Exception {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 3; i < 7; i++) {
            if (cardValues.get(i - 3) == cardValues.get(i)) {
                break;
            }
        }
        if (i < 7) {
            // move the four of a kind to the start
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            // remove the last two card values
            cardRank.remove(5);
            cardRank.remove(5);
            cardRank.add(0, 7);
            return cardRank;
        }  else {
            return null;
        }
    }


    /**
     * Rank 8: Straight Flush TODO
     * @param cardValues
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasStraightFlush (ArrayList<Integer> cardValues) throws Exception {
        return null;
    }

    /**
     * Rank 9: Royal Flush TODO
     * @param cardValues
     * @return
     * @throws Exception
     */

    public ArrayList<Integer> hasRoyalFlush(ArrayList<Integer> cardValues) throws Exception {
        return null;
    }

}
