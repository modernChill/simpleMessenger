package gui;

import web.client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClientGUI extends ChatClient{
    JTextArea inMessage;
    JTextField outMessage;


    private int windowHeight = 400;
    private int windowWidth = 600;


    public ClientGUI(String ip, int port) {
        super(ip, port);
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
        frame.setSize(windowWidth, windowHeight);
        frame.setVisible(true);

    }
    @Override
    public void start() {
        Thread readerThread = new Thread(new ListenerMessage());
        readerThread.start();
    }

    public class ListenerMessage extends IncomingReader{
        String message;
        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null) {
                    inMessage.append(message + "\n");
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }


    public class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                sendMessage(outMessage.getText());

            } catch (Exception ex) {ex.printStackTrace();}
            outMessage.setText("");
            outMessage.requestFocus();

        }
    }
}
