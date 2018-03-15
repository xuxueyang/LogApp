package LogApp.Controller;

import LogApp.LogFactory;
import LogApp.LogStatic;
import LogApp.Model.LogEach;
import LogApp.Tool.Log;
import LogApp.Tool.LogEvent;
import LogApp.LogFileManager;
import LogApp.Tool.LogGenerator;
import LogApp.View.LogEachPanel;
import LogApp.View.LogJFrame;
import LogApp.View.LogMaxMainJFrame;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class LogEachController implements LogCtrInterface {

//    private Queue<HashMap<String,String>> memoQueue = new LinkedBlockingQueue<HashMap<String, String>>();
    private LogEvent each_changeMessage(LogEvent logEvent){
        LogFileManager.getLogFileManager().changeMessage(logEvent.getUuid(),logEvent.getSource().toString());
        //TODO 因为2个视图，需要同步更新
//        LogMaxMainJFrame.getLogMaxMainJFrame().changeMessage(logEvent.getUuid(),logEvent.getSource().toString());
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("changeMessage",
                new Object[]{logEvent.getUuid(),logEvent.getSource().toString()},LogStatic.FunctionPanel.showAllNoOver);
        LogJFrame.getLogJFrame().changeMessage(logEvent.getUuid(),logEvent.getSource().toString());
        //备忘录，加入这些改变的————想了想，在这边处理有这边的好处，阔以记录，但是也阔以在每个JTextArea里增加一个阔以撤回的。这样也不错吧0.0
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent each_changeOverState(LogEvent logEvent){
        LogFileManager.getLogFileManager().changeOverState(((LogEachPanel) logEvent.getSource()).uuid);
        //视图的添加需要保证时间一直才能添加
        LogEachPanel logEachPanel = (LogEachPanel) logEvent.getSource();
        if(LogStatic.GLOBAL_DATE.equals(logEachPanel.belongDate))
            LogJFrame.getLogJFrame().changeOverState(logEachPanel);
        //TODO 需要改变主面板的状态（比如，删除）
//        LogMaxMainJFrame.getLogMaxMainJFrame().changeOverState(logEachPanel);
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("changeOverState", new LogEachPanel[]{logEachPanel},LogStatic.FunctionPanel.showAllNoOver);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent each_del(LogEvent logEvent){
        try {
            LogFileManager.getLogFileManager().delEachPanel(logEvent.getSource().toString());
            //将这些面板统一管理以便于多方面删除（或者说，删除以UUID为标识，而不是应该直接传面板，这样不通用）
//            LogEachPanel logEachPanel = (LogEachPanel)logEvent.getSource();
            LogJFrame.getLogJFrame().delEachPanel(logEvent.getSource().toString());
//            LogMaxMainJFrame.getLogMaxMainJFrame().removeLogEacgPanel(logEvent.getSource().toString());
            LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("removeLogEacgPanel",
                    new Object[]{logEvent.getSource().toString()},LogStatic.FunctionPanel.showAllNoOver);
//            LogMaxMainJFrame.getLogMaxMainJFrame().delEachPanel((LogEachPanel)logEvent.getSource());
        } catch (IOException e) {
            e.printStackTrace();
            String error = LogGenerator.serialize(e);
            Log.Loggin(error, LogStatic.Log_classify.SystemLog.name());
        }
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent each_load(LogEvent logEvent){
        //将数据加在到fileManager以及添加到JFrame中
        List<LogEach>  list = (ArrayList<LogEach>)logEvent.getSource();
        for(LogEach logEach:list){
            LogFileManager.getLogFileManager().addLogEach(logEach);
            LogJFrame.getLogJFrame().addEachPanel(LogFactory.createLogEachPanel(logEach));
        }
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }

    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "each_changeMessage":
                return this.each_changeMessage(logEvent);
            case "each_changeOverState":
                return this.each_changeOverState(logEvent);
            case "each_del":
                return this.each_del(logEvent);
            case "each_load":
                return  this.each_load(logEvent);
            default:
                return null;
        }
    }
}
