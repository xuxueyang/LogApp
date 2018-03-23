package LogApp.View;

import LogApp.LogFactory;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogNotification;
import LogApp.LogStatic;
import LogApp.Extends.CalendarPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ��ѩ�� on 2017/12/6.
 */
public class LogMenu extends JPanel {
    private JButton button;
    private JButton gregorianCalendarButton;
//    private boolean isShowLogDay = false;
//    private boolean isShowCalendar = false;
//    private CalendarPanel calendarPanel;
    private JFrame calendarJFrame;
    private JButton showLogDay;
    // ��ʾ�����ռ���־�İ�ť
    private JButton showLogTel;
    //�����ʾ�ļ��ϴ���ť
    private JButton chooseUploadFile;
    private JButton chooseDownFile;
    public LogMenu(){
        this.init();
    }
    private void  init(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT,10,20));
        this.setBackground(Color.blue);
        this.setSize(LogStatic.menuWidth,LogStatic.menuHeight);
        this.button = LogFactory.getMenuAddButton(120,this.getHeight()-20);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
        });
        this.add(button);
        this.gregorianCalendarButton = LogFactory.getChooseCalendarButton(120,this.getHeight()-20);
        this.gregorianCalendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseCalendarClick();
            }
        });
        this.add(gregorianCalendarButton);
        this.showLogDay = LogFactory.getLogDayButton(120,this.getHeight()-20);
//        this.showLogDay.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showLogDayJFrame();
//            }
//        });
        this.add(this.showLogDay);
        this.showLogTel = LogFactory.getLogTelButton(120,this.getHeight()-20);
//        this.showLogTel.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showLogTelJFrame();
//            }
//        });
        this.add(this.showLogTel);
        this.chooseUploadFile = LogFactory.getChooseUploadFileButton(120,this.getHeight()-20);
        this.add(chooseUploadFile);
        this.chooseDownFile = LogFactory.getChooseDownFileButton(120,this.getHeight()-20);
        this.add(chooseDownFile);

    }
    private void add(){
        //��Ϊ��û�����������ж��ܲ��ܴ���
        LogEvent logEvent = new LogEvent(this,LogStatic.resource.menu_can_add.name());
//        boolean canAdd = LogNotification.broadcast(logEvent);
        ArrayList<LogEvent> canAddList = LogNotification.broadcast(logEvent);
        if(canAddList.size()>0){
            LogEvent logEvent1 = canAddList.get(0);
            if((boolean)logEvent1.getSource()){
                logEvent = new LogEvent(this,LogStatic.resource.menu_add.name());
                LogNotification.broadcast(logEvent);
            }
//            if(canAdd){
//                logEvent = new LogEvent(this,LogStatic.resource.menu_add.name());
//                LogNotification.broadcast(logEvent);
//            }
        }

    }
    private void chooseCalendarClick(){
        // ���������ؼ������
        if(calendarJFrame==null){
            calendarJFrame = LogFactory.getCalendarJFrame();
//            calendarJFrame.setLocation(LogStatic.x+this.gregorianCalendarButton.getX(),LogStatic.y+this.gregorianCalendarButton.getY()+this.gregorianCalendarButton.getHeight());
        }
        if(!calendarJFrame.isVisible()){
            calendarJFrame.setLocation(LogStatic.x+this.gregorianCalendarButton.getX(),LogStatic.y+this.gregorianCalendarButton.getY()+this.gregorianCalendarButton.getHeight());
            calendarJFrame.setVisible(true);
        }else {
            calendarJFrame.setVisible(false);
        }
    }
//    private void showLogDayJFrame(){
////        LogEvent logEvent;
////        this.isShowLogDay = !this.isShowLogDay;
////        if(this.isShowLogDay){
//////            this.gregorianCalendarButton.getText()
////            logEvent = new LogEvent(null,LogStatic.resource.day_remark_show.name());
////        }else {
////            logEvent = new LogEvent(null,LogStatic.resource.day_remark_hide.name());
////        }
//        LogEvent logEvent = new LogEvent(null,LogStatic.resource.day_remark_visible.name());
//        LogNotification.broadcast(logEvent);
//    }
//    private void showLogTelJFrame(){
//        LogEvent logEvent = new LogEvent(null,LogStatic.resource.tel_remark_visible.name());
//        LogNotification.broadcast(logEvent);
//    }
    public void hasChooseCalendar(String data){
        if(calendarJFrame!=null){
            calendarJFrame.setVisible(false);
        }
        this.gregorianCalendarButton.setText(data);
        //��Ϊ�޸������ڣ�����NotePadҲ��Ҫ���ã���Ϊ�����ɡ�

    }
}
