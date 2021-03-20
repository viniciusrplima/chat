package chat.client.gui;

import chat.client.gui.GuiClient;


/**
 * Setup standard client
 */
public class GuiClientMain {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 3333;

    public static void main(String[] args) {
        GuiClient client = new GuiClient(ADDRESS, PORT);
        client.start();
    }
}