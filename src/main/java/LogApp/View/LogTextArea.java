package LogApp.View;

import LogApp.Tool.LogEvent;
import LogApp.Tool.LogNotification;
import LogApp.LogStatic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.util.Date;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 徐雪阳 on 2017/12/6.
 */
public class LogTextArea extends JTextArea {
    private Stack<String> stack = new Stack<String>();
    private String uuid;
    private static  int col = 40;
    private int maxRow= LogStatic.LogTextArea_maxRow;
    private int rows;
    private boolean isOver;
//    private long timestamp = new Date().getTime();
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //如果是结束了，那么画道杠
        if(isOver){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(Color.white);
            for(int i=0;i<this.rows;i++){
//                System.out.println("rows:"+rows+" i:"+ i+ " getHeight"+ getHeight()+" y " + getY());
//                double y = this.getY()-this.getHeight()/2+this.getHeight()/this.rows*(0.5+i);
                double y = this.getY()-5+32*i;//因为每次增长是32 =-= 干脆写死得了
                g2.draw(new Line2D.Double(this.getX()-this.getWidth()/2,y,this.getLocation().getX()+this.getWidth(),y));
            }
//            g2.draw(new Line2D.Double(x, y, 200, 200));
//            g2.drawString("画一条线的示例", x, 250);
        }
    }

    public LogTextArea(final String uuid, String str, boolean isOver){
        super(1,col);
        //TODO 修改字体避免乱码
        this.setFont(new Font(LogStatic.FontName, Font.PLAIN,LogStatic.FontSize));
        this.uuid = uuid;
        int max = LogStatic.menuWidth-LogStatic.eachPanelCheckBoxWidth-10-LogStatic.eachPanel_detailButtonWidth-LogStatic.eachPanel_delButtonWidth;
        while(getColumnWidth()*col>max){
            setColumns(--col);
//            System.out.println(col);
        }
        this.setAutoscrolls(true);
        this.setLineWrap(true);
        this.isOver = isOver;
        this.setText(str);
        stack.push(str);
        this.check();
        //TODO 本来想限制JTextArea的大小的，但是后来觉得，这个功能麻烦而且鸡肋，
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(e.isControlDown()&&(e.getKeyCode()==KeyEvent.VK_Z)){
                    //不会为空了，因为至少有一个默认的str
                    if(stack.empty())
                        return;
                    String tmp = "";
                    if(stack.size()<=1){
                        tmp = stack.get(0);
                    }else{
                        tmp = stack.pop();
                    }
                    setText(tmp);
                    LogNotification.broadcast(new LogEvent(tmp,LogStatic.resource.each_changeMessage.name(),uuid));
                    repaint();
                    return;
                }
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    setText(getText()+LogStatic.LineDivision);
                }
                //这时候ctrl+v的已经粘贴到这里了。
                stack.push(getText());
                LogNotification.broadcast(new LogEvent(getText(),LogStatic.resource.each_changeMessage.name(),uuid));
            }
        });
//        this.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                super.keyPressed(e);
//                if ((e.isControlDown() == true)&& (e.getKeyCode() == KeyEvent.VK_Z)){
//                    //撤回消息
//                    if(stack.empty())
//                        return;
//                    String tmp = stack.pop();
//                    setText(tmp);
//                    LogNotification.broadcast(new LogEvent(tmp,LogStatic.resource.each_changeMessage.name(),uuid));
//                    repaint();
//                    return;
//                }
//            }
//            @Override
//            public void keyTyped(KeyEvent e) {
//                //ctrl按下时不做考虑（但是如果是ctrl+v则需要考虑了
//                if(e.isControlDown()==true&&(e.getKeyCode()==KeyEvent.VK_V))
//                {

//                }
//                if(e.isControlDown() == true)
//                    return;
////                super.keyTyped(e);
//                String str = getText();
//                int row = 1;
//                while(str.indexOf("\n")!=-1){
//                    str = str.substring(str.indexOf("\n")+1);
//                    row++;
//                }
//                rows = row>maxRow?maxRow:row;
//
//                if(row>maxRow) {
//                    e.setKeyChar('\0');//超过5行则不能再输入
//                    StringBuffer stringBuffer = new StringBuffer();
//                    str = getText();
//                    for(int i=0;i<maxRow;i++){
//                        if(i<maxRow-1)
//                            stringBuffer.append(str.substring(0,str.indexOf("\n")+1));
//                        else
//                            stringBuffer.append(str.substring(0,str.indexOf("\n")));
//                        str = str.substring(str.indexOf("\n")+1);
//                    }
//                    setText(stringBuffer.toString());
//                }
////                如果长度超过规定的，也不能显示
//                if((col+15)*maxRow<getText().length()){
//                    e.setKeyChar('\0');
//                }
//                //通知数据的修改
////                String tmp = "" + e.getKeyChar();
////                if("\\u0013".equals(tmp))
////                    tmp  = "";
//                //因为到时候修改需要uuid，所以不能在这边设置，还是在LogNotification那边设置吧。
////                if(new Date().getTime()-timestamp>5000){
////                    LogNotification.broadcast(new LogEvent(getText()+e.getKeyChar(),LogStatic.resource.each_changeMessage.name(),uuid));
////                }else{
////                    LogNotification.broadcast(new LogEvent(getText()+e.getKeyChar(),LogStatic.resource.each_changeMessage.name(),null));
////                }
////                timestamp = new Date().getTime();
//                if(stack.size()>0){
//                    String last = stack.get(stack.size()-1);
//                    if(!last.equals(getText()+e.getKeyChar())){
//                        cacheStr = getText()+e.getKeyChar();
//                        LogNotification.broadcast(new LogEvent(getText()+e.getKeyChar(),LogStatic.resource.each_changeMessage.name(),uuid));
//                        stack.push(getText()+e.getKeyChar());
//                    }
//                }else{
//                    cacheStr = getText()+e.getKeyChar();
//                    LogNotification.broadcast(new LogEvent(getText()+e.getKeyChar(),LogStatic.resource.each_changeMessage.name(),uuid));
//                    stack.push(getText()+e.getKeyChar());
//                }
////                System.out.println(getText()+e.getKeyChar());
//            }
//        });
    }
    public void setIsOver(boolean isOver){
        this.isOver = isOver;
//        System.out.print(isOver);
        validate();
        repaint();
    }
    public void check(){
        String str = getText();
        int row = 1;
        while(str.indexOf("\n")!=-1){
            str = str.substring(str.indexOf("\n")+1);
            row++;
        }
        rows = row>maxRow?maxRow:row;
        if(rows>maxRow){
            StringBuffer stringBuffer = new StringBuffer();
            str = getText();
            for(int i=0;i<maxRow;i++){
                if(i<maxRow-1)
                    stringBuffer.append(str.substring(0,str.indexOf("\n")+1));
                else
                    stringBuffer.append(str.substring(0,str.indexOf("\n")));
                str = str.substring(str.indexOf("\n")+1);
            }
            setText(stringBuffer.toString());
        }
    }
    //因为每次直接设置会导致同步而多出一个字符，所以增加暂时的输入缓存，判断如果不一样再赋值
    private String cacheStr = "";
    /**
     *
     * @param message
     */
    public void setMessage(String message){
        if(!cacheStr.equals(message)){
            this.setText(message);
//            this.check();
        }
        cacheStr="";
    }
    public String getMessage(){
        return this.getText();
    }
}
