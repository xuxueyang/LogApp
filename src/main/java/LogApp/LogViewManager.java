package LogApp;

import LogApp.View.LogEachPanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 如果文件管理数据，View管理所有的界面。（暂存）
 */
public class LogViewManager {
    //含有的视图——获取parent进行更新应该是更好的。
    private Map<String,LogEachPanel> viewMap = new HashMap<String, LogEachPanel>();
    //观测监听者
    private Map<String,JPanel> observers = new HashMap<String, JPanel>();
    private LogViewManager(){

    }
    private static LogViewManager logViewManager;
    public static LogViewManager getLogViewManager(){
        if(logViewManager==null){
            logViewManager = new LogViewManager();
        }
        return logViewManager;
    }
    public void removeLogEachPanel(){

    }
    public void addLogEachPanel(LogEachPanel logEachPanel){
        viewMap.put(logEachPanel.uuid,logEachPanel);
    }
    public LogEachPanel getLogEachPanel(String uuid){
        if(viewMap.containsKey(uuid)){
            return viewMap.get(uuid);
        }
        return  null;
    }
}
