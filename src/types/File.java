package types;

import java.math.BigInteger;
import java.net.URL;

public class File {

    private URL mURL;
    private BigInteger fullFileLength;
    private String status;
    private Progress[] progress = new Progress[6];

    public File(URL URL, String status, Progress[] progress) {
        mURL = URL;
        this.status = status;
        this.progress = progress;
    }


    public BigInteger getFullFileLength() {
        return fullFileLength;
    }

    public void setFullFileLength(BigInteger fullFileLength) {
        this.fullFileLength = fullFileLength;
    }

    public URL getURL() {
        return mURL;
    }

    public void setURL(URL URL) {
        mURL = URL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProgress(int partNumber, BigInteger startBit, BigInteger size){
        progress[partNumber] = new Progress(partNumber, startBit, size);
    }

    public Progress getProgress(int partNumber){
        return progress[partNumber];
    }
}
