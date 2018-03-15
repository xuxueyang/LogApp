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
//        //TODO ������Щload����Ӧ���׳��쳣��
//        //���Ϊ�վʹ���
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
        //ȷ�����ᣬ����Ӧ�ò���
//        if(logEvent.getUuid().equals(DayUUID))
        LogFileManager.getLogFileManager().changeMessage(DayUUID,logEvent.getSource().toString());
        //��ΪĿǰ��Щ�ı䶼������������ͼ�Ǳ߸ı�Ŵ���ȥ�ģ�������ʱ��߶����ᴦ����ͼ�Ǳߵ����ݣ��Ժ���������ط��ı䣬���ܻᵼ����ͼ��data���ݲ�һ��
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
