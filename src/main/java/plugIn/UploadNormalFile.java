package plugIn;

import LogApp.Tool.UploadFile.LinuxSFTP;
import com.jcraft.jsch.ChannelSftp;

//TODO ��С���ܵ�main����ת�Ƶ���߰ɡ��������ϴ��Ĺ���
public class UploadNormalFile {

    public static void main(String[] args) {
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
//        LinuxSFTP.upload_file("D:\\browser.rar",channelSftp);
        LinuxSFTP.downloadNormal("���Ͷ���������Ϸ����_11741023.pdf","D:\\���Ͷ���������Ϸ����_11741023.pdf",channelSftp);
        System.exit(0);
    }

}
