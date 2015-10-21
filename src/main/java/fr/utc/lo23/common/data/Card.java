package fr.utc.lo23.common.data;

/**
 * Classe représentant les cartes
 * Created by Haroldcb on 21/10/2015.
 */
public class Card {
    /**
     * value : valeur de la carte
     * symbol: symbole de la carte
     * id: char[3] composé de value et symbol
     */
    private String value;
    private char symbol;
    private char[] id;

    /**
     * Constructeur
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

    public void setValue(String value) {
        this.value = value;
    }

    public char getSymbol() {
        return symbol;
    }

    //setters

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public char[] getId() {
        return id;
    }

    public void setId(char[] id) {
        this.id = id;
    }
}
