package lk.ijse.project.chatapplicationexam.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lk.ijse.project.chatapplicationexam.Server.Server;
import lk.ijse.project.chatapplicationexam.Util.Name;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class ClientController implements Initializable {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientName.setText(Name.ClientName);
        new Thread(() -> {
            try {
                socket = new Socket("localhost",3001);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                System.out.println("Client Connected");

                while (socket.isConnected()) {
                    String msgReceived = dis.readUTF();
                   receiveMessage(msgReceived);
                }
                msgContainVBOX.heightProperty().addListener((observable, oldValue, newValue) -> {
                    scPane.setVvalue(1.0);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @FXML
    private Label ClientName;

    @FXML
    private Button Send;

    @FXML
    private AnchorPane anchoPane;

    @FXML
    private VBox msgContainVBOX;

    @FXML
    private TextField msgText;

    @FXML
    private ScrollPane scPane;

    @FXML
    void sendBTNAction(ActionEvent event) throws IOException {
        String msg = msgText.getText();
        if (msgText.getText().equals("BYE")) {
            Server.closeSocket();
        } else if (msgText.getText().equals("DATE")) {
            LocalDate date = getDate();

            dos.writeUTF(date.toString());
            dos.flush();
        } else if (msgText.getText().equals("UPTIME")) {

        } else if (msgText.getText().equals("TIME")) {
            LocalTime time = getTime();
            dos.writeUTF(time.toString());
            dos.flush();
        }else if (msgText.getText().equals("HELP")) {
            FXCollections.observableArrayList("HELP","UPTIME","TIME");
           dos.writeUTF("HELP");
           dos.flush();
        }
    }
    public void receiveMessage(String message) {
        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-font-weight: bold");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,5));
        hBox.getChildren().add(textFlow);
        Platform.runLater(() -> {
            msgContainVBOX.getChildren().add(hBox);
        });
    }
    public LocalDate getDate(){
        LocalDate date = LocalDate.now();
        return date;
    }
    public LocalTime getTime(){
        LocalTime time = LocalTime.now();
        return time;
    }

}
