package lab3;

import java.io.Serializable;

public class FlightsData implements Serializable{
    public String[] columns;
    final public static int delayIndex = 18;
    final public static int isCanceled
    public FlightsData(String data){
        this.columns = StringTools.splitCSV(data);
    }
}
