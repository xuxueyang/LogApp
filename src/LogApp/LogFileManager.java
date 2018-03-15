package LogApp;

import LogApp.FileManager.LogFileLoadAndSave;
import LogApp.FileManager.LogFileManager_LogBase;
import LogApp.FileManager.LogFileManager_NoteTreeBase;
import LogApp.Model.LogBase;
import LogApp.Model.LogDay;
import LogApp.Model.LogEach;
import LogApp.Model.Tree.NoteTreeBase;
import LogApp.Tool.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理数据以及对文件的操作。
 */
public class LogFileManager {
    private static  LogFileManager logFileManager ;
    private LogFileManager(){
    }
    public static LogFileManager getLogFileManager(){
        if(logFileManager == null){
            logFileManager = new LogFileManager();
        }
        return logFileManager;
    }
    private LogFileManager_LogBase logFileManager_logBase = new LogFileManager_LogBase();
    private LogFileManager_NoteTreeBase logFileManager_noteTreeBase = new LogFileManager_NoteTreeBase();

////    private LogDay logDay;
//    private Map<String,LogBase> dataMap = new HashMap<>();
//    //目前先直接就写存储LogEach，之后要记录的是LogYear,或者因为每次操作的是eachPanel，阔以在初始化时
//    //将year下属的list =》 dataMap

    /**
     *加载日元数据
     * @param dayString  LogDay的文件名
     */
    public  void data_init_load_day(String dayString){
        //TODO 根据文件名加载数据
    }

    /**
     * 树有关的
     */
    public List<NoteTreeBase> getAllNoteTree(){
        return  logFileManager_noteTreeBase.getAllNoteTree();
    }
/**
 * Logday有关的
 */
    public void addLogDay(LogDay logDay){
        logFileManager_logBase.addLogDay(logDay);
    }
    public  LogDay createLogDay(String uuid){
        return logFileManager_logBase.createLogDay(uuid);
    }
    public  LogDay createLogTel(String uuid){
        return logFileManager_logBase.createLogTel(uuid);
    }
/***
 * each有关的
 */
    /**
     *
     * @param uuid
     * @return
     */
    public  LogEach createEach(String uuid){
        return logFileManager_logBase.createEach(uuid);
    }
    public  LogEach addLogEach(LogEach logEach){
        return logFileManager_logBase.addLogEach(logEach);
    }
    public LogBase changeMessage(String uuid,String message){
       return logFileManager_logBase.changeMessage(uuid,message);
    }
    public void changeOverState(String uuid){
        logFileManager_logBase.changeOverState(uuid);
    }
    public void delEachPanel(String uuid)throws IOException{
        logFileManager_logBase.delEachPanel(uuid);
    }
    public void saveAll()throws IOException{
        logFileManager_logBase.saveAll();
    }
    public static void main(String[] args){
        boolean b = Object.class.isAssignableFrom(String.class);
        System.out.println(b);
    }
    public <T> Object getDataByUUID(String uuid,Class<T> T){
        boolean b = LogBase.class.isAssignableFrom(T);
        if(b){
            return logFileManager_logBase.getDataByUUID(uuid,T);
        }else{
            return  null;
        }
    }
    public List<LogEach> getAllNoOverList(){
        return logFileManager_logBase.getAllNoOverList();
    }
    public List<LogEach> getAllOverList(){
        return logFileManager_logBase.getAllOverList();
    }
}
