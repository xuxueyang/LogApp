package LogApp.Controller;

import LogApp.LogFileManager;
import LogApp.LogStatic;
import LogApp.Model.LogBase;
import LogApp.Model.LogDetail;
import LogApp.Model.LogEach;
import LogApp.Tool.LogEvent;
import LogApp.View.LogJFrame;
import LogApp.View.QT.LogDetailSingleQT;

import java.awt.*;

public class LogDetailQTController implements LogCtrInterface {
    private String currentUUID;
    private LogEvent detail_del(LogEvent logEvent){
        if(logEvent.getSource()!=null&&logEvent.getSource().toString().equals(this.currentUUID)){
            LogDetailSingleQT.getLogDetailSingleQT().hideDetail();
            this.currentUUID = null;
        }
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent detail_visible(LogEvent logEvent){
        // 1.第一次显示2.显示关闭3.EachPanel关闭了4.已显示点了另一个EachPanel的detail
        //3
        if(logEvent.getSource()==null){
            LogDetailSingleQT.getLogDetailSingleQT().hideDetail();
            this.currentUUID = null;
        }else {
            if (currentUUID == null) {
                //1
                currentUUID = logEvent.getUuid();
            }
            if(currentUUID.equals(logEvent.getUuid())&&LogDetailSingleQT.getLogDetailSingleQT().isVisible()){
                LogDetailSingleQT.getLogDetailSingleQT().hideDetail();
                this.currentUUID = null;
            }else {
                //2 4
                String uuid = logEvent.getUuid();
                LogEach logEach = (LogEach) LogFileManager.getLogFileManager().getDataByUUID(uuid, LogEach.class);
                if (logEach.getLogDetail() == null) {
                    logEach.createLogDetail();
                }
                Point point = (Point) logEvent.getSource();
                LogDetailSingleQT.getLogDetailSingleQT().showDetail(logEach.getLogDetail(), point.getX(), point.getY());
            }
            currentUUID = logEvent.getUuid();
        }
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent detail_move(LogEvent logEvent){
        Point point = (Point)logEvent.getSource();
//        LogDetailSingleQT.getLogDetailSingleQT().setAlwaysOnTop(true);
        LogDetailSingleQT.getLogDetailSingleQT().move(point.getX(),point.getY());
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent detail_changeMessage(LogEvent logEvent){
        LogEach logEach = (LogEach)LogFileManager.getLogFileManager().getDataByUUID(logEvent.getUuid(),LogEach.class);
        logEach.setRemarks(logEvent.getSource().toString());
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent detail_changeClassify(LogEvent logEvent){
        LogEach logEach = (LogEach)LogFileManager.getLogFileManager().getDataByUUID(logEvent.getUuid(),LogEach.class);
        logEach.setClassify((LogStatic.classify)logEvent.getSource());
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent detail_changePriority(LogEvent logEvent){
        LogEach logEach = (LogEach)LogFileManager.getLogFileManager().getDataByUUID(logEvent.getUuid(),LogEach.class);
        logEach.setPriority((LogStatic.priority)logEvent.getSource());
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent detail_changeMarkStar(LogEvent logEvent){
        LogEach logEach = (LogEach)LogFileManager.getLogFileManager().getDataByUUID(logEvent.getUuid(),LogEach.class);
        logEach.setMark_star((LogStatic.mark_star)logEvent.getSource());
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "detail_visible":
                return detail_visible(logEvent);
            case "detail_move":
                return  detail_move(logEvent);
            case "detail_del":
                return  detail_del(logEvent);
            case "detail_changeMessage":
                return  detail_changeMessage(logEvent);
            case "detail_changeClassify":
                return detail_changeClassify(logEvent);
            case "detail_changePriority":
                return detail_changePriority(logEvent);
            case "detail_changeMarkStar":
                return detail_changeMarkStar(logEvent);
            default:
                return null;
        }
    }
}
