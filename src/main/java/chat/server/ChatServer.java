package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;

import chat.server.ClientThread;

import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;

public class ChatServer {

    private ServerSocket server;
    private List<Socket> clients;

    public static void main(String args[]) {
        int PORT = 3333;

        new ChatServer(PORT);
    }

    public ChatServer(int port) {
        try {
            this.setupServer(port);
        } 
        catch (IOException ioe) {
            System.out.println("Failed to run server: " + ioe.getMessage());
        }
    }

    public ServerSocket getServer() {
        return this.server;
    }

    public void setupServer(int port) throws IOException {
        this.server = new ServerSocket(port);
        System.out.println("Server up at " + port);

        this.clients = new ArrayList<Socket>();
        
        while (true) {
            this.listenConnection();
        }
    }

    public void listenConnection() {
        try {
            this.listenConnectionSafe();
        }
        catch (IOException ioe) {
            System.out.println("Failed to connecy client: " + ioe.getMessage());
        }
    }

    public void setupClientListener(Socket client, int index) {
        new ClientThread(client, index) {
            
            @Override
            public void run() {
                try {

                    Scanner sc = new Scanner(this.client.getInputStream());
                    
                    while (sc.hasNextLine()) {
                        String message = sc.nextLine();
                        
                        System.out.println("[Client " + this.index + "] " + message);
                    }
                }
                catch (IOException ioe) {
                    System.out.println("Message lost");
                }
            }
        }.start();
    }

    public void listenConnectionSafe() throws IOException {
        Socket client = this.server.accept();

        this.setupClientListener(client, this.clients.size());
        this.clients.add(client);
        System.out.println("Client connected");
    }

}