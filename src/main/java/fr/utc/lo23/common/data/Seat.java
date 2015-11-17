package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.SeatException;

import java.io.Serializable;

/**
 * Class representing a player on the table and his start and actual account
 * Created by Haroldcb on 21/10/2015.
 */
public class Seat implements Serializable {
    /**
     * player : player
     * startAmount : account at the beginning of the game
     * curentAccount : account actually possessed
     */
    private UserLight player;
    private int startAmount;
    private int currentAccount;
    //TODO private EnumerationStatusPlayer statusPlayer;

    /**
     * Constructeur
     * @param player
     * @param startAmount
     * @param currentAccount
     */
    public Seat(UserLight player, int startAmount, int currentAccount) {
        this.setPlayer(player);
        this.setStartAmount(startAmount);
        this.setCurrentAccount(currentAccount);
        //TODO this.statusPlayer = ?;
    }

    /**
     * Constructeur par défaut
     */
    public Seat() {
        this.player = new UserLight();
        this.setStartAmount(0);
        this.setCurrentAccount(0);
        //TODO this.statusPlayer = ?;
    }

    /**
     * method to add a won amount to the actual
     * @param amount : won amount
     */
    public void winAmount(int amount){
        this.currentAccount += amount;
    }

    /**
     * method to set the account with a start amount at the beginning of the game
     * @param amount : amount to start with
     */
    public void updateCurrentAccount(int amount){
        this.setCurrentAccount(amount);
    }

    /**
     * méthode permettant d'enlever un montant misé au montant actuel
     * @param amount : amount to bet
     */
    public void spendAmount(int amount) throws SeatException{
        if(this.currentAccount > amount)
            this.currentAccount -= amount;
        else
            throw new SeatException("You're account can't be negative!");

    }


    public int getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(int currentAccount) {
        this.currentAccount = currentAccount;
    }

    public UserLight getPlayer() {
        return player;
    }

    public void setPlayer(UserLight player) {
        this.player = player;
    }

    public int getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
    }

    /* TODO
    public EnumerationStatusPlayer getStatusPlayer() {
        return statusPlayer;
    }

    public void setStatusPlayer(EnumerationStatusPlayer statusPlayer) {
        this.statusPlayer = statusPlayer;
    }
    */
}
