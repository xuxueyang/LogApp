package example.e_20180315;
import java.io.*;
public class Copy
{
    public static void main(String[] args)throws Exception//�쳣ֱ���׳�ȥ�ˣ��Լ�����Ͳ�������
    {
        FileInputStream fis=new FileInputStream("../BT5R3-KDE-32.iso");//Ҫ�����ļ���·��
        FileOutputStream fos=new FileOutputStream("BT5R3-KDE-32.iso");//Ҫ���ļ����Ƶ������·��
        BufferedInputStream bufis=new BufferedInputStream(fis);
        BufferedOutputStream bufos=new BufferedOutputStream(fos);
        byte[] by=new byte[1024*1024*150];//byte[]����Ĵ�С�����ݸ����ļ��Ĵ�С���Ե�����1Gһ�¿���5M��1G����150M���Լ�������
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
            System.out.print("\r                                         \r"+(1-bufis.available()/begin)*100+"%");//��ʾ���ȣ�����ļ�����
            //��2G���ϣ�����һ��ʼ��һֱ��ʾ0.0%����Ϊ��������ݹ�С����ʧ�ˣ����������ڸ��ƣ�Ҫ�ȵ�
        }
        bufos.close();
        bufis.close();
        long e=System.nanoTime();
        System.out.println("\n��ʱ"+(e-f)/1000000000+"��");//��ʾ����ʱ
    }
}