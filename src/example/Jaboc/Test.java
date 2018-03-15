package example.Jaboc;
//  BufferedReader     bufferedReader = new BufferedReader(new InputStreamReader(new MultiMemberGZIPInputStream(new FileInputStream(gzFile)),


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/*
 * ע��wordתpdfҪ��װ�����ӡ������Ҫ����
 * ʹ��jacob��ܣ���dll�ļ��ŵ�jre/binĿ¼��
 */
public class Test {
    private ActiveXComponent wordCom = null;
    private  Object wordDoc = null;
    private  final Variant False = new Variant(false);
    private  final Variant True = new Variant(true);


    public static final int EXCEL_HTML = 44;
    public static final int WORD_HTML = 8;




    /**
     * ��word�ĵ�
     *
     *
     @param filePath word�ĵ�
     *


     @return ����word�ĵ�����
     */

    public  boolean openWord(String filePath) {
        //����ActiveX����


        wordCom = new ActiveXComponent("Word.Application");
        try {
//����wrdCom.Documents��Dispatch
            Dispatch wrdDocs = wordCom.getProperty("Documents").toDispatch();
//����wrdCom.Documents.Open������ָ����word�ĵ�������wordDoc
            wordDoc = Dispatch.invoke(wrdDocs, "Open", Dispatch.Method,
                    new Object[] { filePath }, new int[1]).toDispatch();
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * �ر�word�ĵ�
     */
    public  void closeWord(boolean saveOnExit) {
        if (wordCom!=null) {
//�ر�word�ļ�
//Dispatch.call(wordDoc, "Close", new Variant(saveOnExit));
            wordCom.invoke("Quit",new Variant[]{});
//wordCom.invoke("Quit",new Variant[0]);
            wordCom=null;
//�ͷ��ڳ����߳������õ�����com������Adobe PDFDistiller
            ComThread.Release();
        }
    }
    /**
     * ��word�ĵ���ӡΪPS�ļ���ʹ��Distiller��PS�ļ�ת��ΪPDF�ļ�
     *
     @param   sourceFilePath
      *          Դ�ļ�·��
     *
     @param   destinPSFilePath
      *          �������ɵ�PS�ļ�·��
     *
     @param   destinPDFFilePath
      *          ����PDF�ļ�·��
     */
    public  void docToPDF(String sourceFilePath, String destinPSFilePath,
                          String destinPDFFilePath) {
        if (!openWord(sourceFilePath)) {
            closeWord(true);
            return;
        }
//����Adobe Distiller��com����
        ActiveXComponent distiller = new ActiveXComponent("PDFDistiller.PDFDistiller.1");
        try {
//���õ�ǰʹ�õĴ�ӡ�����ҵ�Adobe Distiller��ӡ������Ϊ "Adobe PDF"
            wordCom.setProperty("ActivePrinter", new Variant("Adobe PDF"));
//����printout�Ĳ�������word�ĵ���ӡΪpostscript�ĵ�������ֻʹ����ǰ5������������Ҫʹ�ø���Ļ����Բο�MSDN��office�������api
//�Ƿ��ں�̨����
            Variant Background = False;
//�Ƿ�׷�Ӵ�ӡ
            Variant Append = False;
//��ӡ�����ĵ�
            int wdPrintAllDocument = 0;
            Variant Range = new Variant(wdPrintAllDocument);
//�����postscript�ļ���·��
            Variant OutputFileName = new Variant(destinPSFilePath);
            Dispatch.callN((Dispatch) wordDoc,
                    "PrintOut", new Variant[] {
                            Background, Append, Range, OutputFileName });
            System.out.println("��word�ĵ���ӡΪps�ĵ��ɹ���");
//����Distiller�����FileToPDF�������õĲ������������ݲο�Distiller Api�ֲ�
            //��Ϊ�����ps�ĵ�·��
            Variant inputPostScriptFilePath = new Variant(destinPSFilePath);
            //��Ϊ�����pdf�ĵ���·��
            Variant outputPDFFilePath = new Variant(destinPDFFilePath);
            //����FileToPDF����Ҫʹ��adobe pdf�����ļ���·����������û�и�ֵ��ʾ����ʹ��pdf�����ļ�
            Variant PDFOption = new Variant("");
            //����FileToPDF������ps�ĵ�ת��Ϊpdf�ĵ�
            Dispatch.callN(distiller, "FileToPDF", new Variant[] {inputPostScriptFilePath, outputPDFFilePath, PDFOption });
            System.out.println("��ps�ĵ�ת��Ϊpdf�ĵ��ɹ���");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            closeWord(true);
        }
    }




    public static void excelToHtml(String xlsfile, String htmlfile)
    {
        ActiveXComponent app = new ActiveXComponent("Excel.Application"); // ����word
        try
        {
            app.setProperty("Visible", new Variant(false));
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(
                    excels,
                    "Open",
                    Dispatch.Method,
                    new Object[] { xlsfile, new Variant(false),
                            new Variant(true) }, new int[1]).toDispatch();
            Dispatch.invoke(excel, "SaveAs", Dispatch.Method, new Object[] {
                    htmlfile, new Variant(EXCEL_HTML) }, new int[1]);
            Variant f = new Variant(false);
            Dispatch.call(excel, "Close", f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            app.invoke("Quit", new Variant[] {});
        }
    }




    public static void wordToHtml(String docfile, String htmlfile)
    {
        ActiveXComponent app = new ActiveXComponent("Word.Application"); // ����word
        try
        {
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.invoke(
                    docs,
                    "Open",
                    Dispatch.Method,
                    new Object[] { docfile, new Variant(false),
                            new Variant(true) }, new int[1]).toDispatch();
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
                    htmlfile, new Variant(WORD_HTML) }, new int[1]);
            Variant f = new Variant(false);
            Dispatch.call(doc, "Close", f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            app.invoke("Quit", new Variant[] {});
        }
    }




    public static void main(String[] argv) {
         Test d2p = new Test();

//d2p.docToPDF("D:\\Ա���������뵥��Ա����.doc", "D:\\Ա���������뵥��Ա����.ps", "D:\\Ա���������뵥.pdf");
        //excelToHtml("D:\\ͨѶ¼2011.02.21.xls","D:\\ͨѶ¼.html");
        // wordToHtml("F:\\����\\Ա���ǼǱ�.doc", "F:\\����\\Ա���ǼǱ�.html");
//boolean   success   =   (new   File("D:\\test.ps")).delete();
//if(success){
//    System.out.println("ɾ����ӡ���ļ��ɹ�");
//            }
    }
}