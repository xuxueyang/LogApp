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
//        //TODO ������Щload����Ӧ���׳��쳣��
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
        //ȷ�����ᣬ����Ӧ�ò���
//        if(logEvent.getUuid().equals(DayUUID))
        LogFileManager.getLogFileManager().changeMessage(DayUUID,logEvent.getSource().toString());
        //��ΪĿǰ��Щ�ı䶼������������ͼ�Ǳ߸ı�Ŵ���ȥ�ģ�������ʱ��߶����ᴦ����ͼ�Ǳߵ����ݣ��Ժ���������ط��ı䣬���ܻᵼ����ͼ��data���ݲ�һ��
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
