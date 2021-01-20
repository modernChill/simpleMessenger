package web.server;

import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer {
    private final int port = 5000;
    private ArrayList clientOutStreams;

    public static void main(String[] args) {
        new ChatServer().go();
    }

    private void go() {
        clientOutStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutStreams.add(writer);

                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
                System.out.println("got a connection");
            }
        } catch (Exception ex) {ex.printStackTrace();}
    }

    private void tellEveryone(String message) {
        Iterator iterator = clientOutStreams.iterator();
        while (iterator.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) iterator.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }


    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket) {
            try {
                socket = clientSocket;
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception ex) {ex.printStackTrace();}
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    tellEveryone(message);
                }
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

}
