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
    private static final int HEIGHT = 200;

    private JFrame frame;
    private JTextField usernameInput;

    private String username;

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


    /**
     * Create window layout
     */
    private void createWindow() {
        this.frame = new JFrame("Informations");

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(20, 20, 200, 30);

        this.usernameInput = new JTextField();
        this.usernameInput.setBounds(20, 50, 200, 30);
        
        JButton submitButton = new JButton("Send");
        submitButton.setBounds(20, 100, 80, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitInformations();
            }
        });

        // add components to frame
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

        // Verify if informations aren't empty
        // and then send them
        if (this.username.length() == 0) {

            System.out.println("The username can't be empty!");
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