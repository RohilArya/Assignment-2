package sample;

import com.sun.xml.internal.ws.server.ServerSchemaValidationTube;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


class FileServer {
    private ServerSocket serverSocket = null;
    private int port=8080;

    public FileServer(int port) throws IOException {
        this.port = port;

        serverSocket = new ServerSocket(port);

        handleRequests();

    }

    public File[] handleRequests() throws IOException {
        System.out.println("Server is listening");
        File[] directory = null;
        while (true) {
            Socket clientSocket = serverSocket.accept();

            ClientConnectionHandler cch = new ClientConnectionHandler("localhost",this.port,clientSocket);

            Thread handlerThread = new Thread(cch);

            handlerThread.start();

            try {

                handlerThread.join();
                //directory = cch.DIR("/home/rohil/Desktop/Server/");

            } catch (InterruptedException e) {
                e.printStackTrace();
            // } catch (IOException e) {
                e.printStackTrace();
            }

            return directory;
        }
    }
    public static void main(String[]args) throws IOException{
        FileServer Server = new FileServer(8080);
    }
}

