package chat.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.lang.Thread;
import java.io.PrintStream;

import chat.dto.MessageDTO;

/**
 * Abstract class for Client
 * Implements some basic functionalities 
 * that will be used by all client types
 */
public abstract class AbstractClient extends Thread implements Client {

    private Socket socket;
    private String address;
    private int port;


    /**
     * Connect to server from ip address and port
     * 
     * @param address address to server
     * @param port port to be connected
     */
    public void connectToServer(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.address = address;
            this.port = port;
        }
        catch (IOException ioe) {
            System.out.println("Failed to connect to server!");
        }
    }


    public Socket getSocket() {
        return this.socket;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }


    @Override
    public void send(String message) {
        try {
            PrintStream clientStream = new PrintStream(this.socket.getOutputStream());
            clientStream.println(message);
        }
        catch (IOException ioe) {
            System.out.println("Failed to send message!");
        }
    }


    @Override
    public void run() {
        try {
            this.listenMessages();
        }
        catch (IOException ioe) {
            System.out.println("Failed to listen messages!");
        }
    }


    /**
     * Wait for messages sent by server
     */
    private void listenMessages() throws IOException {
        Scanner sc = new Scanner(this.socket.getInputStream());

        while (sc.hasNextLine()) {
            String message = sc.nextLine();

            this.receive(message);
        }
    }

    @Override
    public void receive(String message) {
        receive(MessageDTO.decode(message));
    }

    protected abstract void receive(MessageDTO dto);
}