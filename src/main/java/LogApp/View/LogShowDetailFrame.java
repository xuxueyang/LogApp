package LogApp.View;

import LogApp.LogFactory;
import LogApp.LogStatic;
import LogApp.Model.LogBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogShowDetailFrame extends JFrame  {
    private static  LogShowDetailFrame logShowDetailFrame;
    private static  LogShowDetailPanel logShowDetailPanel;

    public static LogShowDetailFrame getLogShowDetailFrame(){
        if(logShowDetailFrame == null){
            logShowDetailFrame = new LogShowDetailFrame();
            logShowDetailFrame.setSize(LogStatic.detailFrameWidth,LogStatic.detailFrameHeight);
            logShowDetailFrame.setUndecorated(false);//����ʾ�߿��Զ��尴��
            logShowDetailPanel = new LogShowDetailPanel();
            logShowDetailFrame.add(logShowDetailPanel);
        }
        return logShowDetailFrame;
    }
    private  LogShowDetailFrame(){

    }
    public void showDetail(LogBase logBase){
        logShowDetailPanel = LogFactory.getLogShowDetailPanel(logBase);
        logShowDetailFrame.add(logShowDetailPanel);
    }
    public void hideDetail(){
        logShowDetailFrame.remove(logShowDetailPanel);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //���Ƹ���С���ݿ�
    }

    public static void main(String[] args){
        LogShowDetailFrame logShowDetailFrame = new LogShowDetailFrame();
        logShowDetailFrame.setVisible(true);
    }
}
