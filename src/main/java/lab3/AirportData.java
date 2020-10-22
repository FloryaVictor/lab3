package lab3;


import java.io.Serializable;

public class AirportData implements Serializable {
    public String[] columns;
    final public static int codeIndex = 0;
    final public static int nameIndex = 1;
    public AirportData(String data)
    {
        this.columns = StringTools.splitCSV(data);
    }
}
