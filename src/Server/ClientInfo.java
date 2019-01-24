package Server;

import java.net.InetAddress;

public class ClientInfo {

    private InetAddress address;
    private int port;
    private int id;
    private String name;

    public ClientInfo(String name, int id, InetAddress address, int port){
        this.name = name;
        this.id = id;
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
