//package com.xxy.main;
//
//import java.util.ArrayList;
//import javax.swing.JFrame;
//import com.xxy.set.setJFrame;
//
//public class mainJava {
//	/***
//	 * �����������Ƕ�̬��ȡxml�ļ�������Ӧ��tab��壬�ڳ�ʼ��mainJavaʱ�����������ļ�����ȡxml�ļ���������ڵ�����֣��������ʼ������࣬Ȼ�󣬸������֣�ͨ�������ж��Ƿ�������������ܹ�ͨ��set������������Ĭ��ֵ��__Ҳ����needShow���ж��Ƿ����������
//	 * @param args
//	 */
//	public static void main(String[] args)
//	{
//		/**
//		 * ��������������г�ʼ��������
//		 */
//		ArrayList<JFrame> frameArray = new ArrayList<JFrame>();
//		Tary tary = new Tary();
//		//������塪���������ھ����Լ��о٣��Ƿ�Ҫ����һЩ��壬����������___������˵��setJFrame������ͼ�λ�xml�ļ������ö��ѣ������Ͻ��˶��ѡ�
//		setJFrame set = new setJFrame();
//		ArrayList<String> needInitFrame = set.getInitFrameName();
//		tary.addJFrame(set);
//		if(needInitFrame!=null)
//		{
//			for(String frame:needInitFrame)
//			{
//				if(!(frame.compareTo("setJFrame")==0))
//				{
//					JFrame jf = set.initAskJFrameByString(frame);//��������������δ֪����
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
