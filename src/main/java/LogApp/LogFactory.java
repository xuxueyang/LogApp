package LogApp;

import LogApp.Extends.CalendarPanel;
import LogApp.Extends.Notepad;
import LogApp.Model.LogBase;
import LogApp.Model.LogEach;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogGenerator;
import LogApp.Tool.LogNotification;
import LogApp.Extends.VFlowLayout;
import LogApp.View.LogEachPanel;
import LogApp.View.LogMenu;
import LogApp.View.LogOverPanel;
import LogApp.View.LogShowDetailPanel;
import LogApp.View.QT.LabelCellRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by 徐雪阳 on 2017/12/6.
 */
public class LogFactory {
    private static LogMenu  logMenu = null;
    public static LogMenu getLogMenu(){
        if(logMenu==null){
            logMenu = new LogMenu();
        }
        return logMenu;
    }
    public static  LogOverPanel  getLogOverPanel(boolean needShowButton){
        LogOverPanel logOverPanel;
        if(needShowButton)
            logOverPanel = new LogOverPanel(getShowOverButton());
        else
            logOverPanel = new LogOverPanel();
        logOverPanel.setLayout(new VFlowLayout());
        return logOverPanel;
    }
    public static  LogOverPanel  getLogNoOverPanel(boolean needShowButton){
        LogOverPanel logOverPanel;
        if(needShowButton)
            logOverPanel = new LogOverPanel(getShowNoOverButton());
        else
            logOverPanel = new LogOverPanel();
        logOverPanel.setLayout(new VFlowLayout());
        return logOverPanel;
    }
    public static JButton getShowOverButton(){
        final JButton jButton = new JButton();
        jButton.setText("已完成");
        jButton.setSize(LogStatic.menuWidth,LogStatic.button_height);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogEvent logEvent  = new LogEvent(jButton,LogStatic.resource.mainFun_showAllOver.name());
                LogNotification.broadcast(logEvent);
            }
        });
        return  jButton;
    }
    public static JButton getShowNoOverButton(){
        final JButton jButton = new JButton();
        jButton.setText("未完成");
        jButton.setSize(LogStatic.menuWidth,LogStatic.button_height);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogEvent logEvent  = new LogEvent(jButton,LogStatic.resource.mainFun_showAllNoOver.name());
                LogNotification.broadcast(logEvent);
            }
        });
        return jButton;
    }

    public  static LogEachPanel createLogEachPanel(String uuid,String belongDate){
        //TODO 因为都是用工厂创建的，所以这里都存到LogViewManager中
        LogEachPanel logEachPanel = new LogEachPanel(uuid,belongDate);
//        LogViewManager.getLogViewManager().addLogEachPanel(logEachPanel);
        return logEachPanel;
    }
    public  static LogEachPanel createLogEachPanel(LogEach logEach){
        LogEachPanel logEachPanel = new LogEachPanel(logEach.getUuid(),logEach.getBelongDateStr());
        logEachPanel.setMessage(logEach.getMessage());
        logEachPanel.setOver(logEach.getIsOver());
        return logEachPanel;
    }
    public static LogShowDetailPanel getLogShowDetailPanel(LogBase logBase){
        LogShowDetailPanel logShowDetailPanel = new LogShowDetailPanel();
        logShowDetailPanel.setSize(LogStatic.detailFrameWidth- LogStatic.detailFrameBubbleWidth,LogStatic.detailFrameHeight);

        return logShowDetailPanel;
    }
    public static JButton getMenuAddButton(int width ,int height){
        JButton button = new JButton();
        button.setText("+");
        button.setSize(width,height);
        return button;
    }
    public static JButton getChooseCalendarButton(int width ,int height){
        JButton button = new JButton();
//        button.setText("选择日期");
        button.setText("!@#%@#$!");//乱码，因为这里会标识着日期
        button.setSize(width,height);
        return button;
    }
    public static JFrame getCalendarJFrame(){
        final JFrame jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setUndecorated(true);
        CalendarPanel p = new CalendarPanel(null, LogStatic.Date_Pattern);
        p.setBounds(0,0,250,300);
        jFrame.setBounds(0,0,250,250);
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.add(p);
//        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) { }
            @Override
            public void windowLostFocus(WindowEvent e) {
//                System.out.println(" jFrame.setVisible(false);");
                jFrame.setVisible(false);
            }
        });
        return jFrame;
    }
    public static JButton getLogDayButton(int width,int height){
        JButton button = new JButton();
        button.setText("日志");
        button.setSize(width,height);
        return button;
    }
    public static JButton getLogTelButton(int width,int height){
        JButton button = new JButton();
        button.setText("技术文档搜集");
        button.setSize(width,height);
        return button;
    }
    private static Notepad notepad;
    public static Notepad getNotePad(Rectangle rectangle,String  title){
        if(notepad==null){
            JFrame mainFrame = new JFrame (title);
            mainFrame.setSize(rectangle.width , rectangle.height);
            mainFrame.setResizable(true);//不可更改大小
            notepad = new Notepad(mainFrame,LogStatic.resource_prefix.day);
            //mainFrame.setLocation( rectangle.x ,rectangle.y);// 起始位置
        }else{
            notepad.init(title);
        }
        return notepad;
    }
    private static Notepad telNotepad;
    public static Notepad getTelNotePad(Rectangle rectangle,String  title){
        if(telNotepad==null){
            JFrame mainFrame = new JFrame (title);
            mainFrame.setSize(rectangle.width , rectangle.height);
            mainFrame.setResizable(true);//不可更改大小
            telNotepad = new Notepad(mainFrame,LogStatic.resource_prefix.tel);
            //mainFrame.setLocation( rectangle.x ,rectangle.y);// 起始位置
        }else{
            telNotepad.init(title);
        }
        return telNotepad;
    }
    public static JComboBox getClassifyJComboBox(){
        return new JComboBox(LogStatic.classify.values());
    }
    public static JComboBox getPriorityJComboBox(){
        return new JComboBox(LogStatic.priority.values());
    }
    public static JComboBox getMarkJComboBox(){
        JComboBox jComboBox_mark = new JComboBox();
        Color[] colors = LogGenerator.getColorsPanelByStringList(LogStatic.mark_star.values());
        jComboBox_mark.setBackground(LogGenerator.getColorByString(LogStatic.mark_star.DarkGray.name()));
        LabelCellRender labelCellRender = new LabelCellRender();
        jComboBox_mark.setRenderer(labelCellRender);
        for(int i=0;i<colors.length;i++){
            jComboBox_mark.addItem(colors[i]);
        }
        return jComboBox_mark;
    }
}
