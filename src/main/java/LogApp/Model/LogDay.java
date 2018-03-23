package LogApp.Model;

import LogApp.LogStatic;
import LogApp.Tool.LogGenerator;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 徐雪阳 on 2017/12/5.
 */
public class LogDay extends LogBase {
    private Map<String,LogBase> dataMap = new HashMap<String, LogBase>();
    public LogDay(String uuid){
        super(uuid);
        this.level = LogStatic.level.day;
        this.title = LogGenerator.pattern_date(LogGenerator.changeDetailPatternToDate(this.createDate));
    }
    // 默认是空的，表示单纯的日志，否则根据这个字段，表示每日日志的不同类型：比如技术的tel
    public String type = null;//鉴于其他地方也需要标签，转入到LogBase中___现在结构上暂时不支持诶___在LogBase中单独加一层标签吧
//    @Override
//    public String getFilePath(){
//        String path = getFolderPath() + "/" + uuid + ".txt";
//        return path;
//    }
}
