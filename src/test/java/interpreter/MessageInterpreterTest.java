package interpreter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import chat.dto.AcceptConnectionMessageDTO;
import chat.dto.ChatMessageDTO;
import chat.dto.MessageDTO;
import chat.interpreter.MessageInterpreter;
import chat.interpreter.interpreterimpl.MessageInterpreterImpl;

public class MessageInterpreterTest {
    
    private MessageInterpreter interpreter;

    @Before
    public void setUp() {
        this.interpreter = new MessageInterpreterImpl();
    }

    public void testEncode(MessageDTO messageDto, String expected) {
        String messageEncoded = interpreter.encode(messageDto);

        assertEquals(expected, messageEncoded);
    }

    public void testDecode(String message, MessageDTO expected) {
        MessageDTO messageDto = interpreter.decode(message);

        assertEquals(expected, messageDto);
    }

    @Test
    public void testEncode01() {
        MessageDTO messageDto = new ChatMessageDTO(5L, "vinicius", "oi!");

        testEncode(messageDto, "1;;5;;vinicius;;oi!");
    }

    @Test
    public void testEncode02() {
        MessageDTO messageDto = new AcceptConnectionMessageDTO(5L);

        testEncode(messageDto, "0;;5");
    }

    @Test
    public void testDecode01() {
        MessageDTO expectedMessage = new ChatMessageDTO(5L, "vinicius", "oi!");

        testDecode("1;;5;;vinicius;;oi!", expectedMessage);
    }

    @Test
    public void testDecode02() {
        MessageDTO expectedMessage = new AcceptConnectionMessageDTO(5L);

        testDecode("0;;5", expectedMessage);
    }

}
