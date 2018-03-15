package LogApp;

import LogApp.Controller.*;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogNotification;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by 徐雪阳 on 2017/12/5.
 */
public   class LogLogic implements LogCtrInterface,KeyListener{
    /**
     * 具体的业务处理类
     */
    private LogEachController logEachController = new LogEachController();
    private LogDayController logDayController = new LogDayController();

    private LogGlobalController logGlobalController = new LogGlobalController();
    private LogMenuController logMenuController = new LogMenuController();
    private LogDetailQTController logDetailQTController = new LogDetailQTController();
    private LogMainFunController logMainFunController = new LogMainFunController();
    private LogTelController logTelController = new LogTelController();
    /**
     * 单例,分发任务
     */
    private static LogLogic logLogic;
    public static LogLogic getLogLogic(){
        if(logLogic==null){
            logLogic = new LogLogic();
        }
        return  logLogic;
    }
    private LogLogic(){

    }
    @Override
    public LogEvent execute(LogEvent e) {
        String area = e.getResource().substring(0,e.getResource().indexOf("_"));
//        System.out.println(area);
        switch (area){
            case "menu":
                return logMenuController.execute(e);
            case "global":
                return logGlobalController.execute(e);
            case "each":
                return logEachController.execute(e);
            case "day":
                return logDayController.execute(e);
            case "detail":
                return logDetailQTController.execute(e);
            case "mainFun":
                return logMainFunController.execute(e);
            case "tel":
                return logTelController.execute(e);
            default:
                return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.isControlDown() == true)&& (e.getKeyCode() == KeyEvent.VK_S)){
            LogNotification.broadcast(new LogEvent(null,LogStatic.resource.global_data_save.name()));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
