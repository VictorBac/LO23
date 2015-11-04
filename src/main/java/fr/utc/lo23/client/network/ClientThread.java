/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utc.lo23.client.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 *
 * @author Jean-CômeInterfaceData
 */
public class ClientThread {
    
    private BufferedReader br;
    private PrintWriter pw;
    private boolean running;
    private InterfaceDataFromCom dataInt;
    
    public ClientThread() {
        
    }
    
    public void stopClient(){
    //Stop this shit !
    };
    
    public void run(){
        
    };
}
