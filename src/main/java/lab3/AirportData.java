package lab3;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

import java.io.ObjectOutputStream;

public class AirportData implements Serializable {
    public String code, name;
    public AirportData(String code, String name)
    {
        this.code = code;
        this.name = name;
    }
    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
        out.writeObject(this);
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.readObject(AirportData.class);
    }
}
