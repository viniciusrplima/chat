package chat.server;

/**
 * Represents a server that can receive messages
 * and should send them to clients
 */
interface Server {

    /**
     * Send message to all clients connected
     * 
     * @param message message to be sent
     */
    public void send(String message);
}