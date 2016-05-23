package golan.spark.stocks;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.HashMap;

/**
 * Created by golaniz on 23/05/2016.
 */
public class ReadFromLocalFile {

    public static final String STOCKS_FILE_LINUX = "/rdd/StocksByDate.utxt";
    public static final String STOCKS_FILE_WIN = "C:\\Users\\golaniz\\Documents\\GitHub\\HelloSpark\\src\\main\\resources\\StocksByDate.utxt";


    public static void main(String[] args) {
        System.out.println("Start");

        SparkConf sparkConf = new SparkConf().setAppName("ReadFromLocalFile");
        String stocksFile;
        if (System.getProperty("os.name").startsWith("Windows")) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("HADOOP_HOME","C:\\classpath\\hadoop\\hadoop-common-2.2.0-bin-master");
            map.put("SPARK_LOCAL_IP","127.0.0.1");
            map.put("SPARK_USER","golaniz");
            Utils.setEnvironmentVariable(map);

            sparkConf.setMaster("local[*]");
            stocksFile = STOCKS_FILE_WIN;
        }
        else {
            stocksFile = STOCKS_FILE_LINUX;
        }

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> all = sparkContext.textFile(stocksFile);

        JavaRDD<StocksVal> stocks = all.map(StocksVal::new);

        JavaPairRDD<StocksVal, StocksVal> cartesian = (JavaPairRDD<StocksVal, StocksVal>) stocks.cartesian(stocks);
        JavaRDD<StocksVal> all2 = (JavaRDD<StocksVal>) cartesian.map(ReadFromLocalFile::mergeStocks);

        System.out.println("all="+all.count());
        System.out.println("all2="+all2.count());
        System.out.println("End");
    }

    private static StocksVal mergeStocks(Tuple2<StocksVal, StocksVal> t) {
        return StocksVal.merge(t._1(), t._2());
    }

}
