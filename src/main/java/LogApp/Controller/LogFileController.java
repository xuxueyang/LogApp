package LogApp.Controller;

import LogApp.LogStatic;
import LogApp.Tool.LogEvent;
import LogApp.Tool.UploadFile.LinuxSFTP;
import com.jcraft.jsch.ChannelSftp;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.sftp.SftpFile;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class LogFileController implements LogCtrInterface{
    /**
     * logEvent.getSource�ﺬ���ļ���Ϣ��ѡ���ϴ��ļ���
     * @param logEvent
     * @return
     */
    private LogEvent file_file_upload(LogEvent logEvent){
        //��������ļ����ϴ�����������
        //�����ļ�ѡ���
        String choosePath = chooseFileOrDir();
        Long size = checkFileSize(choosePath);
        if(size==0)
            return new LogEvent("�ϴ��ļ���СΪ0", LogStatic.resource.Return.name(),logEvent.getUuid());
        if(size>100*1024*1024){
            return new LogEvent("�ϴ��ļ���С����100M", LogStatic.resource.Return.name(),logEvent.getUuid());
        }
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
        //��ȡ�����ļ���·����֧���ϴ��ļ��У�
        LinuxSFTP.upload_file(choosePath,channelSftp);



        // TODO ���ж���û�����������û����ʾ������Ϣ��return ��ȥ��
//        if(Ping.ping())
        //TODO ѹ��
        //TODO �ϴ�
        //TODO ɾ��ѹ���ļ�����֮ǰ��ѹ���ļ������ڴ��У�
//        ChannelSftp channelSftp = LinuxSFTP.getConnect();
//        LinuxSFTP.upload("�ϴ��ļ���",channelSftp);
//        return new LogEvent(true, LogStatic.resource.Return.name(),logEvent.getUuid());
        System.out.println("�ϴ��ļ���");
        return new LogEvent("�ϴ����ļ�"+choosePath, LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent file_file_down(LogEvent logEvent){
        //�����ļ�������Linuxroot�ļ����¶�ȡ�ļ��б���״������ʾ�ļ�
        String choosePath = chooseDownFilePath();
        //TODO root�ļ����µ����ݣ�ѡ��һ�������ļ�����
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
        //java.io.FileNotFoundException: C:\Users\Administrator\Desktop �����⣬bug����Ϊû������ļ���
        String fileName = "Log_Diary.rar";
        LinuxSFTP.downloadNormal(fileName,choosePath+"\\"+"Log_Diary.rar",channelSftp);
        return new LogEvent("�������ļ�", LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    @Override
    public LogEvent execute(LogEvent logEvent) {
        switch (logEvent.getResource()){
            case "file_file_upload":
                return this.file_file_upload(logEvent);
            case "file_file_down":
                return this.file_file_down(logEvent);
            default:
                return null;
        }

    }
    public String chooseFileOrDir(){
        JFileChooser chooser=new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result=chooser.showOpenDialog(null);
        String fname=chooser.getName(chooser.getSelectedFile());
//        System.out.println("fname--->"+fname);
        String filePath="";
        if(result==JFileChooser.APPROVE_OPTION)
        {
            filePath=chooser.getSelectedFile().getPath();
        }
        Object[] options = { "�ϴ��ļ�", "ȡ��" };
        int results= JOptionPane.showOptionDialog(null, "�Ƿ񱣴�?", "Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
        if(results==JOptionPane.OK_OPTION)
        {
            return filePath;
        }
//        if(results==JOptionPane.NO_OPTION )
//        {
//            return "";
//        }
        return "";
    }

    /**
     *
     * @return ѡ�������ļ��ı���·��
     */
    public String chooseDownFilePath(){
        JFileChooser chooser=new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result=chooser.showOpenDialog(null);
        String fname=chooser.getName(chooser.getSelectedFile());
//        System.out.println("fname--->"+fname);
        String filePath="";
        if(result==JFileChooser.APPROVE_OPTION)
        {
            filePath=chooser.getSelectedFile().getPath();
        }
        Object[] options = { "�����ļ�", "ȡ��" };
        int results= JOptionPane.showOptionDialog(null, "�Ƿ񱣴�?", "Warning",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
        if(results==JOptionPane.OK_OPTION)
        {
            return filePath;
        }
//        if(results==JOptionPane.NO_OPTION )
//        {
//            return "";
//        }
        return "";
    }
    public Long checkFileSize(String filePath){
        Long size = 0L;
        File file = new File(filePath);
        if(file.exists()&&file.isFile()){
            size+=file.length();
        }else if(file.exists()&&file.isDirectory()){
            for(File childrenFile:file.listFiles()){
                size+=checkFileSize(childrenFile.getAbsolutePath());
            }
        }
        return size;
    }
    public static void  main(String[] args){
        SshClient client=new SshClient();
        try{
            client.connect("�˴���Linux������IP");
            //�����û���������
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername("root");
            pwd.setPassword("123456");
            int result=client.authenticate(pwd);
            if(result== AuthenticationProtocolState.COMPLETE){//����������
                System.out.println("==============="+result);
                List<SftpFile> list = client.openSftpClient().ls("/etc/mail/");
                for (SftpFile f : list) {
                    System.out.println(f.getFilename());
                    System.out.println(f.getAbsolutePath());
                    if(f.getFilename().equals("aliases")){
                        OutputStream os = new FileOutputStream("d:/mail/"+f.getFilename());
                        client.openSftpClient().get("/etc/mail/aliases", os);
                        //����Ϊ��λ��ȡ�ļ�start
                        File file = new File("d:/mail/aliases");
                        BufferedReader reader = null;
                        try {
                            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
                            reader = new BufferedReader(new FileReader(file));
                            String tempString = null;
                            int line = 1;//�к�
                            //һ�ζ���һ�У�ֱ������nullΪ�ļ�����
                            while ((tempString = reader.readLine()) != null) {
                                //��ʾ�к�
                                System.out.println("line " + line + ": " + tempString);
                                line++;
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e1) {
                                }
                            }
                        }
                        //����Ϊ��λ��ȡ�ļ�end
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
