package LogApp.Tool;

import LogApp.Model.LogBase;
import LogApp.Model.LogDetail;
import LogApp.Model.Tree.NoteTreeBase;

public class LogEncode {

//    /**
//     * TODO �����String��ֱ�ӽ��ܣ�����������࣬�ж���û�н����ֶΣ�������ʱ�͹涨Ϊ�������
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
        //TODO ���ı�����
        return domain;
    }
    public static String UnEncoding(String domain){
        //���ı�����

        return domain;
    }

    /**
     * //TODO �ؼ������ǣ�������ã�emmm����Ĭ����ǿ����Ϊ����
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
     * //TODO �ؼ������ǣ�������ã�emmm����Ĭ����ǿ����Ϊ����
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
     * //TODO �ؼ������ǣ�������ã�emmm����Ĭ����ǿ����Ϊ����
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
