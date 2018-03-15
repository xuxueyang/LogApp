package LogApp.View;

import LogApp.LogFactory;
import LogApp.LogStatic;
import LogApp.Tool.Log;
import LogApp.View.MaxFunctionPanel.InvokeFun;
import LogApp.View.MaxFunctionPanel.LogFactory_MaxFun;
import LogApp.View.MaxFunctionPanel.showAllNoOver;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * 相比对于每日的小面板，这个面板更偏向全局，包含：数据分析、图表动画显示、按约定好的数据排序数据，增加不以时间为单位的计划列表（或以年周为单位的计划列表）
 * 2017-12-15功能：1.制作一个不以日为单位的记录面板2.显示全部的未完成列表以及滚动的功能。Table实现吧。
 */
public class LogMaxMainJFrame extends JFrame{
    private static LogMaxMainJFrame logMaxMainJFrame;
    private static int mainPanelWightX=1;
    private static int detailPanelWightX=5;
    public static LogMaxMainJFrame getLogMaxMainJFrame(){
        if(logMaxMainJFrame==null){
            logMaxMainJFrame = new LogMaxMainJFrame();
        }
        return logMaxMainJFrame;
    }
    //JMenu
    //Panel
    private JMenuBar jMenuBar;
    private JScrollPane jScrollPane;
    private JEditorPane jEditorPane;
    private JPanel mainPanel;
    private JPanel detailPanel;
    private LogMaxMainJFrame(){
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);

        this.setJMenuBar(getjMenuBar());
        mainPanel = LogFactory_MaxFun.getMainJPanel();
        this.add(mainPanel);
        detailPanel = new JPanel();
        detailPanel.setBackground(Color.black);
//        this.add(mainPanel);
        this.add(detailPanel);
        //TODO 根据点击不同按钮，加载不同功能面板。
        jScrollPane = new JScrollPane();
        detailPanel.add(jScrollPane);
        this.setSize(LogStatic.maxDefaultWidth,LogStatic.maxDefaultHeight);
        this.setLocation(80,80);
        //进行布局
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx=0;
        gridBagConstraints.weighty=1;
        gridBagConstraints.weightx=mainPanelWightX;
        gridBagConstraints.gridy=0;
        gridBagLayout.setConstraints(mainPanel,gridBagConstraints);
        gridBagConstraints.gridx=1;
        gridBagConstraints.weightx=detailPanelWightX;
        gridBagLayout.setConstraints(detailPanel,gridBagConstraints);

    }
    private InvokeFun invokeFun;
    public void Invoke(String methodName,Object[] params,LogStatic.FunctionPanel functionPanel){
        if(functionPanel==null)
            return;
        if(invokeFun==null||!invokeFun.functionPanel.equals(functionPanel)){
                //TODO 初始化invokeFun
                //TODO 让工厂初始化大小
                //TODO INVOKE来初始化数据。
            this.invokeFun = LogFactory_MaxFun.getFunPanelByName(functionPanel);
        }
        if(this.invokeFun==null)
        {
            Log.Loggin("未找到该功能面板类:"+functionPanel.name(),LogStatic.Log_classify.SystemLog.name());
            return;
        }
        try {
            invokeFun.Invoke(methodName,params);
            //为true改变状态，为false，有专门的方法的
            if(invokeFun.isNeedShow(methodName)){
                int k = Math.round((float)detailPanelWightX/(detailPanelWightX+mainPanelWightX+0.1f));
                jScrollPane.setPreferredSize (new Dimension (LogStatic.maxDefaultWidth*k,LogStatic.maxDefaultHeight));
                jScrollPane.setViewportView(invokeFun.getShowPanel());
                this.setVisible(true);
            }
            if(invokeFun.isNeedHide(methodName)){
                this.setVisible(false);
            }
            Reshow();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    private JMenuBar getjMenuBar(){
        if(jMenuBar==null){
            jMenuBar = new JMenuBar();
            //菜单条
            JMenu mFile = new JMenu ( "File");
            JMenu mEdit = new JMenu ( "Edit");
            JMenu mFormat = new JMenu ("Format");
            JMenu mHelp = new JMenu ("Help");

            //"文件"
            JMenuItem miNew = new JMenuItem ("New");
            JMenuItem miOpen = new JMenuItem ("Open");
            JMenuItem miSave = new JMenuItem ("Save");
            JMenuItem  miSaveAs = new JMenuItem ("Save as");
            JMenuItem  miExit = new JMenuItem ("Exit");

            //"编辑"
            JMenuItem  miCut = new JMenuItem ("Cut");
            JMenuItem  miCopy = new JMenuItem ("Copy");
            JMenuItem  miPaste = new JMenuItem ("Paste");
            JMenuItem  miDelete = new JMenuItem ("Delete");

            //"格式"
            JMenuItem    miFont = new JMenuItem ("Font");
            JMenuItem   miLowtoCapital = new JMenuItem("Low to Capital");
            JMenuItem    miCapitaltoLow = new JMenuItem("Capital to Low");
            JMenuItem    miEncrypt = new JMenuItem("Encrypt");
            JMenuItem  miDisencrypt = new JMenuItem("Disencrypt");

            //"帮助"
            JMenuItem    miAboutNotepad = new JMenuItem ("About LogApp");
            //添加文件菜单项
            mFile.add(miNew);
            mFile.add(miOpen);
            mFile.add(miSave);
            mFile.add(miSaveAs);
            mFile.add(miExit);

            //添加编辑菜单项
            mEdit.add(miCut);
            mEdit.add(miCopy);
            mEdit.add(miPaste);
            mEdit.add(miDelete);

            //添加格式菜单项
            mFormat.add(miFont);
            mFormat.add(miLowtoCapital);
            mFormat.add(miCapitaltoLow);
            mFormat.add(miEncrypt);
            mFormat.add(miDisencrypt);

            //添加帮助菜单项
            mHelp.add(miAboutNotepad);

            //菜单条添加菜单
            jMenuBar.add(mFile);
            jMenuBar.add(mEdit);
            jMenuBar.add(mFormat);
            jMenuBar.add(mHelp);
        }
        return jMenuBar;
    }
    private void Reshow(){
        validate();
        repaint();
    }
//    public void delEachPanel(LogEachPanel logEachPanel){
//
//    }
}
