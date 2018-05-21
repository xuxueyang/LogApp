package LogApp.Model.Tree;

import LogApp.FileManager.LogFileLoadAndSave;
import LogApp.LogStatic;
import LogApp.Tool.LogGenerator;

import java.io.IOException;
import java.util.ArrayList;

enum TreeLevel{
    Top,
    Note,
    Bottom
}
public class NoteTreeBase {
    protected boolean isDiary = false;
    public boolean isExist = true;//�����߼�ɾ��
    public boolean isEncode = false;//Ĭ��û�м���
    protected String UUID;
    protected TreeLevel treeLevel;
    protected NoteTreeBase parrent;
    protected ArrayList<NoteTreeBase> arrayList = new ArrayList<NoteTreeBase>();
    protected String title="";
    protected String message="";

    public String getUUID() {
        return UUID;
    }

    public boolean isDiary() {
        return isDiary;
    }

    public ArrayList<NoteTreeBase> getNotes() {
        return arrayList;
    }

    private void update(){
        //�Ը�����и���
        NoteTreeBase Pparrent = getTop();
        if(!Pparrent.isDiary){
            Pparrent.update();
            this.isDiary = true;
        }
    }

    private NoteTreeBase getTop(){
        NoteTreeBase Pparrent = this.parrent;
        if(Pparrent==null||this.treeLevel.name().equals(TreeLevel.Top.name())){
            return this;
        }
        while (Pparrent.parrent!=null||!Pparrent.treeLevel.name().equals(TreeLevel.Top.name())){
            Pparrent = Pparrent.parrent;
        }
        return Pparrent;
    }
    public boolean isTop(){
        return this.treeLevel.name().equals(TreeLevel.Top.name());
    }
    public  void del(){
        this.isExist = false;
        this.isDiary = true;
    }
    public   void save()throws IOException {
        if(this.isDiary){
            this.isDiary = false;
            LogFileLoadAndSave.getLogFileLoadAndSave().save(this);
        }
    }
    public NoteTreeBase(){
        this.treeLevel = TreeLevel.Top;
        init();
    }
    public NoteTreeBase(NoteTreeBase parrent){
        this.parrent = parrent;
        this.treeLevel = TreeLevel.Bottom;
        init();
    }
    private void init(){
        this.UUID = LogGenerator.getUUID();
    }
    public  void addChildren(NoteTreeBase children){
        arrayList.add(children);
        this.treeLevel = TreeLevel.Note;
        update();
    }
    public boolean removeChildren(NoteTreeBase children){
        arrayList.remove(children);
        if(arrayList.size()<1){
            this.treeLevel = TreeLevel.Bottom;
        }
        update();
        return  true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(!this.title.equals(title)){
            this.title = title;
            update();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(!this.message.equals(message)){
            this.message = message;
            update();
        }
    }

    /**
     * ��Ϊ���в㼶��ϵ���������л�ʱֻ���л�Top�Ľڵ㡣
     * @return
     */
    public String getFolderPath(){
        //���ݴ������ں�·������ȡ���������ļ�Ӧ�õ�·��
        String path = LogStatic.REAL_PATH_NOTETREE;//��ͷ�͡�Log������
        return path;
    }
    public String getFilePath(){
        NoteTreeBase Pparrent = getTop();
        return getFolderPath() + "/" + Pparrent.UUID + "."+LogStatic.FileType.txt.name();
    }
    @Override
    public String toString() {
        return LogGenerator.serialize(this);
    }
}
