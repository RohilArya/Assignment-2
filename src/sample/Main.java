package sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane editArea = new GridPane();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //List<File> Serverfiles = (List<File>) new File("Home/Rohil/Desktop/Server/");
        //List<File> Clientfiles = (List<File>) new File("Home/Rohil/Desktop/Client/");

        TableView<File> table = new TableView<>();
        //String rowIndex = DataType().getServerFilename;
        Scanner Scanner = new Scanner(new File("Home/Rohil/Desktop/Server/"));
        ArrayList<String> Serverfiles = new ArrayList<String>();
        while(Scanner.hasNext())
        {
            Serverfiles.add(Scanner.next());
        }
        Scanner.close();
        /*TableColumn<String, String> serverFiles = new TableColumn<>("ServerFiles");
        serverFiles.setCellValueFactory((TableColumn.CellDataFeatures<String, String> cellData) -> {
            return new ReadOnlyIntegerWrapper(Serverfiles.get(rowIndex));
        });*/

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String filename = "Blah";
                try {
                    Socket clientSocket = new Socket("127.0.0.1",1342);
                    String path = "Home/Rohil/Desktop/Server/";
                    new ClientConnectionHandler(clientSocket).download(filename,path);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editArea.add(downloadButton, 1, 4);

        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String filename = "Blah";
                try {
                    Socket clientSocket = new Socket("127.0.0.1",1342);
                    String path = "Home/Rohil/Desktop/Client/";
                    new ClientConnectionHandler(clientSocket).upload(filename,path);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        editArea.add(downloadButton, 1, 3);

    }


    public static void main(String[] args) {
        launch(args);
    }

}
