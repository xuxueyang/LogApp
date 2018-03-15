package LogApp.View.MaxFunctionPanel;

import LogApp.LogFactory;
import LogApp.LogStatic;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogNotification;
import LogApp.View.LogOverPanel;
import LogApp.View.MaxFunctionPanel.InvokeFun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogFactory_MaxFun {
    public static InvokeFun getFunPanelByName(LogStatic.FunctionPanel functionPanel){
        String fun = functionPanel.name();
        switch (fun){
            case "showAllNoOver":
                return new showAllNoOver();
            case "noteTree":
                return new noteTree();
            default:
                return null;
        }
    }

    /**
     * 那些按钮根据LogStatic.FunctionPanel的选项设置
     * @return 返回mainPanel面板
     */
    public static JPanel getMainJPanel(){
        JPanel mainPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        mainPanel.setBackground(Color.darkGray);
        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
        s.fill = GridBagConstraints.BOTH;
        s.gridx=0;
        s.gridwidth=1;
        s.gridheight=1;
        s.weightx=1;
        s.weighty=1;
        s.anchor = GridBagConstraints.NORTH;
        LogStatic.FunctionPanel[] values = LogStatic.FunctionPanel.values();
        for(int i=0;i<values.length;i++){
            s.gridy=i;
            String clickName = values[i].name();
            JButton jButton = new JButton(clickName);
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //添加点击事件，广播修改mainPanel的
//                    LogStatic.resource.mainFun_showAllNoOver.name()
                    String resource = "mainFun_" + clickName;
                    LogEvent logEvent = new LogEvent(null,resource);
                    LogNotification.broadcast(logEvent);
                }
            });
            mainPanel.add(jButton);
            layout.addLayoutComponent(jButton,s);
        }
        return mainPanel;
    }
//    public static JPanel getShowAllNoOverJPanel(){
//        JPanel jPanel = new JPanel();
//        GridBagLayout gridBagLayout = new GridBagLayout();
//        jPanel.setLayout(gridBagLayout);
//        LogOverPanel logOverPanel = LogFactory.getLogNoOverPanel(false);
//        //TODO 添加点击事件
//        JComboBox markJComboBox = LogFactory.getMarkJComboBox();
//        JComboBox classifyJComboBox = LogFactory.getClassifyJComboBox();
//        JComboBox priorityJComboBox = LogFactory.getPriorityJComboBox();
//        jPanel.add(classifyJComboBox);
//        jPanel.add(priorityJComboBox);
//        jPanel.add(markJComboBox);
//        jPanel.add(logOverPanel);
//        //TODO 添加布局
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.fill = GridBagConstraints.NONE;
//        gridBagConstraints.gridy=0;
////        gridBagConstraints.gridy=1;
//        gridBagConstraints.gridwidth = 1;
//        gridBagConstraints.gridheight = 1;
//        gridBagConstraints.weightx=0;
//        gridBagConstraints.weighty=0;
//        gridBagConstraints.gridx=0;
//        gridBagLayout.setConstraints(classifyJComboBox,gridBagConstraints);
//        gridBagConstraints.gridx=1;
//        gridBagLayout.setConstraints(priorityJComboBox,gridBagConstraints);
//        gridBagConstraints.gridx=2;
//        gridBagLayout.setConstraints(markJComboBox,gridBagConstraints);
//        gridBagConstraints.fill = GridBagConstraints.BOTH;
//        gridBagConstraints.gridx=0;
//        gridBagConstraints.gridy=1;
//        gridBagConstraints.gridwidth = 3;
//        gridBagConstraints.gridheight = 2;
//        gridBagConstraints.weighty = 2;
//        gridBagLayout.setConstraints(logOverPanel,gridBagConstraints);
//        return jPanel;
//    }
}
