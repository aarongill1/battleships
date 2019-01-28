package Client;

import Main.Game;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static DatagramSocket socket;

    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    private Socket boardSocket;

    private InetAddress address;
    private String name;
    private int port;
    private static boolean running;

//    public Client(String name, String address, int port){
      public Client(String address, int port){


        try {
            this.address = InetAddress.getByName(address);
            this.port = port;
            this.name = "";
            socket = new DatagramSocket();
            running = true;
            listen();
//            send("\\con: " + name);
            send("\\con: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean isCommand(String message, DatagramPacket packet){
        if (message.startsWith("\\con:")){
            //Run connection code
            return true;
        }
        return false;
    }

    private static boolean isInitialize(String message, DatagramPacket packet){
          if (message.startsWith("\\set: ")){
              return true;
          } else
          return false;
    }

    private static boolean isHit(String message, DatagramPacket packet){
          if (message.startsWith("\\hit: ")){
              return true;
          } else
            return false;
    }

    private static boolean isMiss(String message, DatagramPacket packet){
          if (message.startsWith("\\miss: ")){
              return true;
          } else
              return false;
    }

    public void send(String message){
        try {
            if(message != "") {
                message += "\\e";  // \\e denotes the end of the message
                byte[] data = message.getBytes();
                //We have to pass in the source, source length, address to send to and the client's port
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                System.out.println("Sent message to: " + address.getHostAddress() + " - " + port);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendShip(String message){
        try {
            //prevents blank messages
            if(message != "") {
                String coordinates = "\\set: ";
                coordinates += message;
                coordinates += "\\e";  // \\e denotes the end of the message
                byte[] data = message.getBytes();
                //We have to pass in the source, source length, address to send to and the client's port
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                System.out.println("Sent message to: " + address.getHostAddress() + " - " + port);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendHit(String message){
        try {
            if(message != "") {
                String coordinates = "\\hit: ";
                coordinates += message;
                coordinates += "\\e";  // \\e denotes the end of the message
                byte[] data = message.getBytes();
                //We have to pass in the source, source length, address to send to and the client's port
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                System.out.println("Sent message to: " + address.getHostAddress() + " - " + port);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendMiss(String message){
        try {
            if(message != "") {
                String coordinates = "\\miss: ";
                coordinates += message;
                coordinates += "\\e";  // \\e denotes the end of the message
                byte[] data = message.getBytes();
                //We have to pass in the source, source length, address to send to and the client's port
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                System.out.println("Sent message to: " + address.getHostAddress() + " - " + port);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private static void listen(){
        Thread listenThread = new Thread("ChatProgram Listener"){
            @Override
            public void run() {
                try {
                    //We put the socket.receive() method in its own thread because otherwise it would cause the server to freeze.
                    //This way the server can continue running while the socket listens in for incoming data
                    while (running) {
                        // By using packet we get access to many more methods we can use e.g. getAddress(), getPort() etc
                        // With a packet we can trace where the data came from
                        byte[] data = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(data, data.length); // Packet stores data to the byte array
                        socket.receive(packet);  // Socket receives data and stores it to packet
                        String message = new String(data);
                        message = message.substring(0, message.indexOf("\\e")); // \\e denotes the end of the message
                        //  MANAGE MESSAGE
                        if(!isCommand(message, packet)) {
//                            ClientsWindow.printToConsole(message);
                            Game.p1printToConsole(message);
                            Game.p2printToConsole(message);
                        }

                        //This will need to be wrapped in a loop for turn based gameplay
                        if(isInitialize(message, packet)){

                        } else if(isHit(message, packet)) {

                        } else if(isMiss(message, packet)) {

                        }
                        //
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }; listenThread.start();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
          this.name = name;
    }

    public void disconnect() {
          socket.disconnect();
    }
}

