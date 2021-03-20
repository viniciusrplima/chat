package chat.server;

import chat.server.ChatServer;

/**
 * Setup server and connect it to internet
 * 
 */
public class ServerMain {

    private static final int PORT = 3333;

    public static void main(String[] args) {
        ChatServer server = new ChatServer(PORT);

        server.start();
    }
}