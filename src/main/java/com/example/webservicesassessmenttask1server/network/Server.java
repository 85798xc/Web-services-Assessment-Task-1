package com.example.webservicesassessmenttask1server.network;

import com.example.webservicesassessmenttask1server.service.XMLService;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class Server {
    private final XMLService xmlService;
    private volatile boolean running = true;
    private ServerSocket serverSocket;

    public void startServer() {
        new Thread(this::runServer).start();
    }

    private void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            this.serverSocket = serverSocket;
            while (running) {
                try (Socket socket = serverSocket.accept();
                     OutputStream out = socket.getOutputStream()) {
                    // Server ready for connections
                }
            }
        } catch (IOException e) {
            if (running) e.printStackTrace();
        }
    }

    public void sendStudentAsXML(Long studentId) throws IOException, JAXBException {
        String xml = xmlService.getStudentAsXML(studentId);
        try (Socket socket = new Socket("localhost", 1234);
             OutputStream out = socket.getOutputStream()) {
            out.write(xml.getBytes());
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
