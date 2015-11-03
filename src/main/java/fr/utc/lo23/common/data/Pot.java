package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Classe représentant la somme totale misée dans un pot.
 * Il peut y avoir plusieurs pots séparés lors du même tour.
 * Created by Haroldcb on 21/10/2015.
 */
public class Pot {
    /**
     * valeurPot : valeur totale dans le pot
     * players : joueurs ayant misé dans le pot
     */
    private ArrayList<Integer> valeurPot;
    private UserLightList players;

    /**
     * Constructeur
     * @param valeurPot
     * @param players
     */
    public Pot(ArrayList<Integer> valeurPot, UserLightList players) {
        this.valeurPot = valeurPot;
        this.players = players;
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

