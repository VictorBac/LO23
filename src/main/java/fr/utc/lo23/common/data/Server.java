package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Rémy on 08/12/2015.
 */
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    private String address;
    private Integer port;

    public Server(String add, Integer pt){
        this.address = add;
        this.port = pt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
