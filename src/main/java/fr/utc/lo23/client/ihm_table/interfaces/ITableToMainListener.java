package fr.utc.lo23.client.ihm_table.interfaces;

import fr.utc.lo23.common.data.*;
import javafx.scene.layout.Pane;

public interface ITableToMainListener {

	/*
	 * Fonction à appeler lorsque l'utilisateur veut créer une table
	 * Permet à IHM-Table de prendre la main et d'afficher le formulaire de création de table
	 */
	public void showTableCreationForm(Pane root);

    /*
     * Fonction à appeler lorsque l'utilisateur veut rejoindre une table
     * Permet à IHM-Table de prendre la main et d'afficher la table
     */
    public void joinTable(Pane root, Table table);
}
