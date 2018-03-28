package LogApp;

import LogApp.Tool.LogGenerator;

import java.awt.*;
import java.nio.charset.Charset;

/**
 * Created by 徐雪阳 on 2017/12/5.
 */

public class LogStatic {
    //TODO 将之作为分类的字段吧。
    //TODO 设置配置文件。因为分类来作为文件夹名字比较好点，否则，感觉这种全日制的路径结构，不适合以后添加对于某个工程的bug诶。还是写在日志里就和便签混淆。
    //所以对于Log_classify、classify、Type，其实都是分类，所以这些地方都做一下修改吧。相当于代码中的保留字段，不允许修改的几个基本分类标签
    public enum Tag{
        Diary,
        SystemLog,
        OperatorLog,
        NoteTree,
    }

//    public enum  Log_classify{
//        SystemLog,
//        OperatorLog
//    }
    public enum mark_star{
        DarkGray,
        Blue,
        Red,
        Green,
        Cyan
    }
    public  enum classify {
        Default,
        Work,
        Game,
        Study,
        Think,
        Bug,
        TODO,
        Read
    }
    public enum priority {
        Default,
        Normal,
        High,
        Low,
        Significance
    }
    public  enum level{
        ERROR,
        base,
        each,
        day,
//        noteTree,
        month,
        year,
        week
    }
    public enum Type{
        Tel,
        Day
    }
    public enum FileType{
        txt,
        dat,
    }
    /**
     * 展现不同功能的面板，反射初始化加入这些面板。命名应该与resource中一致！否则会无法识别出这个模块
     */
    public enum FunctionPanel{
        showAllNoOver,
        showAllOver,
        noteTree,
    }
    public enum  resource_prefix{
        each,
        tel,
        day,
        detail,
        mainFun,
        menu,
        gloabal,
        file,
        Return
    }
    public enum  resource_suffix{
        _remark_visible,
        _changeMessage,
    }
    public  enum resource{
        file_file_upload,
        file_file_down,
        each_changeOverState,
        each_changeMessage,
        each_del,
        each_load,
        tel_remark_visible,
        tel_load,
        tel_changeMessage,
        day_remark_visible,
        day_load,
        day_changeMessage,
//        day_create, //因为从来都是在加载的时候创建LogDay，所以不需要协议
//        day_save,
//        detail_show,
//        detail_hide,
        detail_visible,
        detail_del,
        detail_changeMessage,
        detail_changeClassify,
        detail_changePriority,
        detail_changeMarkStar,
        detail_move,//这个用于实时的修改窗口的位置，通知显示细节的面板能够跟随 UUID为空，不做记录
        //主界面的功能
        mainFun_showAllOver,
        mainFun_showAllNoOver,
        mainFun_noteTree,
        menu_add,
//        menu_can_add,
        menu_chooseCalender,
        global_data_save,//UUID为空，不做记录
        global_data_init,
        global_exit,
        Return,
    }
    //Log记录延时
    public static long Operator_Delay = 2000;
    //全局，日期
    public static String GLOBAL_DATE ;//= LogGenerator.getNowDate();
    {
        GLOBAL_DATE  = LogGenerator.getNowDate();
    }
    //绝对路径
//    public static String REAL_PATH = ClassLoader.getSystemResource("").toString();
    public static String REAL_PATH = "D:/xxy/Log_Diary";//路径
    public static String REAL_PATH_NOTETREE = REAL_PATH + "/"+Tag.NoteTree.name();//树形思维树
    public static String REAL_PATH_DIARY = REAL_PATH + "/"+Tag.Diary.name();//个人日志
    public static String REAL_PATH_LOG = REAL_PATH + "/" + Tag.SystemLog.name();//系统日志
    public static String REAL_PATH_OPERATOR = REAL_PATH + "/" + Tag.OperatorLog.name();//操作数据
//    public static String ENCODE = Charset.defaultCharset().displayName();
    public static String ENCODE = "GBK";
    {

    }
    //是否用数据库，还是从文件读取
    public static boolean isUseLocalDataBase = false;
    //LogEvent缓存大小
    public static int NLogEventCacheSize = 10;
    //LogDate
    public static String Date_Pattern = "yyyy-MM-dd";
    public static String Date_Detail_Pattern = "";
    public static String Date_Path_Pattern = "yyyy/MM/dd";
    //JMaxMainJFrame
    public static int maxDefaultWidth = 950;
    public static int maxDefaultHeight = 850;

    //JFrame
//    public static int maxTask = 6; //最多显示6条记录
    public static int defaultWidth = 680;
    public static int defaultHeight = 830;
    public static String ChangeStateString = ".*";
//    居中显示
//    public static int x =  (int)Math.round((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-defaultWidth)/2);
//    public static int y =  (int)Math.round((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-defaultHeight)/2);
//    右上显示
    public static int x =  (int)Math.round((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-defaultWidth-200));
    public static int y =  100;

    //LogMenu
    public static  int menuWidth = defaultWidth;
    public static  int menuHeight = 200;
    //JEachPanel
    public static int eachPanelCheckBoxWidth = 80;
    public static int eachPanelCheckBoxHeight = 80;
    public static int eachPanel_detailButtonWidth = 100;
    public static int eachPanel_detailButtonHeight = 80;
    public static int eachPanel_delButtonWidth = 100;
    public static int eachPanel_delButtonHeight = 80;
    //LogTextArea
    public static int LogTextArea_maxRow = 1;
//    public static int LogTextArea_cacheNum = 10;
    //JButton
    public static  int button_height = 100;
    //ShowDetail面板大小
    public static int detailFrameBubbleWidth = 50;
    public static int detailFrameWidth = 300;
    public static int detailFrameHeight = 500;
    //Timer
    public static long delay = 1000;
    public static long period = 4000;
    //字符名字
    public static String FontName="楷体";
    public static int FontSize=14;
    public static String LineDivision = "+";
//    //ENCODE
//    public static String encode = Charset.defaultCharset().displayName();
//    public static byte[] lineSep;
//    {
//        try {
//            lineSep = System.getProperty("line.separator").getBytes(encode);
//        }catch (Exception e){
//            lineSep = "\n".getBytes();
//        }
//    }
}
