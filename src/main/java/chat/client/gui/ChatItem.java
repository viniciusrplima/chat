package chat.client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChatItem extends JPanel {

    public ChatItem(String username, String message) {
        this(username, message, false);
    }

    public ChatItem(String username, String message, boolean isOwner) {
        setLayout(new BorderLayout());

        if (isOwner) setBackground(new Color(180, 255, 180));
        else setBackground(Color.white);

        setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setForeground(Color.blue);
        add(usernameLabel, BorderLayout.NORTH);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.black);
        add(messageLabel, BorderLayout.SOUTH);
    }

}
