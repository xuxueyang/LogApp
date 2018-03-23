package LogApp.Model;

import LogApp.LogStatic;

/**
 * Created by 徐雪阳 on 2017/12/5.
 */
//implements TagInterface
    //TODO 现在还有个问题==在LogDetail里的分类是对于日志的，也就是说，其实现在做的分类应该是在这个层级之上的分类
public class LogEach  extends  LogBase {
//    private LogS  tag;
    private boolean isOver = false;

    public  boolean getIsOver(){
        return this.isOver;
    }
    public void  setOver(boolean result){
        this.update();
        this.isOver = result;
    }
    public LogEach(String uuid){
        super(uuid);
        this.level = LogStatic.level.each;
    }
    private LogDetail logDetail;

    public LogDetail getLogDetail() {
        if(this.logDetail==null)
            createLogDetail();
        return logDetail;
    }
    public void setRemarks(String str){
        String remarks = this.getLogDetail().getRemarks();
        if(!remarks.equals(str)){
            this.update();
            this.logDetail.setRemarks(str);
        }
    }
    public void setPriority(LogStatic.priority priority){
        LogStatic.priority priority1 = this.getLogDetail().getPriority();
        if(!priority1.equals(priority)){
            this.update();
            this.logDetail.setPriority(priority);
        }
    }
    public void setClassify(LogStatic.classify classify){
        LogStatic.classify classify1 = this.getLogDetail().getClassify();
        if(!classify1.equals(classify)){
            this.update();
            this.logDetail.setClassify(classify);
        }
    }
    public void setMark_star(LogStatic.mark_star mark_star){
        LogStatic.mark_star mark_star1 = this.getLogDetail().getMark_star();
        if(!mark_star1.equals(mark_star)){
            this.update();
            this.logDetail.setMark_star(mark_star);
        }
    }
    public void createLogDetail() {
        this.logDetail = new LogDetail(this.uuid);
    }

//    public String getTag() {
//        if(tag==null||"".equals(tag)){
//            this.tag = LogStatic.Tag.Diary.name();
//        }
//        return tag;
//    }
//    //?入口？怎么修改Tag，还是在Classify中，支持修改LogEach的标签。
//    public void setTag(String tag) {
//        this.tag = tag;
//    }

//    @Override
//    public String getBelongTag() {
//        return this.logDetail.getClassify().name();
//    }
}
