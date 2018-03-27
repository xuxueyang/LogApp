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
 * Created by 徐雪阳 on 2017/12/5.
 * 作为窗体，负责显示界面
 */
public  class LogJFrame extends JFrame implements ActionListener {
    /**
     * 拖拽的功能
     */
    private Rectangle rect;
    private int frameLeft;// 窗体离屏幕左边的距离
    private int frameRight;// 窗体离屏幕右边的距离；
    private int frameTop;// 窗体离屏幕顶部的距离
    private int frameWidth;	// 窗体的宽
    private int frameHeight;	// 窗体的高

    private int screenXX;// 屏幕的宽度；
    private Point point;	// 鼠标在窗体的位置
//    private boolean isVisible = this.isVisible();

    private Timer timer = new Timer(30, this);
    private boolean isDraging;
//    private boolean isFocusabe = true;
    /**
     * 显示数据设置
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
         * 增加拖拽隐藏的功能，添加事件监听
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
//        //TODO 修改最大支持的次数，只是自动隐藏第一个
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
    //TODO =-=为什么这里改变状态的添加会很延迟才刷新视图呢....
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
     * 添加自动吸附收缩的功能
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isDraging&& this.isVisible()){
            //这里的顺序需要吸附在前，否则会Detail会因为继续计算偏移而上移。———而且得好好处理，不然因为传入导致main隐藏，Detail一起隐藏
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

        // 获取窗体的轮廓
        rect = new Rectangle(0, 0, frameWidth, frameHeight);
        // 获取鼠标在窗体的位置
        point =getMousePosition();
        //当吸附的时候自动置顶
        if ((frameTop > -5 && frameTop < 5) && (frameLeft > -5 && frameLeft < 5) && !(isPtInRect(rect, point))) {
            // 当窗体的上边框与屏幕的顶端的距离小于5时 ，
            // 并且鼠标不再窗体上将窗体隐藏到屏幕的顶端
            setLocation(frameLeft - frameWidth + 1, 5);
            setAlwaysOnTop(true);
        } else if ((frameTop < 0) && isPtInRect(rect, point)) {
            setLocation(frameLeft, 0);// 窗口隐藏了，鼠标指到他，就显示出来；
            setAlwaysOnTop(false);
        } else if (frameTop > -5 && frameTop < 5 && !(isPtInRect(rect, point))) {
            // 当窗体的上边框与屏幕的顶端的距离小于5时 ，
            // 并且鼠标不再窗体上将窗体隐藏到屏幕的顶端
            setLocation(frameLeft, 5 - frameHeight);
            setAlwaysOnTop(true);
        }else{
            //其他情况就是正常拖拽，不会影响
            if(getLocation().x!=LogStatic.x||getLocation().y!=LogStatic.y){
                Point point = new Point(getLocation().x-LogStatic.x,getLocation().y-LogStatic.y);
                LogNotification.broadcast(new LogEvent(point,LogStatic.resource.detail_move.name(),null));
            }
        }
        LogStatic.x = getLocation().x;
        LogStatic.y = getLocation().y;
    }
    /**
     * 检测是否在矩形框内
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
        //数据不需要清理，权当缓存，视图需要初始化
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
