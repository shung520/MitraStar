package ntut.edu.lab1323.mitrastar.service.socket;

import android.util.Log;

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
            this.serverIPAddress = InetAddress.getByName(serverIP);
            UDPClient.this.clientSocket = new DatagramSocket();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("UDPClient", content);
                    byte[] data = content.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(data, content.length(), UDPClient.this.serverIPAddress, UDPClient.this.serverPort);
                    UDPClient.this.clientSocket.send(sendPacket);

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
}
