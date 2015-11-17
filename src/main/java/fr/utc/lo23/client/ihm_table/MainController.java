package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainController {

	IHMTable table = new IHMTable();

	@FXML
	public Pane IAmYourPaneLuke;

	public MainController(){
		//constructor
	}
	
	@FXML
	private void launchTable(javafx.event.ActionEvent event) {
		table.getTableToMainListener().showTableCreationForm(IAmYourPaneLuke);
	}

	@FXML
	private void joinTable(javafx.event.ActionEvent event) {
		table.getTableToMainListener().joinTable(IAmYourPaneLuke,null);
	}
	
}
