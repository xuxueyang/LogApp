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
 * Created by ��ѩ�� on 2017/12/5.
 */
public class LogDay extends LogBase {
    private Map<String,LogBase> dataMap = new HashMap<String, LogBase>();
    public LogDay(String uuid){
        super(uuid);
        this.level = LogStatic.level.day;
        this.title = LogGenerator.pattern_date(LogGenerator.changeDetailPatternToDate(this.createDate));
    }
    // Ĭ���ǿյģ���ʾ��������־�������������ֶΣ���ʾÿ����־�Ĳ�ͬ���ͣ����缼����tel
    public String type = null;//���������ط�Ҳ��Ҫ��ǩ��ת�뵽LogBase��___���ڽṹ����ʱ��֧����___��LogBase�е�����һ���ǩ��
//    @Override
//    public String getFilePath(){
//        String path = getFolderPath() + "/" + uuid + ".txt";
//        return path;
//    }
}
