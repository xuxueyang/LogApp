package LogApp.Tool;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by 徐雪阳 on 2017/12/6.
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
    private Object[] objects;
    public String getUuid(){
        return this.uuid;
    }
    public  LogEvent( Object source, String resource ){
        this(source,resource,LogGenerator.getUUID());
    }
    public  LogEvent( Object source, String resource,String uuid ){
        this(source,resource,uuid,null);
    }
    public  LogEvent( Object source, String resource,String uuid,Object[] objects){
        this.source = source;
        this.resource = resource;
        this.uuid = uuid;
        this.timestamp = new Date().getTime();
        this.objects = objects;
    }
    @Override
    public String toString() {
        return "操作类型： " + getResource() + ";" +
                "操作时间： " + ZonedDateTime.now() + ";" +
                "操作ID: " + uuid + ";"
                ;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}
