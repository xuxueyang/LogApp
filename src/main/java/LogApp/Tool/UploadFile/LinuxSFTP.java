package LogApp.Tool.UploadFile;

import LogApp.Tool.MD5Util;
import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class LinuxSFTP {
    //����Ĭ�ϵĲ���Ŀǰ
    private  static  String ipAddr = "39.108.233.104";
    private  static  String username = "root";
    private  static  String password = "Xuxueyang1";
    private  static  String uploadServerPath = "/root/LogAppFile";

    //��������
    private static LinuxSFTP linkSftp = null;
    private LinuxSFTP(){

    }
    private static Map<String,ChannelSftp> sftpMap = new HashMap<>();
    public static ChannelSftp getConnect(){
        return  LinuxSFTP.getConnect(ipAddr,22,username,password);
    }
    /**
     * ����sftp������
     * @param host ����
     * @param port �˿�
     * @param username �û���
     * @param password ����
     * @return
     */
    public static  ChannelSftp getConnect(String host, int port, String username,
                                  String password){
        if(linkSftp == null){
            linkSftp = new LinuxSFTP();
        }
        StringBuffer bufferString = new StringBuffer();
        bufferString.append(host);
        bufferString.append(port);
        bufferString.append(username);
        bufferString.append(password);
        String mad5 = MD5Util.MD5(bufferString.toString());
        if(sftpMap.containsKey(mad5)){
            return sftpMap.get(mad5);
        }else{
            ChannelSftp channelSftp  =  linkSftp.connect(host,port,username,password);
            sftpMap.put(mad5,channelSftp);
            return channelSftp;
        }
    }

    private static ChannelSftp connect(String host, int port, String username,
                               String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {

        }
        return sftp;
    }

    public static void upload(String uploadFilePath,ChannelSftp sftp){
        LinuxSFTP.upload(uploadServerPath,uploadFilePath,sftp);
    }
    /**
     * �ϴ��ļ�
     * @param directory �ϴ���Ŀ¼
     * @param uploadFile Ҫ�ϴ����ļ�
     * @param sftp
     */
    private static void upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void download( String downloadFile,String saveFile, ChannelSftp sftp){
        LinuxSFTP.download(uploadServerPath,downloadFile,saveFile,sftp);
    }
    /**
     * �����ļ�
     * @param directory ����Ŀ¼
     * @param downloadFile ���ص��ļ�
     * @param saveFile ���ڱ��ص�·��
     * @param sftp
     */
    private static void download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void delete(String deleteFile,ChannelSftp sftp){
        LinuxSFTP.delete(uploadServerPath,deleteFile,sftp);
    }
    /**
     * ɾ���ļ�
     * @param directory Ҫɾ���ļ�����Ŀ¼
     * @param deleteFile Ҫɾ�����ļ�
     * @param sftp
     */
    private static void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Vector listFiles(ChannelSftp sftp)throws SftpException{
        return LinuxSFTP.listFiles(uploadServerPath,sftp);
    }
    /**
     * �г�Ŀ¼�µ��ļ�
     * @param directory Ҫ�г���Ŀ¼
     * @param sftp
     * @return
     * @throws SftpException
     */
    private static  Vector listFiles(String directory, ChannelSftp sftp) throws SftpException{
        return sftp.ls(directory);
    }

//    public static void main(String[] args) {
//        ChannelSftp channelSftp = LinuxSFTP.getConnect();
//        LinuxSFTP.upload("D:\\xxy\\Log_Diary.rar",channelSftp);
//        LinuxSFTP.download("Log_Diary.rar","D:\\Log_Diary.rar",channelSftp);
//
////        SFTPExample sf = new SFTPExample();
////        String host = "192.168.0.1";
////        int port = 22;
////        String username = "root";
////        String password = "root";
////        String directory = "/home/httpd/test/";
////        String uploadFile = "D:\\tmp\\upload.txt";
////        String downloadFile = "upload.txt";
////        String saveFile = "D:\\tmp\\download.txt";
////        String deleteFile = "delete.txt";
////        ChannelSftp sftp=sf.connect(host, port, username, password);
////        sf.upload(directory, uploadFile, sftp);
////        sf.download(directory, downloadFile, saveFile, sftp);
////        sf.delete(directory, deleteFile, sftp);
////        try{
////            sftp.cd(directory);
////            sftp.mkdir("ss");
////            System.out.println("finished");
////        }catch(Exception e){
////            e.printStackTrace();
////        }
//    }
}
