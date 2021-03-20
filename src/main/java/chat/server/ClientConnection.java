package chat.server;

import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.lang.Thread;
import java.io.PrintStream;

import chat.server.Server;

/**
 * Represents the connection from client
 */
public class ClientConnection extends Thread {

    private Socket socket;
    private Server server;
    private String username;


    /**
     * Constructs ClientConnection from socket and server
     * 
     * @param socket client socket
     * @param server server that could receive messages
     */
    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }


    @Override
    public void run() {
        try {
            this.listenMessages();
        }
        catch (IOException ioe) {
            System.out.println("Messages lost!");
        }
    }

    
    /**
     * Send message to client associated with 
     * this socket
     * 
     * @param message message to be sent
     */
    public void send(String message) throws IOException {
        PrintStream clientStream = new PrintStream(this.socket.getOutputStream());
        clientStream.println(message);
    }


    /**
     * Wait for message that can be received from 
     * client
     * 
     * @throws IOException thrown when message lost
     */
    private void listenMessages() throws IOException {
        Scanner sc = new Scanner(this.socket.getInputStream());

        // The first message must be the username
        this.username = sc.nextLine();

        while (sc.hasNextLine()) {
            String message = sc.nextLine();
            String formatedMessage = this.formatMessage(message);

            this.server.send(formatedMessage);
        }
    }


    /**
     * Format a given message
     * 
     * @param message message to be formated
     * @return formated string message
     */
    private String formatMessage(String message) {
        return String.format("[%s] %s", this.username, message);
    }

    
}