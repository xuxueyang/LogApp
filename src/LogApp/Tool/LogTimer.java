package LogApp.Tool;

import LogApp.LogStatic;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ʱ���࣬���ڶ�ʱ����
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
        //��������ʱ�Զ�����
        this.timer = new Timer(true);
        LogTimerTask task = new LogTimerTask();
        //����1���ÿ��20���Զ���������
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
