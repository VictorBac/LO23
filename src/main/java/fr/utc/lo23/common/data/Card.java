package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;
import java.io.Serializable;

/**
 * Class to represent a card
 * Created by Ying on 21/10/2015.
 */
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * value : value of the card
     * symbol: symbol of the card
     * id: string made up by value and symbol
     */
    private Integer value;
    private char symbol;
    private String id;

    /**
     * Constructor with value and symbol.
     * For example Card card = new Card(13,'C');
     * @param value
     * @param symbol spade & heart & diamond & club
     */
    public Card(Integer value, char symbol) throws CardFormatInvalidException{
        if ( value > 14 || value < 1)
            throw new CardFormatInvalidException("Number must be an integer between 1 ~ 13");
        if ( symbol != 'S' && symbol != 'H' && symbol != 'D' && symbol != 'C' )
            throw new CardFormatInvalidException("Symbol must be 'S' or 'H' or 'D' or 'C'");
        else{
            this.value = value;
            this.symbol = symbol;
            this.id = symbol + Integer.toString(value);
        }
    }

    // getters

    public Integer getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public char getSymbol() {
        return symbol;
    }

    //setters

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }

}
