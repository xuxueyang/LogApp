package LogApp.Tool;

import LogApp.LogStatic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public  class Log {
	private Log() {
		// private������ʹ���޷���ʵ����
	}
	private  static List<LogEvent> logEventList = new ArrayList<LogEvent>();
	public static  synchronized void addLogEvent(LogEvent logEvent){
		//�жϣ����UUIDΪ�գ���ô������¼=-=�������Ա���һЩ��������϶��Ĳ���
		if(logEvent.getUuid()!=null){
//                ���棬�ڶ�ʱ���У�ͳһд���ļ��Ա�����ļ���Ƶ������
			//�ж���������ǲ��Ǻ��ϴ�һ������֤������¼������Ϊeach_message���ر�Ƶ��
			if(!(logEventList.size()>0
					&&logEventList.get(logEventList.size()-1).getUuid()==logEvent.getUuid()
					&&logEventList.get(logEventList.size()-1).getResource()==logEvent.getResource()
					&&logEvent.getTimestamp()-logEventList.get(logEventList.size()-1).getTimestamp()<LogStatic.Operator_Delay)){
				logEventList.add(logEvent);
				if(logEventList.size()>= LogStatic.NLogEventCacheSize){
					refreshCache();
				}
			}
		}
	}
	public static synchronized void refreshCache(){
		if(logEventList.size()<=0)
			return;
		Log.Loggin(Log.changeArrayToString(logEventList), LogStatic.Tag.OperatorLog.name());
		logEventList.clear();
	}
//	private static String path = LogStatic.REAL_PATH_LOG;
    public static String encode = Charset.defaultCharset().displayName();
	public static String changeArrayToString(List<LogEvent> list){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<list.size();i++){
			sb.append(list.get(i).toString());
			if(i<list.size()-1)
				sb.append( System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	/**
	 *
	 * @param log ����
	 * @param classify  �������Ŀǰ֧��Log��ϵͳ��־��Operator:������־
	 * @return
	 */
	public static String Loggin(String log,String classify)
	{
		if(!LogValidators.isInRange(classify, LogStatic.Tag.SystemLog.name(),LogStatic.Tag.OperatorLog.name())){
			return "";
		}
		//ÿ��������ʱ�򣬶�������һ����־�ļ�������������Ϊ����
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(LogStatic.Date_Pattern);
		String dstDir = formatter.format(date);
//		String dstDir = LogGenerator.getNowDate();
		File dstFile=null;
		if("".equals(LogStatic.REAL_PATH))
		{
			File directory = new File("");//�趨Ϊ��ǰ�ļ���
			File tmp = new File(directory.getAbsolutePath()   + "/" + classify);
			if(!tmp.exists())
				tmp.mkdirs();
			dstFile=new File(directory.getAbsolutePath() +"/" + classify + "/" +dstDir+"_LOGFILE"+".txt");
		}
		else
		{
			String path = LogStatic.REAL_PATH + "/" + classify;
			File tmp = new File(path);
			if(!tmp.exists())
				tmp.mkdirs();
			dstFile=new File(path  + "/" + dstDir + "_LOGFILE"+".txt");
		}

		//������׷�ӵ�txt�ı����
		FileOutputStream o=null;
		String errMsg="";
		try {
			o=new FileOutputStream(dstFile,true);
			try {
				o.write(log.getBytes(encode));
				//��ȡϵͳ���з���д��
				o.write( System.getProperty("line.separator").getBytes(encode));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				errMsg=e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				errMsg=e.getMessage();
			}finally{
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			errMsg=e.getMessage();
		}finally{	
				try {
					if(o!=null)
						o.close();
				} catch (IOException e) {
					e.printStackTrace();
					errMsg=e.getMessage();
				}
		}
		
		return errMsg;
	}

//	public static String getPath() {
//		return path;
//	}
//
//	public static boolean setPath(String path) {
//		File file = new File(path);
//		if (!file.isDirectory())
//			return false;
//		Log.path = path;
//		return true;
//	}

}
