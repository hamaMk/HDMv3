package worker;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Task;

public class Ghost extends Task{


    private static LongProperty aabs = new  SimpleLongProperty(Ghost.class, "aabs", 0);


    public static long getAabs() {
        return aabs.get();
    }

    public static LongProperty aabsProperty() {
        return aabs;
    }

    public static void setAabs(long aabs) {
        Ghost.aabs.set(aabs);
    }


    @Override
    protected Object call() throws Exception {


        return null;
    }
}
