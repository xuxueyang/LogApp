package example.e_20180315;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * JAVA FTPClient 工具类
 *
 * commons-net-1.4.1.jar PFTClinet jar包
 *
 * @author : hpp
 */
public class FtpClientUtils{

    /**
     * Description: 向FTP服务器上传文件
     * @Version1.0
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public static String uploadFile(
        String url,//FTP服务器hostname
        int port,//FTP服务器端口
        String username, // FTP登录账号
        String password, //FTP登录密码
        String path, //FTP服务器保存目录
        String filename, //上传到FTP服务器上的文件名
        InputStream input // 输入流
    ) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            if(port>0)
                ftp.connect(url, port);//连接FTP服务器
            else
                ftp.connect(url);
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            System.out.println(reply);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
//                System.out.println("FTP服务器 拒绝连接");
                return "error:FTP服务器 拒绝连接";
            }
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(filename, input);

            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            return "error:IOException";
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {

                }
            }
        }
        return "";
    }

    /**
     * 删除文件
     * @param fileName 要删除的文件地址
     * @return true/false
     * @throws IOException
     */
    public static boolean delete(String fileName, FTPClient ftpClient) throws IOException {
        return ftpClient.deleteFile(fileName);
    }


    /**
     * 下载文件到指定目录
     * @param ftpFile 文件服务器上的文件地址
     * @param dstFile 输出文件的路径和名称
     * @throws Exception
     */
    public static void downLoad(String ftpFile, String dstFile, FTPClient ftpClient) throws Exception {
        if (StringUtils.isBlank(ftpFile)) {
            throw new RuntimeException("ftpFile为空");
        }
        if (StringUtils.isBlank(dstFile)) {
            throw new RuntimeException("dstFile为空");
        }
        File file = new File(dstFile);
        FileOutputStream fos = new FileOutputStream(file);
        ftpClient.retrieveFile(ftpFile, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 从文件服务器获取文件流
     * @param ftpFile 文件服务器上的文件地址
     * @return {@link InputStream}
     * @throws IOException
     */
    public static InputStream retrieveFileStream(String ftpFile, FTPClient ftpClient) throws IOException {
        if (StringUtils.isBlank(ftpFile)) {
            throw new RuntimeException("ftpFile为空");
        }
        return ftpClient.retrieveFileStream(ftpFile);
    }

//    public static void main(String[] args) {
//        try {
////            String warUrl = "http://img03.sogoucdn.com/v2/thumb/resize/w/80/h/80?appid=100520147&url=http%3A%2F%2Fdl.app.sogou.com%2Fpc_logo%2F934314257951978909.png";
//            FileInputStream in=new FileInputStream(new File("D:\\hello.war"));
//            String message = uploadFile("172.16.103.70", 21, "root", "Xuxueyang1", "/usr/local/tomcat/server/webapps", "hello.war", in);
//            System.out.println(message);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
