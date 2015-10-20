package fr.utc.lo23.client.ihm_table;

import java.security.Timestamp;
import fr.utc.lo23.client.data.*;

public interface ITableToDataListener {

	public void showTable(Table table);
	public void addChatMessage(Userlight player, String message, Timestamp time);
	public void launchGame(Table table);
	public void launchManche();
	public void launchTour();
	public void dealCards(Game game, DealType deal);
	public void showStopVote(Game game);
	public void stopGame(Game game);
	// + liste de listeners
	// voir avec data quels observables il doit mettre en place !!
}
