package LogApp.Model;

import java.time.ZonedDateTime;

/**
 * Created by 徐雪阳 on 2017/12/5.
 * 对于year没有整体记录，但是阔以有整体的日志备忘录，对于一些重要日子是可以标记的。
 */
public class LogYear  extends  LogBase{
    public LogYear(String uuid){
        super(uuid);
    }
}
