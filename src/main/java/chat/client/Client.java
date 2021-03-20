package chat.client;

/**
 * Represents the client
 */
public interface Client {

    
    /**
     * Send message to server
     * 
     * @param message message to be sent
     */
    public void send(String message);


    /**
     * Receive message from server
     * 
     * @param message message that was sent
     */
    public void receive(String message);


}