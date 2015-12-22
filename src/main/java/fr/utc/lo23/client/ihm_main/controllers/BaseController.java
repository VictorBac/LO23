package fr.utc.lo23.client.ihm_main.controllers;

import javafx.fxml.Initializable;

/**
 * The Class from which all of our Controllers inherit
 * Created by jbmartin on 01/12/2015.
 */
public abstract class BaseController implements Initializable {

    protected MainControllerClient mController; // big boss !

    public void setMainController(MainControllerClient controllerMain) {
        this.mController = controllerMain;
    }


}
