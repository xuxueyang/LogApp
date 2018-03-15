package LogApp.View;

import LogApp.Tool.LogEvent;
import LogApp.Tool.LogNotification;
import LogApp.LogStatic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ��ѩ�� on 2017/12/6.
 */
public class LogEachPanel extends JPanel {
    /**
     * ��������,panel����id������Ψһ��Ӧ
     */
    public   String uuid;
    public  String belongDate;
    /**
     * View����
     */
    private  boolean isOver;
    private  LogTextArea logTextArea;
    private  JCheckBox jCheckBox;
    //���ұߵĵ����ť��ʵ��ɾ���뱸ע���顣
    private JButton detailButton;
    private JButton delButton;
//    private boolean isShowDetail = false;
//    public  LogEachPanel(){
//        this("",false);
//    }
    public LogEachPanel(String  uuid,String belongDate){
        //�����id������������id��ʼ�����ݡ�
        this(uuid,belongDate,false);
    }
//    public  LogEachPanel(String text){
//        this(text,false);
//    }
    public  LogEachPanel(String uuid,String belongDate,boolean isOver){
        super();
        this.uuid = uuid;
        this.belongDate = belongDate;
        this.isOver = isOver;
        this.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
        this.jCheckBox = new JCheckBox();
        this.jCheckBox.setSelected(isOver);
        this.jCheckBox.setSize(LogStatic.eachPanelCheckBoxWidth,LogStatic.eachPanelCheckBoxHeight);
        this.logTextArea = new LogTextArea(uuid,"",isOver);
//        this.logTextArea.setText(text);

        this.jCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = (JCheckBox) e.getSource();
                setOver(checkBox.isSelected());
                checkBoxClick(checkBox.isSelected());
            }
        });
        this.detailButton =  new JButton();
        this.detailButton.setText("Detail");
        this.detailButton.setSize(LogStatic.eachPanel_detailButtonWidth, LogStatic.eachPanel_detailButtonHeight);
        this.detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chilk_Detail();
            }
        });
        this.delButton = new JButton();
        this.delButton.setText("Del");
        this.delButton.setSize(LogStatic.eachPanel_delButtonWidth,LogStatic.eachPanel_delButtonHeight);
        this.delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                del();
            }
        });
        this.add(this.jCheckBox);
//        this.add(new JScrollPane(this.logTextArea));
        this.add(this.logTextArea);
        this.add(this.detailButton);
        this.add(this.delButton);
        this.setBackground(Color.lightGray);
        //TODO �ƺ�û��
//        this.setSize(LogStatic.defaultWidth-150,LogStatic.eachPanel_delButtonHeight);
    }
    private void  checkBoxClick(boolean isOver){
            LogEvent logEvent = new LogEvent(this,LogStatic.resource.each_changeOverState.name());
            LogNotification.broadcast(logEvent);
    }

    /**
     * �õ���ʽ���ݿ���ʾ����
     */
    private void chilk_Detail(){
        int off = 10;
//        Point point = new Point(this.getParent().getX()+this.getX()+this.detailButton.getX()+this.detailButton.getWidth()/2+off,
//                this.getParent().getY()+this.getY()+this.detailButton.getY()+this.detailButton.getHeight()+off);
//        System.out.println(point);
//        Point point = new Point(this.getParent().getLocation().x+this.getLocation().x+this.detailButton.getWidth()/2+off,
//                this.getParent().getLocation().y+this.getLocation().y+this.detailButton.getHeight()+off);
        int x=this.getLocation().x+this.detailButton.getX()+this.detailButton.getWidth()/2+off;
        int y=this.getLocation().y+this.detailButton.getY()+this.detailButton.getHeight()+off;
        Container container = this.getParent();
        while (container!=null){
            x+=container.getLocation().x;
            y+=container.getLocation().y;
            container=container.getParent();
        }
        Point point = new Point(x,y);
        LogEvent logEvent = new LogEvent(point,LogStatic.resource.detail_visible.name(),this.uuid);
        LogNotification.broadcast(logEvent);
    }
    private void del(){
        LogEvent logEvent1 = new LogEvent(this.uuid,LogStatic.resource.each_del.name());
        //ɾ�������Ļᵼ������panel��detailpanel1����ʧ��
        LogEvent logEvent2 = new LogEvent(this.uuid,LogStatic.resource.detail_del.name());
        LogNotification.broadcast(logEvent1);
        LogNotification.broadcast(logEvent2);
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
        this.jCheckBox.setSelected(over);
        this.logTextArea.setIsOver(over);
    }
    public void setMessage(String message){
        this.logTextArea.setMessage(message);
    }
    public String getMessage(){
        return this.logTextArea.getMessage();
    }
}
