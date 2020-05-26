package downloadWindow;

import downloadComplete.Complete;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Home;
import merger.MergeView;
import preferences.Constants;
import worker.DownloadTask;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.out;

public class DownloadPopUp implements Initializable {



    public Button btnStart;
    public Button btnPause;
    public ProgressIndicator mIndicator;
    public ProgressBar p1;
    public ProgressBar p2;
    public ProgressBar p3;
    public ProgressBar p4;
    public ProgressBar p5;
    public ProgressBar p6;
    public Label lblFileName;
    public Label lblFileSize;
    public Label lblDownloaded;
    public Label lblSpeed;
    public Label lblDownloadStatus;
    public CheckBox chkPath;
    private URL url;


    private String fileName;
    public static String nameFile;
    public static String pathFile;
    private String contentType;

    long totalSizeOfFile;

    private String tempPath =  "C:\\Users\\hama\\Downloads\\Music\\downs\\";
    private String savePath = "";
    private int parts = Constants.downloadThreads;
    private int finishedParts = 0;

    private List<DownloadTask> tasks = new ArrayList<>();

    URLConnection  konekt = null;
    HttpsURLConnection connectS = null;

    private static boolean https = false;

    @FXML
    private TextField bxPath;



    public String customPath;
    private boolean customPathIsActive;

    public DownloadPopUp() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDownload(Home.getSurl());
       // activateSSL();
        bxPath.setText("default-path");
    }

    @FXML
    public void setCustomPath(){
        FileChooser fileChooser = new FileChooser();
        File tmp = fileChooser.showSaveDialog(btnStart.getScene().getWindow());
        customPath = tmp.getAbsolutePath();
        bxPath.setText(customPath);
        customPathIsActive = true;
    }


    public void btnStartClick(){

        lblSpeed.setText("N/A (Experimental feature)");
        //speed.textProperty().bind();
        for (DownloadTask task: tasks){
            new Thread(task).start();
            task.setOnSucceeded(event -> {
                finishedParts += 1;
                out.println("#### part download complete ### finishedParts " + finishedParts);

                // when all file parts have finished downloading
                if (finishedParts == parts){
                    out.println("all parts successfully downloaded ");
                    lblDownloadStatus.setText("Complete");
                    // mIndicator.setProgress(1);
                    lblDownloaded.setText(String.valueOf(totalSizeOfFile/1048652)+" (MB)");

                    merge();
                }
            });
        }

        btnStart.setDisable(true);
    }

    public void btnPauseClick(){///experimental

    }

    public void display(URL url) throws IOException{
        this.url = url;
        out.println("final linky pass :: " + url);
        Stage stage = new Stage();
        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("downloadPopUP.fxml"));
        Parent  root = loader.load();
        stage.setTitle("HDM");
        stage.setScene(new Scene(root));
        stage.show();
        out.println("test tag1 :: downloadPopUp.display -> done");


        //initDownload(url);
    }

    //ssl handler
    public static void activateSSL(){
        // Create a new trust manager that trust all certificates
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Activate the new trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public boolean extensionIsFound(ArrayList<String> list){
        boolean stat = false;
        for(String x: list){
            if(contentType.contains(x)) stat=true;
        }
        return stat;
    }



    public String pathProcessor(String path){


        ArrayList<String> audio = new ArrayList<>();
        audio.add("audio");
        audio.add("mp3");
        audio.add("m4a");
        audio.add("wav");
        audio.add("flac");


        ArrayList<String> video = new ArrayList<>();
        video.add("video");
        video.add("mp4");
        video.add("flv");
        video.add("3gp");
        video.add("mkv");
        video.add("avi");

        ArrayList<String> document = new ArrayList<>();
        document.add("application");
        document.add("pdf");
        document.add("doc");
        document.add("docx");
        document.add("txt");
        document.add("ppt");

        ArrayList<String> picture = new ArrayList<>();
        picture.add("jpeg");
        picture.add("png");
        picture.add("jpg");
        picture.add("gif");
        picture.add("image");

        ArrayList<String> compressed = new ArrayList<>();
        compressed.add("zip");
        compressed.add("exe");
        compressed.add("jar");
        compressed.add("rar");
        compressed.add("7zip");
        compressed.add("tar");



        if(extensionIsFound(audio)){
            path += "music\\";
        }
        else if(extensionIsFound(video)){
            path += "video\\";
        }
        else if(extensionIsFound(document)){
            path += "document\\";
        }
        else if(extensionIsFound(picture)){
            path += "picture\\";
        }
        else if(extensionIsFound(compressed)){
            path += "compressed\\";
        }
        else path += "other\\";
        out.println("cont type :: "+ contentType);
        return path;
    }




    public URLConnection getConnectionObject(){
        if(https)
        return connectS;
        else return konekt;
    }



    //start download sequence
    public void initDownload(URL url){


        if(url.toString().contains("https")){
//            activateSSL();
            try {
                connectS = (HttpsURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            https =true;
            out.println("ssl activated -> https connection detected ");
        }else{
            try {
                konekt = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            https = false;
        }


            getConnectionObject().setRequestProperty("User-Agent", "Mozilla");


        try {
            getConnectionObject().connect();
           // out.println("response :" + konekt.getResponseMessage() + "::" + konekt.getResponseCode());

        } catch (IOException e) {
            e.printStackTrace();
        }//////////////////////////////////////////////////////////



        totalSizeOfFile = getConnectionObject().getContentLengthLong();
        long l = totalSizeOfFile/1048652;
        lblFileSize.setText( l +"  (MB)");


        //getting proper path depending on file being downloaded
        contentType = getConnectionObject().getContentType();

//         tempPath = pathProcessor();///////////////////////////////////////


        //choose new path
        FileChooser fileChooser = new FileChooser();
        File tmpr = null;
        if(chkPath.isSelected()) {
            tmpr = fileChooser.showSaveDialog(chkPath.getScene().getWindow());
            out.println("save path ->>>> " + tmpr.getAbsolutePath() + " :: " + tmpr.getPath());
            savePath = tmpr.getPath();
        }

        //split download file
        long baseLength = totalSizeOfFile/parts;



        long lastStop = 0;
        for(int i=0; i < parts ; i++){
            String partName = tempPath + "part" + i;
            File file = new File(partName);
            long startBit = 0;
            long stopBit = 0;

            if (lastStop != 0)
                startBit = lastStop + 1;


            stopBit = startBit + baseLength;
            if (stopBit > totalSizeOfFile){
                stopBit = totalSizeOfFile;
            }

            //generate download threads
            out.println("startBit " + startBit + " stopBit " + stopBit + " :: total " + totalSizeOfFile);
            DownloadTask downloadTask = new DownloadTask(url, startBit, stopBit, file);
            tasks.add(downloadTask);

            lastStop = stopBit;
        }


        String tmp = url.toString();
        fileName =  tmp.substring(tmp.lastIndexOf("/") + 1);
        lblFileName.setText(fileName);

       // if(customPathIsActive)path = customPath;



//        binding(thread0);


          //  downloaded.textProperty().bind(thread0.valueProperty());

    }

    private void binding(Worker<Boolean> worker) {
        p6.progressProperty().bind(worker.progressProperty());
    }



    //when done downloading
    public void merge() {


        Home.addToBank(fileName, totalSizeOfFile/1048652 );

        //out.println(path);

        if(customPathIsActive)
            savePath = customPath;
        else
            savePath = tempPath + fileName;


        out.println(" SAVE PATH ->> " + savePath);
        nameFile = fileName;
        pathFile = tempPath;

        List<File> files = new ArrayList<>();
        for (DownloadTask task: tasks){
            files.add(task.getFile());
        }

        try {
            new MergeView().display(files, new File(savePath));
        } catch (IOException e) {
            e.printStackTrace();
        }


        Stage tmp = (Stage)btnStart.getScene().getWindow();
        tmp.close();
        customPathIsActive = false;

    }


    public static void execSeq() {
        new Complete().display(nameFile);
    }
}
