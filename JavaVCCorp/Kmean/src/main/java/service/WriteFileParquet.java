package service;

import entities.ModelLog;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.List;

public class WriteFileParquet {
    private List<ModelLog> listModelLog;

    public WriteFileParquet(List<ModelLog> listModelLog) {
        setListModelLog(listModelLog);
    }

    public List<ModelLog> getListModelLog() {
        return listModelLog;
    }

    public void setListModelLog(List<ModelLog> listModelLog) {
        this.listModelLog = listModelLog;
    }

    public void writeDataToPath() {
//        SparkSession spark = SparkSession.builder()
//                .appName("Write file parquet to HDFS")
//                .master("local")
//                .getOrCreate();

        SparkSession spark = SparkSession.builder()
                .appName("Write file parquet to HDFS")
                .master("spark://sparkmaster:7077")
                .getOrCreate();

        Dataset<Row> listModelLogDF = spark.createDataFrame(listModelLog, ModelLog.class);
        listModelLogDF.write().parquet("hdfs://namenode:9000/user/duy/data/pageviewlog");
//        listModelLogDF.write().parquet("pageviewlog");

        spark.stop();
    }
}
