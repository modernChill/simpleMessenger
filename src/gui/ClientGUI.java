package gui;

import web.client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientGUI {
    JTextArea inMessage;
    JTextField outMessage;

    private ChatClient client;

    private int windowHeight = 500;
    private int windowWidth = 400;

    public ClientGUI(ChatClient client) {
        this.client = client;
    }

    public void load() {
        JFrame frame = new JFrame("Chat Client");
        JPanel mainPanel = new JPanel();

        inMessage = new JTextArea(15, 50);
        inMessage.setLineWrap(true);
        inMessage.setWrapStyleWord(true);
        inMessage.setEditable(false);

        JScrollPane scroller = new JScrollPane(inMessage);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        outMessage = new JTextField(20);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());

        mainPanel.add(scroller);
        mainPanel.add(outMessage);
        mainPanel.add(sendButton);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(windowHeight, windowWidth);
        frame.setVisible(true);

    }




    public class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                client.sendMessage(outMessage.getText());

            } catch (Exception ex) {ex.printStackTrace();}
            outMessage.setText("");
            outMessage.requestFocus();

        }
    }
}
