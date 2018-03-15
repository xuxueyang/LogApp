package LogApp.FileManager;

import LogApp.LogStatic;
import LogApp.Model.Tree.NoteTreeBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogFileManager_NoteTreeBase implements LogFileManagerInterface{
    private Map<String,NoteTreeBase> dataMap = new HashMap<String, NoteTreeBase>();

    public void saveAll() throws IOException {
        for(NoteTreeBase noteTreeBase:dataMap.values()){
            noteTreeBase.save();
        }
    }
    public NoteTreeBase addNoteTree(){
        NoteTreeBase noteTreeBase = new NoteTreeBase();
        dataMap.put(noteTreeBase.getUUID(),noteTreeBase);
        return noteTreeBase;
    }
    public void changeTitle(String uuid,String title){

    }
    public void changeMessage(String uuid,String message){

    }
    public void removeNoteTree(){

    }
    public List<NoteTreeBase> getAllNoteTree(){
        List<NoteTreeBase> nodes = new ArrayList<NoteTreeBase>();
        List<String> puuidList = LogFileLoadAndSave.getLogFileLoadAndSave().loadPUuidByPath(LogStatic.REAL_PATH_NOTETREE);
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
            NoteTreeBase node = null;
            if(dataMap.containsKey(uuid)){
                node = dataMap.get(uuid);
                if(node.isExist){
//                    node = (NoteTreeBase)node;
                    nodes.add(node);
                }
            } else{
                node  = LogFileLoadAndSave.getLogFileLoadAndSave().load(puuid,NoteTreeBase.class);
                if(node!=null&&node.getUUID()!=null&&node.isExist){
                    dataMap.put(node.getUUID(),node);
                    nodes.add(node);
                }
            }
        }
        return nodes;
    }
}
