package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jianghan on 01/11/2015.
 */
public class InterfaceFromIHMMainTest {
    public InterfaceFromIHMMain interfaceDataFromIHMMain = new InterfaceFromIHMMain(new DataManagerClient());


    @Test
    public void importFilesTest() {
//        interfaceDataFromIHMMain.importFiles("/Users/mac/Desktop/src/tmp");
        interfaceDataFromIHMMain.importFiles("./tmp/src");
    }
}