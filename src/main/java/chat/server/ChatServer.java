package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;

import java.io.PrintStream;

import java.util.List;
import java.util.ArrayList;
import java.lang.Thread;

import java.util.Scanner;

import chat.server.Server;
import chat.server.ClientConnection;

public class ChatServer extends Thread implements Server {

    private ServerSocket server;
    private List<ClientConnection> clients;


    /**
     * Construct server from given port
     * 
     * @param port port to bind server
     */
    public ChatServer(int port) {
        try {
            this.setupServer(port);
        } 
        catch (IOException ioe) {
            System.out.println("Failed to run server: " + ioe.getMessage());
        }
    }


    /**
     * Initialize server valiables, and bind to port
     * 
     * @param port port to bind server
     * @throws IOException when error occurs at bind
     */
    private void setupServer(int port) throws IOException {
        this.server = new ServerSocket(port);
        System.out.println("Server up at " + port);

        this.clients = new ArrayList<ClientConnection>();
    }


    /**
     * Thread function to wait for clients
     */
    @Override
    public void run() {

        while (true) {
            this.listenConnection();
        }
    }


    /**
     * Returns SocketServer associated with server
     * 
     * @return the socket of this server
     */
    public ServerSocket getServer() {
        return this.server;
    }


    /**
     * Send message to all clientes currently connected
     * 
     * @param message message to be sent
     */
    @Override
    public void send(String message) {
        try {

            for(ClientConnection client: this.clients) {
                
                client.send(message);
            }

            System.out.println(message);
        }
        catch (IOException ioe) {
            System.out.println("Failed to sent message: " + ioe.getMessage());
        }
    }


    /**
     * Caught the error from waiting fo client
     */
    private void listenConnection() {
        try {
            this.listenConnectionSafe();
        }
        catch (IOException ioe) {
            System.out.println("Failed to connecy client: " + ioe.getMessage());
        }
    }


    /**
     * Wait for a client connection
     * 
     * @throws IOException thrown when error occurs to connect
     */
    private void listenConnectionSafe() throws IOException {
        Socket socket = this.server.accept();

        ClientConnection client = this.createClientConnection(socket);
        this.clients.add(client);
        System.out.println("Client connected");
    }

    /**
     * Creates ClientConnection from client socket
     *  
     * @param socket socket from client connection 
     * @return the client connection object
     */
    private ClientConnection createClientConnection(Socket socket) {
        ClientConnection connection = new ClientConnection(socket, this);
        connection.start();

        return connection;
    }


}