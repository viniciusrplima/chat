package chat.client.gui;

import javax.swing.JFrame;

class GuiClient extends JFrame {

    public static void main(String[] args) {
        final int PORT = 3333;
        final String ADDRESS = "127.0.0.1";

        new GuiClient(ADDRESS, PORT);
    }

    public GuiClient(String address, int port) {

    }

}