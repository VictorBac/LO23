package fr.utc.lo23.common.data;

/**
 * Class to represent a card
 * Created by Haroldcb on 21/10/2015.
 */
public class Card {
    /**
     * value : value of the card
     * symbol: symbol of the card
     * id: char[3] compos� de value et symbol
     */
    private String value;
    private char symbol;
    private char[] id;

    /**
     * Constructor
     * @param value
     * @param symbol
     * @param id
     */
    public Card(String value, char symbol, char[] id) {
        this.value = value;
        this.symbol = symbol;
        this.id = id;
    }

    // getters

    public String getValue() {
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

    public void setValue(String value) {
        this.value = value;
    }

    public void setId(char[] id) {
        this.id = id;
    }

}
