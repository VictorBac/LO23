package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.EnumerationCard;
import fr.utc.lo23.common.data.PlayerHand;
import fr.utc.lo23.server.data.exceptions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class of combination calculator to find the winner
 * Created by Jianghan on 21/10/2015.
 */
public class CombinationCalculator {

    /**
     * @param listOfPlayer : player list and their hand cards
     * @param cardsOnField : cards on the table
     */
    public ArrayList<PlayerHand> getWinner(ArrayList<PlayerHand> listOfPlayer, ArrayList<Card> cardsOnField) {
        this.showHand(listOfPlayer, cardsOnField);
        try {
            if (cardsOnField.size() != 5) {
                throw new CardsNumberException(cardsOnField);
            }
        } catch (CardsNumberException e) {
            e.printStackTrace();
        }

        ArrayList<PlayerHand> winnerList = new ArrayList<PlayerHand>();
        ArrayList<Integer> biggestCardRank = new ArrayList<Integer>(6);
        biggestCardRank.addAll(Arrays.asList(0, 0, 0, 0, 0, 0));
        ArrayList<Card> currentHand;
        ArrayList<Integer> currentRank;

        // Bubble sort the winner list
        for (int i = 0; i < listOfPlayer.size(); i++) {
            currentHand = listOfPlayer.get(i).getListCardsHand();
            try {
                if (currentHand.size() != 2) {
                    throw new CardsNumberException(currentHand);
                }
            } catch (CardsNumberException e) {
                e.printStackTrace();
            }

            currentRank = getHandRank(currentHand, cardsOnField);
            if (greaterThan(currentRank, biggestCardRank)) {
                biggestCardRank = currentRank;
                winnerList.clear();
                winnerList.add(listOfPlayer.get(i));
            } else if (currentRank.equals(biggestCardRank)) {
                winnerList.add(listOfPlayer.get(i));
            }
        }
        this.showWinnerList(winnerList, biggestCardRank);
        return winnerList;
    }

    public void showHand(ArrayList<PlayerHand> listOfPlayer, ArrayList<Card> cardsOnField) {
        System.out.print("Cards on field: ");
        for(Card c : cardsOnField){
            c.printCard();
        }
        System.out.println("");
        System.out.print("Cards in hands: ");
        for(PlayerHand p : listOfPlayer) {
            System.out.print(p.getPlayer().getPseudo() + " ");
            p.getListCardsHand().get(0).printCard();
            p.getListCardsHand().get(1).printCard();
            System.out.print("   ");
        }
        System.out.println("   ");
    }

    public void showWinnerList(ArrayList<PlayerHand> listOfPlayer, ArrayList<Integer> biggestCardRank) {
        System.out.println("Winner list with rank " + biggestCardRank);
        for(PlayerHand p : listOfPlayer) {
            System.out.print(p.getPlayer().getPseudo() + " ");
        }
        System.out.println();
    }

    public boolean greaterThan(ArrayList<Integer> left, ArrayList<Integer> right) {
        try {
            if (left.size() != right.size()) {
                throw new CardValueNumberInvalidException(right);
            }
        } catch (CardValueNumberInvalidException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < left.size(); i++) {
            if (left.get(i) > right.get(i)) return true;
            if (left.get(i) < right.get(i)) return false;
        }
        return false;
    }



    public ArrayList<Integer> getHandRank(ArrayList<Card> cardsOnHand, ArrayList<Card> cardsOnField) {
        // Add cardsOnHand and cardsOnField (7 cards) to variable to cards.
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.addAll(cardsOnHand);
        cards.addAll(cardsOnField);
        // take out cardValues of cards. Sort it as desc.
        ArrayList<Integer> cardValues = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            cardValues.add(cards.get(i).getValue());
        }

        Collections.sort(cardValues);
        Collections.reverse(cardValues);
        // Try to find the first matched card rank. Start at the highest rank.
        ArrayList<Integer> cardRank = hasRoyalFlush(cards);
        if (cardRank == null) cardRank = hasRoyalFlush(cards);
        if (cardRank == null) cardRank = hasStraightFlush(cards);
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
     * Rank 1: High card
     * @param cardValues
     * @return
     * @throws Exception
     */
    protected ArrayList<Integer> hasHighCard(ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        // Just remove the two smallest values since cardValues has been sorted.
        cardRank.remove(5);
        cardRank.remove(5);
        cardRank.add(0, 1);
        return cardRank;
    }

    /**
     * Rank 2: One pair
     * @param cardValues
     * @return cardRank if has one pair. null if not.
     * @throws Exception
     */
    protected ArrayList<Integer> hasOnePair(ArrayList<Integer> cardValues) {
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
            // remove the last two card values
            cardRank.remove(5);
            cardRank.remove(5);
            // add card rank 2
            cardRank.add(0, 2);
            return cardRank;
        }  else {
            return null;
        }
    }

    /**
     * Rank 3: Two pairs
     * @param cardValues
     * @return
     * @throws Exception
     */
    protected ArrayList<Integer> hasTwoPair(ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 1; i < 7; i++) {
            if (cardValues.get(i -1) == cardValues.get(i)) {
                break;
            }
        }
        if (i < 5) {
            // move the pair to the start
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            for (i=i+2; i < 7; i++) {
                if (cardValues.get(i -1) == cardValues.get(i)) {
                    break;
                }
            }
            if (i < 7) {
                // move the pair to the start
                cardRank.add(2, cardRank.remove(i));
                cardRank.add(2, cardRank.remove(i));
                // remove the last two card values
                cardRank.remove(5);
                cardRank.remove(5);
                // add card rank 2
                cardRank.add(0, 3);
                return cardRank;
            }  else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Rank 4: Three of a kind
     * @param cardValues
     * @return
     * @throws Exception
     */
    protected ArrayList<Integer> hasThree(ArrayList<Integer> cardValues) {
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
            // remove the last two card values
            cardRank.remove(5);
            cardRank.remove(5);
            // add card rank 3
            cardRank.add(0, 4);
            return cardRank;
        }  else {
            return null;
        }
    }

    /**
     * Rank 5: Straight
     * @param cardValues
     * @return
     * @throws Exception
     */
    protected ArrayList<Integer> hasStraight(ArrayList<Integer> cardValues) {
        if (cardValues.size() < 5) return null;
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        if (cardRank.get(0) == 14)  cardRank.add(1);
        for (int i = 0; i < 4; i++) {
            Integer highcard = cardRank.get(i);
            if (cardRank.contains(highcard - 1) &&
                    cardRank.contains(highcard - 2) &&
                    cardRank.contains(highcard - 3) &&
                    cardRank.contains(highcard - 4))
            {
                cardRank.clear();
                cardRank.add(5);
                for (int j = highcard; j > highcard - 5 ; j--) {
                    cardRank.add(j);
                }
                return cardRank;
            }

        }
        return null;
    }

    /**
     * Rank 6: Flush
     * @param cards
     * @return
     * @throws Exception
     */
    protected ArrayList<Integer> hasFlush (ArrayList<Card> cards) {
        ArrayList<Integer> cardRank = new ArrayList<Integer>();
        ArrayList<Integer> spade = new ArrayList<Integer>();
        ArrayList<Integer> heart = new ArrayList<Integer>();
        ArrayList<Integer> diamond = new ArrayList<Integer>();
        ArrayList<Integer> club = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            EnumerationCard symbol = cards.get(i).getSymbol();
            Integer value = cards.get(i).getValue();
            if (symbol == EnumerationCard.SPADE) spade.add(value);
            if (symbol == EnumerationCard.HEART) heart.add(value);
            if (symbol == EnumerationCard.DIAMOND) diamond.add(value);
            if (symbol == EnumerationCard.CLUB) club.add(value);
        }
        if (spade.size() >= 5) {
            cardRank = spade;
        } else if (heart.size() >= 5) {
            cardRank = heart;
        } else if (diamond.size() >= 5) {
            cardRank = diamond;
        } else if (club.size() >= 5) {
            cardRank = club;
        } else {
            return null;
        }
        Collections.sort(cardRank);
        Collections.reverse(cardRank);
        while (cardRank.size() > 5) cardRank.remove(5);
        cardRank.add(0,6);
        return cardRank;
    }

    /**
     * Rank 7: FullHouse
     * @param cardValues
     * @return
     */
    protected ArrayList<Integer> hasFullHouse (ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        // try to three of a kind
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
        }  else {
            return null;
        }
        // try to find one pair
        for (i = 4; i < 7; i++) {
            if (cardValues.get(i - 1) == cardValues.get(i)) {
                break;
            }
        }
        if (i < 7) {
            // move the pair to the 4th and 5th card
            cardRank.add(3, cardRank.remove(i));
            cardRank.add(3, cardRank.remove(i));
            // remove the last two card values
            cardRank.remove(5);
            cardRank.remove(5);
            // add card rank 2
            cardRank.add(0, 7);
            return cardRank;
        }  else {
            return null;
        }
    }

    /**
     * Rank 8: Four of a kind
     * @param cardValues
     * @return
     * @throws Exception
     */
    public ArrayList<Integer> hasFour(ArrayList<Integer> cardValues) {
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
            // add card rank 7
            cardRank.add(0, 8);
            return cardRank;
        }  else {
            return null;
        }
    }


    /**
     * Rank 9: Straight Flush
     * @param cards
     * @return
     * @throws Exception
     */
    protected ArrayList<Integer> hasStraightFlush (ArrayList<Card> cards) {
        ArrayList<Integer> cardRank = new ArrayList<Integer>();
        ArrayList<Integer> spade = new ArrayList<Integer>();
        ArrayList<Integer> heart = new ArrayList<Integer>();
        ArrayList<Integer> diamond = new ArrayList<Integer>();
        ArrayList<Integer> club = new ArrayList<Integer>();
        for (int i = 0; i < cards.size(); i++) {
            EnumerationCard symbol = cards.get(i).getSymbol();
            Integer value = cards.get(i).getValue();
            if (symbol == EnumerationCard.SPADE) spade.add(value);
            if (symbol == EnumerationCard.HEART) heart.add(value);
            if (symbol == EnumerationCard.DIAMOND) diamond.add(value);
            if (symbol == EnumerationCard.CLUB) club.add(value);
        }
        if (spade.size() >= 5) {
            cardRank = spade;
        } else if (heart.size() >= 5) {
            cardRank = heart;
        } else if (diamond.size() >= 5) {
            cardRank = diamond;
        } else if (club.size() >= 5) {
            cardRank = club;
        } else {
            return null;
        }
        Collections.sort(cardRank);
        Collections.reverse(cardRank);
        cardRank = hasStraight(cardRank);
        if (cardRank == null) {
            return null;
        } else {
            cardRank.set(0,9);
            return cardRank;
        }
    }

    /**
     * Rank 10: Royal Flush
     * @param cards
     * @return
     * @throws Exception
     */
    protected ArrayList<Integer> hasRoyalFlush(ArrayList<Card> cards) {
        ArrayList<Integer> cardRank = hasStraightFlush(cards);
        if (cardRank == null ) {
            return null;
        } else if (cardRank.get(1) == 14) {
            cardRank.set(0,10);
            return  cardRank;
        } else return null;

    }

}
