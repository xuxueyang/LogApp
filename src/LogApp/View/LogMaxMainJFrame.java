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
 * ��ȶ���ÿ�յ�С��壬�������ƫ��ȫ�֣����������ݷ�����ͼ������ʾ����Լ���õ������������ݣ����Ӳ���ʱ��Ϊ��λ�ļƻ��б���������Ϊ��λ�ļƻ��б�
 * 2017-12-15���ܣ�1.����һ��������Ϊ��λ�ļ�¼���2.��ʾȫ����δ����б��Լ������Ĺ��ܡ�Tableʵ�ְɡ�
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
        //TODO ���ݵ����ͬ��ť�����ز�ͬ������塣
        jScrollPane = new JScrollPane();
        detailPanel.add(jScrollPane);
        this.setSize(LogStatic.maxDefaultWidth,LogStatic.maxDefaultHeight);
        this.setLocation(80,80);
        //���в���
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
                //TODO ��ʼ��invokeFun
                //TODO �ù�����ʼ����С
                //TODO INVOKE����ʼ�����ݡ�
            this.invokeFun = LogFactory_MaxFun.getFunPanelByName(functionPanel);
        }
        if(this.invokeFun==null)
        {
            Log.Loggin("δ�ҵ��ù��������:"+functionPanel.name(),LogStatic.Log_classify.SystemLog.name());
            return;
        }
        try {
            invokeFun.Invoke(methodName,params);
            //Ϊtrue�ı�״̬��Ϊfalse����ר�ŵķ�����
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
            //�˵���
            JMenu mFile = new JMenu ( "File");
            JMenu mEdit = new JMenu ( "Edit");
            JMenu mFormat = new JMenu ("Format");
            JMenu mHelp = new JMenu ("Help");

            //"�ļ�"
            JMenuItem miNew = new JMenuItem ("New");
            JMenuItem miOpen = new JMenuItem ("Open");
            JMenuItem miSave = new JMenuItem ("Save");
            JMenuItem  miSaveAs = new JMenuItem ("Save as");
            JMenuItem  miExit = new JMenuItem ("Exit");

            //"�༭"
            JMenuItem  miCut = new JMenuItem ("Cut");
            JMenuItem  miCopy = new JMenuItem ("Copy");
            JMenuItem  miPaste = new JMenuItem ("Paste");
            JMenuItem  miDelete = new JMenuItem ("Delete");

            //"��ʽ"
            JMenuItem    miFont = new JMenuItem ("Font");
            JMenuItem   miLowtoCapital = new JMenuItem("Low to Capital");
            JMenuItem    miCapitaltoLow = new JMenuItem("Capital to Low");
            JMenuItem    miEncrypt = new JMenuItem("Encrypt");
            JMenuItem  miDisencrypt = new JMenuItem("Disencrypt");

            //"����"
            JMenuItem    miAboutNotepad = new JMenuItem ("About LogApp");
            //����ļ��˵���
            mFile.add(miNew);
            mFile.add(miOpen);
            mFile.add(miSave);
            mFile.add(miSaveAs);
            mFile.add(miExit);

            //��ӱ༭�˵���
            mEdit.add(miCut);
            mEdit.add(miCopy);
            mEdit.add(miPaste);
            mEdit.add(miDelete);

            //��Ӹ�ʽ�˵���
            mFormat.add(miFont);
            mFormat.add(miLowtoCapital);
            mFormat.add(miCapitaltoLow);
            mFormat.add(miEncrypt);
            mFormat.add(miDisencrypt);

            //��Ӱ����˵���
            mHelp.add(miAboutNotepad);

            //�˵�����Ӳ˵�
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
