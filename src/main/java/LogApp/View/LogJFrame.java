package LogApp.View;

import LogApp.Tool.LogEvent;
import LogApp.LogFactory;
import LogApp.LogStatic;
import LogApp.Tool.LogNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Created by ��ѩ�� on 2017/12/5.
 * ��Ϊ���壬������ʾ����
 */
public  class LogJFrame extends JFrame implements ActionListener {
    /**
     * ��ק�Ĺ���
     */
    private Rectangle rect;
    private int frameLeft;// ��������Ļ��ߵľ���
    private int frameRight;// ��������Ļ�ұߵľ��룻
    private int frameTop;// ��������Ļ�����ľ���
    private int frameWidth;	// ����Ŀ�
    private int frameHeight;	// ����ĸ�

    private int screenXX;// ��Ļ�Ŀ�ȣ�
    private Point point;	// ����ڴ����λ��
//    private boolean isVisible = this.isVisible();

    private Timer timer = new Timer(30, this);
    private boolean isDraging;
//    private boolean isFocusabe = true;
    /**
     * ��ʾ��������
     */
    private  int maxTask = 6;
    private  int maxOverTask = 1;
    private  int maxNoOverTask = maxTask-maxOverTask;
    private  int numOverTask ;
    private  int numNoOverTask ;
    private static LogJFrame logJFrame;
    public static  LogJFrame getLogJFrame(){
        if(logJFrame==null){
            logJFrame = new LogJFrame();
            logJFrame.setLocation(LogStatic.x,LogStatic.y);
            logJFrame.setSize(LogStatic.defaultWidth,LogStatic.defaultHeight);
            logJFrame.setResizable(false);
        }
        return logJFrame;
    }
    private LogMenu logMenu;
    private LogTwoStatusPanel noOverPanel;
    private LogTwoStatusPanel isOverPanel;
    private HashMap<String,LogEachPanel> logEachPanelHashMap = new HashMap<String,LogEachPanel>();
    private LogJFrame(){
        this.initData();
        this.initView();
    }
    public static void main(String[] args){
        LogJFrame jFrame = LogJFrame.getLogJFrame();
        jFrame.setVisible(true);

    }
    private void initData(){
        isDraging = false;
        numOverTask = 0;
        numNoOverTask = 0;
    }

    public LogMenu getLogMenu() {
        return logMenu;
    }

    private void initView(){
//        this.removeAll();
        this.setLayout(new BorderLayout());
//        this.logMenu = new LogMenu();
        this.logMenu = LogFactory.getLogMenu();
        this.add(logMenu, BorderLayout.NORTH);
        this.noOverPanel = LogFactory.getLogNoOverPanel(true);
        this.add(noOverPanel,BorderLayout.CENTER);
        this.isOverPanel = LogFactory.getLogOverPanel(true);
        this.add(isOverPanel,BorderLayout.SOUTH);
        /**
         * ������ק���صĹ��ܣ�����¼�����
         */
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                isDraging = true;
            }

            public void mouseReleased(MouseEvent e) {
                isDraging = false;
            }
        });
        this.ReShow();
    }
//    public boolean canAddEachPanel(){
//        //TODO �޸����֧�ֵĴ�����ֻ���Զ����ص�һ��
////        return this.numOverTask+this.numNoOverTask < this.maxTask||this.numOverTask>this.maxOverTask;
//        return true;
//    }
    public Rectangle getRect(){
        return new Rectangle(this.frameLeft,frameTop,frameWidth,frameHeight);
    }
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(b){
//            this.isVisible = true;
            timer.start();
        }else{
//            this.isVisible = false;
            timer.stop();
        }
    }
    public void changeMessage(String uuid,String message){
        if(logEachPanelHashMap.containsKey(uuid)){
            LogEachPanel logEachPanel = logEachPanelHashMap.get(uuid);
            if(!logEachPanel.getMessage().equals(message)){
                logEachPanel.setMessage(message);
                hasChange();
            }

        }
    }

    public  void delEachPanel(String uuid){
        LogEachPanel logEachPanel;
        if(logEachPanelHashMap.containsKey(uuid))
            logEachPanel = logEachPanelHashMap.get(uuid);
        else
            return;
        if(logEachPanel.isOver()){
            if(this.isOverPanel.removeLogEacgPanel(logEachPanel.uuid)){
                this.numOverTask--;
            }
        }else{
            if(this.noOverPanel.removeLogEacgPanel(logEachPanel.uuid)){
                this.numNoOverTask--;
            }
        }
        if(logEachPanelHashMap.containsKey(logEachPanel.uuid)){
            logEachPanelHashMap.remove(logEachPanel.uuid);
        }
        this.ReShow();
    }
    public  void delEachPanel(LogEachPanel logEachPanel){
        if(logEachPanel.isOver()){
            if(this.isOverPanel.removeLogEacgPanel(logEachPanel.uuid)){
                this.numOverTask--;
            }
        }else{
            if(this.noOverPanel.removeLogEacgPanel(logEachPanel.uuid)){
                this.numNoOverTask--;
            }
        }
        if(logEachPanelHashMap.containsKey(logEachPanel.uuid)){
            logEachPanelHashMap.remove(logEachPanel.uuid);
        }
        this.ReShow();
    }
    public void addEachPanel(LogEachPanel logEachPanel){
        if(logEachPanel.isOver()){
            this.numOverTask++;
            this.isOverPanel.addLogEachPanel(logEachPanel);
        }
        else{
            this.numNoOverTask++;
            this.noOverPanel.addLogEachPanel(logEachPanel);
        }
        logEachPanelHashMap.put(logEachPanel.uuid,logEachPanel);
        this.ReShow();
    }
    //TODO =-=Ϊʲô����ı�״̬����ӻ���ӳٲ�ˢ����ͼ��....
    public void changeOverState(LogEachPanel logEachPanel){
        if(logEachPanel.isOver()){
            if(this.noOverPanel.removeLogEacgPanel(logEachPanel.uuid)){
                this.numNoOverTask--;
            }
            this.isOverPanel.addLogEachPanel(logEachPanel);
            this.numOverTask++;
        }else{
            if(this.isOverPanel.removeLogEacgPanel(logEachPanel.uuid)){
                this.numOverTask--;
            }
            this.noOverPanel.addLogEachPanel(logEachPanel);
            this.numNoOverTask++;
        }
        this.ReShow();

    }
    private  void ReShow(){
        if(this.numOverTask+this.numNoOverTask<=this.maxTask){
            this.noOverPanel.ReShow(this.numNoOverTask);
            this.isOverPanel.ReShow(this.numOverTask);
        }else{
            int tmp = this.maxOverTask - this.numOverTask ;
            tmp = tmp > 0? tmp:0;
            int noOverShowCount = this.numNoOverTask > (this.maxNoOverTask+tmp)? this.maxNoOverTask+tmp:this.numNoOverTask;
            this.noOverPanel.ReShow(noOverShowCount);
            this.isOverPanel.ReShow(this.maxTask-noOverShowCount);
        }
        validate();
        repaint();
    }
    /**
     * ����Զ����������Ĺ���
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isDraging&& this.isVisible()){
            //�����˳����Ҫ������ǰ�������Detail����Ϊ��������ƫ�ƶ����ơ����������ҵúúô�����Ȼ��Ϊ���뵼��main���أ�Detailһ������
            autoAdsorb();
//            if(getLocation().x!=LogStatic.x||getLocation().y!=LogStatic.y){
//                Point point = new Point(getLocation().x-LogStatic.x,getLocation().y-LogStatic.y);
//                LogNotification.broadcast(new LogEvent(point,LogStatic.resource.detail_move.name(),null));
//            }
        }
    }
    private  void autoAdsorb(){
        frameLeft = getLocationOnScreen().x;
        frameTop = getLocationOnScreen().y;
        frameWidth = getWidth();
        frameHeight = getHeight();
        screenXX = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        frameRight =screenXX- frameLeft - frameWidth;

        // ��ȡ���������
        rect = new Rectangle(0, 0, frameWidth, frameHeight);
        // ��ȡ����ڴ����λ��
        point =getMousePosition();
        //��������ʱ���Զ��ö�
        if ((frameTop > -5 && frameTop < 5) && (frameLeft > -5 && frameLeft < 5) && !(isPtInRect(rect, point))) {
            // ��������ϱ߿�����Ļ�Ķ��˵ľ���С��5ʱ ��
            // ������겻�ٴ����Ͻ��������ص���Ļ�Ķ���
            setLocation(frameLeft - frameWidth + 1, 5);
            setAlwaysOnTop(true);
        } else if ((frameTop < 0) && isPtInRect(rect, point)) {
            setLocation(frameLeft, 0);// ���������ˣ����ָ����������ʾ������
            setAlwaysOnTop(false);
        } else if (frameTop > -5 && frameTop < 5 && !(isPtInRect(rect, point))) {
            // ��������ϱ߿�����Ļ�Ķ��˵ľ���С��5ʱ ��
            // ������겻�ٴ����Ͻ��������ص���Ļ�Ķ���
            setLocation(frameLeft, 5 - frameHeight);
            setAlwaysOnTop(true);
        }else{
            //�����������������ק������Ӱ��
            if(getLocation().x!=LogStatic.x||getLocation().y!=LogStatic.y){
                Point point = new Point(getLocation().x-LogStatic.x,getLocation().y-LogStatic.y);
                LogNotification.broadcast(new LogEvent(point,LogStatic.resource.detail_move.name(),null));
            }
        }
        LogStatic.x = getLocation().x;
        LogStatic.y = getLocation().y;
    }
    /**
     * ����Ƿ��ھ��ο���
     * @param rect
     * @param point
     * @return
     */
    private boolean isPtInRect(Rectangle rect, Point point) {
        if (rect != null && point != null) {
            int x0 = rect.x;
            int y0 = rect.y;
            int x1 = rect.width;
            int y1 = rect.height;
            int x = point.x;
            int y = point.y;

            return x >= x0 && x < x1 && y >= y0 && y < y1;
        }
        return false;
    }
    public LogTwoStatusPanel getNoOverPanel(){
        return this.noOverPanel;
    }
    public LogTwoStatusPanel getIsOverPanel(){
        return this.isOverPanel;
    }
    public void clear(){
        //���ݲ���Ҫ����Ȩ�����棬��ͼ��Ҫ��ʼ��
        initData();
//        initView();
        this.isOverPanel.clear();
        this.noOverPanel.clear();
    }
    public void hasChange(){
        if(!LogJFrame.getLogJFrame().getTitle().endsWith(LogStatic.ChangeStateString)){
            LogJFrame.getLogJFrame().setTitle(LogJFrame.getLogJFrame().getTitle()+LogStatic.ChangeStateString);
        }
    }
    public void hasSave(){
        String title = LogJFrame.getLogJFrame().getTitle();
        if(title.endsWith(LogStatic.ChangeStateString))
            LogJFrame.getLogJFrame().setTitle(title.substring(0,title.length()-LogStatic.ChangeStateString.length()));
    }
}
