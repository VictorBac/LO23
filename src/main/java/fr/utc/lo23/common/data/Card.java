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
<<<<<<< Updated upstream
     * 2 ~ 14 represent 2 ~ 10 J Q K A. DONT CHANGE IT ANYMORE PLS.
     * For example Card card = new Card(14,'C');
=======
     * For example Card card = new Card(13,"SPADE");
>>>>>>> Stashed changes
     * @param value
     * @param symbol spade & heart & diamond & club
     * @throws CardFormatInvalidException
     */
    public Card(Integer value, EnumerationCard symbol) throws CardFormatInvalidException{
        if ( value > 14 || value < 2)
<<<<<<< Updated upstream
            throw new CardFormatInvalidException("Number must be an integer between 2 ~ 14");
=======
            throw new CardFormatInvalidException("Number must be an integer between 1 ~ 13");
>>>>>>> Stashed changes
        else{
            this.value = value;
            this.symbol = symbol;
            this.id = symbol + Integer.toString(value);
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
