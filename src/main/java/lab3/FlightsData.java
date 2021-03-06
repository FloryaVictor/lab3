package lab3;

import java.io.Serializable;

public class FlightsData implements Serializable{
    public String[] columns;
    final public static int originAirportIdIndex = 11;
    final public static int destAirportIdIndex = 14;
    final public static int delayIndex = 18;
    final public static int isCanceledIndex = 19;
    final public static String specTrue = "1.00";

    public FlightsData(String data){
        this.columns = StringTools.splitCSV(data);
    }

    public Boolean isCanceled(){
        return columns[isCanceledIndex].equals(specTrue);
    }

    public Float getDelay(){
        String delay = columns[delayIndex];
        try {
            return Float.parseFloat(delay);
        } catch (NumberFormatException e)
        {
            return 0.0f;
        }
    }

    public String getOrigin(){
        return columns[originAirportIdIndex];
    }

    public String getDestination(){
        return columns[destAirportIdIndex];
    }
}
