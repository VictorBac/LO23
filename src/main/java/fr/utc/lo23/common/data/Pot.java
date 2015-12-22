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
     * @param valeurPot : value of the pot
     * @param players : players in the pot
     */
    public Pot(int valeurPot, UserLightList players) {
        this.setValeurPot(valeurPot);
        this.setPlayers(players);
    }

    /**
     * Default constructor
     */
    public Pot() {
        this.players = new UserLightList();
        this.valeurPot = 0;
    }

    public void addValue(int value){
        valeurPot += value;
    }

    /**
     * add player at the end of the pot
     * @param player : player to add
     */
    public void addPlayer(UserLight player){
        this.players.getListUserLights().add(player);
    }

/********************* Getters and Setters ***************************/

    /**
     * return the pot's value
     * @return : value
     */
    public int getValeurPot() {
        return valeurPot;
    }

    /**
     * set the pot's value
     * @param valeurPot : value tot set
     */
    public void setValeurPot(int valeurPot) {
        this.valeurPot = valeurPot;
    }

    /**
     * return list of players
     * @return : list of players
     */
    public UserLightList getPlayers() {
        return players;
    }

    /**
     * set list of players' value
     * @param players : value to set
     */
    public void setPlayers(UserLightList players) {
        this.players = players;
    }
}

