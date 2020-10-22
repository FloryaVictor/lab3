package lab3;

import java.io.Serializable;

public class FlightsData implements Serializable{
    public String[] columns;

    public FlightsData(String data){
        this.columns = StringTools.splitCSV(data);
    }
}
