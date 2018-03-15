//package com.xxy.main;
//
//import java.util.ArrayList;
//import javax.swing.JFrame;
//import com.xxy.set.setJFrame;
//
//public class mainJava {
//	/***
//	 * 设置面板仅仅是动态读取xml文件建立相应的tab面板，在初始化mainJava时，根据配置文件，读取xml文件，利用里节点的名字，来反射初始化这个类，然后，根据名字，通过反射判断是否有这个变量而能够通过set方法，来设置默认值。__也根据needShow来判断是否加入这个面板
//	 * @param args
//	 */
//	public static void main(String[] args)
//	{
//		/**
//		 * 交付到设置面板中初始化！！！
//		 */
//		ArrayList<JFrame> frameArray = new ArrayList<JFrame>();
//		Tary tary = new Tary();
//		//设置面板————用于决定以及列举，是否要加入一些面板，开机自启等___反过来说，setJFrame仅用于图形化xml文件的设置而已，本质上仅此而已。
//		setJFrame set = new setJFrame();
//		ArrayList<String> needInitFrame = set.getInitFrameName();
//		tary.addJFrame(set);
//		if(needInitFrame!=null)
//		{
//			for(String frame:needInitFrame)
//			{
//				if(!(frame.compareTo("setJFrame")==0))
//				{
//					JFrame jf = set.initAskJFrameByString(frame);//打包后，这里出现了未知错误！
//					if(jf!=null)
//						frameArray.add(jf);
//				}
//			}
//		}
//		for(JFrame frame:frameArray){
//			tary.addJFrame(frame);
//		}
//	}
//}
