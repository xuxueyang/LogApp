package LogApp.FileManager;

import LogApp.LogStatic;
import LogApp.Model.LogBase;
import LogApp.Model.Tree.NoteTreeBase;
import LogApp.Tool.LogGenerator;
import LogApp.Tool.LogValidators;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFileLoadAndSave {
    private static  LogFileLoadAndSave logFileLoadAndSave ;
    private LogFileLoadAndSave(){

    }
    public static LogFileLoadAndSave getLogFileLoadAndSave(){
        if(logFileLoadAndSave == null){
            logFileLoadAndSave = new LogFileLoadAndSave();
        }
        return logFileLoadAndSave;
    }

    /**
     * 因为只有UUID，默认从最初路径加载
     * @param uuid
     * @param <T>
     * @return
     */
    public <T> T loadByUUID(String uuid,String path,Class<T> T){
        if(LogValidators.isEmpty(path)){
            path = LogStatic.REAL_PATH;
        }
        //只支持LogBase的加载
//        if(!LogValidators.isInRange(T.getName(),LogStatic.level.base.name())){
//            return  null;
//        }
        //因为没有路径，就采取遍历循环当前目录找数据文件了
        String puuid = LogFileLoadAndSave.searchFileByName(path,uuid);
        if(puuid=="")
            return null;
        else {
            return load(puuid,T);
        }
    }
    /**
     *
     * @param PUuid path+uuid的结合
     * @param T
     * @param <T>
     * @return
     */
    public  <T> T load(String PUuid, Class<T> T){
        String json = loadJsonByPUUID(PUuid);
        if(!"".equals(json)){
            //TODO 添加解密功能,反射查看有没有解密的字段，有的话，对其中的文本解密。
            //TODO gson解析日期，如果含有Mar这种，会解析数字出错--
            return LogGenerator.unserialize(json,T);
        }
        return  null;
    }
    public String loadJsonByPUUID(String pUuid){
        File file = new File(pUuid);
        if(file.exists()) {
            //读取数据
            StringBuffer json = new StringBuffer();
            String m;
            BufferedReader bufferedReader = null;
            try {
//                new FileReader(file)   ————因为InputStreamReader StreamDecoder.forInputStreamReader Charset.defaultCharset().name();
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file),LogStatic.ENCODE);
                bufferedReader = new BufferedReader(reader);
                while ((m = bufferedReader.readLine()) != null) {
                    json.append(m);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
            return json.toString();
        }
        return "";
    }

    /**
     * 得到路径下全部的uuid
     * @param path
     * @return
     */
    public List<String> loadPUuidByPath(String path){
        List<String> list = new ArrayList<String>();
        File file = new File(path);
        if(file.isDirectory()){
            for(File tmp:file.listFiles()){
                List<String> tmpList = loadPUuidByPath(tmp.getAbsolutePath());
                for(String  str:tmpList){
                    list.add(str);
                }
            }
        }else{
            //  强制规定uuid的是不含-，以区别是不是LogEach？或者利用LogBase的classify？
//            if(!file.getName().contains("-"))
            list.add(file.getAbsolutePath());
        }
        return list;
    }


    /**
     * logBase中的save是调用文件管理的，所以本身里面不能再调用save
     * @param logBase
     */
    public void save(LogBase logBase) throws IOException {

        File file = new File(logBase.getFolderPath());
        if(!file.exists()){
            file.mkdirs();
        }
        file = new File(logBase.getFilePath());
        FileOutputStream o=new FileOutputStream(file,false);
        //TODO 加密
        o.write(logBase.toString().getBytes(LogStatic.ENCODE));
        o.close();
    }
    public void save(NoteTreeBase noteTreeBase) throws IOException {
        File file = new File(noteTreeBase.getFolderPath());
        if(!file.exists()){
            file.mkdirs();
        }
        file = new File(noteTreeBase.getFilePath());
        FileOutputStream o=new FileOutputStream(file,false);
        // TODO 加密
        o.write(noteTreeBase.toString().getBytes(LogStatic.ENCODE));
        o.close();
    }
//    public void save(LogEach logEach) throws IOException{
//        //这里可以用xml格式的存储，也可以使用json，json比较简单，而xml的话，有层次结构。emmmm每条记录用json格式存储，而总文件meta数据，用xml吧。
////        System.out.println(LogGenerator.serialize(logEach));
//        File file = new File(logEach.getFolderPath());
//        if(!file.exists()){
//            file.mkdirs();
//        }
//        file = new File(logEach.getFilePath());
//        FileOutputStream o=new FileOutputStream(file,false);
//        o.write(logEach.toString().getBytes(LogStatic.ENCODE));
//        o.close();
//    }
    public static String searchFileByName(String path,String name)
    {
        String string = "";
        File file = new File(path);
        //递归获取文件
        //TODO 为了性能，先判断一下有没有这样的文件
        String Fpath = file.getAbsolutePath();
        for(String str:file.list() ){
            File tmp = new File(Fpath+"/"+str);
            if(tmp.isDirectory())
            {
                String tmpStr = searchFileByName(tmp.getAbsolutePath(),name);
                if(tmpStr.compareTo("")!=0)
                    return tmpStr;
            }
            else{
                if(tmp.getName().compareTo(name)==0)
                    return tmp.getAbsolutePath();
            }
        }
        return string;
    }
}
