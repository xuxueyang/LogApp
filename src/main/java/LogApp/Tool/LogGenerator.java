package LogApp.Tool;

import LogApp.LogStatic;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class LogGenerator {
    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID() {
            return UUID.randomUUID().toString().replace("-", "");
        }
    public static String getNowDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(LogStatic.Date_Pattern);
        String nowDate = formatter.format(date);
        return nowDate;
    }
    public static String pattern_date(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(LogStatic.Date_Pattern);
        String nowDate = formatter.format(date);
        return nowDate;
    }
    public static String getNowDatePath(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(LogStatic.Date_Path_Pattern);
        String nowDate = formatter.format(date);
        return nowDate;
    }
    private static  Gson gson = new Gson();
    public static String serialize(Object object){
        return gson.toJson(object);
    }
    public static <T> T  unserialize(String json,Class<T> classOfT){
        try {
            return gson.fromJson(json,classOfT);
        }catch (JsonSyntaxException e){
            return null;
        }
    }

    public static Color[] getColorsPanelByStringList(LogStatic.mark_star[] strs){
        ArrayList<Color> jPanelArrayList  = new ArrayList<Color>();
        for(LogStatic.mark_star str:strs){
            Color color = getColorByString(str.name());
//            JPanel jPanel = new JPanel();
//            jPanel.setBackground(color);
//            jPanelArrayList.add(jPanel);
            jPanelArrayList.add(color);
        }
//        return jPanelArrayList.toArray(new JPanel[jPanelArrayList.size()]);
        return jPanelArrayList.toArray(new Color[jPanelArrayList.size()]);
    }
    public static Color getColorByString(String  str){
        switch (str.toLowerCase()){
            case "blue":
                return Color.BLUE;
            case "red":
                return Color.red;
            case "green":
                return Color.green;
            case "cyan":
                return Color.cyan;
            case "darkgray":
                return Color.darkGray;
            default:
                return Color.darkGray;
        }
    }
}
