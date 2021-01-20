package web.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private String ip;
    private int port;

    public ChatClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        setUpNetworking();
        start();
    }
    public void start() {
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    public void sendMessage(String message) {
        try {
            writer.println(message);
            writer.flush();
        } catch (Exception ex) {ex.printStackTrace();}
    }

    private void setUpNetworking() {
        try {
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("networking established");

        } catch (IOException ex) {ex.printStackTrace();}
    }

    public class IncomingReader implements Runnable {
        String message;
        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }
}
