package chat.client.cli;

import java.io.IOException;
import java.net.Socket;

import java.io.PrintStream;

import java.util.Scanner;


class CliClient {

    private Socket client;

    public static void main(String[] args) {
        final int PORT = 3333;
        final String ADDRESS = "127.0.0.1";

        new CliClient(ADDRESS, PORT);
    }

    public CliClient(String address, int port) {
        try {
            this.setupClient(address, port);
        }
        catch (IOException ioe) {
            System.out.println("Failed to connect client: " + ioe.getMessage());
        }
    }

    public void setupClient(String address, int port) throws IOException {
        this.client = new Socket(address, port);

        this.setupCli();
    }

    public void setupCli() {
        try {

            Scanner sc = new Scanner(System.in);
            PrintStream output = new PrintStream(this.client.getOutputStream());
            
            while (true) {
                String message = sc.nextLine();
                output.println(message);
            }
        }
        catch (IOException ioe) {
            System.out.println("Unexpected connection error occurred");
        }
    }
}