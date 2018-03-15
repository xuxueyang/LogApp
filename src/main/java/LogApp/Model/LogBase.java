package LogApp.Model;

import LogApp.FileManager.LogFileLoadAndSave;
import LogApp.LogStatic;
import LogApp.Tool.LogGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ��ѩ�� on 2017/12/5.
 */
public  class LogBase {
    /**
     * Ĭ�������ݣ���Ҫд��
     */
    protected boolean isDiary = false;
    public boolean isExist = true;//�����߼�ɾ��
    public boolean isEncode = false;//Ĭ��û�м���

    protected String uuid;
    protected Date createDate;
    protected Date updateData;
    protected String belongDateStr;
//    protected String filePath = "";
    protected LogStatic.classify classify = LogStatic.classify.Default;
    protected LogStatic.level level = LogStatic.level.base;


    protected String message;
    protected String title;
    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateData() {
        return updateData;
    }

    public String getLevel() {
        if(level==null)
            return LogStatic.level.ERROR.name();
        return level.name();
    }

    /**
     * ���������ˣ����������л�ʱ���������Ҫ����ߵ�init...����ֱ����������˺ͳ�ʼ����Ӧ���ͺ���...
     * @param uuid
     */
    public LogBase(String uuid){
        init(uuid);
    }
    private void init(String uuid){
        this.createDate = new Date();
        this.belongDateStr = LogStatic.GLOBAL_DATE;
        this.updateData = this.createDate;
        this.message = "";
        this.title = uuid;
        this.uuid = uuid;
    }
    public void setMessage(String message) {
        if(!this.message.equals(message))
        {
            this.message = message;
            update();
        }
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        update();
    }


    public String getUuid() {
        return uuid;
    }

    public void update() {
        this.updateData = new Date();
        this.isDiary = true;
    }
    public   void save()throws IOException{
        if(this.isDiary){
            this.isDiary = false;//��Ϊ�����ˣ�����Ϊfalse��ͬʱ���������б��ⱻ�ݹ�
//            if(this.isExist){
////                ����Ҫ���£���Ϊ��ÿ�θ���Ҳ����isDiaryΪtrueʱ�ͻᱻ��ǣ���������ֻ��Ҫ����isDiaryΪfalse����
////                this.update();//��Ϊ������ͬʱ�����ݱ�Ǳ�ȥ������ʱ������Ը����޸�ʱ���ˡ�
//                LogFileManager.getLogFileManager().save(this);
//            }else{
//                LogFileManager.getLogFileManager().saveToDustbin(this);
//            }
            LogFileLoadAndSave.getLogFileLoadAndSave().save(this);

        }
    }
    public String getBelongDateStr(){
        if(this.belongDateStr==null){
            this.belongDateStr=LogGenerator.pattern_date(createDate);
            update();
        }
        return this.belongDateStr;
    }
    public  void del(){
        this.isExist = false;
        this.isDiary = true;
    }
    public String getFolderPath(){
        //���ݴ������ں�·������ȡ���������ļ�Ӧ�õ�·��
        String path = "";
        if(this.belongDateStr==null){
            SimpleDateFormat formatter = new SimpleDateFormat(LogStatic.Date_Path_Pattern);
            path = formatter.format(createDate);
        }else{
            path = this.belongDateStr.replace("-","/");
        }
//        path =  LogStatic.REAL_PATH + path+ "/"+ uuid + ".txt";
        path = LogStatic.REAL_PATH_DIARY + "/" + path;//��ͷ�͡�Log������
        return path;
    }
    public String getFilePath(){
        String path = getFolderPath() + "/" + uuid + ".txt";
        return path;
    }
    @Override
    public String toString() {
        return LogGenerator.serialize(this);
    }
}
