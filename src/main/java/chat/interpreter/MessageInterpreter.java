package chat.interpreter;

import chat.dto.MessageDTO;

/**
 * Define a comunication language between server
 * and client
 */
public interface MessageInterpreter {
    
    /**
     * Encode object into a especified code language
     * 
     * @param message Object message to be encoded
     * @return encoded message
     */
    String encode(MessageDTO message);

    /**
     * Decode message from especified code language
     * 
     * @param message message to be decoded into an object
     * @return decodifed object
     */
    MessageDTO decode(String message);

}
