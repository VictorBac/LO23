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
    private Userlight player;
    private int startAmount;
    private int currentAccount;

    /**
     * m�thode permettant d'ajouter un montant gagn� au montant actuel
     * @param amount : montant gagn�
     */
    public void winAmount(int amount){}

    /**
     * m�thode permettant de mettre � jour le montant actuel en fonction du montant pass� en param�tre
     * @param amount : monant gagn� ou perdu
     */
    public void updateCurrentAccount(int amount){}

    /**
     * m�thode permettant d'enlever un montant mis� au montant actuel
     * @param amount : montant mis�
     */
    public void spendAmount(int amount){}
}
