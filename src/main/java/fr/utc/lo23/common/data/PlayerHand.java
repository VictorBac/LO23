package fr.utc.lo23.common.data;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Calss representing the cards in a player's hand
 * Created by Haroldcb on 21/10/2015.
 */
public class PlayerHand implements Serializable {
    /**
     * listCardsHand : cards in player's hand
     * player : owner of the hand
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

    public ArrayList<Card> getListCardsHand() {
        return listCardsHand;
    }

    public void setListCardsHand(ArrayList<Card> listCardsHand) {
        this.listCardsHand = listCardsHand;
    }

    public UserLight getPlayer() {
        return player;
    }

    public void setPlayer(UserLight player) {
        this.player = player;
    }

    public Boolean isAllin() {
        return allin;
    }

    public void setAllin() {
        this.allin = true;
    }

    public Boolean isFold(){
        if(getListCardsHand().size()==0)
            return true;
        else
            return false;
    }

    public void setFold(){
        getListCardsHand().clear();
    }

    public Boolean isActive(){
        if(!isFold() && !isAllin())
            return true;
        else
            return false;
    }
}
