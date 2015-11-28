package fr.utc.lo23.common.data;

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
     * id: char[3] made up by value and symbol
     */
    private Integer value;
    private char symbol;
    private char[] id;

    /**
     * Constructor
     * @param value
     * @param symbol
     * @param id
     */
    public Card(Integer value, char symbol, char[] id) {
        this.value = value;
        this.symbol = symbol;
        this.id = id;
    }

    // getters

    public Integer getValue() {
        return value;
    }

    public char[] getId() {
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

    public void setId(char[] id) {
        this.id = id;
    }

}
