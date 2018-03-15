package LogApp.Tool;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by ��ѩ�� on 2017/12/6.
 */
public class LogEvent {
    private   Object source;
    public Object getSource(){
        return this.source;
    }
    private String resource;
    public String getResource(){
        return this.resource;
    }
    private String uuid;
    private long timestamp;
    public String getUuid(){
        return this.uuid;
    }
    public  LogEvent( Object source, String resource ){
        this.source = source;
        this.resource = resource;
        this.uuid = LogGenerator.getUUID();
    }
    public  LogEvent( Object source, String resource,String uuid ){
        this.source = source;
        this.resource = resource;
        this.uuid = uuid;
        this.timestamp = new Date().getTime();
    }
    @Override
    public String toString() {
        return "�������ͣ� " + getResource() + ";" +
                "����ʱ�䣺 " + ZonedDateTime.now() + ";" +
                "����ID: " + uuid + ";"
                ;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
