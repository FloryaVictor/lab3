package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;


public class Main {
    public static void main(String[] args) throws Exception
    {
        if (args.length != 3) {
            System.err.println("Usage: lab3.Main <input path table> <input path dict> <output path>");
            System.exit(-1);
        }
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flights = sc.textFile(args[0]);
        JavaRDD<String> airports = sc.textFile(args[1]);

        //removing csv headers
        final String flightsHeader = flights.first();
        final String airportsHeader = airports.first();
        flights = flights.filter(line -> !line.equals(flightsHeader));
        airports = airports.filter(line -> !line.equals(airportsHeader));

        //creating broadcast for airports
        JavaPairRDD<String, AirportData> airportsDataByCode = airports.mapToPair(p ->
            new Tuple2<>(StringTools.splitCSV(p)[0], new AirportData(StringTools.splitCSV(p)[1]))
        );
        
    }
}

