package data;

public class DownloadItem {

    private String fileName;
    private double size;
    private double downloaded;
    private String status;

    public DownloadItem(String fileName, double size, double downloaded, String status) {
        this.fileName = fileName;
        this.size = size;
        this.downloaded = downloaded;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(double downloaded) {
        this.downloaded = downloaded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
