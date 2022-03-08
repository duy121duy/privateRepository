package main;

import entities.ModelLog;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import service.ReadFileText;
import service.WriteFileParquet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class MainLocal {
    public static List<String> getFiles() throws IOException {
        return Files.walk(Paths.get("sample-text"))
                .filter(Files::isRegularFile)
                .map(Path::toString).collect(Collectors.toList());
    }

    public static void writeParquetFile() throws IOException, ParseException {
        System.out.println("START PROCESSING........");

        SparkConf conf = new SparkConf().setAppName("Read file text from HDFS").setMaster("local");
        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);

        ReadFileText readFileText = new ReadFileText(getFiles(), javaSparkContext);
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
//
        SparkSession spark = SparkSession.builder()
                .appName("Read file parquet to HDFS")
                .master("local")
                .getOrCreate();
//        Dataset<Row> parquetFile = spark.read().parquet("hdfs://namenode:9000/user/duy/data/pageviewlog");
        Dataset<Row> parquetFile = spark.read().parquet("pageviewlog");
//        parquetFile.createOrReplaceTempView("parquetFileDF");
//
//        parquetFile.show();
//        parquetFile.show();
//        execution1(spark, parquetFile);
//        execution2(spark, parquetFile);
        execution3(spark, parquetFile);

        spark.stop();
    }

    public static void execution1(SparkSession spark, Dataset<Row> parquetFile) {
//        Dataset<Row> data1 = parquetFile.groupBy("guid", "domain").count();
//        data1.createOrReplaceTempView("data1DF");
//        Dataset<Row> data2 = data1.groupBy("guid").max("count");
//        data2.createOrReplaceTempView("data2DF");
//
//        Dataset<Row> data3 = spark.sql("SELECT df1.guid, df1.domain FROM data1DF AS df1 JOIN (SELECT guid, max(count) AS maxcount FROM data2DF) AS df2 " +
//                "ON df1.guid = df2.guid AND df1.count = df2.maxcount");
//
//        data3.show();
        parquetFile.createOrReplaceTempView("parquetFileDF");
        Dataset<Row> guidDF = spark.sql("SELECT * FROM (SELECT p.guid, p.domain, p.count, RANK() OVER( \n " +
                "PARTITION BY p.guid \n" +
                "ORDER BY p.count DESC \n" +
                ") rank FROM (SELECT guid, domain, COUNT(*) AS count FROM parquetFileDF GROUP BY guid, domain) AS p) AS r WHERE r.rank IN(1)");
        guidDF.show(1000);
    }

    public static void execution2(SparkSession spark, Dataset<Row> parquetFile) {
//        parquetFile.createOrReplaceTempView("parquetFileDF");
//        Dataset<Row> guidDF = spark.sql("SELECT a.ip, COUNT(a.guid) AS count FROM (SELECT DISTINCT ip, guid FROM parquetFileDF) AS a GROUP BY a.ip ORDER BY count DESC");
//        guidDF.show();
        Dataset<Row> data1 = parquetFile.groupBy("guid", "ip").count();
        Dataset<Row> data2 = data1.groupBy("ip").count();
        data2 = data2.orderBy(data2.col("count").desc());
        data2.show();
    }

    public static void execution3(SparkSession spark, Dataset<Row> parquetFile) {
        parquetFile.createOrReplaceTempView("pageviewLog");

        Dataset<Row> sqlDF = spark.sql("SELECT * FROM pageviewLog " +
                "WHERE ABS(unix_timestamp(timeCreate) - unix_timestamp(cookieCreate)) <= 1800");
        sqlDF.show();
    }
}
