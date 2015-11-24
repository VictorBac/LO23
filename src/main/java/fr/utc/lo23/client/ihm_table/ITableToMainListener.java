package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.common.data.*;
import javafx.scene.layout.Pane;

public interface ITableToMainListener {

	/*
	 * Fonction � appeler lorsque l'utilisateur veut cr�er une table
	 * Permet � IHM-Table de prendre la main et d'afficher le formulaire de cr�ation de table
	 */
	public void showTableCreationForm(Pane root);

    /*
     * Fonction � appeler lorsque l'utilisateur veut rejoindre une table
     * Permet � IHM-Table de prendre la main et d'afficher la table
     */
    public void joinTable(Pane root, Table table);
}
