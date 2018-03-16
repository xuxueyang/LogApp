package example.e_20180315;
import java.io.*;
public class Copy
{
    public static void main(String[] args)throws Exception//异常直接抛出去了，自己玩玩就不处理了
    {
        FileInputStream fis=new FileInputStream("../BT5R3-KDE-32.iso");//要复制文件的路径
        FileOutputStream fos=new FileOutputStream("BT5R3-KDE-32.iso");//要把文件复制到哪里的路径
        BufferedInputStream bufis=new BufferedInputStream(fis);
        BufferedOutputStream bufos=new BufferedOutputStream(fos);
        byte[] by=new byte[1024*1024*150];//byte[]数组的大小，根据复制文件的大小可以调整，1G一下可以5M。1G以上150M，自己多试试
        int len;
        boolean flag=true;
        long f=System.nanoTime();
        double begin=bufis.available();
        while(flag)
        {
            len=bufis.read(by);
            if(len==-1)
            {
                flag=false;
                continue;
            }
            bufos.write(by,0,len);
            bufos.flush();
            System.out.print("\r                                         \r"+(1-bufis.available()/begin)*100+"%");//显示进度，如果文件过大
            //（2G以上，可能一开始会一直显示0.0%，因为算出的数据过小，丢失了，不过还是在复制，要等等
        }
        bufos.close();
        bufis.close();
        long e=System.nanoTime();
        System.out.println("\n用时"+(e-f)/1000000000+"秒");//显示总用时
    }
}