package web.client;

import gui.ClientGUI;

public class StartClient {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error input data (ip, port)");
            System.exit(0);
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(ip, port);
        ClientGUI gui = new ClientGUI(client);
        gui.load();
    }
}
