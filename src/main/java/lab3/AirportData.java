package lab3;


import java.io.Serializable;

public class AirportData implements Serializable {
    public String code, name;
    public AirportData(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
}
