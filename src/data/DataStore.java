package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStore implements Serializable {

    private ArrayList<Data> bank;

    public DataStore(){
        bank = new  ArrayList<>();
    }

    public void add(Data data){
        bank.add(data);
    }

    public ArrayList<Data> getBank(){
        return bank;
    }

}
