package LogApp.Controller;

import LogApp.LogFactory;
import LogApp.LogFileManager;
import LogApp.LogStatic;
import LogApp.Model.LogEach;
import LogApp.Tool.LogEvent;
import LogApp.View.LogEachPanel;
import LogApp.View.LogMaxMainJFrame;

import java.util.ArrayList;
import java.util.List;

public class LogMainFunController implements LogCtrInterface {
    private LogEvent mainFun_showAllNoOver(LogEvent logEvent){
        //TODO 再次点击隐藏面板
        List<LogEach> list = LogFileManager.getLogFileManager().getAllNoOverList();
        ArrayList<LogEachPanel> logEachPanelList = new ArrayList<LogEachPanel>();
        for(LogEach logEach:list){
            logEachPanelList.add(LogFactory.createLogEachPanel(logEach));
        }
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("showAllNoOver", new ArrayList[]{logEachPanelList}, LogStatic.FunctionPanel.showAllNoOver);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent mainFun_showAllOver(LogEvent logEvent){
//        //TODO 再次点击隐藏面板
        List<LogEach> list = LogFileManager.getLogFileManager().getAllOverList();
        ArrayList<LogEachPanel> logEachPanelList = new ArrayList<>();
        for(LogEach logEach:list){
            logEachPanelList.add(LogFactory.createLogEachPanel(logEach));
        }
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("showAllOver", new ArrayList[]{logEachPanelList}, LogStatic.FunctionPanel.showAllOver);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent mainFun_noteTree(LogEvent logEvent){
        //加载树
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("Show",new Object[]{},LogStatic.FunctionPanel.noteTree);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "mainFun_showAllNoOver":
                return  this.mainFun_showAllNoOver(logEvent);
            case "mainFun_showAllOver":
                return  this.mainFun_showAllOver(logEvent);
            case "mainFun_noteTree":
                return  this.mainFun_noteTree(logEvent);
            default:
                return null;
        }
    }
}
