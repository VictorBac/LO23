package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Groupe;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;

import java.util.UUID;

/**
 * Interface pr�sent�e par le module Data � IHM Table
 * Created by Haroldcb on 20/10/2015.
 */
public interface InterfaceDataFromIHMTable {
    /**
     * m�thode permettant de connecter un joueur
     * @param login : identifiant du joueur
     * @param password : mot de passe
     */
    public void logUser(String login, String password);

    /**
     * m�thode permettant d'ajouter un contact dans une cat�gorie
     * @param contact : contact � ajouter
     * @param category : groupe dans lequel l'ajouter
     */
    public void addContact(UserLight contact, Groupe category);

    /**
     * m�thode permettant de supprimer un contact de sa liste
     * @param contact
     */
    public void deleteContact(UserLight contact);

    /**
     * m�thode permettant de cr�er une liste de contact
     * @param name
     */
    public void createContactList(String name);

    /**
     * m�thode permettant de supprimer sa liste de contacts
     * @param name
     */
    public void deleteContactList(String name);

    /**
     * m�thode permettant de notifier le contact de son ajout
     * @param contact : contact � notifier
     */
    public void contactAddedNotificationReceived(UserLight contact);



    /**
     * m�thode permettant de cr�er une nouvelle table
     * @param table : table � cr�er
     */
    public void tableToCreate(Table table);

    /**
     * m�thode permettant de jouer une partie
     * @param idTable : table sur laquelle lancer la partie
     */
    public void playGame(UUID idTable);

    /**
     * m�thode permettant au cr�ateur de la table de demander l'arr�t de la partie
     */
    public void askStopGame();

    /**
     * ???
     */
    public void saveGame();

    /**
     * m�thode permettant de sauvegarder la partie
     * @param table : table � sauvegarder
     */
    public void saveLogGame(Table table);

    /**
     * m�thode permettant de r�cup�rer un utilisateur
     * @param user : utilisateur � r�cup�rer
     */
    public void getUser(UserLight user);

    /**
     * confirmation de reception d'une carte distribu�e
     */
    public void confirmationCardRecieved();

    /**
     * m�thode permettant de rejouer une action
     * @param action : action � rejouer
     */
    public void replayAction(Action action);

    /**
     * confirmation de reception d'une action effectu�e
     * @param action : action envoy�e
     */
    public void confirmationActionRecieved(Action action);

    /**
     * confirmation de fin de tour
     */
    public void confirmationEndTurn();
}
