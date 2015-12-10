package fr.utc.lo23.client.ihm_table.interfaces;

import java.security.Timestamp;
import java.util.ArrayList;

import fr.utc.lo23.common.data.*;

public interface ITableToDataListener {

	/*
	 * Fonction à appeler après l'envoi de la table vers le serveur, et réception de celle-ci.
	 * Permet à IHM-Table d'afficher la table.
	 */
	public void showTable(Table table);

    /*
     * Fonction à appeler après avoir reçu un nouveau message chat.
     * Permet à IHM-Table d'afficher le message
     */
    public void notifyNewChatMessage(MessageChat message);

    /*
     * Fonction à appeler après l'arrivée d'un nouveau utilisteur sur la partie
     * isPlayer (true) -> le user est un joueur
     * isPlayer(false) -> le user est un spectateur
     */
    public void notifyNewUser(UserLight user, boolean isPlayer);

    /*
     * Fonction à appeler après le départ d'un nouveau utilisteur sur la partie
     * isPlayer (true) -> le user est un joueur
     * isPlayer(false) -> le user est un spectateur
     */
    public void notifyUserLeft(UserLight user, boolean isPlayer);

    /*
    * Fonction à appeler après réception de la confirmation du serveur pour lancer la partie
    * permet à IHM-Table de savoir que les joueurs vont devoir mettre leurs montants de départ
    */
    public void notifyPreparationPhase();

    /*
     * Fonction à appeler après le lancement d'un jeu
     * Permet à IHM-Table d'afficher la demande de quantité d'argent de départ à l'utilisateur
     */
    public void askMoneyAmount();

    /*
     * Fonction à appeler après la réception du montant d'un utilisateur
     * Permet à IHM-Table d'afficher le montant d'un utilisateur
     */
    public void notifyMoneyAmountAnswer(UserLight player,Integer amount);

    /*
     * Fonction à appeler après avoir reçu les montants de tous les joueurs
     * Permet à IHM-Table de demander à l'utilisteur s'il est prêt et accepte les montants des autres joueurs
     */
    public void askReadyGame();

    /*
     * Fonction à appeler après avoir reçu une réponse d'un joueur
     * Permet à IHM-Table d'afficher la réponse d'un joueur
     */
    public void notifyReadyGameAnswer(UserLight player, boolean hasValidated);

    /*
     * Fonction à appeler après les ready de tous les joueurs.
     * Permet de prévenir IHM-Table que la Game a été lancée.
     */
	public void notifyStartGame(Table table);

    /*
     * Fonction à appeler après le début d'une manche.
     * Permet à IHM-Table de savoir que la manche est lancée.
     */
    public void notifyStartHand();

    /*
     * Fonction à appeler après le début d'un tour.
     * Permet à IHM-Table de savoir que le tour a été lancé.
     */
    public void notifyStartTour();

    /*
     * Fonction à appeler en deux temps:
     *  - une fois en début de manche, après réception de nos propres cartes: la ArrayList sera alors de taille 1
     *  - une fois en fin de manche, après réception des cartes des autres joueurs
     * Permet à IHM-Table d'afficher les cartes des joueurs.
     */
    public void notifyPlayersCards(ArrayList<PlayerHand> playerHands);

    /*
     * Fonction à appeler après avoir reçu une demande d'action
     * Permet à IHM-Table de demander l'action au joueur
     */
    public void askAction(Action actionToFill, EnumerationAction[] listPossible);

    /*
     * Fonction à appeler après avoir reçu une action faite par un joueur
     * Permet à iHM-Table d'afficher cette action
     */
    public void notifyAction(Action action);

    /*
     * Fonction à appeler après que tous les joueurs aient fait leur action de ce tour
     * Permet à IHM-Table de savoir que le tour est terminé et qu'un prochain peut avoir lieu
     */
    public void notifyEndTour(Integer pot);

    /*
     * Fonction à appeler après avoir reçu des cartes du milieu
     * permet à IHM-Table de les afficher
     */
    public void notifyCommonCards(ArrayList<Card> listCards);

    /*
     * Fonction à appeler après la résolution d'une manche
     * permet à IHM-Table d'afficher les nouvelles informations
     */
    public void notifyEndHand(ArrayList<Seat> seatPlayers);

    /*
     * Fonction à appeler après la confirmation du serveur qu'un vote pour quitter la partie a été lancé
     * Permet à IHM-Table d'afficher la demande de fin de jeu auprès des utilisateurs
     */
	public void askEndGameVote(Game game);

    /*
     * Fonction à appeler après la confirmation du serveur que le jeu s'arrête
     * Permet à IHM-Table d'arrêter le jeu.
     */
	public void stopGame(Game game);

    /*
     * Fonction à appeler après avoir reçu la réponse d'un utilisateur
     * Permet à IHM-Table d'afficher la réponse d'un utilisateur au vote de fin de jeu
     */
    public void notifyPlayerVoteEndGameAnswer(UserLight player,boolean accept);

}
