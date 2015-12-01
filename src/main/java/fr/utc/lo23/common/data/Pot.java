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
    private ArrayList<Integer> valeurPot;
    private UserLightList players;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * @param valeurPot
     * @param players
     */
    public Pot(ArrayList<Integer> valeurPot, UserLightList players) {
        this.setValeurPot(valeurPot);
        this.setPlayers(players);
    }

    /**
     * Constructeur par défaut
     */
    public Pot() {
        this.players = new UserLightList();
        this.valeurPot = new ArrayList<Integer>();
    }

    /**
     * add player to the end of the pot
     * @param player : player to add
     */
    public void addPlayer(UserLight player){
        this.players.getListUserLights().add(player);
    }

    //TODO delete
    public int add(int a, int b){
        return a + b;
    }

/********************* Getters and Setters ***************************/

    public ArrayList<Integer> getValeurPot() {
        return valeurPot;
    }

    public void setValeurPot(ArrayList<Integer> valeurPot) {
        this.valeurPot = valeurPot;
    }

    public UserLightList getPlayers() {
        return players;
    }

    public void setPlayers(UserLightList players) {
        this.players = players;
    }
}

