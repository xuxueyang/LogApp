package plugIn;

import LogApp.Tool.UploadFile.LinuxSFTP;
import com.jcraft.jsch.ChannelSftp;

//TODO 把小功能的main方法转移到这边吧。现在是上传的功能
public class UploadNormalFile {

    public static void main(String[] args) {
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
//        LinuxSFTP.upload_file("D:\\browser.rar",channelSftp);
        LinuxSFTP.downloadNormal("大型多人在线游戏开发_11741023.pdf","D:\\大型多人在线游戏开发_11741023.pdf",channelSftp);
        System.exit(0);
    }

}
