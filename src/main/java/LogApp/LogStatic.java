package LogApp;

import LogApp.Tool.LogGenerator;

import java.awt.*;
import java.nio.charset.Charset;

/**
 * Created by ��ѩ�� on 2017/12/5.
 */

public class LogStatic {
    //TODO ��֮��Ϊ������ֶΰɡ�
    //TODO ���������ļ�����Ϊ��������Ϊ�ļ������ֱȽϺõ㣬���򣬸о�����ȫ���Ƶ�·���ṹ�����ʺ��Ժ���Ӷ���ĳ�����̵�bug��������д����־��ͺͱ�ǩ������
    //���Զ���Log_classify��classify��Type����ʵ���Ƿ��࣬������Щ�ط�����һ���޸İɡ��൱�ڴ����еı����ֶΣ��������޸ĵļ������������ǩ
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
     * չ�ֲ�ͬ���ܵ���壬�����ʼ��������Щ��塣����Ӧ����resource��һ�£�������޷�ʶ������ģ��
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
//        day_create, //��Ϊ���������ڼ��ص�ʱ�򴴽�LogDay�����Բ���ҪЭ��
//        day_save,
//        detail_show,
//        detail_hide,
        detail_visible,
        detail_del,
        detail_changeMessage,
        detail_changeClassify,
        detail_changePriority,
        detail_changeMarkStar,
        detail_move,//�������ʵʱ���޸Ĵ��ڵ�λ�ã�֪ͨ��ʾϸ�ڵ�����ܹ����� UUIDΪ�գ�������¼
        //������Ĺ���
        mainFun_showAllOver,
        mainFun_showAllNoOver,
        mainFun_noteTree,
        menu_add,
//        menu_can_add,
        menu_chooseCalender,
        global_data_save,//UUIDΪ�գ�������¼
        global_data_init,
        global_exit,
        Return,
    }
    //Log��¼��ʱ
    public static long Operator_Delay = 2000;
    //ȫ�֣�����
    public static String GLOBAL_DATE ;//= LogGenerator.getNowDate();
    {
        GLOBAL_DATE  = LogGenerator.getNowDate();
    }
    //����·��
//    public static String REAL_PATH = ClassLoader.getSystemResource("").toString();
    public static String REAL_PATH = "D:/xxy/Log_Diary";//·��
    public static String REAL_PATH_NOTETREE = REAL_PATH + "/"+Tag.NoteTree.name();//����˼ά��
    public static String REAL_PATH_DIARY = REAL_PATH + "/"+Tag.Diary.name();//������־
    public static String REAL_PATH_LOG = REAL_PATH + "/" + Tag.SystemLog.name();//ϵͳ��־
    public static String REAL_PATH_OPERATOR = REAL_PATH + "/" + Tag.OperatorLog.name();//��������
//    public static String ENCODE = Charset.defaultCharset().displayName();
    public static String ENCODE = "GBK";
    {

    }
    //�Ƿ������ݿ⣬���Ǵ��ļ���ȡ
    public static boolean isUseLocalDataBase = false;
    //LogEvent�����С
    public static int NLogEventCacheSize = 10;
    //LogDate
    public static String Date_Pattern = "yyyy-MM-dd";
    public static String Date_Detail_Pattern = "";
    public static String Date_Path_Pattern = "yyyy/MM/dd";
    //JMaxMainJFrame
    public static int maxDefaultWidth = 950;
    public static int maxDefaultHeight = 850;

    //JFrame
//    public static int maxTask = 6; //�����ʾ6����¼
    public static int defaultWidth = 680;
    public static int defaultHeight = 830;
    public static String ChangeStateString = ".*";
//    ������ʾ
//    public static int x =  (int)Math.round((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-defaultWidth)/2);
//    public static int y =  (int)Math.round((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-defaultHeight)/2);
//    ������ʾ
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
    //ShowDetail����С
    public static int detailFrameBubbleWidth = 50;
    public static int detailFrameWidth = 300;
    public static int detailFrameHeight = 500;
    //Timer
    public static long delay = 1000;
    public static long period = 4000;
    //�ַ�����
    public static String FontName="����";
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
