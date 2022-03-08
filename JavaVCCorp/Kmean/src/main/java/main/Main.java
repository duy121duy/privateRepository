package main;

import entities.ModelLog;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import service.ReadFileText;
import service.WriteFileParquet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static List<String> listAllFilePath(Path hdfsFilePath, FileSystem fs) throws IOException {
        List<String> filePathList = new ArrayList<>();
        Queue<Path> fileQueue = new LinkedList<>();
        fileQueue.add(hdfsFilePath);
        while (!fileQueue.isEmpty()) {
            Path filePath = fileQueue.remove();
            if (fs.isFile(filePath)) {
                filePathList.add(filePath.toString());
            } else {
                FileStatus[] fileStatus = fs.listStatus(filePath);
                for (FileStatus fileStat : fileStatus) {
                    fileQueue.add(fileStat.getPath());
                }
            }
        }
        return filePathList;
    }

    public static List<String> getFiles() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://namenode:9000");
        FileSystem fileSystem = FileSystem.get(configuration);
        Path hdfsWritePath = new Path("/user/duy/data/sample-data");

        return listAllFilePath(hdfsWritePath, FileSystem.get(configuration));
    }

    public static void writeParquetFile() throws IOException, ParseException {
        System.out.println("START PROCESSING........");

        SparkConf conf = new SparkConf().setAppName("Read file text from HDFS").setMaster("spark://sparkmaster:7077");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);

        List<String> strings = Stream.of("hdfs://namenode:9000/user/duy/data/sample-data/pt-v-1533869954405.dat"
                , "hdfs://namenode:9000/user/duy/data/sample-data/pt-v-1533870013310.dat").collect(Collectors.toList());

        ReadFileText readFileText = new ReadFileText(strings, javaSparkContext);
        List<ModelLog> listModelLog = readFileText.getListModelLog();

        for (ModelLog model : listModelLog) {
            System.out.println(model);
        }

        WriteFileParquet writer = new WriteFileParquet(listModelLog);
        writer.writeDataToPath();

        System.out.println("END PROCESSING.....");
        javaSparkContext.stop();
    }

    public static void main(String[] args) throws IOException, ParseException {
        writeParquetFile();

        SparkSession spark = SparkSession.builder()
                .appName("Read file parquet to HDFS")
                .master("spark://sparkmaster:7077")
                .getOrCreate();
        Dataset<Row> parquetFile = spark.read().parquet("hdfs://namenode:9000/user/duy/data/pageviewlog");
        parquetFile.show();

        execution1(spark, parquetFile);
        execution2(spark, parquetFile);
        execution3(spark, parquetFile);

        spark.stop();
    }

    public static void execution1(SparkSession spark, Dataset<Row> parquetFile) {
        parquetFile.createOrReplaceTempView("parquetFileDF");
        Dataset<Row> guidDF = spark.sql("SELECT * FROM (SELECT p.guid, p.domain, p.count, RANK() OVER( \n " +
                "PARTITION BY p.guid \n" +
                "ORDER BY p.count DESC \n" +
                ") rank FROM (SELECT guid, domain, COUNT(*) AS count FROM parquetFileDF GROUP BY guid, domain) AS p) AS r WHERE r.rank IN(1)");
        guidDF.show(1000);
    }

    public static void execution2(SparkSession spark, Dataset<Row> parquetFile) {
        Dataset<Row> data1 = parquetFile.groupBy("guid", "ip").count();
        Dataset<Row> data2 = data1.groupBy("ip").count();
        data2 = data2.orderBy(data2.col("count").desc());
        data2.show(1000);
    }

    public static void execution3(SparkSession spark, Dataset<Row> parquetFile) {
        parquetFile.createOrReplaceTempView("pageviewLog");

        Dataset<Row> sqlDF = spark.sql("SELECT * FROM pageviewLog " +
                "WHERE ABS(unix_timestamp(timeCreate) - unix_timestamp(cookieCreate)) <= 1800");
        sqlDF.show(1000);
    }
}
