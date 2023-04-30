package telran.multithreading.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{
    // ANSI escape code
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private static HashMap<Level, String[]> colorMap = new HashMap<>();

    {
    	colorMap.put(Level.WARNING, new String[] {ANSI_RED, ANSI_BLACK});
    	colorMap.put(Level.SEVERE, new String[] {ANSI_RED, ANSI_RED});
    	colorMap.put(Level.INFO, new String[] {ANSI_GREEN, ANSI_BLUE});
    	colorMap.put(Level.FINE, new String[] {ANSI_BLACK, ANSI_BLACK});   
    	colorMap.put(Level.CONFIG, new String[] {ANSI_CYAN, ANSI_BLACK}); 
    }
    @Override
    public String format(LogRecord record)
    {    	
    	Level level = record.getLevel();
        StringBuilder builder = new StringBuilder();      

        builder.append(colorMap.get(level)[0]);
        if (level.equals(Level.CONFIG)) {
        	builder.append("-----STATISTICS-----\n");
        } else {
        	builder.append("[");
        builder.append(calcDate(record.getMillis()));
        builder.append("] ");
        }

        builder.append(colorMap.get(level)[1]);
        builder.append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null)
        {
            builder.append("\t");
            for (int i = 0; i < params.length; i++)
            {
                builder.append(params[i]);
                if (i < params.length - 1)
                    builder.append(", ");
            }
        }

        builder.append(ANSI_RESET);
        builder.append("\n");
        return builder.toString();
    }

    private String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultdate = new Date(millisecs);
        return date_format.format(resultdate);
    }
}