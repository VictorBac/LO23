package fr.utc.lo23.server.data;

import com.sun.org.apache.bcel.internal.generic.NEW;
import fr.utc.lo23.common.data.Card;
import fr.utc.lo23.common.data.PlayerHand;

import java.util.ArrayList;

/**
 * Classe de calcul des combinaisons determiannt le gagnant d'un tour
 * Created by Haroldcb on 21/10/2015.
 */
public class CombinationCalculator {
    /**
     * @param listOfPlayer : liste des joueurs de la table et de leurs cartes,
     * @param cardsOnField : Cartes sur la table
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
}
