package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

/**
 * Created by harshan on 30/03/17.
 */
public class Client extends Application {
    private BorderPane layout;
    private TableView<File> tableLeft;
    private TableView<File> tableRight;
    private String hostname = null;
    private int port=8080;
    public String dFile = "";
    public void Client(String ip, int port)
    {
        this.hostname = ip;
        this.port = port;
    }

    public static void main(String[]args) throws IOException{
        Client client = new Client();
        client.Client("localhost",8080);
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 500, 600));
        layout=new BorderPane();
        Scene scene = new Scene(layout,500,600);
        GridPane editArea = new GridPane();
        tableLeft = new TableView<>();
        tableRight = new TableView<>();


        /*****************************LEFT *****************************/
        final File[] fileNameL = {null};
        TableColumn<File,String> serverColumn = null;
        serverColumn = new TableColumn<>("Server");
        serverColumn.setMinWidth(250);
        tableLeft.getColumns().add(serverColumn);

        /*********************DIR FOR SERVER***********************/
        File folderL = new File("/home/rohil/Desktop/Server/");
        File[] listofFilesL = folderL.listFiles();

        for (int i = 0; i < listofFilesL.length; i++) {
            listofFilesL[i].getName();
        }

        /******************************LEFT SIDE CLICKED******************************/
        ObservableList<File> ofileListL = FXCollections.observableArrayList(listofFilesL);
        tableLeft.setItems(ofileListL);
        tableLeft.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount()==1){
                    fileNameL[0] =(tableLeft.getSelectionModel().getSelectedItem());
                    dFile = fileNameL[0].toString();
                }
            }
        });

        /*****************************RIGHT *****************************/
        final File[] fileNameR = {null};
        TableColumn<File,String> clientColumn = null;
        clientColumn = new TableColumn<>("Client");
        clientColumn.setMinWidth(250);
        tableRight.getColumns().add(clientColumn);

        /*********************DIR FOR CLIENT***********************/
        File folderR = new File("/home/rohil/Desktop/Client/");
        File[] listofFilesR = folderR.listFiles();

        for (int i = 0; i < listofFilesR.length; i++) {
            listofFilesR[i].getName();
        }
        /******************************RIGHT SIDE CLICKED******************************/
        ObservableList<File> ofileListR = FXCollections.observableArrayList(listofFilesR);
        tableRight.setItems(ofileListR);
        tableRight.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isPrimaryButtonDown() && event.getClickCount()==1){
                    fileNameR[0] =(tableRight.getSelectionModel().getSelectedItem());
                }
            }
        });

        /*****************************BUTTONS*****************************/
        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Download Pressed--------");
                try {
                    sendCommand(fileNameL[0],"Download ");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        editArea.add(downloadButton, 1, 1);

        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Upload Pressed--------");
                try {
                    sendCommand(fileNameR[0],"Upload ");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editArea.add(uploadButton, 3, 1);
        /*****************************FORMAT*****************************/
        layout.setTop(editArea);
        layout.setLeft(tableLeft);
        layout.setRight(tableRight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /*****************************COMMAND*****************************/
    public void sendCommand(File fileName, String command) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder content = new StringBuilder();

        try {
            String line = br.readLine();
            content.append("?");
            while (line != null) {
                line = br.readLine();
                content.append(line);
                content.append("?");
            }
            System.out.println(content.toString());
        }
        finally {
            br.close();
        }
        if(command == "Upload ") {
            String fullCommand = command + "rohil " + "/home/rohil/Desktop/Server/ " + content.toString();
            //System.out.println(fullCommand);
            Socket socket = new Socket(this.hostname, this.port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.print(fullCommand);
            out.flush();
            socket.close();


        }
        if(command == "Download "){
            String path = "/home/rohil/Desktop/Server/";
            String fullCommand = command + "rohil " + path + dFile;
            System.out.println(fullCommand);
            Socket socket = new Socket(this.hostname, this.port);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br1 = new BufferedReader(isr);
            String fileContents = br1.readLine();
            socket.close();
            br1.close();
            is.close();
            isr.close();

        }

    }
}
