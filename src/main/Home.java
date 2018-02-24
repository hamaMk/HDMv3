package main;

import data.Data;
import data.DataStore;
import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import downloadWindow.DownloadPopUp;
import inputWindow.UrlInput;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import preferences.Config;
import preferences.ConfigStore;
import preferences.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class Home implements Initializable{

    public static TableView<Data> table;
    public TableColumn<Data, String> fileNameCol;
    public TableColumn<Data, String> fileSizeCol;
    public TableColumn<Data, String> downloadedCol;
    public TableColumn<Data, String> statusCol;

    public TreeView<String> treeView;


   // public Button addNewDownload;
 //   public Button btnSettings;
    public Label lblSettings;
    public Label lblNewdownload;
    public Label lblClose;
    public VBox box;

    Properties system = System.getProperties();
    private static URL sURL;
    static ObservableList<Data> bank = FXCollections.observableArrayList();
    ObservableList<Data> bank1 = FXCollections.observableArrayList();


    public void fileStructure(){
        TreeItem<String> root = new TreeItem<>("All downloads");

        TreeItem<String> complete = new TreeItem<>("Finished downloads");
        TreeItem<String> incomplete = new TreeItem<>("Incomplete downloads");

        TreeItem<String> compressed = new TreeItem<>("Compressed");
        TreeItem<String> videos = new TreeItem<>("Videos");
        TreeItem<String> music = new TreeItem<>("Music");
        TreeItem<String> pictures = new TreeItem<>("Pictures");
        TreeItem<String> documents = new TreeItem<>("Documents");
        TreeItem<String> other = new TreeItem<>("Others");


        complete.setExpanded(true);
        complete.getChildren().addAll(compressed,videos,music,pictures,documents,other);

        // incomplete.setExpanded(true);
        incomplete.getChildren().addAll(compressed,videos,music,pictures,documents,other);

        root.setExpanded(true);
        root.getChildren().addAll(complete, incomplete);

        treeView.setRoot(root);
    }


    private void loadConfig() {
        system.put("proxySet", Config.isStatus());
        if(Config.getProxyHost()!= null && Config.getProxyPort() != null) {
            system.put("http.proxyHost", Config.getProxyHost());
            system.put("http.proxyPort", Config.getProxyPort());
        }
    }

    public void getConfigs(){
        ConfigStore store = null;
        ObjectInputStream configIn = null;
        try {
            configIn = new ObjectInputStream(new FileInputStream("resources/config.ser"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            store = (ConfigStore) configIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Config.setProxyHost(store.getProxyHost());
        Config.setProxyPort(store.getProxyPort());
        Config.setStatus(store.getStatus());

    }


    public ArrayList<Data> getBanks(){
        DataStore dataStore = null;
        ObjectInputStream bankIn = null;
        try {
            bankIn = new ObjectInputStream(new FileInputStream("resources/bank.ser"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dataStore = (DataStore) bankIn.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dataStore.getBank();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //retrieving configuration file
        Path path = Paths.get("resources/config.ser");
        if(path.toFile().exists()){
            getConfigs();
        }
        else out.println("no config file found");



        //loading configurations
        loadConfig();

        fileStructure();

        fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileSizeCol.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        downloadedCol.setCellValueFactory(new PropertyValueFactory<>("downloaded"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
/*
        Data data = new Data();
        data.setFileName("test file1");
        data.setFileSize(100);
        data.setDownloaded(200);
        data.setStatus("downloading");

        bank.add(data);
        table.setItems(bank);*/


        Path path1 = Paths.get("resources/bank.ser");
        if(path1.toFile().exists()){
            ArrayList<Data> buff = getBanks();
            for(Data x: buff){
                bank.add(x);
            }
        }
        else out.println("no bank file found");

    /*    try{
            table.setItems(bank);
        }catch(NullPointerException nope){
            out.println("line 183 ->> Home");
        }*/


       // AwesomeIcon.VOLUME_UP

       // box.setStyle("-fx-background : linear-gradient(red, yellow);");

        AwesomeDude.setIcon(lblClose, AwesomeIcon.SIGNOUT, "20px");
        AwesomeDude.setIcon(lblNewdownload, AwesomeIcon.PLUS_SIGN_ALT, "30px");
        AwesomeDude.setIcon(lblSettings, AwesomeIcon.GEARS, "30px");
    }


    public void btnCloseClick(){
       // Stage stage = (Stage)lblClose.getScene().getWindow();
       // stage.close();
        Platform.exit();
    }

    public void btnAddLinkClick() throws IOException {

        UrlInput tmp = new UrlInput();
        tmp.display();
    }

    public void btnSettingsClick(){
        new Settings().display();
    }

    public static void linkReceived(String url) throws IOException {
      // out.println("boom " + url);

        new DownloadPopUp().display(makeUrl(url));

    }

    public static URL makeUrl(String tmp)throws MalformedURLException{
        sURL =  new URL(tmp);
        return sURL;
    }

    public static URL getSurl(){
        return sURL;
    }

    public static TableView<Data> getTable(){
        return table;
    }
   static int x=0;

    public static void addToBank(String fileName, long fileSize){
        Data data = new Data();
        data.setFileName(fileName);
        data.setFileSize(fileSize /1048652);
        data.setDownloaded(0);
        data.setStatus("ready");

        bank.add(data);

    }

}
