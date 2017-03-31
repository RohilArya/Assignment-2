package sample;

import javafx.stage.FileChooser;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.SplittableRandom;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class ClientConnectionHandler implements Runnable{
    private static String computerName;
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
            System.out.println("Would you like to upload, download or DIR");
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
    public void handleRequest(String mainRequestLine) throws IOException {
        try
        {
            StringTokenizer requestTokenizer = new StringTokenizer(mainRequestLine);
            String command = null;
            String name = null;
            String filename = "Blah";
            String path = null;
           // String = "home/" + computerName + "Desktop/Server/";
            String pathC = "Home/" + computerName + "Desktop/Client/";
            String content = null;
            command = requestTokenizer.nextToken();
            name = requestTokenizer.nextToken();
            //pathS = requestTokenizer.nextToken();
            content = requestTokenizer.nextToken();

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            if (command.equals("Download"))
            {
                //download(filename,pathS);
            }
            if (command.equals("upload"))
            {
                //upload(filename,pathS);
            }
            if (command.equals("DIR"))
            {
                //DIR(pathS);
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
    /*
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
    }*/

    public void download(String filename, String pathS)
    {
        String pathC = null;
        try
        {
            PrintWriter writer = new PrintWriter(pathC + filename);
            BufferedReader reader = new BufferedReader(new FileReader(pathS + filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                writer.println(line);
            }
            System.out.println("Disconnected from server");
            writer.close();
            socket.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upload(String filename,String pathS)
    {
        try {
            PrintWriter writer = new PrintWriter(pathS + filename,"UTF-8");
            String content = null;
            writer.print(content);
            System.out.println("Disconnected from server");
            writer.close();
            socket.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public File[] DIR(String pathS) throws IOException {
        File folder = new File(pathS);
        File[] listofFiles = folder.listFiles();

        for (int i = 0; i < listofFiles.length; i++)
        {
            listofFiles[i].getName();
        }
        System.out.println("Disconnected from server");
        socket.close();
        return listofFiles;
    }

    public String getComputerName() {
        return this.computerName;
    }
    public void setComputerName(String computerName)
    {
        this.computerName = computerName;
    }

}
