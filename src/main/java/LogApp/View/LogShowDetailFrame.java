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
            logShowDetailFrame.setUndecorated(false);//不显示边框，自定义按键
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
        //绘制浮动小气泡框
    }

    public static void main(String[] args){
        LogShowDetailFrame logShowDetailFrame = new LogShowDetailFrame();
        logShowDetailFrame.setVisible(true);
    }
}
