package LogApp.Model;

import LogApp.FileManager.LogFileLoadAndSave;
import LogApp.LogStatic;
import LogApp.Tool.LogGenerator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 徐雪阳 on 2017/12/5.
 */
public  class LogBase {
    /**
     * 默认脏数据，需要写入
     */
    protected boolean isDiary = false;
    public boolean isExist = true;//采用逻辑删除
    public boolean isEncode = false;//默认没有加密

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
     * 陷入误区了，，，反序列化时候根本不需要走这边的init...而是直接添加数据了和初始化相应面板就好了...
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
            this.isDiary = false;//因为保存了，设置为false，同时可以无意中避免被递归
//            if(this.isExist){
////                不需要更新，因为在每次更新也就是isDiary为true时就会被标记，所以这里只需要设置isDiary为false即可
////                this.update();//因为保存了同时脏数据标记被去掉，这时候就阔以更新修改时间了。
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
        //根据创建日期和路径，获取到该数据文件应该的路径
        String path = "";
        if(this.belongDateStr==null){
            SimpleDateFormat formatter = new SimpleDateFormat(LogStatic.Date_Path_Pattern);
            path = formatter.format(createDate);
        }else{
            path = this.belongDateStr.replace("-","/");
        }
//        path =  LogStatic.REAL_PATH + path+ "/"+ uuid + ".txt";
        path = LogStatic.REAL_PATH_DIARY + "/" + path;//回头和“Log”区别
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
