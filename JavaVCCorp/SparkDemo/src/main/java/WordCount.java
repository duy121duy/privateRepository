import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class WordCount {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("Word Counter");
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        JavaRDD<String> inputFile = javaSparkContext.textFile("input.txt");
        JavaRDD<String> wordsFromFile = inputFile.flatMap(s -> Arrays.asList(s.split(" ")).iterator());
        JavaPairRDD<String, Integer> countData = wordsFromFile.mapToPair(s -> new Tuple2<>(s,1)).reduceByKey(Integer::sum);
        countData.saveAsTextFile("OutputPath1");
        javaSparkContext.close();
    }
}
