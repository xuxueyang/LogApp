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

/**
 * Created by ��ѩ�� on 2017/12/5.
 * ��������ĳ�ʼ��,�����´���Ĵ�������ʾ
 */
public class LogStart {

    public static  void main(String[] args){
        LogStart logStart = new LogStart();
    }

    public LogStart(){
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
        setLookStyle();
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

