package LogApp.Controller;

import LogApp.LogFactory;
import LogApp.LogFileManager;
import LogApp.LogStatic;
import LogApp.Model.LogEach;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogNotification;
import LogApp.View.LogJFrame;
import LogApp.View.LogMaxMainJFrame;

public class LogMenuController implements LogCtrInterface {
//    private  LogEvent menu_can_add(LogEvent logEvent){
//        return new LogEvent(LogJFrame.getLogJFrame().canAddEachPanel(),LogStatic.resource.Return.name(),logEvent.getUuid());
//    }
    private  LogEvent menu_add(LogEvent logEvent){
        LogEach logEach =  LogFileManager.getLogFileManager().createEach(logEvent.getUuid());
        LogJFrame.getLogJFrame().addEachPanel(LogFactory.createLogEachPanel(logEvent.getUuid(),logEach.getBelongDateStr()));
//        LogMaxMainJFrame.getLogMaxMainJFrame().addLogEachPanel(LogFactory.createLogEachPanel(logEvent.getUuid(),logEach.getBelongDateStr()));
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("addLogEachPanel",
                new Object[]{LogFactory.createLogEachPanel(logEvent.getUuid(),logEach.getBelongDateStr())},
                LogStatic.FunctionPanel.showAllNoOver);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent menu_chooseCalender(LogEvent logEvent){
//        String title  = LogJFrame.getLogJFrame().getTitle();
//        if(!title.startsWith(logEvent.getSource().toString())||!title.equals(LogJFrame.getLogJFrame().getLogMenu().getChooseDate())){
        if(!logEvent.getSource().toString().equals(LogStatic.GLOBAL_DATE)){
            LogStatic.GLOBAL_DATE = logEvent.getSource().toString();
            //说明日期更改了(加载其他日志了)
            LogNotification.broadcast(new LogEvent(null,LogStatic.resource.global_data_save.name(),logEvent.getUuid()));
            LogJFrame.getLogJFrame().getLogMenu().hasChooseCalendar(LogStatic.GLOBAL_DATE);
            //TODO 设置加载文件的全路径,切换日期时加载对应日期的下的，可是==我想万一不只是加载一个呢，所以这里改为list吧？
//            String initPath = LogStatic.REAL_PATH_DIARY + "/" + LogStatic.GLOBAL_DATE.replace("-","/");
            LogNotification.broadcast(
                    new LogEvent(
                            LogStatic.GLOBAL_DATE.replace("-", "/"),
                            LogStatic.resource.global_data_init.name()));
        }
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "menu_add":
                return this.menu_add(logEvent);
//            case "menu_can_add":
//                return this.menu_can_add(logEvent);
            case "menu_chooseCalender":
                return this.menu_chooseCalender(logEvent);
            default:
                return null;
        }
    }
}
