package LogApp.Tool;

import LogApp.FileManager.LogFileLoadAndSave;
import LogApp.LogStatic;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Tary implements ActionListener,MouseListener,WindowListener,WindowStateListener {
 
  private TrayIcon trayIcon=null;
  public static String imageName="x.png";
  public static String imagePath="";
  private Image iconImage;
  private ArrayList<JFrame> jFrameArray=new ArrayList<JFrame>();
  private PopupMenu popup= new PopupMenu();
  private int N;
  public Tary(){
    if (SystemTray.isSupported()) {
		File file = new File("");
		Tary.imagePath = LogFileLoadAndSave.searchFileByName(file.getAbsolutePath(),imageName);
		if("".equals(imagePath))
			iconImage = new RandomImage(40,40,1).getImage();
		else
			iconImage= Toolkit.getDefaultToolkit().getImage(imagePath);
		
    	SystemTray tray = SystemTray.getSystemTray();
    	MenuItem defaultItem = new MenuItem("退出！");
    	defaultItem.addActionListener(this);
    	popup.add(defaultItem);
    	
    	trayIcon = new TrayIcon(iconImage, "ScreenCapture", popup);
    	trayIcon.addMouseListener(this);
    	try {
    		tray.add(trayIcon);
    	} catch (AWTException e) {
			String error = LogGenerator.serialize(e);
			Log.Loggin(error,LogStatic.Tag.SystemLog.name());
    	}
    }
   }
  
  /*
   * frame为数组，用于添加后续的。根据framTitl来设置事件
   */
  public void addJFrame(JFrame jFrame)
  {
	  this.jFrameArray.add(jFrame);
	  jFrame.setIconImage(iconImage);
	  jFrame.addWindowListener(this);
	  jFrame.addWindowStateListener(this);
	  jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
	  if(jFrame.getTitle()=="")
		  jFrame.setTitle(Integer.toString(N++));
	  MenuItem defaultItem = new MenuItem(jFrame.getTitle());
	  defaultItem.addActionListener(this);
	  popup.add(defaultItem);
  }
  
  private int getIndex(String name)
  {
	  int i=0;
	 loop1: for(;i<this.jFrameArray.size();i++)
	  {
		  if(name.compareTo(this.jFrameArray.get(i).getTitle())==0)
			  break loop1;
	  }
	  if(i>=this.jFrameArray.size())
		  i=-1;
	  return i;
  }
  /*
   *以下全部为事件侦听
   */
  public void windowActivated(WindowEvent arg0) {
	 // System.out.println("windowActivated ");
  }
 
  public void windowClosed(WindowEvent arg0) {
	 // System.out.println("windowClosed ");
	  arg0.getComponent().setVisible(false);
	  //同时通知要保存一下数据
	  LogNotification.broadcast(new LogEvent(null, LogStatic.resource.global_exit.name()));
  }
 
  public void windowClosing(WindowEvent arg0) {
	 // System.out.println("windowClosing ");
  }
 
  public void windowDeactivated(WindowEvent arg0) {
	 // System.out.println("windowDeactivated ");
  }
 
  public void windowDeiconified(WindowEvent arg0) {
	 // System.out.println("windowDeiconified ");
	  
  }
 
  public void windowIconified(WindowEvent arg0) {
 //  System.out.println("windowIconified ");
   arg0.getComponent().setVisible(false);
  }
 
  public void windowOpened(WindowEvent arg0) {
	  //System.out.println("windowOpened ");
  }
  
  public void actionPerformed(ActionEvent e) {
	  String name=e.getActionCommand();
	  int i=this.getIndex(name);
	  if(i>=0)
		  this.jFrameArray.get(i).setVisible(true);  
	  else
	  {
		  for(JFrame frame:this.jFrameArray)
		  {
			   frame.setVisible(false);
		  }
		  //发送消息，通知应用程序关闭，同时保存数据
		  LogNotification.broadcast(new LogEvent(null, LogStatic.resource.global_exit.name()));
		  System.exit(0);
	  }
		  
}
  
  public void mouseClicked(MouseEvent e) {
   if(e.getClickCount()==2)
   {
	  for(JFrame frame:this.jFrameArray)
	  {
		   frame.setVisible(true);
		   frame.setExtendedState(JFrame.NORMAL);
		   frame.repaint();
	  }

   }
  }
 
  public void mouseEntered(MouseEvent arg0) {
  // System.out.println("mouseEntered ");
   
  }
 
  public void mouseExited(MouseEvent arg0) {
  // System.out.println("mouseExited ");
  }
 
  public void mousePressed(MouseEvent arg0) {
  // System.out.println("mousePressed ");
  }
 
  public void mouseReleased(MouseEvent arg0) {
   //System.out.println("mouseReleased ");
  }


	@Override
	public void windowStateChanged(WindowEvent arg0) {
		if(arg0.getNewState() == 1 || arg0.getNewState() == 7) {
//			System.out.println("窗口最小化");
			arg0.getComponent().setVisible(false);
		}
//		if(state.getNewState() == 1 || state.getNewState() == 7) {
//			System.out.println("窗口最小化");
//		}else if(state.getNewState() == 0) {
//			System.out.println("窗口恢复到初始状态");
//		}else if(state.getNewState() == 6) {
//			System.out.println("窗口最大化");
//		}
	}
}