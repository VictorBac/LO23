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
    //private char symbol;
    private String id;
    private EnumerationCard symbol;
    private final char spade = 'S';
    private final char heart = 'H';
    private final char diamond = 'D';
    private final char club = 'C';

    /**
     * Constructor with value and symbol.
     * For example Card card = new Card(13,'C');
     * @param value
     * @param symbol spade & heart & diamond & club
     */
    public Card(Integer value, EnumerationCard symbol) throws CardFormatInvalidException{
        if ( value > 13 || value < 1)
            throw new CardFormatInvalidException("Number must be an integer between 1 ~ 13");
        else{
            this.value = value;
            this.symbol = symbol;
            this.id = symbol + Integer.toString(value);
        }
    }

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
    // getters

    public Integer getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public EnumerationCard getSymbol() {
        return symbol;
    }

    //setters

    public void setSymbol(EnumerationCard symbol) {
        this.symbol = symbol;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }

}
