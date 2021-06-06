package chat.dto;

public class MessageDTO {
    private String username;
    private String message;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static String encode(MessageDTO dto) {
        return String.format("%s;;%s", dto.getUsername(), dto.getMessage());
    }

    public static MessageDTO decode(String message) {
        String[] splitted = message.split(";;");
        MessageDTO dto = new MessageDTO();
        dto.setUsername(splitted[0]);
        dto.setMessage(splitted[1]);

        return dto;
    }
}
