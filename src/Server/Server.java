package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

    private static DatagramSocket socket;
    private static boolean running;
    private static int clientId;
    private static ArrayList<ClientInfo> clients = new ArrayList<ClientInfo>();

    // Starts server, create and initialise resources required
    public static void start(int port){

        try {
            socket = new DatagramSocket(port);
            running = true;
            listen();
            System.out.println("Server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send a message to every connected client
    private static void broadcast(String message){

        for(ClientInfo info : clients){
            send(message, info.getAddress(), info.getPort());
        }
    }


    // Send messages to individual clients
    private static void send(String message, InetAddress address, int port){

        try {
            message += "\\e";  // \\e denotes the end of the message
            byte[] data = message.getBytes();
            //We have to pass in the source, source length, address to send to and the client's port
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            System.out.println("Sent message to: " + address.getHostAddress()+ " - " + port);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    // Will contain a thread and will be listening the entire time the server is running, waiting for messages to arrive
    // and taking relevant actions
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
                            broadcast(message);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }; listenThread.start();

    }

    //SERVER COMMAND LIST
    // \\con:[name]  => connects client to server
    // \\dis:[id]  => disconnects client from server


    private static boolean isCommand(String message, DatagramPacket packet){
        if (message.startsWith("\\con:")){
            //Run connection code
            String name = message.substring(message.indexOf(":")+1); // Take what's after the colon
            // Using ClientId++ will prevent duplicate IDs
            clients.add(new ClientInfo(name, clientId++, packet.getAddress(), packet.getPort()));
//            broadcast("Player " + name + " connected!");
            broadcast("Player connected!");
            return true;

        }
        return false;
    }

    // Stops the server without closing the program
    public static void stop(){
        running = false;
    }

}