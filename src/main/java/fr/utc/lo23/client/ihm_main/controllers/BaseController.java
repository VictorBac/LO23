package fr.utc.lo23.client.ihm_main.controllers;

/**
 * The Class from which all of our Controllers inherit
 * It s mainly used to access our main Controller
 * Created by jbmartin on 01/12/2015.
 */
public abstract class BaseController {

    /**
     * Instance of the MainControllerClient which manages Controllers and the IHMMainClientManager
     */
    protected MainControllerClient mController;

    /**
     * Setter of the instance of the MainControllerClient
     * @param controllerMain instance of the MainControllerClient
     */
    public void setMainController(MainControllerClient controllerMain) {
        this.mController = controllerMain;
    }


}
