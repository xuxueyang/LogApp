package plugIn;

import LogApp.Tool.UploadFile.LinuxSFTP;
import com.jcraft.jsch.ChannelSftp;

//TODO 把小功能的main方法转移到这边吧。现在是上传的功能
public class UploadNormalFile {

    public static void main(String[] args) {
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
        LinuxSFTP.uploadNormalFile("D:\\browser.rar",channelSftp);
//        LinuxSFTP.download("Log_Diary.rar","D:\\Log_Diary.rar",channelSftp);
        System.exit(0);
    }

}
