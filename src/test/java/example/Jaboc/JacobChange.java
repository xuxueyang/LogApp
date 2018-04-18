package example.Jaboc;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.util.HashMap;
import java.util.Iterator;

public class JacobChange {
    /**
     * ���ļ�
     * 
     * @param documents
     * @param inputDocPath
     * @return
     */
    private Dispatch open(Dispatch documents, String inputDocPath) {
        return Dispatch.call(documents, "Open", inputDocPath).toDispatch();
    }

    /**
     * ѡ������
     * 
     * @param word
     * @return
     */
    private Dispatch select(ActiveXComponent word) {
        return word.getProperty("Selection").toDispatch();
    }

    /**
     * �Ѳ�����ƶ����ļ���λ��
     * 
     * @param selection
     */
    private void moveStart(Dispatch selection) {
        Dispatch.call(selection, "HomeKey", new Variant(6));
    }

    /**
     * ��ѡ�����ݻ����㿪ʼ�����ı�
     * 
     * @param selection
     *            ѡ������
     * @param toFindText
     *            Ҫ���ҵ��ı�
     * @return true�����ҵ���ѡ�и��ı���false��δ���ҵ��ı���
     */
    private boolean find(Dispatch selection, String toFindText) {
        // ��selection����λ�ÿ�ʼ��ѯ
        Dispatch find = Dispatch.call(selection, "Find").toDispatch();
        // ����Ҫ���ҵ�����
        Dispatch.put(find, "Text", toFindText);
        // ��ǰ����
        Dispatch.put(find, "Forward", "True");
        // ���ø�ʽ
        Dispatch.put(find, "format", "True");
        // ��Сдƥ��
        Dispatch.put(find, "MatchCase", "True");
        // ȫ��ƥ��
        Dispatch.put(find, "MatchWholeWord", "True");
        // ���Ҳ�ѡ��
        return Dispatch.call(find, "Execute").getBoolean();
    }

    /**
     * ��ѡ�������滻Ϊ�趨�ı�
     * 
     * @param selection
     * @param newText
     */
    private void replace(Dispatch selection, String newText) {
        Dispatch.put(selection, "Text", newText);
    }

    /**
     * ȫ���滻
     * 
     * @param selection
     * @param oldText
     * @param replaceObj
     */
    private void replaceAll(Dispatch selection, String oldText, Object replaceObj) {
        moveStart(selection);
        String newText = (String) replaceObj;
        while (find(selection, oldText)) {
            replace(selection, newText);
            Dispatch.call(selection, "MoveRight");
        }
    }

    /**
     * ��ӡ
     * 
     * @param document
     */
    private void print(Dispatch document) {
        Dispatch.call(document, "PrintOut");
    }

    /**
     * �����ļ�
     * 
     * @param word
     * @param outputPath
     */
    private void save(ActiveXComponent word, String outputPath) {
        Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", outputPath);
    }

    /**
     * �ر��ļ�
     * 
     * @param doc
     */
    private void close(Dispatch doc) {
        Dispatch.call(doc, "Close", new Variant(true));
    }

    /**
     * �����ӡdoc�ĵ�
     * 
     * @param inputDocPath
     * @param outPutDocPath
     * @param data
     * @param isPrint
     */
    public void saveDoc(String inputDocPath, String outPutDocPath, HashMap data, boolean isPrint) {
        // ��ʼ��com���߳�
        ComThread.InitSTA();
        // word���г������
        ActiveXComponent word = new ActiveXComponent("Word.Application");
        // �ĵ�����
        Dispatch wordObject = (Dispatch) word.getObject();
        // �������� Variant(true)��ʾwordӦ�ó���ɼ�
        Dispatch.put((Dispatch) wordObject, "Visible", new Variant(false));
        // word�����ĵ�
        Dispatch documents = word.getProperty("Documents").toDispatch();
        // ���ĵ�
        Dispatch document = this.open(documents, inputDocPath);
        Dispatch selection = this.select(word);
        Iterator keys = data.keySet().iterator();
        String oldText;
        Object newValue;
        while (keys.hasNext()) {
            oldText = (String) keys.next();
            newValue = data.get(oldText);
            this.replaceAll(selection, oldText, newValue);
        }
        // �Ƿ��ӡ
        if (isPrint) {
            this.print(document);
        }
        this.save(word, outPutDocPath);
        this.close(document);
        word.invoke("Quit", new Variant[0]);
        // �ر�com���߳�
        ComThread.Release();
    }
}