package LogApp.Controller;

import LogApp.Extends.Notepad;
import LogApp.LogFactory;
import LogApp.LogFileManager;
import LogApp.LogStatic;
import LogApp.Model.LogDay;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogGenerator;

import java.awt.*;
import java.util.List;

public class LogDayController implements LogCtrInterface {
    private Notepad notepad;
    private String DayUUID;
    private LogEvent day_load(LogEvent logEvent){
        List<LogDay> logDayArrayList = (List<LogDay>)logEvent.getSource();
//        //TODO 对于这些load错误应该抛出异常吧
//        //如果为空就创建
        LogDay logDay = null;
        for(int i=0;i<logDayArrayList.size();i++){
            LogDay tmp = logDayArrayList.get(i);
            if(tmp.type==null||"".equals(tmp.type)||LogStatic.Type.Day.name().equals(tmp.type)){
                logDay = tmp;
            }
        }
        if(logDay==null){
            logDay = LogFileManager.getLogFileManager().createLogDay(LogGenerator.getUUID());
        }
        LogFileManager.getLogFileManager().addLogDay(logDay);

        this.DayUUID = logDay.getUuid();
        notepad  = LogFactory.getNotePad(new Rectangle(100,100,800,600),logDay.getTitle());
        notepad.setText(logDay.getMessage());
//        notepad.setVisible(false,new Point(0,0));
        return new LogEvent(true, LogStatic.resource.Return.name(),logEvent.getUuid());
    }
//    private LogDay day_create(LogEvent logEvent){
//        return LogFileManager.getLogFileManager().createLogDay(logEvent.getUuid());
//    }
//    private boolean day_save(LogEvent logEvent){
//
//        return true;
//    }
//    private boolean day_remark_show(LogEvent logEvent){
//        if(notepad.is)
//        notepad.setVisible(true,new Point(0,0));
//        return true;
//    }
//    private boolean day_remark_hide(LogEvent logEvent){
//        notepad.setVisible(false,new Point(0,0));
//        return true;
//    }
    private LogEvent day_remark_visible(LogEvent logEvent){
        notepad.changeVisible();
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent day_changeMessage(LogEvent logEvent){
        //确保不会，不过应该不会
//        if(logEvent.getUuid().equals(DayUUID))
        LogFileManager.getLogFileManager().changeMessage(DayUUID,logEvent.getSource().toString());
        //因为目前这些改变都仅仅是由于视图那边改变才传过去的，所以暂时这边都不会处理试图那边的数据，以后如果其他地方改变，可能会导致视图和data数据不一致
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "day_load":
                return this.day_load(logEvent);
            case "day_remark_visible":
                return this.day_remark_visible(logEvent);

//            case "day_remark_show":
//                return this.day_remark_show(logEvent);
//            case "day_remark_hide":
//                return this.day_remark_hide(logEvent);
            case "day_changeMessage":
                return this.day_changeMessage(logEvent);
//            case "day_create":
//                this.day_create(logEvent);
//                return  true;
//            case "day_save":
//                return this.day_save(logEvent);
            default:
                return null;
        }
    }
}
