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
    private Userlight player;
    private int startAmount;
    private int currentAccount;

    /**
     * méthode permettant d'ajouter un montant gagné au montant actuel
     * @param amount : montant gagné
     */
    public void winAmount(int amount){}

    /**
     * méthode permettant de mettre à jour le montant actuel en fonction du montant passé en paramètre
     * @param amount : monant gagné ou perdu
     */
    public void updateCurrentAccount(int amount){}

    /**
     * méthode permettant d'enlever un montant misé au montant actuel
     * @param amount : montant misé
     */
    public void spendAmount(int amount){}
}
