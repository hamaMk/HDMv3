package worker;

import downloadWindow.DownloadPopUp;
import javafx.concurrent.Task;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection ;
import java.net.URL;

import static java.lang.System.out;

public class DowloadTask extends Task<Boolean> {

    private URL mURL;
    private long startBit;
    private long stopBit;
    private long totalLength;
    private String path;
    private File file;
    private byte[] buff = new byte[10000];

    private static long absoluteProgress;


    public static long getAbsoluteProgress(){
        return absoluteProgress;
    }

    public DowloadTask(URL URL, long startBit, long stopBit, String path) {
        mURL = URL;
        this.startBit = startBit;
        this.stopBit = stopBit;
        this.path = path;
    }

    @Override
    protected Boolean call() throws Exception {

        file = new File(path);

        OutputStream outputStream = null;
        InputStream inputStream = null;
        URLConnection  connection = null;

        try {

            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
           out.println("file not found :: line 46");
        }

        //  System.out.println("tag1 #### ");

        try {
            if(mURL.toString().contains("https")){
               // DownloadPopUp.activateSSL();
            }
            //connection = (HttpURLConnection)new URL("http://localhost/hama/website/movies/the%20hundred%201.mp4").openConnection();
            connection = (URLConnection )mURL.openConnection();


            //request headers
            //connection.setRequestProperty();
         //   connection.addRequestProperty("User-Agent", "Mozilla");
           // connection.addRequestProperty("Referer", "google.com");
            connection.setRequestProperty("Range","bytes="+ startBit+"-"+stopBit);
            //  out.println("response code :"+ connection.getResponseCode());

        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println("tag2 #### ");

        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("tag3 #### ");

        //request headers
        // connection.setRequestProperty("Range","bytes=0-");

        // System.out.println("tag4 #### ");
        //connecting
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }


        out.println("connected ->> " + connection.getHeaderField("Status-Code"));

        //setting the totalPartLength;
        totalLength = connection.getContentLengthLong();

        //  System.out.println("downloading part1 #### ");

        this.updateMessage("Downloading");
        long downloaded =0;
        try {
            while (downloaded<totalLength){

                int size = inputStream.read(buff,0,5000);
                if(size!=-1){

                    //System.out.println("downlen ... "+ size);
                    outputStream.write(buff,0,size);
                    downloaded += size;
                    //progress bar
                    this.updateProgress(downloaded, totalLength);



                }else {
                    out.println("download err");
                    break;
                }


            }

        } catch (IOException e) {
            out.println("could not download file");
            //this.updateMessage("Download failed");
        }
        this.updateMessage("Download complete");
        try {

            outputStream.close();
            inputStream.close();
            ///*********WARNING*********///
            connection = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        /////Ghost.......for calculating total downloaded from all threads
       // getGhost(downloaded);

         out.println("#### download complete ### "+downloaded);
        //out.println("absolute val "+absoluteProgress);

        return true;
    }

    public synchronized void getGhost(long downloaded){

        Ghost.setAabs(downloaded);
    }
}
