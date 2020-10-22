package lab3;


import java.io.Serializable;

public class AirportData implements Serializable {
    public String name;
    public AirportData(String name)
    {
        this.name = name;
    }
}
