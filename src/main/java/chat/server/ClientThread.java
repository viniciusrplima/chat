package chat.server;

import java.net.Socket;
import java.lang.Thread;

class ClientThread extends Thread {
    protected Socket client;
    protected int index;

    public ClientThread(Socket client, int index) {
        this.client = client;
        this.index = index;
    }

    @Override
    public void run() {}
}