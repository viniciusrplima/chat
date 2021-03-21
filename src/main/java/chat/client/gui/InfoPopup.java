package chat.client.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.lang.Thread;


/**
 * Popup to get informations of user and
 * connection
 */
public class InfoPopup {

    private static final int WIDTH = 320;
    private static final int HEIGHT = 240;

    private JFrame frame;
    private JTextField usernameInput;
    private JTextField addressInput;
    private JTextField portInput;

    private String username;
    private String address;
    private int port;

    private boolean isSubmitted;


    /**
     * Creates popup to asks for informations
     */
    public InfoPopup() {
        this.createWindow();
        this.waitForSubmission();

        this.destroyWindow();
    }


    public String getUsername() {
        return this.username;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }


    /**
     * Create window layout
     */
    private void createWindow() {
        this.frame = new JFrame("Informations");

        // input address
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setBounds(20, 20, 160, 30);

        this.addressInput = new JTextField();
        this.addressInput.setBounds(20, 50, 160, 30);
        this.addressInput.setText("127.0.0.1");

        // input port
        JLabel portLabel = new JLabel("Port");
        portLabel.setBounds(190, 20, 200, 30);

        this.portInput = new JTextField();
        this.portInput.setBounds(190, 50, 50, 30);
        this.portInput.setText("3333");

        // input username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(20, 90, 200, 30);

        this.usernameInput = new JTextField();
        this.usernameInput.setBounds(20, 120, 200, 30);
        
        // submit button
        JButton submitButton = new JButton("Send");
        submitButton.setBounds(20, 160, 80, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitInformations();
            }
        });

        // add components to frame
        this.frame.add(addressLabel);
        this.frame.add(this.addressInput);
        this.frame.add(portLabel);
        this.frame.add(this.portInput);
        this.frame.add(usernameLabel);
        this.frame.add(this.usernameInput);
        this.frame.add(submitButton);

        // setup frame and show it
        this.frame.setLayout(null);
        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }


    /**
     * Destroy window after submission
     */
    private void destroyWindow() {
        this.frame.dispose();
    }


    /**
     * Send the informations to client
     */
    private void submitInformations() {
        this.username = this.usernameInput.getText().trim();
        this.address = this.addressInput.getText().trim();
        this.port = Integer.valueOf(this.portInput.getText());

        // Verify if informations aren't empty
        // and then send them
        if (this.username.length() == 0) {

            System.out.println("The username can't be empty!");
        }
        else if (this.address.length() == 0) {

            System.out.println("The address can't be empty!");
        }
        else if (this.port < 0) {

            System.out.println("Port must be positive!");
        }
        else {
            // this close the popup
            this.isSubmitted = true;
        }
    }


    /**
     * Wait for user actions until
     * the user submit informations
     */
    private void waitForSubmission() {

        while (!this.isSubmitted) {
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ie) {
            }
        }
    }

}