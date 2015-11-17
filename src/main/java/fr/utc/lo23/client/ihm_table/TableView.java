package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TableView{
	

	public void createTable(Pane root, IHMTable ihmTable) {
		try {
			FXMLLoader loader = new FXMLLoader();
			root.getChildren().setAll((Node) loader.load(getClass().getResource("tableView.fxml")));
			TableController tableController = (TableController) loader.getController();
			tableController.setInterface(ihmTable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
