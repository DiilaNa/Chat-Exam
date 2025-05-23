package lk.ijse.project.chatapplicationexam.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.project.chatapplicationexam.Util.Name;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button loginBTN;

    @FXML
    private TextField nameText;

    @FXML
    void loginBTNAction(ActionEvent event) throws IOException {
        String name = nameText.getText();
        Name.ClientName = name;
        if (name.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter your name", ButtonType.OK).show();
        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Client.fxml"));
            Stage currentStage = (Stage) loginBTN.getScene().getWindow();
            currentStage.close();
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Client Login");
            stage.setResizable(false);
            stage.show();
        }
    }

}
