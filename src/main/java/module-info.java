module lk.ijse.project.chatapplicationexam {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens lk.ijse.project.chatapplicationexam to javafx.fxml;
    exports lk.ijse.project.chatapplicationexam;
    opens lk.ijse.project.chatapplicationexam.Controller to javafx.fxml;
    exports lk.ijse.project.chatapplicationexam.Controller;
    exports lk.ijse.project.chatapplicationexam.Server to javafx.graphics;
    exports lk.ijse.project.chatapplicationexam.Client to javafx.graphics;
}