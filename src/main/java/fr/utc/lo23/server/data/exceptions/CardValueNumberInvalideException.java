package fr.utc.lo23.server.data.exceptions;

import java.util.ArrayList;

/**
 * Created by Jianghan on 15-12-1.
 */
public class CardValueNumberInvalideException extends Exception {
    public CardValueNumberInvalideException(ArrayList[] cardValue) {
        System.out.println("There is something wrong with the number of card value:" + cardValue);
    }
}