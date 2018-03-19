package LogApp.Controller;

import LogApp.LogStatic;
import LogApp.Tool.LogEvent;

public class LogFileController implements LogCtrInterface{
    /**
     * logEvent.getSource里含有文件信息。
     * @param logEvent
     * @return
     */
    private LogEvent file_file_upload(LogEvent logEvent){
        //打包本地文件，上传到服务器。
        // TODO 先判断有没有联网，如果没有显示信息
//        if(Ping.ping())
        //TODO 压缩
        //TODO 上传
        //TODO 删除压缩文件（或之前的压缩文件存在内存中）
//        ChannelSftp channelSftp = LinuxSFTP.getConnect();
//        LinuxSFTP.upload("上传文件名",channelSftp);
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
