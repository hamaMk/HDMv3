package merger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

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


    public synchronized void display(ProcessBuilder processBuilder, String path, String fileName) throws IOException {

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
        MergeLogic task = new MergeLogic(processBuilder);

        Thread thread = new Thread(task);
       // thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(event -> {
            File f1 = new File(path+"part1");
            f1.delete();
            File f2 = new File(path+"part2");
            f2.delete();
            File f3 = new File(path+"part3");
            f3.delete();
            File f4 = new File(path+"part4");
            f4.delete();
            File f5 = new File(path+"part5");
            f5.delete();
            File f6 = new File(path+"part6");
            f6.delete();
          //  closeStage();

            stage.close();
        });


    }


    public void closeStage(){
        out.println("fuuuuuuuuuuuuuuuuuu*****ji ");
        Stage stage = (Stage)label1.getScene().getWindow();
        label2.setText("DONE");
        stage.close();
    }






}
