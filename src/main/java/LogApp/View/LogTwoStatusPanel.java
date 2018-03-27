package LogApp.View;

import LogApp.LogFileManager;
import LogApp.LogStatic;
import LogApp.Model.LogEach;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by 徐雪阳 on 2017/12/6.
 */
public class LogTwoStatusPanel extends JPanel {
    private JPanel contentPanel;
    private JButton jButton;
//    private JScrollPane jScrollPane;
    public LogTwoStatusPanel(){
        this(null);
    }
    public LogTwoStatusPanel(JButton jButton){
        if(jButton !=null){
            this.jButton = jButton;
            this.add(this.jButton);
        }
    }
    private HashMap<String ,LogEachPanel> map = new HashMap<String ,LogEachPanel>();
    public void showByPriority(LogStatic.priority  priority){
        this.removeAll();
        for(String key:map.keySet()){
            LogEachPanel panel = map.get(key);
            //TODO 最好这里图像不涉及文件...
            LogEach logEach = (LogEach)LogFileManager.getLogFileManager().getDataByUUID(panel.uuid,LogEach.class);
            if(LogStatic.priority.Default.name().equals(priority.name())||logEach.getLogDetail().getPriority().name().equals(priority.name())){
                this.add(panel);
            }
        }
        repaint();
    }
    public void showByClassify(LogStatic.classify classify){
        this.removeAll();
        for(String key:map.keySet()){
            LogEachPanel panel = map.get(key);
            //TODO 最好这里图像不涉及文件...
            LogEach logEach = (LogEach)LogFileManager.getLogFileManager().getDataByUUID(panel.uuid,LogEach.class);
            if(LogStatic.classify.Default.name().equals(classify.name())||logEach.getLogDetail().getPriority().name().equals(classify.name())){
                this.add(panel);
            }
        }
        repaint();
    }
    public void showByMark(LogStatic.mark_star markStar){
        this.removeAll();
        for(String key:map.keySet()){
            LogEachPanel panel = map.get(key);
            //TODO 最好这里图像不涉及文件...
            LogEach logEach = (LogEach)LogFileManager.getLogFileManager().getDataByUUID(panel.uuid,LogEach.class);
            if(LogStatic.mark_star.DarkGray.name().equals(markStar.name())||logEach.getLogDetail().getMark_star().name().equals(markStar.name())){
                this.add(panel);
            }
        }
        repaint();
    }
    public  void addLogEachPanel(LogEachPanel logEachPanel){
//        if(this.jScrollPane==null){
//            this.jScrollPane = new JScrollPane();
//            jScrollPane.setPreferredSize(this.getSize());
////            jScrollPane.setViewportView(this);
//            this.add(jScrollPane);
//        }
        if(!map.containsKey(logEachPanel.uuid)){
            map.put(logEachPanel.uuid,logEachPanel);
            this.add(logEachPanel);
//            this.updateUI();
            validate();
            repaint();
        }
    }
    private boolean removeLogEacgPanel(LogEachPanel logEachPanel){
        if(map.containsKey(logEachPanel.uuid)){
            map.remove(logEachPanel.uuid);
            this.remove(logEachPanel);
            validate();
            repaint();
            return true;
        }else{
            return false;
        }
    }
    public boolean removeLogEacgPanel(String  uuid){
        if(!map.containsKey(uuid)){
            return true;
        }else{
            LogEachPanel logEachPanel = map.get(uuid);
            return removeLogEacgPanel(logEachPanel);
        }
    }
    public void changeMessage(String uuid,String message){
        if(map.containsKey(uuid)){
            LogEachPanel logEachPanel = map.get(uuid);
            if(!logEachPanel.getMessage().equals(message)){
                logEachPanel.setMessage(message);
            }
        }
    }
    /**
     * 根据能显示的数目重新展示修改显示的面板
     */
    public void  ReShow(int showCount){
        int count = this.getComponentCount();
        if(this.jButton!=null){
            showCount++;
        }
        Component[] components = this.getComponents();
        if(count>=showCount){
            // 优先移除第一个
//            for(int i=count-1;i>=showCount;i--){
//                this.remove(components[i]);
//            }
            for(int i=1;i<=count-showCount;i++){
                this.remove(components[i]);
            }
        }else{
            int k=showCount-count;
            for(String key:map.keySet()){
                Component tmp = map.get(key);
                if(!contains(tmp) && k>0){
                    this.add(tmp);
                    k--;
                }
            }
        }
        repaint();
    }
    public boolean contains(Component component){
        Component[] components = this.getComponents();
        for(Component com:components){
            if(com.equals(component)){
                return true;
            }
        }
        return false;
    }
    public boolean contains(String uuid){
        if(map.containsKey(uuid))
            return true;
        return false;
    }
    public synchronized void clear(){
            Object[] set = map.keySet().toArray();
            for(int i=0;i<set.length;i++)
                this.removeLogEacgPanel(map.get(set[i].toString()));
    }
}
