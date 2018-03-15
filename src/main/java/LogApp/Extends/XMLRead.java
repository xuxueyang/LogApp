package LogApp.Extends;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XMLRead {
	public static String delNote(String xml)
	{
		//É¾³ý×¢ÊÍ
		return xml.replaceAll("<!--"+".*?"+"-->", "");
	}
	public static ArrayList<String> getAllClass(String xml)
	{
		ArrayList<String> allName = new ArrayList<String>();
		//É¾³ý×¢ÊÍ£¬±ÜÃâ±»¸ÉÈÅ
		xml=delNote(xml);
		Matcher matcher = Pattern.compile("<xml-body>"+"(.*)"+"</xml-body").matcher(xml);
		String classPattern = "<"+"(\\S)+"+"(\\s)"+"(.*?)"+">"+"(\\s)*"+"</"+"(\\S)+"+">";
		if(matcher.find()){
			String body =  matcher.group(1);
			Matcher classMatcher = Pattern.compile(classPattern).matcher(body);
			
			while(classMatcher.find())
			{
				String tmp = classMatcher.group(0).split(" ")[0].substring(1);
				allName.add(tmp);
			}
		}
		if(allName.size()>=1)
			return allName;
		else
			return null;
	}
	public static String[] getAllKeyName(String parentKey,String xml)
	{
		Matcher matcher = Pattern.compile("<"+parentKey+"(.*?)"+">").matcher(xml);
		if(matcher.find()){
			String body =  matcher.group(1);
			String[] allName = body.split(" ");
			return allName;
		}
		return null;
	}
	
	
	public static String getValue(String parentKey,String key,String xml)
	{
		//É¾³ýËùÓÐµÄ×¢ÊÍ
		xml=delNote(xml);
		Matcher matcher = Pattern.compile(".*"+"<"+parentKey+"[^<>]*"+key+"="+"(\\S+?)"+"[\\s>]").matcher(xml);
		if(matcher.find()){
				String  str = matcher.group(1);
				if(str.startsWith("\"")||str.startsWith("\'"))
					str=str.substring(1);
				if(str.endsWith("\"")||str.endsWith("\'"))
					str=str.substring(0,str.length()-1);
				return str;
		}
		return "";
	}
	public static String setValue(String parentKey,String key,String xml,String newValue)
	{
		StringBuffer bufValue=new StringBuffer(newValue);
		if(!newValue.startsWith("\"")||!newValue.startsWith("'"))
		{
			bufValue.insert(0, "\"");
		}
		if(!newValue.endsWith("\"")||!newValue.endsWith("'"))
		{
			bufValue.append("\"");
		}
		Matcher matcher = Pattern.compile(".*"+"<"+parentKey+"[^<>]*"+key+"="+"(\\S+?)"+"[\\s>]").matcher(xml);
		while(matcher.find()){
			StringBuffer buffer = new StringBuffer(xml);
			buffer.replace(matcher.start(1), matcher.end(1), bufValue.toString());

			return buffer.toString();
		}
		return "";
	}
	
}
