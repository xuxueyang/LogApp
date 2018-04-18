package LogApp.Tool.UploadFile;

import LogApp.Tool.LogGenerator;
import LogApp.Tool.MD5Util;
import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class LinuxSFTP {
    //采用默认的参数目前
    private  static  String ipAddr = "47.106.140.252";
    private  static  String username = "root";
    private  static  String password = "Xuxueyang1";
    private  static  String uploadServerPath = "/root/LogAppFile";
    private  static  String uploadNormalFileServerPath = "/root/MyFile";

    //单例连接
    private static LinuxSFTP linkSftp = null;
    private LinuxSFTP(){

    }
    private static Map<String,ChannelSftp> sftpMap = new HashMap<>();
    public static ChannelSftp getConnect(){
        return  LinuxSFTP.getConnect(ipAddr,22,username,password);
    }
    /**
     * 连接sftp服务器
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
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

    public static void uploadLogFile(String uploadFilePath,ChannelSftp sftp){
        LinuxSFTP.upload(uploadServerPath,uploadFilePath,sftp);
    }
    public static boolean upload_file(String uploadFilePath,ChannelSftp sftp){
        File file = new File(uploadFilePath);
        if(!file.exists()){
            return false;
        }
        if(file.isFile()){
            return LinuxSFTP.upload(uploadNormalFileServerPath,uploadFilePath,sftp);
        }else if(file.isDirectory()){
            return LinuxSFTP.uploadNormalDir(uploadNormalFileServerPath,uploadFilePath,sftp);
        }
        return false;
    }
    private static boolean uploadNormalDir(String basePath,String dirPath,ChannelSftp sftp){
        File file = new File(dirPath);
        if(!file.isDirectory()||!file.exists()){
            return false;
        }
        String baseName = basePath + "/"+file.getName()+"_"+LogGenerator.getNowDate()+"_"+LogGenerator.getUUID();
        //递归上传
        for(File childrenFile:file.listFiles()){
            if(childrenFile.isFile()){
                LinuxSFTP.upload(baseName,childrenFile.getAbsolutePath(),sftp);
            }else{
                LinuxSFTP.uploadNormalDir(baseName,childrenFile.getAbsolutePath(),sftp);
            }
        }
        return true;
    }
    /**
     * 上传文件
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    private static boolean upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(uploadFile);
            FileInputStream fileInputStream = new FileInputStream(file);
            sftp.put(fileInputStream, file.getName());
            fileInputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void downloadNormal( String downloadFile,String saveFile, ChannelSftp sftp){
        LinuxSFTP.download(uploadNormalFileServerPath,downloadFile,saveFile,sftp);
    }
    public static void download( String downloadFile,String saveFile, ChannelSftp sftp){
        LinuxSFTP.download(uploadServerPath,downloadFile,saveFile,sftp);
    }
    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     * @param sftp
     */
    private static boolean download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(saveFile);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            sftp.get(downloadFile,fileOutputStream);
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void delete(String deleteFile,ChannelSftp sftp){
        LinuxSFTP.delete(uploadServerPath,deleteFile,sftp);
    }
    /**
     * 删除文件
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
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
     * 列出目录下的文件
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    private static  Vector listFiles(String directory, ChannelSftp sftp) throws SftpException{
        return sftp.ls(directory);
    }

}
