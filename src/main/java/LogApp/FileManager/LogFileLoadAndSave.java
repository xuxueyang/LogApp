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
     * ��Ϊֻ��UUID��Ĭ�ϴ����·������
     * @param uuid
     * @param <T>
     * @return
     */
    public <T> T loadByUUID(String uuid,String path,Class<T> T){
        if(LogValidators.isEmpty(path)){
            path = LogStatic.REAL_PATH;
        }
        //ֻ֧��LogBase�ļ���
//        if(!LogValidators.isInRange(T.getName(),LogStatic.level.base.name())){
//            return  null;
//        }
        //��Ϊû��·�����Ͳ�ȡ����ѭ����ǰĿ¼�������ļ���
        String puuid = "";
        if(!LogStatic.isUseLocalDataBase){
            puuid =  LogFileLoadAndSave.searchFileByName(path,uuid);
        }else{
            puuid=uuid;
        }
        if(puuid=="")
            return null;
        else {
            return load(puuid,T);
        }
    }
    /**
     *
     * @param PUuid path+uuid�Ľ��
     * @param T
     * @param <T>
     * @return
     */
    public  <T> T load(String PUuid, Class<T> T){
        String json = "";
        if(!LogStatic.isUseLocalDataBase){
            json = loadJsonByPUUID(PUuid);
            if(!"".equals(json)){
                //TODO ��ӽ��ܹ���,����鿴��û�н��ܵ��ֶΣ��еĻ��������е��ı����ܡ�
                // gson�������ڣ��������Mar���֣���������ֳ���--
                return LogGenerator.unserialize(json,T);
            }
        }else {
            //�����ݿ����ȡ
            //TODO �Լ�д������ʵ����model�ɡ�
            //�ȵõ�uuid
            String[] strs = PUuid.split("\\\\");
            String uuid = strs[strs.length-1];
            //�����ļ��ĺ�׺
            uuid = uuid.split(".")[0];
            //ͨ��uuid+T��name��������
        }
        return  null;
    }
    public String loadJsonByPUUID(String pUuid){
        File file = new File(pUuid);
        if(file.exists()) {
            //��ȡ����
            StringBuffer json = new StringBuffer();
            String m;
            BufferedReader bufferedReader = null;
            try {
//                new FileReader(file)   ����������ΪInputStreamReader StreamDecoder.forInputStreamReader Charset.defaultCharset().name();
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
     * �õ�·����ȫ����uuid
     * @param path
     * @return
     */
    public List<String> loadPUuidByPath(String path){
        List<String> list = new ArrayList<String>();
        if(!LogStatic.isUseLocalDataBase){
            File file = new File(path);
            if(file.isDirectory()){
                for(File tmp:file.listFiles()){
                    List<String> tmpList = loadPUuidByPath(tmp.getAbsolutePath());
                    for(String  str:tmpList){
                        list.add(str);
                    }
                }
            }else{
                //  ǿ�ƹ涨uuid���ǲ���-���������ǲ���LogEach����������LogBase��classify��
//            if(!file.getName().contains("-"))
                list.add(file.getAbsolutePath());
            }
            return list;
        }else{
            String[] tags = path.split("\\\\");
            String tag = tags[tags.length-1];
            boolean contain = false;
            for(LogStatic.Tag tmp:LogStatic.Tag.values()){
                if(tmp.name().equals(tag)){
                    contain = true;
                    break;
                }
            }
            if(!contain){
                //����ȫ����uuid
            }
            //���ݿ����uuid
            return list;
        }

    }


    /**
     * logBase�е�save�ǵ����ļ�����ģ����Ա������治���ٵ���save
     * @param logBase
     */
    public void save(LogBase logBase) throws IOException {
        if(!LogStatic.isUseLocalDataBase){
            File file = new File(logBase.getFolderPath());
            if(!file.exists()){
                file.mkdirs();
            }
            file = new File(logBase.getFilePath());
            FileOutputStream o=new FileOutputStream(file,false);
            //TODO ����
            o.write(logBase.toString().getBytes(LogStatic.ENCODE));
            o.close();
        }else {
            //��Ϊ==�Ҷ��ǲ�������ת�͵ķ�ʽ�洢�ģ����ǿȿ�==�ͺܾ��������û�취���������ж���
            String className = logBase.getClass().getName();
            //TODO ��Ҫ֧��������ô�洢ӳ�䵽���ݿ�����
        }
    }
    public void save(NoteTreeBase noteTreeBase) throws IOException {
        if(!LogStatic.isUseLocalDataBase){
            File file = new File(noteTreeBase.getFolderPath());
            if(!file.exists()){
                file.mkdirs();
            }
            file = new File(noteTreeBase.getFilePath());
            FileOutputStream o=new FileOutputStream(file,false);
            // TODO ����
            o.write(noteTreeBase.toString().getBytes(LogStatic.ENCODE));
            o.close();
        }else{
            //��Ϊ==�Ҷ��ǲ�������ת�͵ķ�ʽ�洢�ģ����ǿȿ�==�ͺܾ��������û�취���������ж���
            String className = noteTreeBase.getClass().getName();
            //TODO ��Ҫ֧��������ô�洢ӳ�䵽���ݿ�����
        }

    }
//    public void save(LogEach logEach) throws IOException{
//        //���������xml��ʽ�Ĵ洢��Ҳ����ʹ��json��json�Ƚϼ򵥣���xml�Ļ����в�νṹ��emmmmÿ����¼��json��ʽ�洢�������ļ�meta���ݣ���xml�ɡ�
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
        //�ݹ��ȡ�ļ�
        //TODO Ϊ�����ܣ����ж�һ����û���������ļ�
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
