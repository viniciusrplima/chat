package chat.interpreter.interpreterimpl;

import chat.dto.AcceptConnectionMessageDTO;
import chat.dto.ChatMessageDTO;
import chat.dto.MessageDTO;
import chat.dto.MessageType;
import chat.interpreter.FailedToDecodeException;
import chat.interpreter.MessageInterpreter;

public class MessageInterpreterImpl implements MessageInterpreter{
    
    @Override
    public String encode(MessageDTO dto) {
        MessageType type = dto.getType();
        String encodedMessage = "";

        if (type == MessageType.ACCEPT_CONNECTION) {
            AcceptConnectionMessageDTO acceptDto = (AcceptConnectionMessageDTO) dto;

            encodedMessage = String.format("%d;;%d", acceptDto.getType().ordinal(), acceptDto.getUserId());
        }
        else if (type == MessageType.CHAT_MESSAGE) {
            ChatMessageDTO chatDto = (ChatMessageDTO) dto;

            encodedMessage = String.format("%d;;%d;;%s;;%s", chatDto.getType().ordinal(), chatDto.getUserId(), 
                chatDto.getUsername(), chatDto.getText());
        }

        return encodedMessage;
    }

    @Override
    public MessageDTO decode(String message) {
        String[] splitted = message.split(";;");
        Long type = Long.decode(splitted[0]);

        MessageDTO result = null;

        if (type == MessageType.ACCEPT_CONNECTION.ordinal()) {
            Long userId = Long.decode(splitted[1]);
            
            result = new AcceptConnectionMessageDTO(userId);
        }
        else if (type == MessageType.CHAT_MESSAGE.ordinal()) {
            Long userId = Long.decode(splitted[1]);
            String username = splitted[2];
            String text = splitted[3];

            result = new ChatMessageDTO(userId, username, text);
        }
        else {
            throw new FailedToDecodeException(
                String.format("Invalid type of message: %s.", message));
        }

        return result;
    }

}
