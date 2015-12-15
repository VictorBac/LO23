package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing the total amount in the pot
 * There can be several separated Pots during the same turn
 * Created by Haroldcb on 21/10/2015.
 */
public class Pot implements Serializable {
    /**
     * valeurPot : total amount in the pot
     * players : player who have bet in the pot
     */
    private int valeurPot;
    private UserLightList players;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * @param valeurPot
     * @param players
     */
    public Pot(int valeurPot, UserLightList players) {
        this.setValeurPot(valeurPot);
        this.setPlayers(players);
    }

    /**
     * Constructeur par d�faut
     */
    public Pot() {
        this.players = new UserLightList();
        this.valeurPot = 0;
    }

    /**
     * add player to the end of the pot
     * @param player : player to add
     */
    public void addPlayer(UserLight player){
        this.players.getListUserLights().add(player);
    }

    public void addPot(int value){
        valeurPot = value;
    }

/********************* Getters and Setters ***************************/

    public int getValeurPot() {
        return valeurPot;
    }

    public void setValeurPot(int valeurPot) {
        this.valeurPot = valeurPot;
    }

    public UserLightList getPlayers() {
        return players;
    }

    public void setPlayers(UserLightList players) {
        this.players = players;
    }
}

