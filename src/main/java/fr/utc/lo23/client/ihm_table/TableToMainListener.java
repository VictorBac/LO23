package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.common.data.Table;
import javafx.scene.layout.Pane;

public class TableToMainListener implements ITableToMainListener{
	private IHMTable ihmtable;

    public TableToMainListener(IHMTable ihmtable){
        setIhmtable(ihmtable);
    }

    public IHMTable getIhmtable() {
        return ihmtable;
    }

    public void setIhmtable(IHMTable ihmtable) {
        this.ihmtable = ihmtable;
    }

    /*
	 * Fonction � appeler lorsque l'utilisateur veut cr�er une table
	 * Permet � IHM-Table de prendre la main et d'afficher le formulaire de cr�ation de table
	 */
	public void showTableCreationForm(Pane root){
		TableView tableView = new TableView();
		tableView.createTable(root);
	}

    /*
     * Fonction � appeler lorsque l'utilisateur veut rejoindre une table
     * Permet � IHM-Table de prendre la main et d'afficher la table
     */
    public void joinTable(Pane root, Table table){

    }
}
