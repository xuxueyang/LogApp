package example.e_20180315;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import com.itextpdf.text.log.Logger;

/**
 * 只看功能，不重视外观
 * @author chaigw
 *
 */
public class ConfigFrame extends JFrame {

    Container container;
    public ConfigFrame() {
        this.setSize(400, 300);
        this.setTitle("CSDN 博客下载器");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        container = this.getContentPane();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public ConfigFrame(String blogName)
    {
        this();
        fillBlogName(blogName);
    }

    public void fillBlogName(String blogName)
    {
        JLabel lb = new JLabel("http://blog.csdn.net/");
        //此处需添加一点就没的功能，以后再说
        JTextField tf = new JTextField(blogName);
        tf.setColumns(6);
        JButton bt = new JButton("生成PDF");
        FlowLayout flow = new FlowLayout();
        JPanel req = new JPanel();
        req.setLayout(flow);
        req.add(lb);
        req.add(tf);
        req.add(bt);
        JPanel log = new JPanel();
        JScrollPane scroLog = new JScrollPane();
        scroLog.setPreferredSize (new Dimension (320,220));
        log.setSize(300,200);
        JTextArea area = new JTextArea(10, 30);
        area.setLineWrap(true);
        log.add(area);
        scroLog.setViewportView(area);
        container.add(req,BorderLayout.CENTER);
//      container.add(log,BorderLayout.SOUTH);
        container.add(scroLog,BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        ConfigFrame configFrame  = new ConfigFrame("");
        configFrame.setVisible(true);
    }
}
