package sample;

import com.sun.deploy.util.SessionState;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

public class Main extends Application {
    private BorderPane layout;
    private TableView<File> table;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("File Sharer v1.0");
        primaryStage.setScene(new Scene(root, 550, 650));
        layout=new BorderPane();
        Scene scene = new Scene(layout,550,650);
        GridPane editArea = new GridPane();
        table = new TableView<>();

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String filename = "Blah";
                try {
                    Socket clientSocket = new Socket("127.0.0.1",1342);
                    String path = "/home/harshan/Desktop/Server/";
                    //new ClientConnectionHandler("localhost",8080).download(filename,path);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editArea.add(downloadButton, 1, 1);

        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String filename = "Blah";
                try {
                    Socket clientSocket = new Socket("127.0.0.1",1342);
                    String path = "/home/harshan/Desktop/Client/";
                    //new ClientConnectionHandler(clientSocket).upload(filename,path);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editArea.add(uploadButton, 3, 1);



        //ObservableList<File> ofileList = FXCollections.observableArrayList(fileList);

        //table.setItems(ofileList);

        layout.setTop(editArea);
        layout.setLeft(table);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        //FileServer server = new FileServer(1206);
        System.out.println("server made");

        //File[] fileList = server.handleRequests(1206);
        launch(args);
    }

}
