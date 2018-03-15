package LogApp.View.MaxFunctionPanel;

import LogApp.LogFactory;
import LogApp.LogStatic;
import LogApp.Tool.Log;
import LogApp.View.LogEachPanel;
import LogApp.View.LogOverPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class showAllNoOver extends InvokeFun{
    private LogOverPanel logOverPanel;
    private JComboBox  markJComboBox;
    private JComboBox classifyJComboBox;
    private JComboBox priorityJComboBox;
    public  showAllNoOver(){
        super();
        functionPanel = LogStatic.FunctionPanel.showAllNoOver;
//        name = "显示全部未完成的";
//        name  = map(functionPanel)
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        logOverPanel = LogFactory.getLogNoOverPanel(false);
        //TODO 添加点击事件
        markJComboBox = LogFactory.getMarkJComboBox();
        markJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox = (JComboBox)e.getSource();
                Object[] objects = jComboBox.getSelectedObjects();
                Color color = (Color)objects[0];
                markJComboBox.setBackground(color);
                logOverPanel.showByMark(LogStatic.mark_star.values()[jComboBox.getSelectedIndex()]);
                //TODO 不能立刻刷新背景，有bug
                markJComboBox.validate();
                markJComboBox.repaint();
                repaint();
            }
        });
        classifyJComboBox = LogFactory.getClassifyJComboBox();
        classifyJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox = (JComboBox)e.getSource();
                logOverPanel.showByClassify(LogStatic.classify.values()[jComboBox.getSelectedIndex()]);
                classifyJComboBox.validate();
                classifyJComboBox.repaint();
                repaint();
            }
        });
        priorityJComboBox = LogFactory.getPriorityJComboBox();
        priorityJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox = (JComboBox)e.getSource();
                logOverPanel.showByPriority(LogStatic.priority.values()[jComboBox.getSelectedIndex()]);
                priorityJComboBox.validate();
                priorityJComboBox.repaint();
                repaint();
            }
        });
        this.add(classifyJComboBox);
        this.add(priorityJComboBox);
        this.add(markJComboBox);
        this.add(logOverPanel);
        // 添加布局
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridy=0;
//        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx=0;
        gridBagConstraints.weighty=0;
        gridBagConstraints.gridx=0;
        gridBagLayout.setConstraints(classifyJComboBox,gridBagConstraints);
        gridBagConstraints.gridx=1;
        gridBagLayout.setConstraints(priorityJComboBox,gridBagConstraints);
        gridBagConstraints.gridx=2;
        gridBagConstraints.gridwidth=2;
        gridBagLayout.setConstraints(markJComboBox,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weighty = 2;
        gridBagLayout.setConstraints(logOverPanel,gridBagConstraints);
    }
    @Override
    public void Invoke(String methodName, Object[] params) throws IllegalAccessException,InvocationTargetException {
//        this.getClass().getMethod(methodName,)
        Class ownerClass = this.getClass();
        Class[] argsClass = new Class[params.length];
        for (int i = 0, j = params.length; i < j; i++) {
            argsClass[i] = params[i].getClass();
        }
        try {
            Method method = ownerClass.getMethod(methodName,argsClass);
            method.invoke(this, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JPanel getShowPanel() {
        return this;
    }

    @Override
    public boolean isNeedShow(String methodName) {
        if(methodName==null||"".equals(methodName))
            return false;
        switch (methodName){
            case "showAllNoOver":
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isNeedHide(String methodName) {
        return false;
    }


    /**
     * 因为数据在maxJFrame和JFrame同时显示，所以记得delete的时候要同时刷新。（还好细节是单例，阔以保持数据同步）
     * @param arrayList
     */
    public void showAllNoOver(ArrayList<LogEachPanel> arrayList){
        if(logOverPanel==null)
            return;
        for(LogEachPanel panel:arrayList){
            logOverPanel.addLogEachPanel(panel);
        }
//        jScrollPane.setPreferredSize (new Dimension (LogStatic.defaultWidth,50*arrayList.size()));
//        int k = Math.round((float)detailPanelWightX/(detailPanelWightX+mainPanelWightX+0.1f));
//        jScrollPane.setPreferredSize (new Dimension(LogStatic.maxDefaultWidth*k,LogStatic.maxDefaultHeight));
//
//        jScrollPane.setViewportView(logOverPanel);
//        this.setVisible(true);
    }
    public void changeMessage(String uuid,String message){
        if(logOverPanel==null)
            return;
        logOverPanel.changeMessage(uuid,message);
    }
    public void removeLogEacgPanel(String uuid){
        if(logOverPanel==null)
            return;
        logOverPanel.removeLogEacgPanel(uuid);
    }
    public void addLogEachPanel(LogEachPanel logEachPanel){
        if(logOverPanel==null)
            return;
        logOverPanel.addLogEachPanel(logEachPanel);
    }
    public void changeOverState(LogEachPanel logEachPanel){
//        if(logEachPanel.isOver()){
//            logOverPanel.addLogEachPanel(logEachPanel);
//        }else{
//            logOverPanel.removeLogEacgPanel(logEachPanel.uuid);
//        }
        // 上面这种方法不能通用与多个面板
        if(logOverPanel.contains(logEachPanel.uuid))
            logOverPanel.removeLogEacgPanel(logEachPanel.uuid);
        else if(!logEachPanel.isOver())
            logOverPanel.addLogEachPanel(logEachPanel);
    }
}
