package chat.client.gui;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

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

    private JFrame frame;

    private JTextField messageInput;


    /**
     * Constructs client from address and port
     * 
     * @param address address to connect
     * @param port port to connect
     */
    public GuiClient(String address, int port) {
        this.asksForCredentials();
        this.loginToServer(address, port);
        this.createChatInterface();
    }

    /**
     * Get user informations to connect
     */
    public void asksForCredentials() {
        InfoPopup popup = new InfoPopup();
        this.username = popup.getUsername();
    }


    /**
     * Connect to server and then send username
     * 
     * @param address address to server
     * @param port port to connect
     */
    public void loginToServer(String address, int port) {
        this.connectToServer(address, port);
        this.send(this.username);
    }


    /**
     * Create laytou for the chat user interface
     */
    public void createChatInterface() {
        this.frame = new JFrame("Chat");

        this.messageInput = new JTextField();
        this.messageInput.setBounds(10, 20, 300, 30);

        JButton submitButton = new JButton("Send");
        submitButton.setBounds(320, 20, 80, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitMessage();
            }
        });

        // add components to frame
        this.frame.add(this.messageInput);
        this.frame.add(submitButton);
        
        // setup frame and show it
        this.frame.setSize(WIDTH, HEIGHT);
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


    @Override
    public void receive(String message) {
        System.out.println(message);
    }


}