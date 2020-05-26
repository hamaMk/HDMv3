package merger;

import javafx.concurrent.Task;
import util.Merge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by HAMANDISHE on 1/21/2018.
 */
public class MergeLogic extends Task<Boolean> {


    private List<File> fileParts;
    private File outputFile;

    public MergeLogic(List<File> fileParts, File outputFile){
        this.fileParts = fileParts;
        this.outputFile = outputFile;
    }

    @Override
    protected Boolean call()   {


        Merge merge = new Merge();
        try {
            merge.mergeFiles(fileParts, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }












}
