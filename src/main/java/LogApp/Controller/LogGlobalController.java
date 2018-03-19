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
        //һ�ַ�ʽ���������LogEachȻ��ֱ����ӣ�������ֻ��uuid���ڸ��������жϣ����uuid������ô�ͼ��أ�������create�������뻹�Ǻ��ߺá�
        //������Ϊ��Э����menu_add����Ȼ�Ѿ��ֿ������һ���ѡ��ǰ���ˡ�
        //        List<String> uuidList = new ArrayList<>();
//        String path;
        //��ʼ����ѡ������Ϊ���أ�Ĭ��������ص�LogEvent��source�����ڡ�
//        if(logEvent.getSource() != null){
//            //TODO ����check�����ǲ��ǺϷ�
//            path = LogStatic.REAL_PATH_DIARY + "/" + logEvent.getSource().toString();
//            LogJFrame.getLogJFrame().clear();
//            LogJFrame.getLogJFrame().setTitle(logEvent.getSource().toString());
//        }else{
//            //TODO ��д�����ص���ģ���ͷ��meta�ļ���ѡ������ϴβ�����
//            path = LogStatic.REAL_PATH_DIARY + "/" + LogGenerator.getNowDatePath();
//            LogJFrame.getLogJFrame().setTitle(LogGenerator.getNowDate());
//        }
//        String path = LogStatic.REAL_PATH_DIARY + "/" + logEvent.getSource().toString().replace("-","/");
        //TODO ��ȫ����������Ҳ���ǻ�ͷ��Ҫ�Ѿ�̬���ú�ȫ����Ϣ���ֿ���
        String path = LogStatic.REAL_PATH_DIARY + "/" + LogStatic.GLOBAL_DATE.replace("-","/");
        LogJFrame.getLogJFrame().clear();
//        LogJFrame.getLogJFrame().setTitle(logEvent.getSource().toString().replace("/","-"));
        LogJFrame.getLogJFrame().setTitle(LogStatic.GLOBAL_DATE.replace("/","-"));

        LogJFrame.getLogJFrame().setVisible(true);
        //����LogEach
        List<String> puuidList =  LogFileLoadAndSave.getLogFileLoadAndSave().loadPUuidByPath(path);
        List<LogEach> list = new ArrayList<LogEach>();
        for(String puuid:puuidList){
            //�����ѯ�ǲ�ѯ�������ļ��µ�ȫ�����ݣ����ܺ�׺����������metaΪ��׺�����ƺ�Ҳ������...Ҳ���Ƕ���LogDay���ļ���Ҫ��취�������֡����磬ֱ�ӽ�·���洢��LogDay�У�
            //��������logDay���̫�ߣ��Ͼ��������ļ���·���Ĵ����Ѿ��������֣�û��Ҫ������...
            LogEach logEach = LogFileLoadAndSave.getLogFileLoadAndSave().load(puuid,LogEach.class);
//            System.out.println(logEach);
            if(logEach != null && logEach.getUuid() != null && logEach.isExist && logEach.getLevel().equals(LogStatic.level.each.name()))
                list.add(logEach);
        }
        //list��logEach����ʱ������
        list.sort(new Comparator<LogEach>() {
            @Override
            public int compare(LogEach o1, LogEach o2) {
                return o1.getUpdateData().getTime()>o2.getUpdateData().getTime() ? 1:-1;
            }
        });
        LogEvent logEvent_each = new LogEvent(list, LogStatic.resource.each_load.name());
        LogNotification.broadcast(logEvent_each);
        //����������(LogDay���͵ģ�����ÿ��Ψһ�ģ�
        List<LogDay> logDayArrayList = new ArrayList<>();
        for(String puuid:puuidList){
            LogDay logDay = LogFileLoadAndSave.getLogFileLoadAndSave().load(puuid,LogDay.class);
            if(logDay != null && logDay.getUuid() != null && logDay.isExist && logDay.getLevel().equals(LogStatic.level.day.name())){
                //LogDay����ֻ��һ��������д�ɶ��Ӧ��Ҳû����--
                logDayArrayList.add(logDay);
            }
        }
//        for(int ii=0; ii < logDayArrayList.size();ii++){
//            //���������ݣ�Ĭ�ϣ�
//            //�����ռ�����������
//        }
//        if(logDayArrayList.size()==0){
//            //˵�����ص�������û�У���ô������
//            LogDay logDay = LogFileManager.getLogFileManager().createLogDay(LogGenerator.getUUID());
//            logDayArrayList.add(logDay);
//        }
        // LogDay���͵�У���ڸ�������ɡ�û���ٸ��Դ���

        LogEvent logEvent_day = new LogEvent(logDayArrayList, LogStatic.resource.day_load.name());
        LogNotification.broadcast(logEvent_day);
        LogEvent logEvent_tel = new LogEvent(logDayArrayList, LogStatic.resource.tel_load.name());
        LogNotification.broadcast(logEvent_tel);
        return new LogEvent(true,LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent global_exit(LogEvent logEvent){
        this.global_data_save(logEvent);
        // ��Ҫǿ��������ģ����Ƿ���ȫ������Ӧ���Ǻ��߱ȽϺ�
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
