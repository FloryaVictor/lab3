package lab3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Iterator;
import java.util.Map;


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
            new Tuple2<>(StringTools.splitCSV(p)[AirportData.codeIndex],
                    new AirportData(p))
        );
        final Broadcast<Map<String, AirportData>> airportsBroadcasted = sc.broadcast(airportsDataByCode.collectAsMap());


        JavaPairRDD<Tuple2<String, String>, FlightsData> flightsDataByNames = flights.mapToPair(f -> {
            FlightsData fdata = new FlightsData(f);
            Tuple2<String, String> names = new Tuple2<>(fdata.getOrigin(),fdata.getDestination());
            return new Tuple2<>(names, fdata);
        });

        flightsDataByNames.groupByKey()
                .mapValues(it -> {
                    Iterator<FlightsData> iter = it.iterator();
                    int count = 0, size = 0;
                    float maxDelay = Float.MIN_VALUE;
                    for (Iterator<FlightsData> iterator = iter; iterator.hasNext(); ) {
                        FlightsData fdata = iterator.next();
                        size++;
                        if (fdata.isCanceled() || fdata.getDelay() > 0.0f) count++;
                        maxDelay = Float.max(maxDelay, fdata.getDelay());
                    }
                    return new Tuple2<>(maxDelay, (count * 100f) / size);
                })
                .map(data ->{
                    String originAirportName = airportsBroadcasted.value().get(data._1()._1()).getName();
                    String destAirportName = airportsBroadcasted.value().get(data._1()._2()).getName();
                    return new Tuple2<>(
                            new Tuple2<>(originAirportName, destAirportName),
                            data._2()
                    );
                }).saveAsTextFile(args[2]);
    }
}

