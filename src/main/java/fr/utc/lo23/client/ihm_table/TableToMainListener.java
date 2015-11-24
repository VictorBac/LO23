package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.client.ihm_table.interfaces.ITableToMainListener;
import fr.utc.lo23.client.ihm_table.views.FormView;
import fr.utc.lo23.client.ihm_table.views.TableView;
import fr.utc.lo23.common.data.Table;
import javafx.scene.layout.Pane;

public class TableToMainListener implements ITableToMainListener {
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
	 * Fonction à appeler lorsque l'utilisateur veut créer une table
	 * Permet à IHM-Table de prendre la main et d'afficher le formulaire de création de table
	 */
    public void showTableCreationForm(Pane root){
        FormView formView = new FormView();
        formView.createForm(root,getIhmtable());
    }

    /*
     * Fonction à appeler lorsque l'utilisateur veut rejoindre une table
     * Permet à IHM-Table de prendre la main et d'afficher la table
     */
    public void joinTable(Pane root, Table table){
        TableView tableView = new TableView();
        tableView.createTable(root,table,getIhmtable());
    }
}
