package main;

import data.Data;
import data.DataStore;
import data.DownloadItem;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import preferences.Config;
import preferences.ConfigStore;
import preferences.Settings;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class Home implements Initializable{

    public  TableView<DownloadItem> table;
    public TableColumn<Data, String> fileNameCol;
    public TableColumn<Data, String> fileSizeCol;
    public TableColumn<Data, String> downloadedCol;
    public TableColumn<Data, String> statusCol;
    public TreeView<String> treeView;
    public ImageView lblSettings;
    public ImageView lblNewdownload;
    public ImageView lblClose;
    public VBox box;

    Properties system = System.getProperties();
    private static URL sURL;
    static ObservableList<Data> bank = FXCollections.observableArrayList();
    static ObservableList<DownloadItem> downloadsList = FXCollections.observableArrayList();


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
        fileSizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        downloadedCol.setCellValueFactory(new PropertyValueFactory<>("downloaded"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

       // downloadsList.add(new DownloadItem("bshbs", 67, 7, "HJS"));
        table.setItems(downloadsList);

        Path path1 = Paths.get("resources/bank.ser");
        if(path1.toFile().exists()){
            ArrayList<Data> buff = getBanks();
            for(Data x: buff){
                bank.add(x);
            }
        }
        else out.println("no bank file found");


        try {
            lblClose.setImage(new Image(new FileInputStream("resources/close.png")));
            lblNewdownload.setImage(new Image(new FileInputStream("resources/add.png")));
            lblSettings.setImage(new Image(new FileInputStream("resources/settings.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void btnCloseClick(){
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

        new DownloadPopUp().display(makeUrl(url));

    }

    public static URL makeUrl(String tmp)throws MalformedURLException{
        sURL =  new URL(tmp);
        return sURL;
    }

    public static URL getSurl(){
        return sURL;
    }


   static int x=0;

    public static void addToBank(String fileName, long fileSize){
        Data data = new Data();
        data.setFileName(fileName);
        data.setFileSize(fileSize /1048652);
        data.setDownloaded(0);
        data.setStatus("ready");

        downloadsList.add(new DownloadItem(fileName, fileSize,fileSize, "complete"));

    }

}
