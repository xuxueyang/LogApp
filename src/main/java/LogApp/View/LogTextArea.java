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
 * Created by ��ѩ�� on 2017/12/6.
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
        //����ǽ����ˣ���ô������
        if(isOver){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(Color.white);
            for(int i=0;i<this.rows;i++){
//                System.out.println("rows:"+rows+" i:"+ i+ " getHeight"+ getHeight()+" y " + getY());
//                double y = this.getY()-this.getHeight()/2+this.getHeight()/this.rows*(0.5+i);
                double y = this.getY()-5+32*i;//��Ϊÿ��������32 =-= �ɴ�д������
                g2.draw(new Line2D.Double(this.getX()-this.getWidth()/2,y,this.getLocation().getX()+this.getWidth(),y));
            }
//            g2.draw(new Line2D.Double(x, y, 200, 200));
//            g2.drawString("��һ���ߵ�ʾ��", x, 250);
        }
    }

    public LogTextArea(final String uuid, String str, boolean isOver){
        super(1,col);
        //TODO �޸������������
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
        //TODO ����������JTextArea�Ĵ�С�ģ����Ǻ������ã���������鷳���Ҽ��ߣ�
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(e.isControlDown()&&(e.getKeyCode()==KeyEvent.VK_Z)){
                    //����Ϊ���ˣ���Ϊ������һ��Ĭ�ϵ�str
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
                //��ʱ��ctrl+v���Ѿ�ճ���������ˡ�
                stack.push(getText());
                LogNotification.broadcast(new LogEvent(getText(),LogStatic.resource.each_changeMessage.name(),uuid));
            }
        });
//        this.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                super.keyPressed(e);
//                if ((e.isControlDown() == true)&& (e.getKeyCode() == KeyEvent.VK_Z)){
//                    //������Ϣ
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
//                //ctrl����ʱ�������ǣ����������ctrl+v����Ҫ������
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
//                    e.setKeyChar('\0');//����5������������
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
////                ������ȳ����涨�ģ�Ҳ������ʾ
//                if((col+15)*maxRow<getText().length()){
//                    e.setKeyChar('\0');
//                }
//                //֪ͨ���ݵ��޸�
////                String tmp = "" + e.getKeyChar();
////                if("\\u0013".equals(tmp))
////                    tmp  = "";
//                //��Ϊ��ʱ���޸���Ҫuuid�����Բ�����������ã�������LogNotification�Ǳ����ðɡ�
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
    //��Ϊÿ��ֱ�����ûᵼ��ͬ�������һ���ַ�������������ʱ�����뻺�棬�ж������һ���ٸ�ֵ
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
