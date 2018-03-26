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
     * logEvent.getSource里含有文件信息。选择上传文件。
     * @param logEvent
     * @return
     */
    private LogEvent file_file_upload(LogEvent logEvent){
        //打包本地文件，上传到服务器。
        //跳出文件选择框
        String choosePath = chooseFileOrDir();
        Long size = checkFileSize(choosePath);
        if(size==0)
            return new LogEvent("上传文件大小为0", LogStatic.resource.Return.name(),logEvent.getUuid());
        if(size>100*1024*1024){
            return new LogEvent("上传文件大小超过100M", LogStatic.resource.Return.name(),logEvent.getUuid());
        }
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
        //获取到子文件的路径（支持上传文件夹）
        LinuxSFTP.upload_file(choosePath,channelSftp);



        // TODO 先判断有没有联网，如果没有显示错误信息。return 出去。
//        if(Ping.ping())
        //TODO 压缩
        //TODO 上传
        //TODO 删除压缩文件（或之前的压缩文件存在内存中）
//        ChannelSftp channelSftp = LinuxSFTP.getConnect();
//        LinuxSFTP.upload("上传文件名",channelSftp);
//        return new LogEvent(true, LogStatic.resource.Return.name(),logEvent.getUuid());
        System.out.println("上传文件了");
        return new LogEvent("上传了文件"+choosePath, LogStatic.resource.Return.name(),logEvent.getUuid());
    }
    private LogEvent file_file_down(LogEvent logEvent){
        //下载文件——从Linuxroot文件夹下读取文件列表，树状加载显示文件
        String choosePath = chooseDownFilePath();
        //TODO root文件夹下的数据，选择一个数据文件下载
        ChannelSftp channelSftp = LinuxSFTP.getConnect();
        //java.io.FileNotFoundException: C:\Users\Administrator\Desktop 有问题，bug是因为没有添加文件名
        String fileName = "Log_Diary.rar";
        LinuxSFTP.downloadNormal(fileName,choosePath+"\\"+"Log_Diary.rar",channelSftp);
        return new LogEvent("下载了文件", LogStatic.resource.Return.name(),logEvent.getUuid());
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
        Object[] options = { "上传文件", "取消" };
        int results= JOptionPane.showOptionDialog(null, "是否保存?", "Warning",
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
     * @return 选择下载文件的本地路径
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
        Object[] options = { "下载文件", "取消" };
        int results= JOptionPane.showOptionDialog(null, "是否保存?", "Warning",
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
            client.connect("此处是Linux服务器IP");
            //设置用户名和密码
            PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
            pwd.setUsername("root");
            pwd.setPassword("123456");
            int result=client.authenticate(pwd);
            if(result== AuthenticationProtocolState.COMPLETE){//如果连接完成
                System.out.println("==============="+result);
                List<SftpFile> list = client.openSftpClient().ls("/etc/mail/");
                for (SftpFile f : list) {
                    System.out.println(f.getFilename());
                    System.out.println(f.getAbsolutePath());
                    if(f.getFilename().equals("aliases")){
                        OutputStream os = new FileOutputStream("d:/mail/"+f.getFilename());
                        client.openSftpClient().get("/etc/mail/aliases", os);
                        //以行为单位读取文件start
                        File file = new File("d:/mail/aliases");
                        BufferedReader reader = null;
                        try {
                            System.out.println("以行为单位读取文件内容，一次读一整行：");
                            reader = new BufferedReader(new FileReader(file));
                            String tempString = null;
                            int line = 1;//行号
                            //一次读入一行，直到读入null为文件结束
                            while ((tempString = reader.readLine()) != null) {
                                //显示行号
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
                        //以行为单位读取文件end
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
