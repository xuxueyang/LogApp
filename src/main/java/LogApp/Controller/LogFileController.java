package LogApp.Controller;

import LogApp.LogStatic;
import LogApp.Tool.LogEvent;

public class LogFileController implements LogCtrInterface{
    /**
     * logEvent.getSource�ﺬ���ļ���Ϣ��
     * @param logEvent
     * @return
     */
    private LogEvent file_file_upload(LogEvent logEvent){
        //��������ļ����ϴ�����������
        // TODO ���ж���û�����������û����ʾ��Ϣ
//        if(Ping.ping())
        //TODO ѹ��
        //TODO �ϴ�
        //TODO ɾ��ѹ���ļ�����֮ǰ��ѹ���ļ������ڴ��У�
//        ChannelSftp channelSftp = LinuxSFTP.getConnect();
//        LinuxSFTP.upload("�ϴ��ļ���",channelSftp);
        return new LogEvent(true, LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "file_file_upload":
                return this.file_file_upload(logEvent);
            default:
                return null;
        }

    }
}
