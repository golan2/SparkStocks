package golan.spark.stocks;

/**
 * Created by golaniz on 20/05/2016.
 */
public class StocksVal {
    public static final String N_A = "N/A";

    private String id   = N_A;
    private String date = N_A;
    private double val1 = Double.NaN;
    private double val2 = Double.NaN;
    private double val3 = Double.NaN;
    private double val4 = Double.NaN;
    private double val5 = Double.NaN;

    public StocksVal(String id, String date, double val1) {
        this.id = id;
        this.date = date;
        this.val1 = val1;
    }

    public StocksVal(String s) {
        try {
            if (s == null) return;
            String[] cols = s.split(",");
            id = cols[0];
            date = cols[1];
            val1 = parseDouble(cols[2]);
            val2 = parseDouble(cols[3]);
            val3 = parseDouble(cols[4]);
            val4 = parseDouble(cols[5]);
            val5 = parseDouble(cols[6]);
        } catch (IndexOutOfBoundsException ignore) {
        }
    }

    private static double parseDouble(String s) {
        try {
            if (s == null || s.length() == 0) return Double.NaN;
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    //================================= Getters

    public String getId() {
        return id;
    }

    public int getDay() {
        if (StocksVal.N_A.equals(date)) return -1;
        String[] cols = date.split("/");
        if (cols.length<1) return -1;
        String day = cols[0].trim();
        try {
            return Integer.parseInt(day);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public int getMonth() {
        if (StocksVal.N_A.equals(date)) return -1;
        String[] cols = date.split("/");
        if (cols.length<1) return -1;
        String day = cols[1].trim();
        try {
            return Integer.parseInt(day);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double getVal1() {
        return val1;
    }
    //================================= Override

    @Override
    public String toString() {
        System.out.println("["+id+"]");
        return "StocksVal{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", val1=" + val1 +
                ", val2=" + val2 +
                ", val3=" + val3 +
                ", val4=" + val4 +
                ", val5=" + val5 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StocksVal rhs = (StocksVal) o;
        return this.getMonth()==rhs.getMonth();
    }

    @Override
    public int hashCode() {
        return getDay();
    }

    //================================= Static calc

    public static boolean isFirstDayOfMonth(String stockAsString) {
        //not the best-performing code you will find but it does the work (theoretically, we could have filter entries without constructing StocksVal objects)
        return new StocksVal(stockAsString).getDay()==1;
    }

    public static StocksVal sumByVal1(StocksVal lhs, StocksVal rhs) throws Exception {
        return new StocksVal("sumByVal1", "sumByVal1", lhs.val1+rhs.val1);
    }

    public static StocksVal merge(StocksVal lhs, StocksVal rhs) {
        String newId = "" + Integer.parseInt(lhs.id) + 10000 + Integer.parseInt(rhs.id);

        StocksVal result = new StocksVal(newId, lhs.date, Math.max(lhs.val1, rhs.val1));
        result.val2 = Math.max(lhs.val2, rhs.val2);
        result.val3 = Math.max(lhs.val3, rhs.val3);
        result.val4 = Math.max(lhs.val4, rhs.val4);
        result.val5 = Math.max(lhs.val5, rhs.val5);

        return result;
    }


}
