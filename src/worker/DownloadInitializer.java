package worker;

import downloadWindow.DownloadPopUp;
import javafx.concurrent.Task;
import types.File;
import types.Progress;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.out;

public class DownloadInitializer extends Task<Boolean> {

    private String link;
    private URL mURL;


    public DownloadInitializer(String link) {
        this.link = link;
        setURL(link);
    }

    public void setURL(String buff) {
        try {
            mURL = new URL(buff);
        } catch (MalformedURLException e) {
            out.println("bad url detected");
        }
    }

    public void makeConnections() throws IOException {
        HttpURLConnection connection1 = (HttpURLConnection) mURL.openConnection();
        connection1.connect();
        connection1.getContentLength();
    }

    @Override
    protected Boolean call() throws Exception {

       // Progress progress = new Progress(1, 2,222);

       // File file = new File()

       // DowloadTask thread1 = new DowloadTask();


        //initiate download sequence
        //new DownloadPopUp().display(file);

        return true;
    }

    //create progresses
    public void createProgress(){

    }

}
