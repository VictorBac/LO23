package fr.utc.lo23.client.ihm_table;

import java.security.Timestamp;
import java.util.ArrayList;

import fr.utc.lo23.common.data.*;

public interface ITableToDataListener {

	/*
	 * Fonction � appeler apr�s l'envoi de la table vers le serveur, et r�ception de celle-ci.
	 * Permet � IHM-Table d'afficher la table.
	 */
	public void showTable(Table table);

    /*
     * Fonction � appeler apr�s avoir re�u un nouveau message chat.
     * Permet � IHM-Table d'afficher le message
     */
    public void notifyNewChatMessage(MessageChat message);

    /*
     * Fonction � appeler apr�s l'arriv�e d'un nouveau utilisteur sur la partie
     * isPlayer (true) -> le user est un joueur
     * isPlayer(false) -> le user est un spectateur
     */
    public void notifyNewUser(UserLight user, boolean isPlayer);

    /*
     * Fonction � appeler apr�s le d�part d'un nouveau utilisteur sur la partie
     * isPlayer (true) -> le user est un joueur
     * isPlayer(false) -> le user est un spectateur
     */
    public void notifyUserLeft(UserLight user, boolean isPlayer);

    /*
    * Fonction � appeler apr�s r�ception de la confirmation du serveur pour lancer la partie
    * permet � IHM-Table de savoir que les joueurs vont devoir mettre leurs montants de d�part
    */
    public void notifyPreparationPhase();

    /*
     * Fonction � appeler apr�s le lancement d'un jeu
     * Permet � IHM-Table d'afficher la demande de quantit� d'argent de d�part � l'utilisateur
     */
    public void askMoneyAmount();

    /*
     * Fonction � appeler apr�s la r�ception du montant d'un utilisateur
     * Permet � IHM-Table d'afficher le montant d'un utilisateur
     */
    public void notifyMoneyAmountAnswer(UserLight player,Integer amount);

    /*
     * Fonction � appeler apr�s avoir re�u les montants de tous les joueurs
     * Permet � IHL-Table de demander � l'utilisteur s'il est pr�t et accepte les montants des autres joueurs
     */
    public void askReadyGame();

    /*
     * Fonction � appeler apr�s avoir re�u une r�ponse d'un joueur
     * Permet � IHM-Table d'afficher la r�ponse d'un joueur
     */
    public void notifyReadyGameAnswer(UserLight player, boolean hasValidated);

    /*
     * Fonction � appeler apr�s les ready de tous les joueurs.
     * Permet de pr�venir IHM-Table que la Game a �t� lanc�e.
     */
	public void notifyStartGame(Table table);

    /*
     * Fonction � appeler apr�s le d�but d'une manche.
     * Permet � IHM-Table de savoir que la manche est lanc�e.
     */
    public void notifyStartHand();

    /*
     * Fonction � appeler apr�s le d�but d'un tour.
     * Permet � IHM-Table de savoir que le tour a �t� lanc�.
     */
    public void notifyStartTour();

    /*
     * Fonction � appeler en deux temps:
     *  - une fois en d�but de manche, apr�s r�ception de nos propres cartes: la ArrayList sera alors de taille 1
     *  - une fois en fin de manche, apr�s r�ception des cartes des autres joueurs
     * Permet � IHM-Table d'afficher les cartes des joueurs.
     */
    public void notifyPlayersCards(ArrayList<PlayerHand> playerHands);

    /*
     * Fonction � appeler apr�s avoir re�u une demande d'action
     * Permet � IHM-Table de demander l'action au joueur
     */
    public void askAction(Action actionToFill, EnumerationAction[] listPossible);

    /*
     * Fonction � appeler apr�s avoir re�u une action faite par un joueur
     * Permet � iHM-Table d'afficher cette action
     */
    public void notifyAction(Action action);

    /*
     * Fonction � appeler apr�s que tous les joueurs aient fait leur action de ce tour
     * Permet � IHM-Table de savoir que le tour est termin� et qu'un prochain peut avoir lieu
     */
    public void notifyEndTour();

    /*
     * Fonction � appeler apr�s avoir re�u des cartes du milieu
     * permet � IHM-Table de les afficher
     */
    public void notifyCommonCards(ArrayList<Card> listCards);

    /*
     * Fonction � appeler apr�s la r�solution d'une manche
     * permet � IHM-Table d'afficher les nouvelles informations
     */
    public void notifyEndHand(ArrayList<Seat> seatPlayers);

    /*
     * Fonction � appeler apr�s la confirmation du serveur qu'un vote pour quitter la partie a �t� lanc�
     * Permet � IHM-Table d'afficher la demande de fin de jeu aupr�s des utilisateurs
     */
	public void askEndGameVote(Game game);

    /*
     * Fonction � appeler apr�s la confirmation du serveur que le jeu s'arr�te
     * Permet � IHM-Table d'arr�ter le jeu.
     */
	public void stopGame(Game game);

    /*
     * Fonction � appeler apr�s avoir re�u la r�ponse d'un utilisateur
     * Permet � IHM-TAble d'afficher la r�ponse d'un utilisateur au vote de fin de jeu
     */
    public void notifyPlayerVoteEndGameAnswer(UserLight player,boolean accept);

}
