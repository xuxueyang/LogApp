package plugIn;

import LogApp.Tool.UploadFile.LinuxSFTP;
import com.jcraft.jsch.ChannelSftp;

//TODO ��С���ܵ�main����ת�Ƶ���߰ɡ��������ϴ��Ĺ���
public class UploadNormalFile {

    public static void main(String[] args) {
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
        LinuxSFTP.uploadNormalFile("D:\\browser.rar",channelSftp);
//        LinuxSFTP.download("Log_Diary.rar","D:\\Log_Diary.rar",channelSftp);
        System.exit(0);
    }

}
