package com.itcr.ce.airwar.server;

/**
 * Created by Sebas Mora on 28/03/2017.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer extends Thread {

    private static final int SERVERPORT = 8085;
    // while this is true the server will run
    private boolean running = false;
    // used to send messages
    private PrintWriter bufferSender;
    // callback used to notify new messages received
    private OnMessageReceived messageListener;
    private ServerSocket serverSocket;
    private Socket client;

    /**
     * Constructor of the class
     *
     * @param messageListener listens for the messages
     */
    TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }


    /**
     * Close the server
     */
    public void close() {

        running = false;

        if (bufferSender != null) {
            bufferSender.flush();
            bufferSender.close();
            bufferSender = null;
        }

        try {
            client.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("S: Done.");
        serverSocket = null;
        client = null;

    }

    /**
     * Method to send the messages from server to client
     *
     * @param message the message sent by the server
     */
    public void sendMessage(String message) {
        if (bufferSender != null && !bufferSender.checkError()) {
            bufferSender.println(message);
            bufferSender.flush();
        }
    }

    /**
     * Builds a new server connection
     */
    private void runServer() {
        running = true;

        try {
            System.out.println("S: Connecting...");

            //create a server socket. A server socket waits for requests to come in over the network.
            serverSocket = new ServerSocket(SERVERPORT);

            //create client socket... the method accept() listens for a connection to be made to this socket and accepts it.
            client = serverSocket.accept();

            System.out.println("S: Receiving...");

            try {

                //sends the message to the client
                bufferSender = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

                //read the message received from client
                //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                DataInputStream in = new DataInputStream(client.getInputStream());

                //in this while we wait to receive messages from client (it's an infinite loop)
                //this while it's like a listener for messages
                while (running) {

                    int message = 0;
                    try {
                        message = in.readInt();
                    } catch (IOException e) {
                        System.out.println("Error reading message: " + e.getMessage());
                    }

                    if (message != 0 && messageListener != null) {
                        messageListener.messageReceived(message);
                    }
                }

            } catch (Exception e) {
                System.out.println("S: Error");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        runServer();

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the Bridge class
    public interface OnMessageReceived {
        void messageReceived(int message);
    }

}