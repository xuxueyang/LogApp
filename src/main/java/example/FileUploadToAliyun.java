package example;

import LogApp.Tool.LogGenerator;

import java.io.*;

public class FileUploadToAliyun {
    public static String ipAddr = "39.108.233.104";
    public static String filePath = "/usr/local/LogFile";

    public static void main(String[] args){
        File file = new File("D:/xxy/1.txt");
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("NULL");
            return;
        }
        String returnMsg = FtpClientUtils.uploadFile(ipAddr,
                Integer.parseInt("21"),
                "root", "Xuxueyang1", filePath, LogGenerator.getUUID(), inputStream);
        System.out.println(returnMsg);
        try {
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
