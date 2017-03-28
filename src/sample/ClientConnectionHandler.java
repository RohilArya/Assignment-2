package sample;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class ClientConnectionHandler implements Runnable{
    private static String computerName = "Rohil";
    private Socket socket = null;
    private BufferedReader requestInput = null;
    private DataOutputStream requestOutput = null;
    public ClientConnectionHandler(Socket socket)
    {
        this.socket = socket;
        try
        {
            requestInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            requestOutput = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server Error while processing new Socket\r\n");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String mainRequestLine = null;
        try
        {
            mainRequestLine = requestInput.readLine();
            //handleRequest(mainRequestLine);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            try
            {
                requestInput.close();
                requestOutput.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleRequest(String mainRequestLine) throws IOException {
        try
        {
            StringTokenizer requestTokenizer = new StringTokenizer(mainRequestLine);
            String command = null;
            String uri = null;
            String httpVersion = null;
            String filename = "Blah";

            command = requestTokenizer.nextToken();
            uri = requestTokenizer.nextToken();
            if (!uri.startsWith("/")) {
                uri = "/" + uri;
            }
            httpVersion = requestTokenizer.nextToken();
            if (command.equalsIgnoreCase("Download") || command.equalsIgnoreCase("Upload") || command.equalsIgnoreCase("DIR")) {
                File baseDir = new File("Home/" + computerName + "/Desktop/Server/" + filename);
                sendFile(new File(baseDir, uri));
            }
            else
            {
                System.err.println("Method Not Allowed: "+mainRequestLine+"\r\n");
            }
        }
        catch (NoSuchElementException e) {
            System.err.println("HTTP/1.1 400 Bad Request: "+mainRequestLine+"\r\n");
            e.printStackTrace();
        }

    }


    /*private String getContentType(String filename) {
        if (filename.endsWith(".html") || filename.endsWith(".htm")) {
            return "text/html";
        } else if (filename.endsWith(".txt")) {
            return "text/plain";
        } else if (filename.endsWith(".css")) {
            return "text/css";
        } else if (filename.endsWith(".js")) {
            return "text/javascript";
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        }
        return "unknown";
    }*/
    private void sendFile(File file) throws IOException {
        String header = "File header\r\n";
        String contentType = "txt";
        //String contentType = getContentType(file.getName());
        byte[] content = new byte[(int)file.length()];
        FileInputStream fileInput = null;

        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            fileInput = null;
        }

        if (fileInput != null) {
            fileInput.read(content);
            fileInput.close();
            sendResponse(header, content);
        } else {
            System.out.println("The requested file ("+file.getName()+") does not exist on the server.");
        }
    }

    private void sendResponse(String header, byte[] content) throws IOException {
        requestOutput.writeBytes(header);
        requestOutput.writeBytes("Content-Type: Txt\r\n");
        requestOutput.writeBytes("Server: File Sharing Server 1.0\r\n");
        requestOutput.writeBytes("Date: "+(new Date())+"\r\n");
        requestOutput.writeBytes("Content-Length: "+content.length+"\r\n");
        //if (lastModified > 0) {
          //  requestOutput.writeBytes("Last-Modified: "+(new Date(lastModified))+"\r\n");
        //}
        requestOutput.writeBytes("Connection: Close\r\n\r\n");
        requestOutput.write(content);
        requestOutput.writeBytes("\r\n");
        requestOutput.flush();
    }

    public void download(String filename)
    {

    }

    public void upload(String filename)
    {

    }
    public void DIR()
    {

    }
}
