package LogApp.Tool;

import LogApp.Model.LogBase;
import LogApp.Model.LogDetail;
import LogApp.Model.Tree.NoteTreeBase;

public class LogEncode {

//    /**
//     * TODO 如果是String，直接解密，如果是其他类，判断有没有解密字段，或者暂时就规定为几个类吧
//     * @param domain
//     * @param <T>
//     * @return
//     */
//    public static <T>T Encoding(Class<T> domain){
//        return domain.cast(domain);
//    }
//    public static <T>T UnEncoding(Class<T> domain){
//        return domain.cast(domain);
//    }
    public static String Encoding(String domain){
        //TODO 对文本加密
        return domain;
    }
    public static String UnEncoding(String domain){
        //对文本解密

        return domain;
    }

    /**
     * //TODO 关键问题是，如果调用，emmm现在默认是强制设为加密
     * @param domain
     * @return
     */
    public static LogBase Encoding(LogBase domain){
        domain.isEncode = true;
        if(domain.isEncode){
            domain.setMessage(Encoding(domain.getMessage()));
        }
        return domain;
    }
    public static LogBase UnEncoding(LogBase domain){
        if(domain.isEncode){
            domain.setMessage(UnEncoding(domain.getMessage()));
        }
        return domain;
    }
    /**
     * //TODO 关键问题是，如果调用，emmm现在默认是强制设为加密
     * @param domain
     * @return
     */
    public static NoteTreeBase Encoding(NoteTreeBase domain){
        domain.isEncode = true;
        if(domain.isEncode){
            domain.setMessage(Encoding(domain.getMessage()));
        }
        return domain;
    }
    public static NoteTreeBase UnEncoding(NoteTreeBase domain){
        if(domain.isEncode){
            domain.setMessage(UnEncoding(domain.getMessage()));
        }
        return domain;
    }
    /**
     * //TODO 关键问题是，如果调用，emmm现在默认是强制设为加密
     * @param domain
     * @return
     */
    public static LogDetail Encoding(LogDetail domain){
        domain.isEncode = true;
        if(domain.isEncode){
            domain.setRemarks(Encoding(domain.getRemarks()));
        }
        return domain;
    }
    public static LogDetail UnEncoding(LogDetail domain){
        if(domain.isEncode){
            domain.setRemarks(UnEncoding(domain.getRemarks()));
        }
        return domain;
    }
}
