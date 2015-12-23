package fr.utc.lo23.server.data.exceptions;

import fr.utc.lo23.common.data.Card;

import java.util.ArrayList;

/**
 * Created by Jianghan on 15-12-1.
 */
public class CardsNumberException extends Exception {
    public CardsNumberException(ArrayList<Card> cards) {
        System.out.println("There is something wrong with cards number:" + cards);
    }
}


