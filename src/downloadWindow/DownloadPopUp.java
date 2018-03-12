package downloadWindow;

import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import main.Home;
import merger.MergeView;
import worker.DowloadTask;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
    public Label fileName;
    public Label fileSize;
    public Label downloaded;
    public Label speed;
    private URL url;

    private boolean t1,t2,t3,t4,t5,t6;
    private String fileNamee;
    private String contentType;
    private String path;
    long totalSizeOfFile;

    DowloadTask thread0;
    DowloadTask thread1;
    DowloadTask thread2;
    DowloadTask thread3;
    DowloadTask thread4;
    DowloadTask thread5;

    URLConnection  konekt = null;

    public DownloadPopUp() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDownload(Home.getSurl());
       // activateSSL();
    }


    public void btnStartClick(){

        Home.addToBank(fileNamee, totalSizeOfFile);

        speed.setText("N/A (Experimental feature)");

        startThread(thread0);
        startThread(thread1);
        startThread(thread2);
        startThread(thread3);
        startThread(thread4);
        startThread(thread5);

        btnStart.setDisable(true);
    }

    public void btnPauseClick(){///experimental

        if(thread0.cancel(true)){
            out.println("paused!!");
        }
        if(thread1.cancel(true)){
            out.println("paused!!");
        }
        if(thread2.cancel(true)){
            out.println("paused!!");
        }
        if(thread3.cancel(true)){
            out.println("paused!!");
        }
        if(thread4.cancel(true)){
            out.println("paused!!");
        }
        if(thread5.cancel(true)){
            out.println("paused!!");
        }

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



    public String pathProcessor(){

        String path = "C:\\Users\\HAMANDISHE\\Videos\\HDM Downloads\\HDMv3\\";

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


    public void autoConfig(URLConnection urlConnection){

        HttpURLConnection httpURLConnection = null;
        HttpsURLConnection httpsURLConnection = null;

        if(urlConnection instanceof HttpURLConnection){
            httpURLConnection = (HttpURLConnection) urlConnection;
        }
        else if(urlConnection instanceof HttpsURLConnection){
            httpsURLConnection = (HttpsURLConnection) urlConnection;
        }

    }


    //start download sequence
    public void initDownload(URL url){


        if(url.toString().contains("https")){
            activateSSL();
            out.println("ssl activated -> https connection detected ");
        }


        //connekt

        try {
            konekt = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  try {
            konekt.setRequestProperty("User-Agent", "Mozilla");
           // konekt.setRequestMethod("HEAD");
            //out.println("response :" + konekt.getResponseMessage() + "::" + konekt.getResponseCode());
      //  } catch (ProtocolException e) {
      //      e.printStackTrace();
      //  }
       /* catch(IOException io){
            io.printStackTrace();
        }*/
        try {
            konekt.connect();
           // out.println("response :" + konekt.getResponseMessage() + "::" + konekt.getResponseCode());

        } catch (IOException e) {
            e.printStackTrace();
        }//////////////////////////////////////////////////////////



        totalSizeOfFile = konekt.getContentLengthLong();
        long l = totalSizeOfFile/1048652;
        fileSize.setText( l +"  (MB)");


        //getting proper path depending on file being downloaded
        contentType = konekt.getContentType();

         path = pathProcessor();///////////////////////////////////////
        
        long baseLength = totalSizeOfFile/6;
        long part2 = baseLength*2;
        long part3 = baseLength*3;
        long part4 = baseLength*4;
        long part5 = baseLength*5;

        //String path = "C:\\Users\\HAMANDISHE\\Videos\\HDM Downloads\\HDMv3\\";

        String tmp = url.toString();
       // out.println("no shiz " +tmp);
        fileNamee =  tmp.substring(tmp.lastIndexOf("/") + 1);
        fileName.setText(fileNamee);

        thread0 = new DowloadTask(url,0, baseLength, path + "part1");
        thread1 = new DowloadTask(url,(baseLength + 1), part2, path + "part2");
        thread2 = new DowloadTask(url,(part2 + 1), part3, path + "part3");
        thread3 = new DowloadTask(url,(part3 + 1), part4, path + "part4");
        thread4 = new DowloadTask(url,(part4 + 1), part5, path + "part5");
        thread5 = new DowloadTask(url,(part5 + 1), totalSizeOfFile,path + "part6");


        binding1(thread0);
        binding2(thread1);
        binding3(thread2);
        binding4(thread3);
        binding5(thread4);
        binding6(thread5);

        //super bind
      //  downloaded.textProperty().bind(Ghost.aabsProperty().asString());



        thread0.setOnSucceeded(e ->{
            t1=true;
            merge();
        });
        thread1.setOnSucceeded(e ->{
            t2=true;
            merge();
        });
        thread2.setOnSucceeded(e ->{
            t3=true;
            merge();
        });
        thread3.setOnSucceeded(e ->{
            t4=true;
            merge();
        });
        thread4.setOnSucceeded(e ->{
            t5 =true;
            merge();
        });
        thread5.setOnSucceeded(e ->{
            t6 =true;
            merge();
        });
//
      //  downloaded.textProperty().bind(thread0.valueProperty());

    }

    private void binding6(Worker<Boolean> worker) {
        p6.progressProperty().bind(worker.progressProperty());
    }

    private void binding5(Worker<Boolean> worker) {
        p5.progressProperty().bind(worker.progressProperty());
    }

    private void binding4(Worker<Boolean> worker) {
        p4.progressProperty().bind(worker.progressProperty());
    }

    private void binding3(Worker<Boolean> worker) {
        p3.progressProperty().bind(worker.progressProperty());
    }

    private void binding2(Worker<Boolean> worker) {
        p2.progressProperty().bind(worker.progressProperty());
    }

    public void binding1(Worker<Boolean> worker){
        p1.progressProperty().bind(worker.progressProperty());
    }



    public void startThread(DowloadTask task){
        Thread thread = new Thread(task);
        thread.start();
    }


    //when done downloading
    public void merge() {

        if(t1 && t2 && t3 && t4 && t5 && t6){
            out.println("all parts successfully downloaded ");
           // mIndicator.setProgress(1);
            downloaded.setText(String.valueOf(totalSizeOfFile/1048652)+" (MB)");


            //out.println(path);
            ProcessBuilder pb = new ProcessBuilder("resources/merge.exe", path, fileNamee);
            out.println("path ->> " + path + fileNamee);
            Process mergeFiles = null;
            //     mergeFiles = pb.start();
            try {
                new MergeView().display(pb, path, fileNamee);
            } catch (IOException e) {
                e.printStackTrace();
            }


            //btnStart.setDisable(false);

        }
    }






}
