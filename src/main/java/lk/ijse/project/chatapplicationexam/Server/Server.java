package lk.ijse.project.chatapplicationexam.Server;

import lk.ijse.project.chatapplicationexam.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static ServerSocket serverSocket;
    private static Socket socket;
    private static Server server;
    private List<Client> clients = new ArrayList<>();
    public Server(Socket socket) {
        this.socket = socket;
    }

    private Server() throws IOException {
        serverSocket = new ServerSocket(3001);
    }

    public static Server getInstance() throws IOException {
        if (server == null) {
            return new Server();
        }
        return server;
    }

    public void makeSocket(){
        while (!serverSocket.isClosed()) {
            try {
                socket = serverSocket.accept();
                Client c = new Client(socket,clients);
                clients.add(c);
                System.out.println("Client socket Accepted" + socket.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeSocket() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            socket.close();
            System.out.println("Socket closed");
        }else {
            System.out.println("Socket not closed");
        }
    }
}
