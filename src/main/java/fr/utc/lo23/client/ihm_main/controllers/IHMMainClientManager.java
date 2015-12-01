package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.data.InterfaceDataFromIHMMain;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;

/**
 * Created by leclercvictor on 24/11/2015.
 */
public class IHMMainClientManager {

    private InterTable  interMainToTable;
    private InterData interMainToData;
    private InterfaceDataFromIHMMain interMainFromData;


    public IHMMainClientManager(InterfaceDataFromIHMMain interMfromData){
        interMfromData = interMainFromData;
        interMainToData = new InterData();
        interMainToTable = new InterTable();

    }




}
