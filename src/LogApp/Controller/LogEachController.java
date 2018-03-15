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
        //TODO ��Ϊ2����ͼ����Ҫͬ������
//        LogMaxMainJFrame.getLogMaxMainJFrame().changeMessage(logEvent.getUuid(),logEvent.getSource().toString());
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("changeMessage",
                new Object[]{logEvent.getUuid(),logEvent.getSource().toString()},LogStatic.FunctionPanel.showAllNoOver);
        LogJFrame.getLogJFrame().changeMessage(logEvent.getUuid(),logEvent.getSource().toString());
        //����¼��������Щ�ı�ġ������������룬����ߴ�������ߵĺô������Լ�¼������Ҳ������ÿ��JTextArea������һ�����Գ��صġ�����Ҳ�����0.0
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent each_changeOverState(LogEvent logEvent){
        LogFileManager.getLogFileManager().changeOverState(((LogEachPanel) logEvent.getSource()).uuid);
        //��ͼ�������Ҫ��֤ʱ��һֱ�������
        LogEachPanel logEachPanel = (LogEachPanel) logEvent.getSource();
        if(LogStatic.GLOBAL_DATE.equals(logEachPanel.belongDate))
            LogJFrame.getLogJFrame().changeOverState(logEachPanel);
        //TODO ��Ҫ�ı�������״̬�����磬ɾ����
//        LogMaxMainJFrame.getLogMaxMainJFrame().changeOverState(logEachPanel);
        LogMaxMainJFrame.getLogMaxMainJFrame().Invoke("changeOverState", new LogEachPanel[]{logEachPanel},LogStatic.FunctionPanel.showAllNoOver);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent each_del(LogEvent logEvent){
        try {
            LogFileManager.getLogFileManager().delEachPanel(logEvent.getSource().toString());
            //����Щ���ͳһ�����Ա��ڶ෽��ɾ��������˵��ɾ����UUIDΪ��ʶ��������Ӧ��ֱ�Ӵ���壬������ͨ�ã�
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
        //�����ݼ��ڵ�fileManager�Լ���ӵ�JFrame��
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
