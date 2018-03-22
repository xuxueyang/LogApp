package plugIn;

import LogApp.Tool.UploadFile.LinuxSFTP;
import com.jcraft.jsch.ChannelSftp;

public class UploadLogFile {
    public static void main(String[] args) {
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
        LinuxSFTP.uploadLogFile("D:\\xxy\\Log_Diary.rar",channelSftp);
//        LinuxSFTP.download("Log_Diary.rar","D:\\Log_Diary.rar",channelSftp);
        System.exit(0);
    }
}
