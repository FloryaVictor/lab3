package lab3;

public class StringTools {
    private final static String csvDelimiter = ",";

    public static String[] splitCSV(String csvLine){
        return removeQuotes(csvLine).split(csvDelimiter);
    }

    public static String removeQuotes(String s)
    {
        return s.replaceAll("\"","");
    }
}
