package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Classe représentant les cartes dans les mains d'un joueur
 * Created by Haroldcb on 21/10/2015.
 */
public class PlayerHand {
    /**
     * listCardsHand : cartes dans la main du joueur
     * player : joueur
     */
    private ArrayList<Card> listCardsHand;
    private UserLight player;

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
     * Constructeur par défaut
     */
    public PlayerHand() {
        this.listCardsHand = new ArrayList<Card>();
        this.player = new UserLight();
    }

    /**
     * Methode permettant d'ajouter une carte à un joueur
     * @param carte : carte à ajouter
     */
    public void addNewCard(Card carte){}

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
