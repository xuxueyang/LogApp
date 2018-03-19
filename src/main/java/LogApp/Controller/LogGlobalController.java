package LogApp.Controller;

import LogApp.FileManager.LogFileLoadAndSave;
import LogApp.LogFileManager;
import LogApp.LogStatic;
import LogApp.Model.LogDay;
import LogApp.Model.LogEach;
import LogApp.Tool.Log;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogGenerator;
import LogApp.Tool.LogNotification;
import LogApp.Tool.UploadFile.LinuxSFTP;
import LogApp.Tool.UploadFile.Ping;
import LogApp.View.LogJFrame;
import com.jcraft.jsch.ChannelSftp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LogGlobalController implements LogCtrInterface {
    private LogEvent global_data_save(LogEvent logEvent){
        try {
            LogFileManager.getLogFileManager().saveAll();
            LogJFrame.getLogJFrame().hasSave();
            return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
        } catch (IOException e) {
            e.printStackTrace();
            String error = LogGenerator.serialize(e);
            Log.Loggin(error,LogStatic.Log_classify.SystemLog.name());
            return new LogEvent(false,LogStatic.resource.Return.name(),logEvent.getUuid());
        }
    }
    private LogEvent global_data_init(LogEvent logEvent){
        //一种方式在这里加在LogEach然后直接添加，或者是只传uuid，在各自里面判断，如果uuid存在那么就加载，否则还是create，想了想还是后者好。
        //不过因为我协议是menu_add，既然已经分开，那我还是选择前者了。
        //        List<String> uuidList = new ArrayList<>();
//        String path;
        //初始化以选择日期为加载，默认这里加载的LogEvent的source是日期。
//        if(logEvent.getSource() != null){
//            //TODO 正则check数据是不是合法
//            path = LogStatic.REAL_PATH_DIARY + "/" + logEvent.getSource().toString();
//            LogJFrame.getLogJFrame().clear();
//            LogJFrame.getLogJFrame().setTitle(logEvent.getSource().toString());
//        }else{
//            //TODO 先写死加载当天的，回头有meta文件再选择加载上次操作的
//            path = LogStatic.REAL_PATH_DIARY + "/" + LogGenerator.getNowDatePath();
//            LogJFrame.getLogJFrame().setTitle(LogGenerator.getNowDate());
//        }
//        String path = LogStatic.REAL_PATH_DIARY + "/" + logEvent.getSource().toString().replace("-","/");
        //TODO 用全局日期来（也就是回头需要把静态配置和全局信息给分开）
        String path = LogStatic.REAL_PATH_DIARY + "/" + LogStatic.GLOBAL_DATE.replace("-","/");
        LogJFrame.getLogJFrame().clear();
//        LogJFrame.getLogJFrame().setTitle(logEvent.getSource().toString().replace("/","-"));
        LogJFrame.getLogJFrame().setTitle(LogStatic.GLOBAL_DATE.replace("/","-"));

        LogJFrame.getLogJFrame().setVisible(true);
        //加载LogEach
        List<String> puuidList =  LogFileLoadAndSave.getLogFileLoadAndSave().loadPUuidByPath(path);
        List<LogEach> list = new ArrayList<LogEach>();
        for(String puuid:puuidList){
            //这里查询是查询出附属文件下的全部数据，不管后缀，就算想以meta为后缀区分似乎也不可以...也就是对于LogDay的文件需要想办法单独区分。比如，直接将路径存储到LogDay中？
            //坏处就算logDay耦合太高，毕竟现在有文件夹路径的存在已经可以区分，没必要再这样...
            LogEach logEach = LogFileLoadAndSave.getLogFileLoadAndSave().load(puuid,LogEach.class);
//            System.out.println(logEach);
            if(logEach != null && logEach.getUuid() != null && logEach.isExist && logEach.getLevel().equals(LogStatic.level.each.name()))
                list.add(logEach);
        }
        //list的logEach按照时间排序
        list.sort(new Comparator<LogEach>() {
            @Override
            public int compare(LogEach o1, LogEach o2) {
                return o1.getUpdateData().getTime()>o2.getUpdateData().getTime() ? 1:-1;
            }
        });
        LogEvent logEvent_each = new LogEvent(list, LogStatic.resource.each_load.name());
        LogNotification.broadcast(logEvent_each);
        //加载日数据(LogDay类型的，包含每日唯一的）
        List<LogDay> logDayArrayList = new ArrayList<>();
        for(String puuid:puuidList){
            LogDay logDay = LogFileLoadAndSave.getLogFileLoadAndSave().load(puuid,LogDay.class);
            if(logDay != null && logDay.getUuid() != null && logDay.isExist && logDay.getLevel().equals(LogStatic.level.day.name())){
                //LogDay理论只有一个，但是写成多个应该也没问题--
                logDayArrayList.add(logDay);
            }
        }
//        for(int ii=0; ii < logDayArrayList.size();ii++){
//            //加载日数据（默认）
//            //加载日技术文字数据
//        }
//        if(logDayArrayList.size()==0){
//            //说明加载的日数据没有，那么创建。
//            LogDay logDay = LogFileManager.getLogFileManager().createLogDay(LogGenerator.getUUID());
//            logDayArrayList.add(logDay);
//        }
        // LogDay类型的校验在各自里面吧。没有再各自创建

        LogEvent logEvent_day = new LogEvent(logDayArrayList, LogStatic.resource.day_load.name());
        LogNotification.broadcast(logEvent_day);
        LogEvent logEvent_tel = new LogEvent(logDayArrayList, LogStatic.resource.tel_load.name());
        LogNotification.broadcast(logEvent_tel);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent global_exit(LogEvent logEvent){
        this.global_data_save(logEvent);
        // 还要强制清理缓存的？还是放在全局做？应该是后者比较好
        Log.refreshCache();
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }

    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "global_data_save":
                return  this.global_data_save(logEvent);
            case "global_data_init":
                return this.global_data_init(logEvent);
            case "global_exit":
                return this.global_exit(logEvent);
//            case LogStatic.resource.global_file_upload.name():

            default:
                return null;
        }
    }
}
