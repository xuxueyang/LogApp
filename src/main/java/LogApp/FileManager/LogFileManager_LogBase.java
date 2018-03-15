package LogApp.FileManager;

import LogApp.LogStatic;
import LogApp.Model.LogBase;
import LogApp.Model.LogDay;
import LogApp.Model.LogEach;
import LogApp.Model.Tree.NoteTreeBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogFileManager_LogBase implements LogFileManagerInterface{
    //    private LogDay logDay;
    private Map<String,LogBase> dataMap = new HashMap<String, LogBase>();
    //目前先直接就写存储LogEach，之后要记录的是LogYear,或者因为每次操作的是eachPanel，阔以在初始化时
    //将year下属的list =》 dataMap

    /**
     * day有关的
     */
    public void addLogDay(LogDay logDay){
        this.dataMap.put(logDay.getUuid(),logDay);
    }
    public  LogDay createLogDay(String uuid){
        LogDay logDay = new LogDay(uuid);
        logDay.type = LogStatic.Type.Day.name();
        this.dataMap.put(uuid,logDay);
        return logDay;
    }
    public  LogDay createLogTel(String uuid){
        LogDay logDay = new LogDay(uuid);
        logDay.type = LogStatic.Type.Tel.name();
        this.dataMap.put(uuid,logDay);
        logDay.setTitle(logDay.getTitle() + "的技术文档");
        return logDay;
    }
/***
 * each有关的
 */
    /**
     *
     * @param uuid
     * @return
     */
    public LogEach createEach(String uuid){
        LogEach logEach = new LogEach(uuid);
        this.dataMap.put(uuid,logEach);
        return logEach;
    }
    public  LogEach addLogEach(LogEach logEach){
        this.dataMap.put(logEach.getUuid(),logEach);
        return logEach;
    }
    public LogBase changeMessage(String uuid, String message){
        LogBase logBase = dataMap.get(uuid);
        if(logBase!=null){
            logBase.setMessage(message);
        }
        return logBase;
    }
    public void changeOverState(String uuid){
        LogBase logBase = dataMap.get(uuid);
        if(logBase!=null && logBase instanceof LogEach){
            LogEach logEach = ((LogEach)logBase);
            logEach.setOver(!((LogEach) logBase).getIsOver());
        }
    }
    public void delEachPanel(String uuid)throws IOException {
        LogBase logBase = dataMap.get(uuid);
        if(logBase!=null){
            logBase.del();
            logBase.save();
            dataMap.remove(uuid);
        }
    }
    @Override
    public void saveAll()throws IOException{
        //保存全部
        for(LogBase logBase:this.dataMap.values()){
            logBase.save();
        }
    }
    public <T> Object getDataByUUID(String uuid,Class<T> T){
        //内存中加载，如果没有，从LogFileAndSave中加载
        if(dataMap.containsKey(uuid))
            return dataMap.get(uuid);
        return  LogFileLoadAndSave.getLogFileLoadAndSave().loadByUUID(uuid,"",T);
    }
    public List<NoteTreeBase> getAllNoteTree(){
        List<NoteTreeBase> nodes = new ArrayList<>();
        List<String> puuidList = LogFileLoadAndSave.getLogFileLoadAndSave().loadPUuidByPath(LogStatic.REAL_PATH_NOTETREE);
        return null;
    }
    public List<LogEach> getAllNoOverList(){
        List<LogEach> logEachList = new ArrayList<>();
        List<String> puuidList = LogFileLoadAndSave.getLogFileLoadAndSave().loadPUuidByPath(LogStatic.REAL_PATH_DIARY);
        for(String puuid:puuidList){
            //判断缓存里有没有，没有再从文件里取
            String uuid="";
            try{
                uuid = puuid.substring(puuid.lastIndexOf("\\")+1,puuid.length());
                uuid = uuid.substring(0,uuid.lastIndexOf("."));
            }catch (Exception e){
                uuid="";
            }
            if("".equals(uuid)){
                continue;
            }
            LogEach logEach = null;
            if(dataMap.containsKey(uuid)){
                LogBase logBase = dataMap.get(uuid);
                if(logBase.isExist&&LogStatic.level.each.toString().equals(logBase.getLevel())){
                    logEach = (LogEach)logBase;
                    if(!logEach.getIsOver())
                        logEachList.add(logEach);
                }
            } else{
                logEach  = LogFileLoadAndSave.getLogFileLoadAndSave().load(puuid,LogEach.class);
                if(logEach!=null&&logEach.getUuid()!=null&&logEach.isExist&&LogStatic.level.each.name().equals(logEach.getLevel())){
                    dataMap.put(logEach.getUuid(),logEach);
                    if(!logEach.getIsOver())
                        logEachList.add(logEach);
                }
            }
        }
        return  logEachList;
    }
    public List<LogEach> getAllOverList(){
        List<LogEach> logEachList = new ArrayList<>();
        List<String> puuidList = LogFileLoadAndSave.getLogFileLoadAndSave().loadPUuidByPath(LogStatic.REAL_PATH_DIARY);
        for(String puuid:puuidList){
            //判断缓存里有没有，没有再从文件里取
            String uuid="";
            try{
                uuid = puuid.substring(puuid.lastIndexOf("\\")+1,puuid.length());
                uuid = uuid.substring(0,uuid.lastIndexOf("."));
            }catch (Exception e){
                uuid="";
            }
            if("".equals(uuid)){
                continue;
            }
            LogEach logEach = null;
            if(dataMap.containsKey(uuid)){
                LogBase logBase = dataMap.get(uuid);
                if(logBase.isExist&&LogStatic.level.each.toString().equals(logBase.getLevel())){
                    logEach = (LogEach)logBase;
                    if(logEach.getIsOver())
                        logEachList.add(logEach);
                }
            } else{
                logEach  = LogFileLoadAndSave.getLogFileLoadAndSave().load(puuid,LogEach.class);
                if(logEach!=null&&logEach.getUuid()!=null&&logEach.isExist&&LogStatic.level.each.name().equals(logEach.getLevel())){
                    dataMap.put(logEach.getUuid(),logEach);
                    if(logEach.getIsOver())
                        logEachList.add(logEach);
                }
            }
        }
        return  logEachList;
    }
}
