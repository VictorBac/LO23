package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.CardFormatInvalidException;
import java.io.Serializable;

/**
 * Created by Ying on 21/10/2015.
 *
 * Class to represent a card
 */
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * value : value of the card
     * symbol: symbol of the card
     * id: string made up by value and symbol
     */
    private Integer value;
    private String id;
    private EnumerationCard symbol;

    /**
     * char value of 4 types of card
     * 'S' : SPADE
     * 'H' : HEART
     * 'D' : DIAMOND
     * 'C' : CLUB
     */
    private final char spade = 'S';
    private final char heart = 'H';
    private final char diamond = 'D';
    private final char club = 'C';

    /**
     * Constructor with value and symbol.
     * 2 ~ 14 represent 2 ~ 10 J Q K A. DONT CHANGE IT ANYMORE PLS.
     * For example Card card = new Card(14,'C');
     * @param value
     * @param symbol spade & heart & diamond & club
     * @throws CardFormatInvalidException
     */
    public Card(Integer value, EnumerationCard symbol) throws CardFormatInvalidException{
        if ( value > 14 || value < 2)
            throw new CardFormatInvalidException("Number must be an integer between 2 ~ 14");
        else{
            this.value = value;
            this.symbol = symbol;
            this.id = symbol + Integer.toString(value);//concatenation between the symbol and the value
        }
    }

    /**
     * Method to change the type of enumeration to the type of char
     * @param symbolEnum the original variable to change
     * @return the changed variable of char type
     * @throws CardFormatInvalidException
     */
    public char enumSymbolToCharSymbol(EnumerationCard symbolEnum) throws CardFormatInvalidException{
        char symbolChar;
        if (symbolEnum.equals(EnumerationCard.SPADE))
            symbolChar = spade;
        else if (symbolEnum.equals(EnumerationCard.HEART))
            symbolChar = heart;
        else if (symbolEnum.equals(EnumerationCard.DIAMOND))
            symbolChar = diamond;
        else if (symbolEnum.equals(EnumerationCard.CLUB))
            symbolChar = club;
        else throw new CardFormatInvalidException("Convert error!");
        return symbolChar;
    }

    @Override
    public String toString () {
        String goodSymbol;
        switch (this.symbol) {
            case SPADE: goodSymbol = "♠"; break;
            case HEART: goodSymbol = "♥"; break;
            case CLUB: goodSymbol = "♣"; break;
            case DIAMOND: goodSymbol = "♦"; break;
            default: goodSymbol = "?";
        }
        switch (this.value) {
            case 14: goodSymbol = goodSymbol + "A"; break;
            case 13: goodSymbol = goodSymbol + "K"; break;
            case 12: goodSymbol = goodSymbol + "Q"; break;
            case 11: goodSymbol = goodSymbol + "J"; break;
            case 10: goodSymbol = goodSymbol + "T"; break;
            default: goodSymbol = goodSymbol+ value.toString();
        }
        return goodSymbol;
    }


    /**
     * Method used to print a Card with the value and the symbol
     */
    public void printCard () {
//        String ANSI_RESET = (char)27 + "[0m ";
//        String ANSI_RED = (char)27 + "[31m ";
//        String ANSI_CYAN = (char)27 + "[36m ";
        String goodSymbol;
        switch (this.symbol) {
            case SPADE: goodSymbol = "♠"; break;
            case HEART: goodSymbol = "♥"; break;
            case CLUB: goodSymbol = "♣"; break;
            case DIAMOND: goodSymbol = "♦"; break;
            default: goodSymbol = "?";
        }
        switch (this.value) {
            case 14: goodSymbol = goodSymbol + "A"; break;
            case 13: goodSymbol = goodSymbol + "K"; break;
            case 12: goodSymbol = goodSymbol + "Q"; break;
            case 11: goodSymbol = goodSymbol + "J"; break;
            case 10: goodSymbol = goodSymbol + "T"; break;
            default: goodSymbol = goodSymbol+ value.toString();
        }
//        System.out.print(ANSI_CYAN + goodSymbol + ANSI_RESET);
        System.out.print(goodSymbol);
    }



    /*********************Getters & Setters*********************/

    /**
     * Getter to return the value of this card
     * @return an integer represent value
     */
    public Integer getValue() { return value; }

    /**
     * Getter to return the symbol of this card
     * @return an enumerationCard represent symbol
     */
    public EnumerationCard getSymbol() { return symbol; }

    /**
     * Getter to return the ID of this card
     * @return a string represent ID
     */
    public String getId() { return id; }

    /**
     * Setter to modify the symbol of this card
     * @param symbol
     */
    public void setSymbol(EnumerationCard symbol) { this.symbol = symbol; }

    /**
     * Setter to modify the value of this type
     * @param value
     */
    public void setValue(Integer value) { this.value = value; }

    /**
     * Setter to set the id of this card
     * @param id
     */
    public void setId(String id) { this.id = id; }

}
