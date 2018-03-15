package LogApp.Model;

import LogApp.LogStatic;
import LogApp.Tool.Log;
import LogApp.Tool.LogGenerator;

public class LogDetail {
    private String remarks;
    private String parentUUID;
    //标记
    private LogStatic.mark_star mark_star;
    //优先级
    private LogStatic.priority priority;
    //分类
    private LogStatic.classify classify;
    private String uuid;
    public LogDetail(String parentUUID){
        this.parentUUID = parentUUID;
        remarks = "";
        this.uuid = LogGenerator.getUUID();
        this.mark_star = LogStatic.mark_star.DarkGray;
        this.priority = LogStatic.priority.Normal;
        this.classify = LogStatic.classify.Default;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getParentUUID() {
        return parentUUID;
    }

    public LogStatic.mark_star getMark_star() {
        if(mark_star==null)
            mark_star = LogStatic.mark_star.DarkGray;
        return mark_star;
    }

    public void setMark_star(LogStatic.mark_star mark_star) {
        if(mark_star==null)
            mark_star = LogStatic.mark_star.DarkGray;
        this.mark_star = mark_star;
    }

    public LogStatic.priority getPriority() {
        if(priority==null)
            priority = LogStatic.priority.Normal;
        return priority;
    }

    public void setPriority(LogStatic.priority priority) {
        if(priority==null)
            priority = LogStatic.priority.Normal;
        this.priority = priority;
    }

    public LogStatic.classify getClassify() {
        if(classify==null)
            classify = LogStatic.classify.Default;
        return classify;
    }

    public void setClassify(LogStatic.classify classify) {
        if(classify==null)
            classify = LogStatic.classify.Default;
        this.classify = classify;
    }
}
