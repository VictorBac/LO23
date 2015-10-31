package fr.utc.lo23.common.data;

/**
 * Classe repr�sentant les joueurs pr�sents autour de la table et leur accompte de d�part
 * ainsi que leur accompte actuel
 * Created by Haroldcb on 21/10/2015.
 */
public class Seat {
    /**
     * player : joueur
     * startAmount : montant de d�part affect� au joueur
     * curentAccount : montant poss�d� � un instant t par la joueur
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
     * Constructeur par d�faut
     */
    public Seat() {
        this.player = new UserLight();
        this.startAmount = 0;
        this.currentAccount = 0;
    }

    /**
     * m�thode permettant d'ajouter un montant gagn� au montant actuel
     * @param amount : montant gagn�
     */
    public void winAmount(int amount){
        this.currentAccount += amount;
    }

    /**
     * m�thode permettant de mettre � jour le montant actuel en fonction du montant pass� en param�tre
     * @param amount : monant gagn� ou perdu
     */
    public void updateCurrentAccount(int amount){
        this.setCurrentAccount(amount);
    }

    /**
     * m�thode permettant d'enlever un montant mis� au montant actuel
     * @param amount : montant mis�
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
