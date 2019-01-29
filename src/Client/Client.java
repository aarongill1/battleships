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

    private static boolean isInitializeP2(String message, DatagramPacket packet){
          if (message.startsWith("\\sep:")){
              return true;
          } else
          return false;
    }

    private static boolean isInitializeP1(String message, DatagramPacket packet){
        if (message.startsWith("\\set:")){
            return true;
        } else
            return false;
    }

    private static boolean isHit(String message, DatagramPacket packet){
          if (message.startsWith("\\hit:")){
              return true;
          } else
            return false;
    }

    private static boolean isMiss(String message, DatagramPacket packet){
          if (message.startsWith("\\miss:")){
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

    public void p1sendShip(String message){
        try {
                System.out.println("msg step1: " + message);
                String coordinates = "\\set: ";
                System.out.println("msg step 2: " + coordinates);
                coordinates += message;
                System.out.println("msg step 3: " + coordinates);
                coordinates += "\\e";  // \\e denotes the end of the message
                System.out.println("msg step 4: " + coordinates);
                byte[] data = coordinates.getBytes();
                //We have to pass in the source, source length, address to send to and the client's port
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                System.out.println("Sent ship update message to: " + address.getHostAddress() + " - " + port);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void p2sendShip(String message){
        try {
            //prevents blank messages
            System.out.println("msg step1: " + message);
            String coordinates = "\\sep: ";
            System.out.println("msg step 2: " + coordinates);
            coordinates += message;
            System.out.println("msg step 3: " + coordinates);
            coordinates += "\\e";  // \\e denotes the end of the message
            System.out.println("msg step 4: " + coordinates);
            byte[] data = coordinates.getBytes();
            //We have to pass in the source, source length, address to send to and the client's port
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            System.out.println("Sent ship update message to: " + address.getHostAddress() + " - " + port);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendHit(String message){
        try {
                String coordinates = "\\hit: ";
                coordinates += message;
                coordinates += "\\e";  // \\e denotes the end of the message
                byte[] data = message.getBytes();
                //We have to pass in the source, source length, address to send to and the client's port
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                System.out.println("Sent message to: " + address.getHostAddress() + " - " + port);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendMiss(String message){
        try {
                String coordinates = "\\miss: ";
                coordinates += message;
                coordinates += "\\e";  // \\e denotes the end of the message
                byte[] data = message.getBytes();
                //We have to pass in the source, source length, address to send to and the client's port
                DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                socket.send(packet);
                System.out.println("Sent message to: " + address.getHostAddress() + " - " + port);
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
                        //This will need to be wrapped in a loop for turn based gameplay
                        if(isInitializeP1(message, packet)) {
                            Game.p1UpdateShips(message);
                            System.out.println("Update1 is functional");
                        } else if(isInitializeP2(message, packet)){
                            Game.p2UpdateShips(message);
                            System.out.println("Update2 is functional");
                        }
//                        } else if(isHit(message, packet)) {
//
//                        } else if(isMiss(message, packet)) {
//
//                        }
                        //
                        if(!isCommand(message, packet) && !isInitializeP1(message, packet) && !isInitializeP2(message, packet)) {
//                            ClientsWindow.printToConsole(message);
                            Game.p1printToConsole(message);
                            Game.p2printToConsole(message);
                        }

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

