package service;

import entities.*;
import entities.hbase.DeptEmpHbase;
import entities.hbase.EmpHbase;
import entities.hbase.SalaryHbase;
import entities.hbase.TitleHbase;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.hibernate.Session;
import utils.HibernateUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HbaseService {
    private static List<ModelLog> listModelLog = new ArrayList<>();

    public static List<String> getFiles() throws IOException {
        return Files.walk(Paths.get("sample-text"))
                .filter(Files::isRegularFile)
                .map(Path::toString).collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException, ParseException {
        // Instantiating configuration class
        Configuration config = HBaseConfiguration.create();

        config.set("zookeeper.znode.parent", "/hbase");
        config.set("hbase.zookeeper.quorum", "127.0.0.1");
        config.set("hbase.zookeeper.property.clientPort", "2181");

        Connection connection = ConnectionFactory.createConnection(config);
        Admin admin = connection.getAdmin();

//        SparkConf conf = new SparkConf().setAppName("Read file text from HDFS").setMaster("local");
//
//        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);
//
//        ReadFileText readFileText = new ReadFileText(getFiles(), javaSparkContext);
//        listModelLog.addAll(readFileText.getListModelLog());
//
//        if (!admin.isTableAvailable(TableName.valueOf("duy:pageviewlog"))) {
//            HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("duy:pageviewlog"));
//            hTableDescriptor.addFamily(new HColumnDescriptor("info"));
//            admin.createTable(hTableDescriptor);
//            if (admin.isTableAvailable(TableName.valueOf("duy:pageviewlog"))) {
//                System.out.println("Table created");
//            }
//        }

//        Table table = connection.getTable(TableName.valueOf("duy:pageviewlog"));

//        Map<Long, List<ModelLog>> stringModelLogMap = new HashMap<>();
//
//        for (ModelLog modelLog : listModelLog) {
//            if (!stringModelLogMap.containsKey(modelLog.getGuid())) {
//                stringModelLogMap.put(modelLog.getGuid(), new ArrayList<>());
//            }
//            stringModelLogMap.get(modelLog.getGuid()).add(modelLog);
//        }
//
//        for (Long number : stringModelLogMap.keySet()) {
//            long count = 0;
//            for (ModelLog modelLog : stringModelLogMap.get(number)) {
//                String rowKey = modelLog.getGuid() + "-" + count++;
//                Put put = new Put(Bytes.toBytes(rowKey));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("timeCreate"),
//                        Bytes.toBytes(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(modelLog.getTimeCreate())));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cookieCreate"),
//                        Bytes.toBytes(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(modelLog.getCookieCreate())));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("browserCode"),
//                        Bytes.toBytes(modelLog.getBrowserCode()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("browserVer"),
//                        Bytes.toBytes(modelLog.getBrowserVer()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("osCode "),
//                        Bytes.toBytes(modelLog.getOsCode()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("osVer "),
//                        Bytes.toBytes(modelLog.getOsVer()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("ip"),
//                        Bytes.toBytes(modelLog.getIp()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("locId"),
//                        Bytes.toBytes(modelLog.getLocId()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("domain"),
//                        Bytes.toBytes(modelLog.getDomain()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("siteId"),
//                        Bytes.toBytes(modelLog.getSiteId()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("cId"),
//                        Bytes.toBytes(modelLog.getcId()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("path"),
//                        Bytes.toBytes(modelLog.getPath()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("referer"),
//                        Bytes.toBytes(modelLog.getReferer()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("flashVersion"),
//                        Bytes.toBytes(modelLog.getFlashVersion()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("jre"),
//                        Bytes.toBytes(modelLog.getJre()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("sr"),
//                        Bytes.toBytes(modelLog.getSr()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("sc"),
//                        Bytes.toBytes(modelLog.getSc()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("geographic"),
//                        Bytes.toBytes(modelLog.getGeographic()));
//                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("category"),
//                        Bytes.toBytes(modelLog.getCategory()));
//                table.put(put);
//            }
//        }

//        Scan scan = new Scan();
//        ResultScanner scanner = table.getScanner(scan);
//
//        for (Result result : scanner) {
//            System.out.print(Bytes.toString(result.getRow()));
//            System.out.println(" = " + Bytes.toString(result.getValue(Bytes.toBytes("info"),
//                    Bytes.toBytes("domain"))));
//        }

//        System.out.println("Domains for guid 8687753366245917174 : ");
//
//        for (String str : execution1(table, "8687753366245917174")) {
//            System.out.println(str);
//        }
//
//        System.out.println("--------------------------------------------------");
//
//        for (IP ip : execution2(table, "8687753366245917174")) {
//            System.out.println("Most used IP of guid 8687753366245917174 :");
//            System.out.println(ip);
//        }
//
//        System.out.println("--------------------------------------------------");
//
//        System.out.print("Last access time of guid 8687753366245917174 : ");
//        System.out.println(execution3(table, "8687753366245917174"));
//
//        System.out.println("--------------------------------------------------");

//        System.out.println("Guids timeCreate - cookieCreate <= 30 min :");
//        for (Result result : execution4(table)) {
//            System.out.print(Bytes.toString(result.getRow()));
//            System.out.println(" = " + Bytes.toString(result.getValue(Bytes.toBytes("info"),
//                    Bytes.toBytes("cookieCreate"))) + " " + Bytes.toString(result.getValue(Bytes.toBytes("info"),
//                    Bytes.toBytes("timeCreate"))));
//        }

//        kMeanExample("kmean.txt");

        Table table = connection.getTable(TableName.valueOf("employees:employees"));

//        putEmpHbaseToHbase(table);

        System.out.println(getEmployeesInDeparment(table, "d005"));

//        Table table = connection.getTable(TableName.valueOf("employees:titles"));

//        putTitleHbase(table);

//        System.out.println(getTitles(table, "10001-"));

//        Table table = connection.getTable(TableName.valueOf("employees:salaries"));

//        putSalaryHbase(table);

//        System.out.println(totalSalaries(table, "1988/06/01"));
        connection.close();
    }

    public static ResultScanner execution(Table table, String guid) throws IOException {
        Filter filter = new PrefixFilter(Bytes.toBytes(guid));
        Scan scan = new Scan();
        scan.setFilter(filter);
        return table.getScanner(scan);
    }

    public static Set<String> execution1(Table table, String guid) throws IOException {
        Set<String> domains = new HashSet<>();
        for (Result result : execution(table, guid)) {
            domains.add(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                    Bytes.toBytes("referer"))));
        }
        return domains;
    }

    public static List<IP> execution2(Table table, String guid) throws IOException {
        List<IP> ips = new ArrayList<>();
        IP ip = null;
        for (Result result : execution(table, guid)) {
            ip = new IP(Bytes.toLong(result.getValue(Bytes.toBytes("info"),
                    Bytes.toBytes("ip"))), 1);
            if (ips.contains(ip)) {
                ips.get(ips.indexOf(ip)).setCount(ips.get(ips.indexOf(ip)).getCount() + 1);
            } else {
                ips.add(ip);
            }
        }
        ips.sort((o1, o2) -> {
            return o2.getCount() - o1.getCount();
        });
        return ips;
    }

    public static Date execution3(Table table, String guid) throws IOException, ParseException {
        int count = 0;
        Date resultDate = null;
        for (Result result : execution(table, guid)) {
            Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .parse(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                            Bytes.toBytes("timeCreate"))));

            if (count == 0) {
                resultDate = date;
                count++;
            } else {
                if (resultDate.compareTo(date) < 0) {
                    resultDate = date;
                }
            }
        }
        return resultDate;
    }

    public static List<Result> execution4(Table table) throws IOException, ParseException {
        List<Result> results = new ArrayList<>();
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            Date timeCreate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .parse(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                            Bytes.toBytes("timeCreate"))));
            Date cookieCreate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .parse(Bytes.toString(result.getValue(Bytes.toBytes("info"),
                            Bytes.toBytes("cookieCreate"))));
            long difference = Math.abs(timeCreate.getTime() - cookieCreate.getTime());
            if (difference <= 1800000) {
                results.add(result);
            }
        }
        return results;
    }

    public static void kMeanExample(String path) throws IOException {
        SparkConf config = new SparkConf().setAppName("JavaKMeansExample")
                .setMaster("local")
                .set("spark.executor.memory", "3g")
                .set("spark.driver.memory", "3g");

        JavaSparkContext javaSparkContext = new JavaSparkContext(config);

        JavaRDD<String> data = javaSparkContext.textFile(path);
        JavaRDD<Vector> parsedData = data.map(s -> {
            String[] strings = s.split(" ");
            double[] values = new double[strings.length];
            for (int i = 0; i < strings.length; i++) {
                values[i] = Double.parseDouble(strings[i]);
            }
            return Vectors.dense(values);
        });
        parsedData.cache();

        int numClusters = 3;
        int numIterations = 20;
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

        System.out.println("\n*****Training*****");
        int clusterNumber = 0;
        for (Vector center : clusters.clusterCenters()) {
            System.out.println("Cluster center for Clsuter " + (clusterNumber++) + " : " + center);
        }

        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);

        // prediction for test vectors
        System.out.println("\n*****Prediction*****");
        System.out.println("[9.0, 0.6, 9.0] belongs to cluster " + clusters.predict(Vectors.dense(9.0, 0.6, 9.0)));
        System.out.println("[0.2, 0.5, 0.4] belongs to cluster " + clusters.predict(Vectors.dense(0.2, 0.5, 0.4)));
        System.out.println("[2.8, 1.6, 6.0] belongs to cluster " + clusters.predict(Vectors.dense(2.8, 1.6, 6.0)));

        javaSparkContext.stop();
    }

    public static List<EmpHbase> getEmpHbase() {
        Session session = null;
        List<EmpHbase> empHbases = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();

            List<DeptEmpHbase> deptEmpHbases = session.createQuery("FROM DeptEmp", DeptEmp.class).list()
                    .stream().map(deptEmp -> {
                        String duration = new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getFromDate())
                                + "-" + new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getToDate());
                        return new DeptEmpHbase(deptEmp.getEmpNo(), deptEmp.getDeptNo(), duration);
                    }).collect(Collectors.toList());

            List<DeptEmpHbase> deptManagerHbases = session.createQuery("FROM DeptManager ", DeptManager.class).list()
                    .stream().map(deptEmp -> {
                        String duration = new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getFromDate())
                                + "-" + new SimpleDateFormat("yyyy/MM/dd").format(deptEmp.getToDate());
                        return new DeptEmpHbase(deptEmp.getEmpNo(), deptEmp.getDeptNo(), duration);
                    }).collect(Collectors.toList());

            EmpHbase empHbase = null;
            Employee employee = null;

            empHbases = new ArrayList<>();

            for (DeptEmpHbase deptEmpHbase : deptEmpHbases) {
                employee = employees.get(employees.indexOf(new Employee(deptEmpHbase.getEmpNo())));
                DeptEmpHbase deptEmpHbase1 = deptEmpHbases.get(deptEmpHbases.indexOf(new DeptEmpHbase(deptEmpHbase.getEmpNo(),
                        String.valueOf(deptEmpHbase.getDeptNo()), null)));
                if (deptEmpHbase1 == null) {
                    empHbases.add(new EmpHbase(deptEmpHbase.getDeptNo() + "-" + deptEmpHbase.getEmpNo(),
                            new SimpleDateFormat("yyyy/MM/dd").format(employee.getBirthDate()), employee.getFirstName(),
                            employee.getLastName(), employee.getGender(), new SimpleDateFormat("yyyy/MM/dd").format(employee.getHireDate()),
                            Stream.of(deptEmpHbase.getDuration()).collect(Collectors.toList()), null));
                } else {
                    empHbases.add(new EmpHbase(deptEmpHbase.getDeptNo() + "-" + deptEmpHbase.getEmpNo(),
                            new SimpleDateFormat("yyyy/MM/dd").format(employee.getBirthDate()), employee.getFirstName(),
                            employee.getLastName(), employee.getGender(), new SimpleDateFormat("yyyy/MM/dd").format(employee.getHireDate()),
                            Stream.of(deptEmpHbase.getDuration()).collect(Collectors.toList()), deptEmpHbase1.getDuration()));
                }
            }

            empHbase = new EmpHbase("d001-110039");
            System.out.println(empHbases.get(empHbases.indexOf(empHbase)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empHbases;
    }

    public static List<TitleHbase> getTitle() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            List<TitleHbase> titleHbases = session.createQuery("FROM Title", Title.class).list()
                    .stream().map(title -> {
                        String duration = new SimpleDateFormat("yyyy/MM/dd").format(title.getFromDate())
                                + "-" + new SimpleDateFormat("yyyy/MM/dd").format(title.getToDate());
                        return new TitleHbase(title.getEmpNo(), title.getTitle(), duration);
                    }).collect(Collectors.toList());
            TitleHbase titleHbase = new TitleHbase(10001, "Senior Engineer", null);
            System.out.println(titleHbases.get(titleHbases.indexOf(titleHbase)));
            return titleHbases;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<SalaryHbase> getSalary() {
        Session session = null;
        try {
            session = HibernateUtils.getSessionFactory().openSession();
            List<SalaryHbase> salaryHbases = session.createQuery("FROM Salary", Salary.class).list()
                    .stream().map(salary -> {
                        String duration = new SimpleDateFormat("yyyy/MM/dd").format(salary.getFromDate())
                                + "-" + new SimpleDateFormat("yyyy/MM/dd").format(salary.getToDate());
                        return new SalaryHbase(salary.getEmpNo(), salary.getSalary(), duration);
                    }).collect(Collectors.toList());
            SalaryHbase salaryHbase = new SalaryHbase(10001, 0, "1986/06/26-1987/06/26");
            System.out.println(salaryHbases.get(salaryHbases.indexOf(salaryHbase)));
            return salaryHbases;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putTitleHbase(Table table) throws IOException {
        for (TitleHbase titleHbase : getTitle()) {
            Put put = new Put(Bytes.toBytes(titleHbase.getEmpNo() + "-" + titleHbase.getTitle()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("duration"),
                    Bytes.toBytes(titleHbase.getDuration()));
            table.put(put);
        }
    }

    public static void putSalaryHbase(Table table) throws IOException {
        for (SalaryHbase salaryHbase : getSalary()) {
            Put put = new Put(Bytes.toBytes(salaryHbase.getEmpNo() + "-" + salaryHbase.getDuration()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("salary"),
                    Bytes.toBytes(salaryHbase.getSalary()));
            table.put(put);
        }
    }

    public static void putEmpHbaseToHbase(Table table) throws IOException {
        for (EmpHbase empHbase : getEmpHbase()) {
            Put put = new Put(Bytes.toBytes(empHbase.getEmpNoDeptNo()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("birthDate"),
                    Bytes.toBytes(empHbase.getBirthDate()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("firstName"),
                    Bytes.toBytes(empHbase.getFirstName()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("lastName"),
                    Bytes.toBytes(empHbase.getLastName()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("gender"),
                    Bytes.toBytes(empHbase.getGender()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("hiredDate"),
                    Bytes.toBytes(empHbase.getHireDate()));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("deptEmp-0"),
                    Bytes.toBytes(empHbase.getDurationEmp().get(0)));
            if (StringUtils.isEmpty(empHbase.getDurationManager())) {
                put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("deptManager"),
                        Bytes.toBytes(empHbase.getDurationManager()));
            }
            table.put(put);
        }
    }

    public static List<EmpHbase> getEmployeesInDeparment(Table table, String deptNo) throws IOException {
        List<EmpHbase> empHbases = new ArrayList<>();
        Filter filter = new PrefixFilter(Bytes.toBytes(deptNo));
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result : resultScanner) {
            empHbases.add(new EmpHbase(Bytes.toString(result.getRow()),
                    Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("birthDate"))),
                    Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("firstName"))),
                    Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("lastName"))),
                    Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("gender"))),
                    Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("hiredDate"))),
                    Stream.of(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("deptEmp-0")))).collect(Collectors.toList()),
                    Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("deptManager")))));
        }
        return empHbases;
    }

    public static List<String> getTitles(Table table, String empNo) throws IOException {
        List<String> titles = new ArrayList<>();
        Filter filter = new PrefixFilter(Bytes.toBytes(empNo));
        Scan scan = new Scan();
        scan.setFilter(filter);
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result : resultScanner) {
            titles.add(Bytes.toString(result.getRow()).split("-")[1] + " Duration : "
                    + Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("duration"))));
        }
        return titles;
    }

    public static long totalSalaries(Table table, String monthOfYear) throws IOException, ParseException {
        long totalSalaries = 0;
        Scan scan = new Scan();
        ResultScanner resultScanner = table.getScanner(scan);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        for (Result result : resultScanner) {
            String[] strings = Bytes.toString(result.getRow()).split("-");
            if (simpleDateFormat.parse(monthOfYear).compareTo(simpleDateFormat.parse(strings[1])) >= 0
                    && simpleDateFormat.parse(monthOfYear).compareTo(simpleDateFormat.parse(strings[2])) < 0) {
                totalSalaries += Bytes.toLong(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("salary")));
            }
        }
        return totalSalaries;
    }
}
