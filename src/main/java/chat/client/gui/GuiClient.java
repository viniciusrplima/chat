package chat.client.gui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.WindowConstants;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;

import java.io.IOException;
import java.net.Socket;

import java.io.PrintStream;

class GuiClient extends JFrame {

    private Socket client;

    private JButton sendButton;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public GuiClient(String address, int port) {
        this.initComponents();
        this.initSocket(address, port);
    }

    private void initSocket(String address, int port) {
        try {

            this.client = new Socket(address, port);
        }
        catch (IOException ioe) {
            System.out.println("Failed to open socket: " + ioe.getMessage());
        }
    }

    private void initComponents() {
        this.sendButton = new JButton();
        this.textArea = new JTextArea();
        this.scrollPane = new JScrollPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.textArea.setColumns(20);
        this.textArea.setRows(5);
        this.scrollPane.setViewportView(this.textArea);

        this.sendButton.setText("Enviar mensagem");
        this.sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                submitMessage();
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(this.scrollPane, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.sendButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this.scrollPane, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.sendButton)
                .addContainerGap(25, Short.MAX_VALUE)
            )
        );

        this.pack();

        System.out.println("packed!");
    }

    private void submitMessage() {
        String message = this.textArea.getText();
        this.textArea.setText("");

        try {

            PrintStream output = new PrintStream(this.client.getOutputStream());
            output.println(message);
        }
        catch (IOException ioe) {
            System.out.println("Failed to send message: " + ioe.getMessage());
        }
    }

    public static void main(String[] args) {
        final int PORT = 3333;
        final String ADDRESS = "127.0.0.1";

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (Exception err) {
            System.out.println("Unexpected error occured! " + err.getMessage());
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiClient(ADDRESS, PORT).setVisible(true);
            }
        });

    }
}