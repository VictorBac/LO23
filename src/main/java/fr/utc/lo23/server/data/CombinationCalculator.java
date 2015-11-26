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

    public Integer hasHighCard(ArrayList<Integer> cardValues) throws Exception {
        return cardValues.get(0);
    }
}
