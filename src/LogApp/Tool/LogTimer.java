package LogApp.Tool;

import LogApp.LogStatic;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器类，用于定时处理
 */
public class LogTimer {
    public static void start(){
        if(logTimer == null){
            LogTimer.logTimer = new LogTimer();
        }
    }
    private Timer timer;
    private LogTimerTask timerTask;
    private static  LogTimer logTimer;
    private LogTimer(){
        //程序运行时自动启动
        this.timer = new Timer(true);
        LogTimerTask task = new LogTimerTask();
        //启动1秒后，每个20秒自动保存数据
        timer.schedule(task, LogStatic.delay, LogStatic.period);
    }
    private  class  LogTimerTask extends TimerTask{
        @Override
        public void run() {
            LogEvent logEvent = new LogEvent(null, LogStatic.resource.global_data_save.name(),null);
            LogNotification.broadcast(logEvent);
//            System.out.println("run");
        }
    }

}
