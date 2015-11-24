package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.common.data.Table;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TableView{

	public void createTable(Pane root, Table table, IHMTable ihmTable) {
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("TableView.fxml"));
			root.getChildren().setAll((Node) loader.load());
			TableController tableController = (TableController) loader.getController();
			tableController.setInterface(ihmTable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
