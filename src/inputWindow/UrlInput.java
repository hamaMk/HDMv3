package inputWindow;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Home;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.out;


public class UrlInput implements Initializable{

    public Button btnAdd;
    public Button btnCancel;
    public TextField input;

    private static String url;


    public void addClick(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String buff = null;
                out.println("line 39 UrlInput test tag");
                if((buff = input.getText()).startsWith("http")){
                    url = buff;
                    out.println("linky tag :: " + buff);
                    try {
                        Home.linkReceived(buff);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        btnCancel.fire();
    }

    public void cancelClick(){
        Stage tmp = (Stage) btnCancel.getScene().getWindow();
        tmp.close();
    }

    public String display(){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("urlInput.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("HDM");
        stage.setScene(new Scene(root));
        stage.show();




        return url;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

////clipboard

    public String getInputFromClipBoard(){
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String tmp=null;
        try {
            String buff=null;
            if(( buff = (String)clipboard.getData( DataFlavor.stringFlavor)).contains("http://")   )
                tmp =  buff;
        } catch (UnsupportedFlavorException e) {
            // e.printStackTrace();
            out.println("no link found in clipboard" );
        } catch (IOException e) {
            out.println("something went wrong ln94" );//e.printStackTrace();
        }
        out.println("clipboard........ "+tmp );
        return tmp;
    }

}
