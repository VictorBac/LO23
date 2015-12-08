package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by Rémy on 08/12/2015.
 */
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    private InetAddress address;
    private String port;

    public Server(InetAddress add, String pt){
        this.address = add;
        this.port = pt;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
