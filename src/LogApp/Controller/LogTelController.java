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

public class LogTelController implements LogCtrInterface {
    private Notepad notepad;
    private String DayUUID;
    private LogEvent tel_load(LogEvent logEvent){
        List<LogDay> logDayArrayList = (List<LogDay>)logEvent.getSource();
//        //TODO 对于这些load错误应该抛出异常吧
        LogDay logDayTel = null;
        for(int i=0;i<logDayArrayList.size();i++){
            LogDay tmp = logDayArrayList.get(i);
            if(LogStatic.Type.Tel.name().equals(tmp.type)){
                logDayTel = tmp;
            }
        }
        if(logDayTel==null){
            logDayTel = LogFileManager.getLogFileManager().createLogTel(LogGenerator.getUUID());
        }
        LogFileManager.getLogFileManager().addLogDay(logDayTel);
        this.DayUUID = logDayTel.getUuid();
        notepad  = LogFactory.getTelNotePad(new Rectangle(100,120,800,600),logDayTel.getTitle());
        notepad.setText(logDayTel.getMessage());
//        notepad.setVisible(false,new Point(0,0));
        return new LogEvent(true, LogStatic.resource.Return.name(),logEvent.getUuid());
    }

    private LogEvent tel_remark_visible(LogEvent logEvent){
        notepad.changeVisible();
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent tel_changeMessage(LogEvent logEvent){
        //确保不会，不过应该不会
//        if(logEvent.getUuid().equals(DayUUID))
        LogFileManager.getLogFileManager().changeMessage(DayUUID,logEvent.getSource().toString());
        //因为目前这些改变都仅仅是由于视图那边改变才传过去的，所以暂时这边都不会处理试图那边的数据，以后如果其他地方改变，可能会导致视图和data数据不一致
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "tel_load":
                return this.tel_load(logEvent);
            case "tel_remark_visible":
                return this.tel_remark_visible(logEvent);
            case "tel_changeMessage":
                return this.tel_changeMessage(logEvent);
            default:
                return null;
        }
    }
}
