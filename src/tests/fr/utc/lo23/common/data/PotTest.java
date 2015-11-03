package fr.utc.lo23.common.data;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Haroldcb on 03/11/2015.
 */
public class PotTest {

    int a = 2;
    int b = 3;

    @Test
    public void testAdd() throws Exception {


        int res = new Pot().add(a, b);

        int resOptimal = a + b;

        assertEquals(resOptimal,res);
    }
}