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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by harshan on 30/03/17.
 */
public class Client extends Application {
    private BorderPane layout;
    private TableView<File> tableLeft;
    private TableView<File> tableRight;

    public static void main(String[]args) throws IOException{
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
        File folderL = new File("/home/harshan/Desktop/Server/");
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
        File folderR = new File("/home/harshan/Desktop/Client/");
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

    public void Client(String ip, int port)
    {

    }
    /*****************************COMMAND*****************************/
    public void sendCommand(File fileName, String command) throws IOException {

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder content = new StringBuilder();

            try {
                String line = br.readLine();
                content.append("\n");

                while (line != null) {
                    content.append(line);
                    content.append("\n");
                    line = br.readLine();
                }
                //System.out.println(content.toString());
            } finally {
                br.close();
            }
        if(command == "Upload ") {
            String fullCommand = command + "harshan " + "/home/harshan/Desktop/Server/" + content;
            System.out.println(fullCommand);

        }
        if(command == "Download "){
            String fullCommand = command + "harshan " + "/home/harshan/Desktop/Server/";
            System.out.println(fullCommand);
        }

    }
}
