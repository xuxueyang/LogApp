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
		// private方法，使得无法被实例化
	}
	private  static List<LogEvent> logEventList = new ArrayList<LogEvent>();
	public static  synchronized void addLogEvent(LogEvent logEvent){
		//判断，如果UUID为空，那么不做记录=-=这样阔以避免一些无意义的拖动的操作
		if(logEvent.getUuid()!=null){
//                缓存，在定时器中，统一写入文件以避免对文件的频繁操作
			//判断这个操作是不是和上次一样，保证操作记录不会因为each_message而特别频繁
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
	 * @param log 内容
	 * @param classify  分类类别，目前支持Log：系统日志与Operator:操作日志
	 * @return
	 */
	public static String Loggin(String log,String classify)
	{
		if(!LogValidators.isInRange(classify, LogStatic.Tag.SystemLog.name(),LogStatic.Tag.OperatorLog.name())){
			return "";
		}
		//每次启动的时候，都会生成一个日志文件————名字为日期
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(LogStatic.Date_Pattern);
		String dstDir = formatter.format(date);
//		String dstDir = LogGenerator.getNowDate();
		File dstFile=null;
		if("".equals(LogStatic.REAL_PATH))
		{
			File directory = new File("");//设定为当前文件夹
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

		//把内容追加到txt文本最后
		FileOutputStream o=null;
		String errMsg="";
		try {
			o=new FileOutputStream(dstFile,true);
			try {
				o.write(log.getBytes(encode));
				//获取系统换行符，写入
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
