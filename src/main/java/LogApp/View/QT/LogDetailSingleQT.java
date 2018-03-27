package LogApp.View.QT;

import LogApp.LogFactory;
import LogApp.LogStatic;
import LogApp.Model.LogBase;
import LogApp.Model.LogDetail;
import LogApp.Model.LogEach;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogGenerator;
import LogApp.Tool.LogNotification;

//import com.trolltech.qt.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Objects;

/**
 * qt��������ʾϸ�ڵ�panel
 * ʹ�ÿ��ӻ��༭�����ؽ��档�������¼�������ģʽ
 */
public class LogDetailSingleQT extends JFrame {
    private String parentUUID;
    private JTextArea qTextEdit;
    private JComboBox  jComboBox_mark;
    private JComboBox  jComboBox_classify;
    private JComboBox  jComboBox_priority;
    private JScrollPane jScrollPane;

    private LogDetailSingleQT(){
        super();
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        //��������������ȼ�����������ǵ�����
        jComboBox_classify = LogFactory.getClassifyJComboBox();
        jComboBox_classify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox = (JComboBox)e.getSource();
                //������֪ͨ
                LogNotification.broadcast(new LogEvent(LogStatic.classify.values()[jComboBox.getSelectedIndex()],
                        LogStatic.resource.detail_changeClassify.name(),
                        parentUUID
                ));
            }
        });
        this.add(jComboBox_classify);
        jComboBox_priority = LogFactory.getPriorityJComboBox();
        jComboBox_priority.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox = (JComboBox)e.getSource();
                //������֪ͨ
                LogNotification.broadcast(new LogEvent(LogStatic.priority.values()[jComboBox.getSelectedIndex()],
                        LogStatic.resource.detail_changePriority.name(),
                        parentUUID
                ));
            }
        });
        this.add(jComboBox_priority);

        jComboBox_mark = LogFactory.getMarkJComboBox();
        jComboBox_mark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox = (JComboBox)e.getSource();
                Object[] objects = jComboBox.getSelectedObjects();
                Color color = (Color)objects[0];
                jComboBox_mark.setBackground(color);
                //TODO ��������ˢ�±�������bug
                jComboBox_mark.validate();
                jComboBox_mark.repaint();
                //������֪ͨ
                LogNotification.broadcast(new LogEvent(LogStatic.mark_star.values()[jComboBox.getSelectedIndex()],
                        LogStatic.resource.detail_changeMarkStar.name(),
                        parentUUID
                        ));
            }
        });
        this.add(jComboBox_mark);
        // �༭���

        qTextEdit = new JTextArea();
        qTextEdit.setLineWrap(true);
        qTextEdit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                LogNotification.broadcast(new LogEvent(qTextEdit.getText(), LogStatic.resource.detail_changeMessage.name(),parentUUID));
            }
        });
        qTextEdit.setSize(new Dimension(LogStatic.detailFrameWidth,LogStatic.detailFrameHeight));
        jScrollPane = new JScrollPane(qTextEdit);
        this.add(jScrollPane);
//        this.add(qTextEdit);
        //���ò���
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridy=0;
//        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx=0;
        gridBagConstraints.weighty=0;
        gridBagConstraints.gridx=0;
        gridBagLayout.setConstraints(jComboBox_classify,gridBagConstraints);
        gridBagConstraints.gridx=1;
        gridBagLayout.setConstraints(jComboBox_priority,gridBagConstraints);
        gridBagConstraints.gridx=2;
        gridBagLayout.setConstraints(jComboBox_mark,gridBagConstraints);
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.weighty = 2;
//        gridBagLayout.setConstraints(qTextEdit,gridBagConstraints);
        gridBagLayout.setConstraints(jScrollPane,gridBagConstraints);
    }
    private static LogDetailSingleQT logDetailSingleQT;

    public static LogDetailSingleQT getLogDetailSingleQT() {
        if(logDetailSingleQT == null){
            //QWidget: Must construct a QApplication before a QPaintDevice
            String[] args = {};
//            QApplication.initialize(args);
            logDetailSingleQT = new LogDetailSingleQT();
            logDetailSingleQT.init();
            logDetailSingleQT.setSize(300,400);
        }
        return logDetailSingleQT;
    }

    /**
     * һ����ʾ�϶�����Ҫһ�����ݵģ�������UUID��������һ��������壬���ؼ���
     */
    private void init(){
        //��ʼ������
        qTextEdit.setText("");
    }
    private void loadData(LogDetail logBase){
        //TODO ��ʼ������
        this.parentUUID = logBase.getParentUUID();
        this.qTextEdit.setText(logBase.getRemarks());
        LogStatic.classify[] values_classify = LogStatic.classify.values();
        for(int i=0;i<values_classify.length;i++){
            if(values_classify[i].equals(logBase.getClassify())){
                this.jComboBox_classify.setSelectedIndex(i);
                break;
            }
        }
        LogStatic.priority[] values_priority = LogStatic.priority.values();
        for(int i=0;i<values_priority.length;i++){
            if(values_priority[i].equals(logBase.getPriority())){
                this.jComboBox_priority.setSelectedIndex(i);
                break;
            }
        }
        LogStatic.mark_star[] values_mark = LogStatic.mark_star.values();
        for(int i=0;i<values_mark.length;i++){
            if(values_mark[i].equals(logBase.getMark_star())){
                this.jComboBox_mark.setSelectedIndex(i);
                this.jComboBox_mark.setBackground(LogGenerator.getColorByString(values_mark[i].name()));
                break;
            }
        }
    }
    public   void moveTo(double offX,double offY){
        if(isVisible()){
//            this.setLocation((int)(LogStatic.x+offX),(int)(LogStatic.y+offY));
            this.setLocation((int)offX,(int)offY);
        }
    }
    public   void move(double offX,double offY){
        if(isVisible()){
            this.setLocation((int)(this.getLocation().x+offX),(int)(this.getLocation().y+offY));
        }
    }
    //    ������ctrlֱ�Ӽ������ݴ��������׵�= - =������View������ļ�ϵͳ��
//    public static void showDetail(String UUID){
//        //��������
//        getLogDetailSingleQT().setVisible(true);
//    }
//    public void changeMarkStarColor(LogStatic.mark_star markStar ){
//        this.jComboBox_mark.setBackground(LogGenerator.getColorByString(markStar.name()));
//    }
    public  void showDetail(LogDetail logDetail,double offX,double offY){
        //��������
        loadData(logDetail);
       this.setVisible(true);
        //�ƶ�
        moveTo(offX,offY);
    }
    public  void hideDetail(){
        init();
        this.setVisible(false);
    }
    public static void main(String[] args) {
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("Default Charset in Use=" + getDefaultCharSet());
    }

    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }
}
//public class LogDetailSingleQT extends QFrame {
//    private String parentUUID;
//    private QTextEdit qTextEdit;
//    private LogDetailSingleQT(){
//        super();
//        qTextEdit = new QTextEdit();
////        QEvent qEvent =  new QEvent(QEvent.Type.GrabKeyboard);
////        qEvent.accept();
////        qTextEdit.add
////        qTextEdit.connectSlotsByName();
////        qTextEdit.event(qEvent);
//        //TODO ��Ҫ ����һ���㲥
////TODO        LogNotification.broadcast(new LogEvent(qTextEdit.toPlainText(), LogStatic.resource.detail_changeMessage.name(),parentUUID));
////            logDetailSingleQT.qTextEdit.textChanged = new Signal0(
////                @Override void emit(){
////            }
////            );
//        QVBoxLayout qvBoxLayout = new QVBoxLayout();
//        qvBoxLayout.addWidget(qTextEdit);
//        this.setLayout(qvBoxLayout);
//    }
//    private static LogDetailSingleQT logDetailSingleQT;
//
//    public static LogDetailSingleQT getLogDetailSingleQT() {
//        if(logDetailSingleQT == null){
//            //QWidget: Must construct a QApplication before a QPaintDevice
//            String[] args = {};
//            QApplication.initialize(args);
//            logDetailSingleQT = new LogDetailSingleQT();
//            logDetailSingleQT.init();
////            logDetailSingleQT.setFixedSize(80,100);
//            logDetailSingleQT.setMinimumSize(200,500);
////            logDetailSingleQT.setWindowFlags(Qt.WindowType.Popup);
//        }
//        return logDetailSingleQT;
//    }
//
//    /**
//     * һ����ʾ�϶�����Ҫһ�����ݵģ�������UUID��������һ��������壬���ؼ���
//     */
//    private void init(){
//        //��ʼ������
//        qTextEdit.clear();
//    }
//    private void loadData(LogDetail logBase){
//        //TODO ��ʼ������
//        this.parentUUID = logBase.getParentUUID();
//    }
//    public   void move(double offX,double offY){
//        if(isVisible()){
//            //TODO �ƶ������ö�λ
//            this.move(LogStatic.x+offX,LogStatic.y+offY);
//        }
//    }
////    ������ctrlֱ�Ӽ������ݴ��������׵�= - =������View������ļ�ϵͳ��
////    public static void showDetail(String UUID){
////        //��������
////        getLogDetailSingleQT().setVisible(true);
////    }
//    public  void showDetail(LogDetail logDetail,double offX,double offY){
//        //��������
//        loadData(logDetail);
////        getLogDetailSingleQT().setVisible(true);
//        QApplication.execStatic();
//        QApplication.shutdown();
//        show();
////        setWindowFlags(Qt.WindowType.WindowStaysOnTopHint);
////        setWindowFlags();
//
//        //�ƶ�
//        move(offX,offY);
//
//    }
//    public  void hideDetail(){
//        init();
////        getLogDetailSingleQT().setVisible(false);
//        hide();
//    }
//}

