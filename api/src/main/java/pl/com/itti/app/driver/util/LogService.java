package pl.com.itti.app.driver.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 * Class to handle the log
 * @author rorczyk
 */
public class LogService {

    static boolean isSaveToFile = true;
    static String SysPath = new File(".").getAbsolutePath();
    private static TreeMap logMap = new TreeMap<Long, String[]>();

    public static void saveToLogFile(String s){
        System.out.println(s);
        Date d = new Date();
        String fileName = "LogService" + dateTimeToStringFileHour(d) + ".txt";
        File logf = new File(SysPath + File.separator +"_log" + File.separator + fileName);
        writeDateTimeAndLineToFile(logf, s);
        saveToTestLogFile(s);
    }

    public static void saveToTestLogFile(String s) {
        Date d = new Date();
        String fileName = "TestLog" + dateTimeToStringFileHour(d) + ".txt";
        File logf = new File(SysPath + File.separator + "_log" + File.separator + fileName);
        writeDateTimeAndLineToFile(logf, s);
    }

    public static void saveExceptionToLogFile(Exception ex){
        ex.printStackTrace();
        Date d = new Date();
        String fileName = "LogException" + dateTimeToStringFileHour(d) + ".txt";
        File logf = new File(SysPath + File.separator +"_log" + File.separator + fileName);
        String exs = exceptionToString(ex);
        int i =0;
        if(isSaveToFile) {
            writeDateTimeAndLineToFile(logf, exs);
        }
    }


    public static void saveToConvertLogFile(String value, Exception ex){
        String s = exceptionToString(ex);
        if(value!=null){
            s = "value=>" + value + "   " + s;
        }else{
            s = "value=>" + "null" + "   " + s;
        }
        Date d = new Date();
        String fileName = "LogConvert" + dateTimeToStringFileHour(d) + ".txt";
        File logf = new File(SysPath + File.separator +"_log" + File.separator + fileName);
        writeDateTimeAndLineToFile(logf, s);
    }


    public static void saveToConvertLogFile(Exception ex){
       saveToConvertLogFile("no value data", ex);
    }


    private static String exceptionToString(Exception ex ){
            String r = null;
            CharArrayWriter cw = new CharArrayWriter();
            PrintWriter w = new PrintWriter(cw);
            ex.printStackTrace(w);
            w.close();
            String trace = cw.toString();
            r = ex.toString() + ": " + trace;
        return r;
    }

    public static void saveSqlExceptionToLogFile(Exception ex, String Sql){
        Date d = new Date();
        String fileName = "LogException" + dateTimeToStringFileHour(d) + ".txt";
        File logf = new File(SysPath + File.separator +"_log" + File.separator + fileName);
        String exs = exceptionToString(ex);
        writeDateTimeAndLineToFile(logf, exs);
    }

        public static Long getDurationMilis(Date startTime) {
        if (startTime == null) return 0l;
        Date ts = new Date();
        return ts.getTime() - startTime.getTime();
    }


    private static String dateTimeToStringFileHour(Date p)
    {
        String r = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH");
            r = sdf.format(p);
        }
        catch (Exception e) {
            LogService.saveToConvertLogFile(e);
        }
        return r;
    }

    private static boolean writeDateTimeAndLineToFile(File of, String line) {
        Writer output;
        try {
            if (createFile(of)) {
                createFile(of);
                output = new BufferedWriter(new FileWriter(of, true));
                output.append(System.getProperty("line.separator")).append(new Date().toString()).append("; ").append(line);
                output.close();
                return true;
            }
        } catch (IOException ex) {
            LogService.saveExceptionToLogFile(ex);
        }
        return false;
    }

    private static boolean createFile(File of) {
        boolean r = false;
        File pf = of.getParentFile();
        try {
            if (!pf.exists()) {
                pf.mkdirs();
            }
            if (!of.exists()) {
                of.createNewFile();
                return true;
            } else {
                if (of.isFile()) {
                    return true;
                }
            }
        } catch (IOException ex) {
            LogService.saveExceptionToLogFile(ex);
        }
        return r;
    }
}
