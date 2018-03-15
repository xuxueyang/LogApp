package LogApp;

import LogApp.View.LogEachPanel;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ����ļ��������ݣ�View�������еĽ��档���ݴ棩
 */
public class LogViewManager {
    //���е���ͼ������ȡparent���и���Ӧ���Ǹ��õġ�
    private Map<String,LogEachPanel> viewMap = new HashMap<String, LogEachPanel>();
    //�۲������
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
