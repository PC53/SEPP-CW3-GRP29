package logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Logger extends Object{
    private List<LogEntry> log;
    private static Logger logger = null;

    private Logger(){
        this.log = new ArrayList<LogEntry>();
    }

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;

    }

    public void logAction(String callerName,
                          Object result){

        log.add(new LogEntry(callerName,result, Collections.emptyMap()));
    }

    public void logAction(String callerName,
                          Object result,
                          Map<String, Object> additionalInfo){
        log.add(new LogEntry(callerName, result, additionalInfo));
    }

    public List<LogEntry> getLog(){
        return this.log;
    }

    public void clearLog(){
        this.log.clear();
    }
}
