package plugIn;

import LogApp.FileManager.LogFileLoadAndSave;
import LogApp.LogStatic;
import LogApp.Tool.LogGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeDateToLong {
    // ��Ϊ�޸�ʱ���ʽΪLong����Ҫ������ǰ�ģ���string���л��ı��Long
    //TODO ��ȡ�����ļ�����ȡcreateDate��updateData����updateData���updateDate���Լ���������ġ�XXX"��ȡת��Ϊʱ�����
    public static void main(String[] args){
        List<String> list = LogFileLoadAndSave.getLogFileLoadAndSave().loadPUuidByPath(LogStatic.REAL_PATH_DIARY);
        //�жϣ�����ļ���txt��β����ô����ȡ�޸�
        for(String path:list){
            if(path.endsWith(".txt")){
                File file = new File(path);
                try {
                    String json = LogFileLoadAndSave.getLogFileLoadAndSave().loadJsonByPUUID(path);
//                    Pattern r = Pattern.compile("(.*)(updateData)(.*)");
//                    Matcher matcher = r.matcher(json);
//                    if(matcher.find()){
//
//                    }
                    json=json.replaceFirst("updateData","updateDate");
                    int index = json.lastIndexOf("updateDate");
                    //TODO �ض��滻
                    int k=0;int start=-1;int end = -1;
                    StringBuffer sb = new StringBuffer();
                    while(index<json.length()){
                        if(":".equals(""+json.charAt(index))&&k==0){
                            k++;
                        }
                        if(k>=1){
                            if("\"".equals(""+json.charAt(index))){
                                k++;
                                if(k==2){
                                    start=index;
                                }else if(k==3) {
                                    end=index;
                                    break;
                                }
                            }else if(start>0){
                                sb.append(json.charAt(index));
                            }
                        }
                        index++;
                    }
//                    System.out.println(path+" : "+sb.toString());
                    String time = LogGenerator.pattern_timestamp_date(sb.toString());
                    if(sb.toString().equals(time)){
                        System.out.println("updateDate: "+path+" : "+sb.toString());
                        continue;
                    }
                    json = ""+json.subSequence(0,start)+time.toString()+json.subSequence(end+1,json.length());
                    //TODO ����createdate�ĸ��£�
                    index = json.lastIndexOf("createDate");
                    //TODO �ض��滻
                    k=0;start=-1;end = -1;
                    sb = new StringBuffer();
                    while(index<json.length()){
                        if(":".equals(""+json.charAt(index))&&k==0){
                            k++;
                        }
                        if(k>=1){
                            if("\"".equals(""+json.charAt(index))){
                                k++;
                                if(k==2){
                                    start=index;
                                }else if(k==3) {
                                    end=index;
                                    break;
                                }
                            }else if(start>0){
                                sb.append(json.charAt(index));
                            }
                        }
                        index++;
                    }
//                    System.out.println(path+" : "+sb.toString());
                    time = LogGenerator.pattern_timestamp_date(sb.toString());
                    if(sb.toString().equals(time)){
                        System.out.println("createDate: "+path+" : "+sb.toString());
                        continue;

                    }
                    json = ""+json.subSequence(0,start)+time.toString()+json.subSequence(end+1,json.length());


//                    String[] strings = json.split(",\"");
//                    for(int i=0;i<strings.length;i++){
//                        if(strings[i].startsWith("updateData")){
//                            strings[i].replaceFirst("updateData","updateDate");
//                            String[] tmp = strings[i].split(":\"");
//                            String date = tmp[1].replaceAll("\"","");
//                            //date ת Long
//                            Long time = LogGenerator.pattern_timestamp_date(date);
//                        }
//                    }
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    bufferedOutputStream.write(json.getBytes(LogStatic.ENCODE));
                    bufferedOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
