package golan.spark.stocks;

import java.lang.management.ManagementFactory;

/**
 * Created by golaniz on 26/05/2016.
 */
public class CheckVer {

    public static void main(String[] args) {
        System.out.println("Java Version: " + System.getProperty("java.version") + " (" + ManagementFactory.getRuntimeMXBean().getVmVersion() + ")");
    }
}
