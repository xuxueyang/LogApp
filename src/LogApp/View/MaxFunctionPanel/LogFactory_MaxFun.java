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
     * ��Щ��ť����LogStatic.FunctionPanel��ѡ������
     * @return ����mainPanel���
     */
    public static JPanel getMainJPanel(){
        JPanel mainPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        mainPanel.setBackground(Color.darkGray);
        GridBagConstraints s= new GridBagConstraints();//����һ��GridBagConstraints��
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
                    //��ӵ���¼����㲥�޸�mainPanel��
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
//        //TODO ��ӵ���¼�
//        JComboBox markJComboBox = LogFactory.getMarkJComboBox();
//        JComboBox classifyJComboBox = LogFactory.getClassifyJComboBox();
//        JComboBox priorityJComboBox = LogFactory.getPriorityJComboBox();
//        jPanel.add(classifyJComboBox);
//        jPanel.add(priorityJComboBox);
//        jPanel.add(markJComboBox);
//        jPanel.add(logOverPanel);
//        //TODO ��Ӳ���
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
