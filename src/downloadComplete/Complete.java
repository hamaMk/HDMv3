package downloadComplete;

import downloadWindow.DownloadPopUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Complete implements Initializable {

    @FXML
    private Label filename;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnOpen;

    public void display(String file){
        Stage stage = new Stage();
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("complete.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filename.setText(DownloadPopUp.nameFile);
    }

    @FXML
    void close() {
        Stage stage = (Stage)btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void open() {
        try {
            Desktop.getDesktop().open(new File(DownloadPopUp.pathFile + DownloadPopUp.nameFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnClose.fire();
    }
}
