package util;

import sun.nio.ch.FileKey;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Merge {

    public static void mergeFiles(List<File> files, File outputFile) throws IOException {

        FileOutputStream fos = new FileOutputStream(outputFile);
        int len = 0;
        byte[] buf = new byte[1024 * 1024]; // 1MB buffer

        for (File file : files) {

            FileInputStream fis = new FileInputStream(file);

            while ((len = fis.read(buf)) != -1)
                fos.write(buf, 0, len);
        }
        fos.close();
    }
}
