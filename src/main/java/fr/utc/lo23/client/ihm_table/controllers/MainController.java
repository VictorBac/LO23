package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MainController {

	IHMTable ihmTable;

    UserLight user;

	@FXML
	public Pane IAmYourPaneLuke;

	public MainController(){
		//constructor
		IHMMainClientManager maii = new IHMMainClientManager();
        ihmTable = maii.getManagerTable();
        User us = new User();
        user = new UserLight("pseudo1");
        us.setCore(user);

        maii.getManagerData().setUserLocal(us);
        System.out.println(maii.getManagerData().getUserLocal());
        System.out.println(ihmTable.getDataInterface().getUser());

	}
	
	@FXML
	private void launchTable(javafx.event.ActionEvent event) {
		ihmTable.getTableToMainListener().showTableCreationForm(IAmYourPaneLuke);
	}

	@FXML
	private void joinTable(javafx.event.ActionEvent event) throws ExistingUserException {
		Table ta = new Table("", null, true,true,5,2,true,100,30);

		ta.getListPlayers().addUser(user);
		ta.getListPlayers().addUser(new UserLight("pseudo2"));
		ta.getListPlayers().addUser(new UserLight("pseudo3"));
		ta.getListPlayers().addUser(new UserLight("pseudo4"));
		ta.getListPlayers().addUser(new UserLight("pseudo5"));


		ihmTable.getTableToMainListener().joinTable(IAmYourPaneLuke,
				ta);
    }
	
}
