package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Remy on 08/12/2015.
 * Class that represent a Server
 */
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    private String address;
    private Integer port;

    /**
     * Constructor
     * @param add String Ip address of the Server
     * @param pt Integer port of the server
     */
    public Server(String add, Integer pt){
        this.address = add;
        this.port = pt;
    }

    /**
     * Get the Ip Address
     * @return String Ip Address of the server
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the Ip Address
     * @param address String Ip Address of the server
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the port
     * @return Integer port of the server
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Set the port
     * @param port Integer port of the server
     */
    public void setPort(Integer port) {
        this.port = port;
    }
}
