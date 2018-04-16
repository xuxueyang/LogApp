package LogApp;

import LogApp.Controller.LogCtrInterface;
import LogApp.Tool.*;
import LogApp.View.LogEachPanel;
import LogApp.View.LogJFrame;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.StandardBorderPainter;
import org.jvnet.substance.button.ClassicButtonShaper;
import org.jvnet.substance.painter.StandardGradientPainter;
import org.jvnet.substance.skin.SubstanceCremeCoffeeLookAndFeel;
import org.jvnet.substance.theme.SubstanceCharcoalTheme;
import org.jvnet.substance.theme.SubstanceEbonyTheme;
import org.jvnet.substance.title.FlatTitlePainter;
import org.jvnet.substance.watermark.SubstanceBinaryWatermark;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ��ѩ�� on 2017/12/5.
 * ��������ĳ�ʼ��,�����´���Ĵ�������ʾ
 */
public class LogStart {

    public static  void main(String[] args){
        LogStart logStart = new LogStart();
    }

    public LogStart(){
        setLookStyle();
        this.initByXml();
//        LogJFrame.getLogJFrame().setVisible(true);

        /**
         * ע�ᵽ��Ϣ����
         */
        LogNotification.addReceiver(LogLogic.getLogLogic());
//        LogNotification.broadcast(new LogEvent(null,LogStatic.resource.global_data_init.name()));
        LogNotification.broadcast(new LogEvent(LogGenerator.getNowDate(),LogStatic.resource.menu_chooseCalender.name()));
        /**
         * ��������
         */
        Tary tary = new Tary();
        tary.addJFrame(LogJFrame.getLogJFrame());
        /**
         * ������ʱ��
         */
        LogTimer.start();
    }

    /**
     * ����xml��ȡ��������Ĭ�����ԡ�
     */
    private void initByXml(){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(LogStatic.CONFIG_PATH);
        if(is==null)
            return;
        Properties properties = new Properties();
        try {
            properties.load(is);
            //����Ĭ������
            LogStatic.REAL_PATH = properties.getProperty("REAL_PATH",LogStatic.REAL_PATH );
            LogStatic.LineDivision = properties.getProperty("LineDivision",LogStatic.LineDivision );
            //ת������
            //��ȡ�������ļ���
            for(String str:LogStatic.CONFIG_STRS){
                try{
                    Integer tmp = Integer.parseInt(properties.getProperty(str));
                    LogStatic.class.getField(str).set(null,tmp);
                }catch (Exception e){
                    Log.Loggin("ת��"+str+"����",LogStatic.Tag.SystemLog.name());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void setLookStyle(){
//         ���ô��ڵķ��
//        String outLookAndFeel ="com.jtattoo.plaf.mcwin.McWinLookAndFeel";
//        try {
//            UIManager.setLookAndFeel(outLookAndFeel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            // �������
            UIManager.setLookAndFeel(new SubstanceCremeCoffeeLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            // ��������
            SubstanceLookAndFeel.setCurrentTheme(new SubstanceCharcoalTheme());
            // ���ð�ť���
            SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
            // ����ˮӡ
//            SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBinaryWatermark());
            // ���ñ߿�
            SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
            // ���ý�����Ⱦ
            SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
            // ���ñ���
            SubstanceLookAndFeel.setCurrentTitlePainter(new FlatTitlePainter());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

