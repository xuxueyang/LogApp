package example.Jaboc;
//  BufferedReader     bufferedReader = new BufferedReader(new InputStreamReader(new MultiMemberGZIPInputStream(new FileInputStream(gzFile)),


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/*
 * 注意word转pdf要安装虚拟打印机，且要配置
 * 使用jacob框架，把dll文件放到jre/bin目录下
 */
public class Test {
    private ActiveXComponent wordCom = null;
    private  Object wordDoc = null;
    private  final Variant False = new Variant(false);
    private  final Variant True = new Variant(true);


    public static final int EXCEL_HTML = 44;
    public static final int WORD_HTML = 8;




    /**
     * 打开word文档
     *
     *
     @param filePath word文档
     *


     @return 返回word文档对象
     */

    public  boolean openWord(String filePath) {
        //建立ActiveX部件


        wordCom = new ActiveXComponent("Word.Application");
        try {
//返回wrdCom.Documents的Dispatch
            Dispatch wrdDocs = wordCom.getProperty("Documents").toDispatch();
//调用wrdCom.Documents.Open方法打开指定的word文档，返回wordDoc
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
     * 关闭word文档
     */
    public  void closeWord(boolean saveOnExit) {
        if (wordCom!=null) {
//关闭word文件
//Dispatch.call(wordDoc, "Close", new Variant(saveOnExit));
            wordCom.invoke("Quit",new Variant[]{});
//wordCom.invoke("Quit",new Variant[0]);
            wordCom=null;
//释放在程序线程中引用的其它com，比如Adobe PDFDistiller
            ComThread.Release();
        }
    }
    /**
     * 将word文档打印为PS文件后，使用Distiller将PS文件转换为PDF文件
     *
     @param   sourceFilePath
      *          源文件路径
     *
     @param   destinPSFilePath
      *          首先生成的PS文件路径
     *
     @param   destinPDFFilePath
      *          生成PDF文件路径
     */
    public  void docToPDF(String sourceFilePath, String destinPSFilePath,
                          String destinPDFFilePath) {
        if (!openWord(sourceFilePath)) {
            closeWord(true);
            return;
        }
//建立Adobe Distiller的com对象
        ActiveXComponent distiller = new ActiveXComponent("PDFDistiller.PDFDistiller.1");
        try {
//设置当前使用的打印机，我的Adobe Distiller打印机名字为 "Adobe PDF"
            wordCom.setProperty("ActivePrinter", new Variant("Adobe PDF"));
//设置printout的参数，将word文档打印为postscript文档。现在只使用了前5个参数，假如要使用更多的话可以参考MSDN的office开发相关api
//是否在后台运行
            Variant Background = False;
//是否追加打印
            Variant Append = False;
//打印所有文档
            int wdPrintAllDocument = 0;
            Variant Range = new Variant(wdPrintAllDocument);
//输出的postscript文件的路径
            Variant OutputFileName = new Variant(destinPSFilePath);
            Dispatch.callN((Dispatch) wordDoc,
                    "PrintOut", new Variant[] {
                            Background, Append, Range, OutputFileName });
            System.out.println("由word文档打印为ps文档成功！");
//调用Distiller对象的FileToPDF方法所用的参数，具体内容参考Distiller Api手册
            //作为输入的ps文档路径
            Variant inputPostScriptFilePath = new Variant(destinPSFilePath);
            //作为输出的pdf文档的路径
            Variant outputPDFFilePath = new Variant(destinPDFFilePath);
            //定义FileToPDF方法要使用adobe pdf设置文件的路径，在这里没有赋值表示并不使用pdf配置文件
            Variant PDFOption = new Variant("");
            //调用FileToPDF方法将ps文档转换为pdf文档
            Dispatch.callN(distiller, "FileToPDF", new Variant[] {inputPostScriptFilePath, outputPDFFilePath, PDFOption });
            System.out.println("由ps文档转换为pdf文档成功！");
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
        ActiveXComponent app = new ActiveXComponent("Excel.Application"); // 启动word
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
        ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word
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

//d2p.docToPDF("D:\\员工假期申请单（员工）.doc", "D:\\员工假期申请单（员工）.ps", "D:\\员工假期申请单.pdf");
        //excelToHtml("D:\\通讯录2011.02.21.xls","D:\\通讯录.html");
        // wordToHtml("F:\\资料\\员工登记表.doc", "F:\\资料\\员工登记表.html");
//boolean   success   =   (new   File("D:\\test.ps")).delete();
//if(success){
//    System.out.println("删除打印机文件成功");
//            }
    }
}