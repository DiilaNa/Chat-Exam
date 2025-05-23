package lk.ijse.project.chatapplicationexam.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Client {
    private Socket socket;
    private List<Client> clients;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String message = "";


    public Client(Socket socket, List<Client> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    message = dis.readUTF();
                    for (Client c : clients) {
                        if (c.socket.getPort() != socket.getPort()) {
                            c.dos.writeUTF(message);
                            c.dos.flush();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
