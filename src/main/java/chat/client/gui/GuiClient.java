package chat.client.gui;


import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chat.client.gui.InfoPopup;
import chat.client.AbstractClient;


/**
 * Gui client to be used by user
 */
public class GuiClient extends AbstractClient {

    private static final int WIDTH = 410;
    private static final int HEIGHT = 500;

    private String username;
    private String address;
    private int port;

    private String messages;

    private JFrame frame;

    private JTextArea messagesDisplay;
    private JScrollPane scrollMessages;
    private JTextField messageInput;


    /**
     * Constructs client from address and port
     */
    public GuiClient() {
        this.messages = "";

        this.asksForCredentials();
        this.loginToServer();
        this.createChatInterface();
    }

    /**
     * Get user informations to connect
     */
    public void asksForCredentials() {
        InfoPopup popup = new InfoPopup();
        this.username = popup.getUsername();
        this.address = popup.getAddress();
        this.port = popup.getPort();
    }


    /**
     * Connect to server and then send username
     */
    public void loginToServer() {
        this.connectToServer(this.address, this.port);
        this.send(this.username);
    }


    /**
     * Create layout for the chat user interface
     */
    public void createChatInterface() {
        this.frame = new JFrame("Chat");

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.messagesDisplay = new JTextArea();
        this.messagesDisplay.setEditable(false);

        this.scrollMessages = new JScrollPane();
        this.scrollMessages.setViewportView(this.messagesDisplay);
        this.scrollMessages.setBounds(10, 20, 390, 400);

        this.messageInput = new JTextField();
        this.messageInput.setBounds(10, 430, 300, 30);

        JButton submitButton = new JButton("Send");
        submitButton.setBounds(320, 430, 80, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitMessage();
            }
        });

        // add components to frame
        this.frame.add(this.scrollMessages);
        this.frame.add(this.messageInput);
        this.frame.add(submitButton);
        
        // setup frame and show it
        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setResizable(false);
        this.frame.setLayout(null);
        this.frame.setVisible(true);
    }


    /**
     * Verify message and send it to server
     */
    private void submitMessage() {
        String message = this.messageInput.getText().trim();

        if (message.length() > 0) {
            this.messageInput.setText("");
            this.send(message);
        }
    }


    /**
     * Scrolls display down
     */
    private void scrollDownDisplay() {
        JScrollBar vertical = this.scrollMessages.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }


    @Override
    public void receive(String message) {
        //System.out.println(message);

        this.messages += message + '\n';
        this.messagesDisplay.setText(this.messages);
        this.scrollDownDisplay();
    }


    public static void main(String[] args) {
        new GuiClient().start();
    }
}