package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe repr�sentant les cartes dans les mains d'un joueur
 * Created by Haroldcb on 21/10/2015.
 */
public class PlayerHand implements Serializable {
    /**
     * listCardsHand : cartes dans la main du joueur
     * player : joueur
     */
    private ArrayList<Card> listCardsHand;
    private UserLight player;
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur
     * @param listCardsHand
     * @param player
     */
    public PlayerHand(ArrayList<Card> listCardsHand, UserLight player) {
        this.listCardsHand = listCardsHand;
        this.player = player;
    }

    /**
     * Constructeur par d�faut
     */
    public PlayerHand() {
        this.listCardsHand = new ArrayList<Card>();
        this.player = new UserLight();
    }

    /**
     * Methode permettant d'ajouter une carte � un joueur
     * @param carte : carte � ajouter
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
}
