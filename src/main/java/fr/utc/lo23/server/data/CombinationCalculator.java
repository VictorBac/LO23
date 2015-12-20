package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.EnumerationCard;
import fr.utc.lo23.common.data.PlayerHand;
import fr.utc.lo23.server.data.exceptions.CardsNumberException;
import fr.utc.lo23.server.data.exceptions.CardValueNumberInvalidException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class of combination calculator to find the winner list.
 * The algo getWinner checks from the highest rank royal flush until the rank high card.
 * Some other useful function in this class:
 * showHand : print a hand.
 * getHandRank: get the rank with 7 Cards as input.
 * Created by Jianghan on 21/10/2015. Contact him for any question.
 */
@SuppressWarnings("unchecked")
public class CombinationCalculator {


    /**
     * You can use this function to get a winner list for one pot. It works well.
     * @param listOfPlayer : Playerhand list. Contains player and his 2 hold cards.
     * @param cardsOnField : the 5 community cards
     * @return winnerList : ArrayList of all the winner.
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

        ArrayList<PlayerHand> winnerList = new ArrayList<>();
        ArrayList<Integer> highestRank = new ArrayList<>(6);
        highestRank.addAll(Arrays.asList(0, 0, 0, 0, 0, 0));
        ArrayList<Card> currentHand;
        ArrayList<Integer> currentRank;

        // Find the winner list with the highest rank
        for (PlayerHand p : listOfPlayer) {
            currentHand = p.getListCardsHand();
            try {
                if (currentHand.size() != 2) {
                    throw new CardsNumberException(currentHand);
                }
            } catch (CardsNumberException e) {
                e.printStackTrace();
            }

            currentRank = getHandRank(currentHand, cardsOnField);
            if (greaterThan(currentRank, highestRank)) {
                highestRank = currentRank;
                winnerList.clear();
                winnerList.add(p);
            } else if (currentRank.equals(highestRank)) {
                winnerList.add(p);
            }
        }
        this.showWinnerList(winnerList, highestRank);
        return winnerList;
    }

    /**
     * Print a Hand to console clearly.
     * Show every player's username, his hold cards and also the community cards.
     * It's better for the class Hand to have a similar function.
     * @param listOfPlayer list of PlayerHand
     * @param cardsOnField cards on field
     */
    public void showHand(ArrayList<PlayerHand> listOfPlayer, ArrayList<Card> cardsOnField) {
        System.out.print("Cards on field: ");
        cardsOnField.forEach(Card::printCard);
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

    /**
     * Print a list of winners and the card rank
     * @param listOfPlayer list of PlayerHand
     * @param highestRank highest cardRank
     */
    private void showWinnerList(ArrayList<PlayerHand> listOfPlayer, ArrayList<Integer> highestRank) {
        System.out.println("Winner list with rank " + highestRank);
        for(PlayerHand p : listOfPlayer) {
            System.out.print(p.getPlayer().getPseudo() + " ");
        }
        System.out.println();
    }

    /**
     * Interger ArrayList comparison
     * @param left the first Integer ArrayList
     * @param right the second Integer ArrayList
     * @return true if the left one greater than the right one
     */
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


    /**
     * Return the card rank of the combination of hold cards and community cards
     * @param cardsOnHand the 2 hold cards
     * @param cardsOnField the 5 community cards
     * @return card rank. The first number represent the rank and the rest 5 numbers is the combination
     */
    public ArrayList<Integer> getHandRank(ArrayList<Card> cardsOnHand, ArrayList<Card> cardsOnField) {
        // Add cardsOnHand and cardsOnField (7 cards) to variable to cards.
        ArrayList<Card> cards = new ArrayList<>(7);
        cards.addAll(cardsOnHand);
        cards.addAll(cardsOnField);
        // take out cardValues of cards. Sort it as desc.
        ArrayList<Integer> cardValues = new ArrayList<>();
        cardValues.addAll(cards.stream().map(Card::getValue).collect(Collectors.toList()));

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
     * @param cardValues card values
     * @return cardRank
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
     * @param cardValues card values
     * @return cardRank if has one pair. null if not.
     */
    protected ArrayList<Integer> hasOnePair(ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 1; i < 7; i++) {
            if (cardValues.get(i - 1).intValue() == cardValues.get(i).intValue()) {
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
     * @param cardValues card values
     * @return cardRank
     */
    protected ArrayList<Integer> hasTwoPair(ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 1; i < 7; i++) {
            if (cardValues.get(i -1).intValue() == cardValues.get(i).intValue()) {
                break;
            }
        }
        if (i < 5) {
            // move the pair to the start
            cardRank.add(0, cardRank.remove(i));
            cardRank.add(0, cardRank.remove(i));
            for (i=i+2; i < 7; i++) {
                if (cardValues.get(i -1).intValue() == cardValues.get(i).intValue()) {
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
     * @param cardValues card values
     * @return cardRank
     */
    protected ArrayList<Integer> hasThree(ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 2; i < 7; i++) {
            if (cardValues.get(i - 2).intValue() == cardValues.get(i).intValue()) {
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
     * @param cardValues card values
     * @return cardRank
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
     * @param cards cards
     * @return cardRank
     */
    protected ArrayList<Integer> hasFlush (ArrayList<Card> cards) {
        ArrayList<Integer> cardRank;
        ArrayList<Integer> spade = new ArrayList<>();
        ArrayList<Integer> heart = new ArrayList<>();
        ArrayList<Integer> diamond = new ArrayList<>();
        ArrayList<Integer> club = new ArrayList<>();
        for (Card c : cards) {
            EnumerationCard symbol = c.getSymbol();
            Integer value = c.getValue();
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
     * @param cardValues card values
     * @return cardRank
     */
    protected ArrayList<Integer> hasFullHouse (ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        // try to three of a kind
        for (i = 2; i < 7; i++) {
            if (cardValues.get(i - 2).intValue() == cardValues.get(i).intValue()) {
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
            if (cardValues.get(i - 1).intValue() == cardValues.get(i).intValue()) {
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
     * @param cardValues card values
     * @return cardRank
     */
    public ArrayList<Integer> hasFour(ArrayList<Integer> cardValues) {
        ArrayList<Integer> cardRank = (ArrayList<Integer>) cardValues.clone();
        int i;
        for (i = 3; i < 7; i++) {
            if (cardValues.get(i - 3).intValue() == cardValues.get(i).intValue()) {
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
     * @param cards card values
     * @return cardRank
     */
    protected ArrayList<Integer> hasStraightFlush (ArrayList<Card> cards) {
        ArrayList<Integer> cardRank;
        ArrayList<Integer> spade = new ArrayList<>();
        ArrayList<Integer> heart = new ArrayList<>();
        ArrayList<Integer> diamond = new ArrayList<>();
        ArrayList<Integer> club = new ArrayList<>();
        for (Card c : cards) {
            EnumerationCard symbol = c.getSymbol();
            Integer value = c.getValue();
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
     * @param cards cards
     * @return cardRank
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
