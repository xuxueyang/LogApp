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
//implements  TagInterface
public  class LogBase {
    /**
     * 默认脏数据，需要写入
     */
    protected boolean isDiary = false;
    public boolean isExist = true;//采用逻辑删除
    public boolean isEncode = false;//默认没有加密
    private LogStatic.Tag tag;
    protected String uuid;
    protected Long createDate;
    protected Long updateDate;
    protected String belongDateStr;
//    protected String filePath = "";
    protected LogStatic.classify classify = LogStatic.classify.Default;
    protected LogStatic.level level = LogStatic.level.base;


    protected String message;
    protected String title;
    public Long getCreateDate() {
        return createDate;
    }

    public Long getUpdateData() {
        return updateDate;
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
        this.createDate = new Date().getTime();
        this.belongDateStr = LogStatic.GLOBAL_DATE;
        this.updateDate = this.createDate;
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
        this.updateDate = new Date().getTime();
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
//        if(this.belongDateStr==null){
//            this.belongDateStr=LogGenerator.pattern_date(createDate);
//            update();
//        }
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
        if(this.tag==null||"".equals(this.tag.name())){
            this.tag = LogStatic.Tag.Diary;
        }
        path = LogStatic.REAL_PATH + "/"+ this.tag.name() + "/" + path;//回头和“Log”区别
        return path;
    }
    public String getFilePath(){
        //TODO 以后把文件写成.dat的后缀吧。
        String path = getFolderPath() + "/" + uuid + "."+LogStatic.FileType.txt.name();
        return path;
    }
    @Override
    public String toString() {
        return LogGenerator.serialize(this);
    }

//    @Override
//    public String getBelongTag(){
//        return this.classify.name();
//    }
}
