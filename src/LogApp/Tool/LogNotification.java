package LogApp.Tool;

import LogApp.Controller.LogCtrInterface;

import java.util.ArrayList;
import java.util.List;

import LogApp.LogStatic;
import LogApp.Tool.Log;

/**
 * Created by 徐雪阳 on 2017/12/6.
 */
public class LogNotification {
    private   static List<LogCtrInterface> receivers = new ArrayList<LogCtrInterface>();
    private LogNotification(){

    }
    //消息队列
//    private static List<LogEvent> logEvents = new ArrayList<>();
    public  static  void addReceiver(LogCtrInterface object){
        LogNotification.receivers.add(object);
    }
    public  static  void removeReceiver(LogCtrInterface object){
        LogNotification.receivers.remove(object);
    }
    public  static synchronized ArrayList<LogEvent> broadcast(LogEvent logEvent){
//        boolean result = false;
        ArrayList<LogEvent> logEventList = new ArrayList<>();
        try{
            Log.addLogEvent(logEvent);
            for(LogCtrInterface tmp: LogNotification.receivers){
                LogEvent tmpEvent = tmp.execute(logEvent);
                if(tmpEvent!=null)
                    logEventList.add(tmpEvent);
            }
        }catch (Exception e){
            e.printStackTrace();
            String error = LogGenerator.serialize(e);
            if(logEvent.getUuid()!=null){
                Log.Loggin(error,LogStatic.Log_classify.SystemLog.name());
            }
        }
//        return  result;
        return logEventList;
    }
}
