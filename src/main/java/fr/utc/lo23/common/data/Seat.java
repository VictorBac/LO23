package fr.utc.lo23.common.data;

/**
 * Classe représentant les joueurs présents autour de la table et leur accompte de départ
 * ainsi que leur accompte actuel
 * Created by Haroldcb on 21/10/2015.
 */
public class Seat {
    /**
     * player : joueur
     * startAmount : montant de départ affecté au joueur
     * curentAccount : montant possédé à un instant t par la joueur
     */
    private UserLight player;
    private int startAmount;
    private int currentAccount;

    /**
     * Constructeur
     * @param player
     * @param startAmount
     * @param currentAccount
     */
    public Seat(UserLight player, int startAmount, int currentAccount) {
        this.player = player;
        this.startAmount = startAmount;
        this.currentAccount = currentAccount;
    }

    /**
     * Constructeur par défaut
     */
    public Seat() {
        this.player = new UserLight();
        this.startAmount = 0;
        this.currentAccount = 0;
    }

    /**
     * méthode permettant d'ajouter un montant gagné au montant actuel
     * @param amount : montant gagné
     */
    public void winAmount(int amount){
        this.currentAccount += amount;
    }

    /**
     * méthode permettant de mettre à jour le montant actuel en fonction du montant passé en paramètre
     * @param amount : monant gagné ou perdu
     */
    public void updateCurrentAccount(int amount){
        this.setCurrentAccount(amount);
    }

    /**
     * méthode permettant d'enlever un montant misé au montant actuel
     * @param amount : montant misé
     */
    public void spendAmount(int amount){
        if(this.currentAccount > amount)
            this.currentAccount -= amount;
        /*
        TODO
        else throw
        */
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
}
