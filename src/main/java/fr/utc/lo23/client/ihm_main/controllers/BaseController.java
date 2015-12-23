package fr.utc.lo23.client.ihm_main.controllers;

/**
 * The Class from which all of our Controllers inherit
 * It s mainly used to access our main Controller
 * Created by jbmartin on 01/12/2015.
 */
public abstract class BaseController {

    protected MainControllerClient mController;

    public void setMainController(MainControllerClient controllerMain) {
        this.mController = controllerMain;
    }


}
