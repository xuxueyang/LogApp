package LogApp.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 徐雪阳 on 2017/12/6.
 */
public class LogOverPanel_tmp extends JPanel {
    private JPanel contentPanel;
    private JButton jButton;
    private JScrollPane jScrollPane;
    public  LogOverPanel_tmp(){
        this(null);
    }
    public LogOverPanel_tmp(JButton jButton){
        if(jButton !=null){
            this.jButton = jButton;
            this.add(this.jButton);
        }
    }
    private JPanel getContentPanel(){
        if(this.contentPanel==null){
            Dimension size = this.getSize();
            if(jButton!=null){
                size.height -=jButton.getHeight();
            }
            this.contentPanel = new JPanel();
            this.jScrollPane = new JScrollPane();
            jScrollPane.setPreferredSize(size);
            jScrollPane.setViewportView(contentPanel);
            this.add(jScrollPane);
        }
        return this.contentPanel;
    }
    private List<LogEachPanel> list = new ArrayList<LogEachPanel>();
    public  void addLogEachPanel(LogEachPanel logEachPanel){

        list.add(logEachPanel);
        getContentPanel().add(logEachPanel);
        repaint();
    }
    public boolean removeLogEacgPanel(LogEachPanel logEachPanel){
        if(list.contains(logEachPanel)){
            list.remove(logEachPanel);
            getContentPanel().remove(logEachPanel);
            repaint();
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据能显示的数目重新展示修改显示的面板
     */
    public void  ReShow(int showCount){
        int count = getContentPanel().getComponentCount();
        Component[] components = getContentPanel().getComponents();
        if(count>=showCount){
            for(int i=count-1;i>=showCount;i--){
                getContentPanel().remove(components[i]);
            }
        }else{
            int k=showCount-count;
            for(int i=0;i<list.size();i++){
                Component tmp = list.get(i);
                if(!contains(tmp) && k>0){
                    getContentPanel().add(tmp);
                    k--;
                }
            }
        }
        repaint();
    }
    public boolean contains(Component component){
        Component[] components = getContentPanel().getComponents();
        for(Component com:components){
            if(com.equals(component)){
                return true;
            }
        }
        return false;
    }
    public synchronized void clear(){
        while(list.size()>0){
            this.removeLogEacgPanel(list.get(0));
        }
    }
}
