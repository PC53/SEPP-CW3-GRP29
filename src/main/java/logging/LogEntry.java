package logging;

import java.util.Map;

public class LogEntry extends Object{

    private String callerName;
    private Object result;
    private Map<String, Object> additionalInfo;

    public LogEntry(String callerName,
                    Object result,
                    Map<String, Object> additionalInfo){
        this.callerName = callerName;
        this.result = result;
        this.additionalInfo = additionalInfo;
    }

    public String getResult() {
        return null;
    }

    public String toString(){
        return null;
    }
}
