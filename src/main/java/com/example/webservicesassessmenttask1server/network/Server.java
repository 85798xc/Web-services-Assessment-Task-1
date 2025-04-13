package com.example.webservicesassessmenttask1server.network;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class Server {
    private volatile boolean running = true;
    private ServerSocket serverSocket;

    public void sendXML(String filePath) throws IOException {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(1234)) {
                this.serverSocket = serverSocket;
                while (running) {
                    try (Socket socket = serverSocket.accept();
                         OutputStream out = socket.getOutputStream()) {
                        Files.copy(Path.of(filePath), out);
                    } catch (IOException e) {
                        if (running) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
