package ntut.edu.lab1323.mitrastar.service.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    private DatagramSocket clientSocket;
    private InetAddress serverIPAddress;
    private int serverPort;

    public UDPClient(String serverIP, int port) {
        try {
            this.serverPort = port;
            this.clientSocket = new DatagramSocket();
            this.serverIPAddress = InetAddress.getByName(serverIP);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendMessage(String content) {
        try {
            byte[] data = content.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, content.length(), this.serverIPAddress, this.serverPort);
            this.clientSocket.send(sendPacket);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
