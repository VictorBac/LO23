package fr.utc.lo23.common.data;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing the cards in a player's hand
 * Created by Haroldcb on 21/10/2015.
 */
public class PlayerHand implements Serializable {

    /**
     * listCardsHand : cards in player's hand
     * player : owner of the hand
     * allin : player is allin
     */
    private ArrayList<Card> listCardsHand;
    private UserLight player;
    private Boolean allin = false;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * @param listCardsHand
     * @param player
     */
    public PlayerHand(ArrayList<Card> listCardsHand, UserLight player) {
        this.listCardsHand = listCardsHand;
        this.player = player;
    }

    /**
     * Constructor
     * @param player
     */
    public PlayerHand(UserLight player) {
        this.listCardsHand = new ArrayList<Card>();
        this.player = player;
    }

    /**
     * Default constructor
     */
    public PlayerHand() {
        this.listCardsHand = new ArrayList<Card>();
        this.player = new UserLight();
    }

    /**
     * Method to add a card to a player
     * @param carte : card to add
     */
    public void addNewCard(Card carte){
        this.listCardsHand.add(carte);
    }

    /******************* Getters and Setters ************************/

    /**
     * get the list of cards
     * @return : list of cards
     */
    public ArrayList<Card> getListCardsHand() {
        return listCardsHand;
    }

    /**
     * affects value of the cards' list
     * @param listCardsHand : value to set
     */
    public void setListCardsHand(ArrayList<Card> listCardsHand) {
        this.listCardsHand = listCardsHand;
    }

    /**
     * get the player who has the cards
     * @return : player
     */
    public UserLight getPlayer() {
        return player;
    }

    /**
     * affects the player
     * @param player
     */
    public void setPlayer(UserLight player) {
        this.player = player;
    }

    /**
     * return the value of allin
     * @return : true if yes, false otherwise
     */
    public Boolean isAllin() {
        return allin;
    }

    /**
     * set the value of allin
     */
    public void setAllin() {
        this.allin = true;
    }

    /**
     * return if the player is fold
     * @return : true if yes, false otherwise
     */
    public Boolean isFold(){
        if(getListCardsHand().size()==0)
            return true;
        else
            return false;
    }

    /**
     * clears the cards list
     */
    public void setFold(){
        getListCardsHand().clear();
    }

    /**
     * return if the player is still playing in the hand
     * @return : true if yes, false otherwhise
     */
    public Boolean isActive(){
        if(!isFold() && !isAllin())
            return true;
        else
            return false;
    }
}
