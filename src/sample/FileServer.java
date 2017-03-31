package sample;

import com.sun.xml.internal.ws.server.ServerSchemaValidationTube;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


class FileServer {
     private ServerSocket serverSocket = null;
    private int port;

    public FileServer(int port) throws IOException {
        this.port = port;

        serverSocket = new ServerSocket(port);

        handleRequests();

    }

    public File[] handleRequests() throws IOException {
        System.out.println("Server is listening");
        File[] directory = null;
        while (true) {
            System.out.println("inside try");
            Socket clientSocket = serverSocket.accept();
            System.out.println("accepting sockets");

            ClientConnectionHandler cch = new ClientConnectionHandler(clientSocket);
            System.out.println("clienthandler");

            Thread handlerThread = new Thread(cch);
            System.out.println("threading");

            handlerThread.start();
            System.out.println("thread starting");

            try {
                System.out.println("inside try1");

                handlerThread.join();
                directory = cch.DIR("/home/harshan/Desktop/Server/");

            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("outside try1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("outside try1");

            System.out.println("outside try");
            return directory;
        }
    }
}

