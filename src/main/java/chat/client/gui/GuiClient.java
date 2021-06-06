package chat.client.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import chat.dto.MessageDTO;
import chat.client.AbstractClient;


/**
 * Gui client to be used by user
 */
public class GuiClient extends AbstractClient {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;

    private String username;
    private String address;
    private int port;

    private JFrame frame;

    private JPanel messagesDisplay;
    private GridBagConstraints grid;
    private JScrollPane scrollMessages;
    private JTextField messageInput;

    private List<MessageDTO> messages;


    /**
     * Constructs client from address and port
     */
    public GuiClient() {
        this.messages = new ArrayList<>();

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

        this.messagesDisplay = new JPanel();
        this.messagesDisplay.setLayout(new GridBagLayout());
        this.messagesDisplay.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.messagesDisplay.setSize(new Dimension(200, 0));

        // Configuring Layout
        this.grid = new GridBagConstraints();
        this.grid.weightx = 1;
        this.grid.weighty = 0;
        this.grid.gridy = 0;
        this.grid.gridx = 0;
        this.grid.anchor = GridBagConstraints.LINE_START;
        this.grid.insets = new Insets(5, 10, 5, 10);

        this.scrollMessages = new JScrollPane();
        this.scrollMessages.setViewportView(this.messagesDisplay);
        this.scrollMessages.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.scrollMessages.setBounds(10, 20, 390, 400);
        this.scrollMessages.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
                JScrollBar vertical = scrollMessages.getVerticalScrollBar();
                vertical.setValue(vertical.getMaximum());
            }
        });

        this.messageInput = new JTextField();
        this.messageInput.setBounds(10, 430, 300, 30);

        // Send message always enter button is pressed
        this.messageInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {}

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitMessage();
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {}
        });

        JButton submitButton = new JButton("Send");
        submitButton.setBounds(320, 430, 80, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitMessage();
            }
        });

        JPanel inputForm = new JPanel();
        inputForm.setLayout(new BorderLayout());
        inputForm.add(messageInput, BorderLayout.CENTER);
        inputForm.add(submitButton, BorderLayout.EAST);
        inputForm.setPreferredSize(new Dimension(0, 40));

        // setup frame and show it
        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setLayout(new BorderLayout());
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                messageInput.requestFocus();
            }
        });

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.frame.add(container, BorderLayout.CENTER);

        // add components to frame
        container.add(this.scrollMessages, BorderLayout.CENTER);
        container.add(inputForm, BorderLayout.SOUTH);
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

    private void addChatItem(MessageDTO dto) {
        JPanel chatItem = new ChatItem(dto.getUsername(), dto.getMessage());

        grid.gridy++;
        this.messagesDisplay.add(chatItem, grid);
        this.messagesDisplay.revalidate();
    }

    @Override
    protected void receive(MessageDTO messageDto) {
        this.messages.add(messageDto);
        this.addChatItem(messageDto);
    }


    public static void main(String[] args) {
        new GuiClient().start();
    }
}