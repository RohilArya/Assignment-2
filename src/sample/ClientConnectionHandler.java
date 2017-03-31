package sample;

import com.sun.corba.se.spi.activation.Server;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.SplittableRandom;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;



public class ClientConnectionHandler implements Runnable{
    private static String computerName;
    private ServerSocket ServerSocket = null;
    private Socket socket = null;
    private String hostname = null;
    private int port=0;
    public Socket clientSocket=null;
    //private BufferedReader requestInput = new BufferedReader();
    private DataOutputStream requestOutput = null;
    String mainRequestLine = null;
    public ClientConnectionHandler(String ip, int port,Socket clientSocket)
    {
        this.hostname = ip;
        this.port = port;
        this.clientSocket=clientSocket;
        try {
            //ServerSocket = new ServerSocket(8080);
            handleRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }
    public void handleRequest() throws IOException {
        try
        {
            System.out.println("ACCEPTED");
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


                String line = "";
               // while (br.readLine() != null) {
                    line = br.readLine();
                    System.out.println(line);
                   // if(line!=null) {
                        //mainRequestLine.append(line);
                       // mainRequestLine.append("\n");
                   // }
                mainRequestLine=line;
                System.out.println(mainRequestLine.toString());
                br.close();

            System.out.println("Unformatted raw data from client: " + mainRequestLine);
            StringTokenizer requestTokenizer = new StringTokenizer(mainRequestLine);

            String command = null;
            String name = null;
            String filename = "Blah";
            String path = null;

           // String = "home/" + computerName + "Desktop/Server/";
            //String pathC = "Home/" + computerName + "Desktop/Client/";
            String content = null;
            command = requestTokenizer.nextToken();
            System.out.println(command);
            name = requestTokenizer.nextToken();
            System.out.println(name);
            path = requestTokenizer.nextToken();
            System.out.println(path);
            content = requestTokenizer.nextToken();
            System.out.println(content);


            if (command.equals("Download"))
            {

                File downloadedFile = new File (path);
                BufferedReader br2 = new BufferedReader(new FileReader(path));
                StringBuilder content1 = new StringBuilder();

                try {
                    String line1 = br.readLine();
                    content1.append("?");
                    while (line != null) {
                        line = br.readLine();
                        content1.append(line);
                        content1.append("?");
                    }
                    System.out.println(content1.toString());
                }
                finally {
                    br2.close();
                }
                PrintWriter Something = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader reading = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                Something.println(content1);

                Something.close();
                reading.close();
                clientSocket.close();
                //download(filename,pathS);
            }
            if (command.equals("Upload"))
            {   File file = new File(path);
                PrintWriter out = new PrintWriter(file);
                out.write(content);
            }
            if (command.equals("DIR"))
            {
                //DIR(pathS);
            }
            output.close();
            br.close();
            clientSocket.close();
        }
        catch (NoSuchElementException e) {
            System.err.println("HTTP/1.1 400 Bad Request: "+mainRequestLine+"\r\n");
            e.printStackTrace();
        }

    }

}
