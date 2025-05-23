package lk.ijse.project.chatapplicationexam.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lk.ijse.project.chatapplicationexam.Server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    private Server server;
    public static Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    String msd = "";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        msgContainVBOX.heightProperty().addListener((obs, oldVal, newVal) -> {
            scPane.setVvalue(1.0);
        });
        new Thread(() -> {
            try {
                server = Server.getInstance();
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                while (socket.isConnected()) {
                    msd = dis.readUTF();
                    recMessage(msd);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            server.makeSocket();
        }).start();
        recMessage("Server Started");
        recMessage("Waiting for Server To Start");
    }

    @FXML
    private AnchorPane anchoPane;

    @FXML
    private VBox msgContainVBOX;

    @FXML
    private ScrollPane scPane;

    public void recMessage(String message) {

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

}


