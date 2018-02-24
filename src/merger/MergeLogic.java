package merger;

import javafx.concurrent.Task;

import java.io.*;

import static java.lang.System.out;

/**
 * Created by HAMANDISHE on 1/21/2018.
 */
public class MergeLogic extends Task<Boolean> {


    public ProcessBuilder processBuilder;


    public MergeLogic(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    @Override
    protected Boolean call()   {


        Process mergeFiles = null;
        try {
                 mergeFiles = processBuilder.start();

            InputStream inputStream = mergeFiles.getInputStream();
            BufferedReader buff = new BufferedReader(new InputStreamReader(inputStream));


            String temp;
            while((temp = buff.readLine())!= null){
                if(temp.equals("merge completed")){
                    out.println("## all parts successfully merged ##");
                    try{
                     //   new MergeView().closeStage();
                    }catch (Exception e){e.printStackTrace();}

                  //  out.println("## all parts successfulggggggggggggggggggggly merged ##");
                    break;
                }
                else if(temp.equals("test :: -> failed")){
                    out.println("## error! ... files could not be merged ##");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }












}
