package chat.server;

import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.lang.Thread;
import java.io.PrintStream;

import chat.dto.AcceptConnectionMessageDTO;
import chat.dto.ChatMessageDTO;
import chat.dto.MessageDTO;
import chat.interpreter.MessageInterpreter;
import chat.interpreter.interpreterimpl.MessageInterpreterImpl;
import chat.server.Server;

/**
 * Represents the connection with a single client
 */
public class ClientConnection extends Thread {

    private Socket socket;
    private Server server;

    private Long userId;
    private String username;

    private MessageInterpreter interpreter;

    private static Long userIdSequence = 1L;

    /**
     * Constructs ClientConnection from socket and server
     * 
     * @param socket client socket
     * @param server server that could receive messages
     */
    public ClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.userId = userIdSequence++;

        this.interpreter = new MessageInterpreterImpl();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        acceptConnection();

        try {
            this.listenMessages();
        }
        catch (IOException ioe) {
            System.out.println("Messages lost!");
        }
    }

    public void acceptConnection() {
        try {
            MessageDTO acceptation = new AcceptConnectionMessageDTO(userId);
            send(acceptation);
        } catch (IOException e) {
            System.out.println("Failed to connect with user: " + username);
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

    public void send(MessageDTO dto) throws IOException {
        String messageEncoded = interpreter.encode(dto);
        send(messageEncoded);
    }

    /**
     * Send message to all clients connected
     * 
     * @param message message to be sent
     */
    private void sendToAll(String message) {
        this.server.send(message);
    }

    private void sendToAll(MessageDTO dto) {
        String messageEncoded = interpreter.encode(dto);
        sendToAll(messageEncoded);
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

            MessageDTO dto = new ChatMessageDTO(userId, username, message);

            sendToAll(dto);
        }
    }

}