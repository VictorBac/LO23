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
     * currentAccount : account actually possessed
     * statusPlayer : Connected, disconnected
     */
    private UserLight player;
    private int startAmount;
    private int currentAccount;
    private EnumerationStatusPlayer statusPlayer;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * @param player : player on the seat
     * @param startAmount : account at the begining of the game
     * @param currentAccount : current amount of money
     */
    public Seat(UserLight player, int startAmount, int currentAccount) {
        this.setPlayer(player);
        this.setStartAmount(startAmount);
        this.setCurrentAccount(currentAccount);
        this.statusPlayer = EnumerationStatusPlayer.CONNECTED;
    }

    /**
     * Constructor called when a player join the table
     * @param player : player on the seat
     */
    public Seat(UserLight player) {
        this.setPlayer(player);
        this.setStartAmount(0);
        this.setCurrentAccount(0);
        this.statusPlayer = EnumerationStatusPlayer.CONNECTED;
    }

    /**
     * default constructor
     */
    public Seat() {
        this.player = new UserLight();
        this.setStartAmount(0);
        this.setCurrentAccount(0);
        this.statusPlayer = EnumerationStatusPlayer.CONNECTED;
    }

    public void addCurrentMoney(Integer amount){
        currentAccount += amount;
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
     * method to soustract a bet amount to the actual amount
     * @param amount : amount to bet
     * @throws SeatException : if account will be negative
     */
    public void spendAmount(int amount) throws SeatException{
        if(this.currentAccount > amount)
            this.currentAccount -= amount;
        else
            throw new SeatException("Your account can't be negative!");

    }


    /**
     * return current account
     * @return : current account
     */
    public int getCurrentAccount() {
        return currentAccount;
    }

    /**
     * set current account
     * @param currentAccount : value to set
     */
    public void setCurrentAccount(int currentAccount) {
        this.currentAccount = currentAccount;
    }

    /**
     * return the player of the seat
     * @return : player
     */
    public UserLight getPlayer() {
        return player;
    }

    /**
     * set the player of the seat
     * @param player : value to set
     */
    public void setPlayer(UserLight player) {
        this.player = player;
    }

    /**
     * return start amount
     * @return : start amount
     */
    public int getStartAmount() {
        return startAmount;
    }

    /**
     * set start amount
     * @param startAmount : value to set
     */
    public void setStartAmount(int startAmount) {
        this.startAmount = startAmount;
    }

    /**
     * return the player's status
     * @return : status
     */
    public EnumerationStatusPlayer getStatusPlayer() {
        return statusPlayer;
    }

    /**
     * set player's status
     * @param statusPlayer : player'sstatus
     */
    public void setStatusPlayer(EnumerationStatusPlayer statusPlayer) {
        this.statusPlayer = statusPlayer;
    }
}
