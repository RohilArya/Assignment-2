package sample;

import com.sun.xml.internal.ws.server.ServerSchemaValidationTube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


class FileServer {
    private ServerSocket ServerSocket = null;

    public FileServer(int port) {
        try {
            ServerSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRequests()
    {
        System.out.println("Server is listening");

        try
        {
            Socket clientSocket = ServerSocket.accept();
            Thread handlerThread = new Thread(new ClientConnectionHandler(clientSocket));
            handlerThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

