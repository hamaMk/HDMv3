package preferences;

import java.io.Serializable;

public class Config implements Serializable {

    private static String proxyHost;
    private static String proxyPort;
    private static boolean status = false;

    public static String getProxyHost() {
        return proxyHost;
    }

    public static void setProxyHost(String proxyHost) {
        Config.proxyHost = proxyHost;
    }

    public static String getProxyPort() {
        return proxyPort;
    }

    public static void setProxyPort(String proxyPort) {
        Config.proxyPort = proxyPort;
    }

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        Config.status = status;
    }
}
