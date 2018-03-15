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
 * Created by 徐雪阳 on 2017/12/5.
 * 负责整体的初始化,包括新窗体的创建与显示
 */
public class LogStart {

    public static  void main(String[] args){
        LogStart logStart = new LogStart();
    }

    public LogStart(){
        this.initByXml();
//        LogJFrame.getLogJFrame().setVisible(true);

        /**
         * 注册到消息中心
         */
        LogNotification.addReceiver(LogLogic.getLogLogic());
//        LogNotification.broadcast(new LogEvent(null,LogStatic.resource.global_data_init.name()));
        LogNotification.broadcast(new LogEvent(LogGenerator.getNowDate(),LogStatic.resource.menu_chooseCalender.name()));
        /**
         * 设置托盘
         */
        Tary tary = new Tary();
        tary.addJFrame(LogJFrame.getLogJFrame());
        /**
         * 启动定时器
         */
        LogTimer.start();
    }

    /**
     * 根据xml读取配置设置默认属性。
     */
    private void initByXml(){
        setLookStyle();
    }
    private void setLookStyle(){
//         设置窗口的风格
//        String outLookAndFeel ="com.jtattoo.plaf.mcwin.McWinLookAndFeel";
//        try {
//            UIManager.setLookAndFeel(outLookAndFeel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            // 设置外观
            UIManager.setLookAndFeel(new SubstanceCremeCoffeeLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            // 设置主题
            SubstanceLookAndFeel.setCurrentTheme(new SubstanceCharcoalTheme());
            // 设置按钮外观
            SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
            // 设置水印
//            SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBinaryWatermark());
            // 设置边框
            SubstanceLookAndFeel.setCurrentBorderPainter(new StandardBorderPainter());
            // 设置渐变渲染
            SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
            // 设置标题
            SubstanceLookAndFeel.setCurrentTitlePainter(new FlatTitlePainter());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

