package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Groupe;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;

import java.util.UUID;

/**
 * Interface présentée par le module Data à IHM Table
 * Created by Haroldcb on 20/10/2015.
 */
public interface InterfaceDataFromIHMTable {
    /**
     * méthode permettant de connecter un joueur
     * @param login : identifiant du joueur
     * @param password : mot de passe
     */
    public void logUser(String login, String password);

    /**
     * méthode permettant d'ajouter un contact dans une catégorie
     * @param contact : contact à ajouter
     * @param category : groupe dans lequel l'ajouter
     */
    public void addContact(UserLight contact, Groupe category);

    /**
     * méthode permettant de supprimer un contact de sa liste
     * @param contact
     */
    public void deleteContact(UserLight contact);

    /**
     * méthode permettant de créer une liste de contact
     * @param name
     */
    public void createContactList(String name);

    /**
     * méthode permettant de supprimer sa liste de contacts
     * @param name
     */
    public void deleteContactList(String name);

    /**
     * méthode permettant de notifier le contact de son ajout
     * @param contact : contact à notifier
     */
    public void contactAddedNotificationReceived(UserLight contact);



    /**
     * méthode permettant de créer une nouvelle table
     * @param table : table à créer
     */
    public void tableToCreate(Table table);

    /**
     * méthode permettant de jouer une partie
     * @param idTable : table sur laquelle lancer la partie
     */
    public void playGame(UUID idTable);

    /**
     * méthode permettant au créateur de la table de demander l'arrêt de la partie
     */
    public void askStopGame();

    /**
     * ???
     */
    public void saveGame();

    /**
     * méthode permettant de sauvegarder la partie
     * @param table : table à sauvegarder
     */
    public void saveLogGame(Table table);

    /**
     * méthode permettant de récupérer un utilisateur
     * @param user : utilisateur à récupérer
     */
    public void getUser(UserLight user);

    /**
     * confirmation de reception d'une carte distribuée
     */
    public void confirmationCardRecieved();

    /**
     * méthode permettant de rejouer une action
     * @param action : action à rejouer
     */
    public void replayAction(Action action);

    /**
     * confirmation de reception d'une action effectuée
     * @param action : action envoyée
     */
    public void confirmationActionRecieved(Action action);

    /**
     * confirmation de fin de tour
     */
    public void confirmationEndTurn();
}
