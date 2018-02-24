package preferences;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class Settings implements Initializable{

    public RadioButton btnOn;
    public RadioButton btnOff;
    public TextField proxyInput;
    public TextField portInput;
    public TextField passwordInput;
    public TextField userNameInput;
    public Button btnCancel;
    public Button btnApply;
    public Button btnDone;

    private String userName;
    private String passWord;
    Properties system = System.getProperties();

    private static HttpURLConnection sConnection;
    private boolean status;

    public static HttpURLConnection getConnection() {
        return sConnection;
    }

    public static void setConnection(HttpURLConnection connection) {
        sConnection = connection;
    }

    public void display(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("settings.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Preferences");
        stage.setScene(new Scene(root));
        stage.show();


    }



    public void setUpConnection(){

        system.put("http.proxyHost", proxyInput.getText());
        system.put("http.proxyPort", portInput.getText());

        passWord = passwordInput.getText();
        userName = userNameInput.getText();

        Config.setProxyHost(proxyInput.getText());
        Config.setProxyPort(portInput.getText());
        Config.setStatus(status);

    }

    public void proxyWithPass(HttpURLConnection connection){
        BASE64Encoder encoder = new BASE64Encoder();
        String tmp = "http:\\" + userName + ":" + passWord;
        String encodedPass = encoder.encode( tmp.getBytes());
        connection.setRequestProperty("Proxy-Authorization", "Basic" + encodedPass);
        sConnection = connection;
    }

    public void btnOnClick(){
        status = true;
        system.put("proxySet", "true");
        proxyInput.setDisable(false);
        portInput.setDisable(false);
        userNameInput.setDisable(false);
        passwordInput.setDisable(false);
    }
    public void btnOffClick(){
        status = false;
        system.put("proxySet", "false");
        proxyInput.setDisable(true);
        portInput.setDisable(true);
        userNameInput.setDisable(true);
        passwordInput.setDisable(true);
    }
    public void btnCancelClick(){
        btnDone.fire();
        btnApply.setDisable(true);
    }
    public void btnApplyClick(){
       if(proxyInput.getText().contains(".") && portInput.getText() != null){
           setUpConnection();
           btnApply.setDisable(true);
       }
       else out.println("fields can not be empty");

    }


    public void btnDoneClick(){
        Stage stage = (Stage)btnDone.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        if(Config.getProxyHost()!= null && Config.getProxyPort() != null) {
          proxyInput.setText(Config.getProxyHost());
          portInput.setText(Config.getProxyPort());
        }


        if(Config.isStatus()){
            btnOn.setSelected(true);
        }

    }









}
