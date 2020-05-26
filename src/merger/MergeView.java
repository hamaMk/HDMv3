package merger;

import downloadComplete.Complete;
import downloadWindow.DownloadPopUp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by HAMANDISHE on 1/21/2018.
 */
public class MergeView {

    public Label label1;
    public Label label2;



    public static MergeView instance;

    public static MergeView getInstance(){
        return instance;
    }


    public synchronized void display(List<File> files, File outputFile) throws IOException {

        Stage stage = new Stage(StageStyle.TRANSPARENT);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MergeView.class.getResource("mergeView.fxml"));




            Parent  root = loader.load();


        // Parent root = FXMLLoader.load(getClass().getResource("mergeView.fxml"));

        stage.setScene(new Scene(root));
        stage.setTitle("Merging files");

        stage.show();

        MergeView tmp = loader.getController();
        instance = tmp;



        MergeLogic task = new MergeLogic(files, outputFile);

        Thread thread = new Thread(task);
       // thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(event -> {
            out.println("Merge complete");
            try {
                for (File file: files)
                    out.println("(" + file.getAbsolutePath()  + ")" + " Part File deleted " + file.delete());
            } catch (Exception e) {
                e.printStackTrace();
            }

            stage.close();
            DownloadPopUp.execSeq();
        });


    }


    public void closeStage(){
        out.println("fuuuuuuuuuuuuuuuuuu*****ji ");
        Stage stage = (Stage)label1.getScene().getWindow();
        label2.setText("DONE");
        stage.close();
    }






}
