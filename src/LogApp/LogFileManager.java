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
 * ���������Լ����ļ��Ĳ�����
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
//    //Ŀǰ��ֱ�Ӿ�д�洢LogEach��֮��Ҫ��¼����LogYear,������Ϊÿ�β�������eachPanel�������ڳ�ʼ��ʱ
//    //��year������list =�� dataMap

    /**
     *������Ԫ����
     * @param dayString  LogDay���ļ���
     */
    public  void data_init_load_day(String dayString){
        //TODO �����ļ�����������
    }

    /**
     * ���йص�
     */
    public List<NoteTreeBase> getAllNoteTree(){
        return  logFileManager_noteTreeBase.getAllNoteTree();
    }
/**
 * Logday�йص�
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
 * each�йص�
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
