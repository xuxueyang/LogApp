package LogApp.Model;

import LogApp.LogStatic;

/**
 * Created by –Ï—©—Ù on 2017/12/5.
 */
public class LogEach  extends  LogBase{

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
}
