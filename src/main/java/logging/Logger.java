package logging;

import java.util.List;
import java.util.Map;

public class Logger extends Object{
    public static Logger getInstance(){
        return null;
    }

    public void logAction(String callerName,
                          Object result){

    }

    public void logAction(String callerName,
                          Object result,
                          Map<String, Object> additionalInfo){

    }

    public List<LogEntry> getLog(){
        return null;
    }

    public void clearLog(){

    }
}
