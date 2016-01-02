package fr.utc.lo23.client.data;

import org.junit.Test;

/**
 * test fro interface IHM MAIN
 * Created by Jianghan on 01/11/2015.
 */
public class InterfaceFromIHMMainTest {
    public InterfaceFromIHMMain interfaceDataFromIHMMain = new InterfaceFromIHMMain(new DataManagerClient());


    @Test
    public void importFilesTest() {
        interfaceDataFromIHMMain.importFiles("./tmp/src");
    }
}