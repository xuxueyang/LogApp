////jacob���
////�ο����ϣ�
////jacob�Ĺٷ��ĵ���http://danadler.com/jacob/
////΢���javasdk�ĵ���http://f2.grp.yahoofs.com/v1/IIwuQ7rQyGCn-b-f4biL9n_xAwI_Oh3ieuWVDkLfPpaKBmRUjeUzAtCy5oljSMgzdV6OZMRRgrEwU1_RtIXS-h0h7O9W4RB7aUs48A/sdkdocs.zip.aa
////http://f1.grp.yahoofs.com/v1/IIwuQzpwMrin-b-fbkPTfyGj7CXbfLb0h3lkvGL1GKMwCNpXXRKiKDwe3cx9aUVjO3KWdb7cPJWmqAXUCY_As6XeyLDKoGLYd7ez6g/sdkdocs.zip.ab
////ע�⣺���ѹ�����Ǳ��ָ�ģ���console��ʹ��type sdkdocs.zip.aa sdkdocs.ab >sdkdocs.zip�����кϲ���
////
////����JACOB is a JAVA-COM Bridge that allows you to call COM Automation components from Java������jacob�ٷ���վ��ԭ�������˾Ͳ��ٶ�˵�ˡ�jacob��ʵ����Щ���װ��com���ܵ�jni���õļ��ϼ�����com�����������jacob����Dan Adlerʹ����c++��д��һ�������ʵ�ֶ�com������/����/���ã�Ȼ��ʹ��java��jni����������Щ����⣬ʵ��JAVA-COM Bridge��
////
////��������������η�װ�Ŀ��Բο�http://danadler.com/jacob/���䷽��������΢���javasdk�ĵ�һ�£�����Ȥ�����ѿ���lookһ�¡�
////jacob����java��΢���com���֮���������ͨ��ʹ��jacob�Դ���dll��̬���ӿ�ͨ��jni�ķ�ʽʵ������sun javaƽ̨�ϵĳ����com����!
////���ص�ַ��
////http://sourceforge.net/project/showfiles.php?group_id=109543&package_id=118368
////���õİ汾�ܶ�
////���ܣ�����ʵ��word��pdf��excel�Ĳ�������ת����
////����˵������
////��ѹ�ļ���jacob_1.11.1.zip ��copy jacob.jar�ļ����뵽classpath
////copy jacob.dll ����java jdk binĿ¼��
////copy template.doc��web-inf��
////ʵ��1�����滻�ı�����ȡword��ǩ��ֵ��application��UserName���ԣ�
//public class  ds{
///*
//public static void main(String[] args) {
//    //����word,����һ��ActivexComponent����
//    ActiveXComponent app = new ActiveXComponent("Word.Application");
//    //Ҫת����word�ļ�
//    String inFile = "D:\\project\\java_word\\java_word\\wordtemplet.doc";
//    //Ҫ�����Ŀ���ļ�
//    String tpFile ="D:\\project\\java_word\\java_word\\wordtemplet3.doc";
//    boolean flag = false;
//    Dispatch xlo = app.getObject();
//    String oldText="1234";
//    String newText="test";
//    boolean visible=false;
//    String bookMarkKey="LB_KJGG";
//    try {
//      //����word���ɼ�
//      app.setProperty("Visible", new Variant(visible));
//      //log.info("����word���ɼ��ɹ�!");
//      System.out.println("����word���ɼ��ɹ�!");
//      Dispatch docs = app.getProperty("Documents").toDispatch();
//      Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method
//                                   ,new Object[]
//                                   {inFile, new Variant(false), new Variant(true)}
//                                   , new int[1]).toDispatch(); //��word�ļ�
//      //��word2003��vba�ĵ���application��UserName���ԡ�
//      String userName=app.getPropertyAsString("UserName");
//      System.out.println("�û�����"+userName);
//      Dispatch selection=app.getProperty("Selection").toDispatch();
//      //�õ�һ�����
//      System.out.println("Selection");
//      Dispatch find = app.call(selection, "Find").toDispatch();
//       //����ʲô�ı�
//       Dispatch.put(find, "Text", oldText);
//       //�滻�ı�
//       Dispatch.call(find,"ClearFormatting");
//       Dispatch.put(find, "Text", oldText);
//       Dispatch.call(find, "Execute");
//       Dispatch.put(selection, "Text", newText);
//       // Dispatch.call(app, "SaveAs", inFile);
//       System.out.println("replace");
//
//       //��ָ����ֵ���õ�ָ���ı�ǩ��ȥ
//       Dispatch activeDocument=app.getProperty("ActiveDocument").toDispatch();
//       System.out.println("activedocument");
//       Dispatch bookMarks = app.call(activeDocument, "Bookmarks").toDispatch();
//       System.out.println("bookmarks");
//       boolean bookMarkExist1=Dispatch.call(bookMarks,"Exists",bookMarkKey).toBoolean();
//       if(bookMarkExist1==true){
//         System.out.println("exists bookmark!");
//         Dispatch rangeItem = Dispatch.call(bookMarks, "Item",bookMarkKey).
//                        toDispatch();
//                    System.out.println("range item!");
//                    Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
//                    System.out.println("range !");
//                    //ȡ��ǩ��ֵ
//                    String bookMarkValue=Dispatch.get(range,"Text").toString();
//                    bookMarkValue="test";
//                    if(bookMarkValue!=null){
//                      Dispatch.put(range, "Text",
//                             new Variant(bookMarkValue));
//                    }
//
//       }
//       else{
//         System.out.println("not exists bookmark!");
//       }
//      //�����ļ�
//      Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {tpFile, new Variant(0)}                      , new int[1]);
//      //��Ϊword��ʽ���浽Ŀ���ļ�
//      Variant f = new Variant(false);
//      Dispatch.call(doc, "Close", f);
//      flag = true;
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//    }
//    finally {
//      app.invoke("Quit", new Variant[] {});
//    }
////ʵ��2����.word->.html��
////��Ϊ΢��û�й���wordԴ���룬����ֱ����java������ȡword�ĺ���Ƕ�������ȫ�����롣���Ա���ͨ��jacob����м��� ����ȻҲ����poi����ȡ��
////     ��˵��poi��ȡ�ķ����ɡ���poi��ȡ�Ļ�����Ҫ����tm-extractors-0.4.jar�ٶ�һ�¿����ҵ����������£�
//         import org.textmining.text.extraction.WordExtractor;
//       try {
//             FileInputStream fileinputstream = new FileInputStream(
//                 filepath);
//           WordExtractor    extractor    =    new    WordExtractor();
//           temp =extractor.extractText(fileinputstream);
//             System.out.println(temp+"==temp");
//             fileinputstream.close();
//           } catch (Exception ex) {
//             System.out.println("FileNotFoundException error" +
//                         ex.getMessage());
//           }
////filepathΪword�ĵ�·��������һ��temp�ַ����������������Ĳ��������ˣ�����Ч���������⡣��Ϊ��word��ʽ�������ˡ�
////��˵��jacob. �ȵ��ٷ���վ��ȥ���أ�http://sourceforge.net/project/showfiles.php?group_id=109543&package_id=118368 jacob.zip.    ����֮���ѹ����jacob.jar�ŵ���Ŀ/web-inf/lib���档��jacob .dll�ŵ�c:/windos/system32/�Լ�java/jdk*.*/jre/bin���档������������������ˡ�˵���룺
//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//public boolean ChageFormat (String FolderPath,String FileName){
//     String FileFormat = "";
//     System.out.println(FolderPath);
//     FileFormat = FileName.substring(FileName.length()-4,FileName.length());
//     System.out.println(FileFormat);
//     if(FileFormat.equalsIgnoreCase(".doc"))
//     {
//         String DocFile = FolderPath +"\\"+ FileName;
//         System.out.println("word�ļ�·����"+DocFile);
//         //word�ļ�������·��
//         String HtmlFile = DocFile.substring(0, (DocFile.length() - 4)) + ".htm";
//         System.out.println("htm�ļ�·����"+HtmlFile);
//         //html�ļ�������·��
//         ActiveXComponent app = new ActiveXComponent("Word.Application");
//         //����word
//         try
//         {
//           app.setProperty("Visible", new Variant(false));
//           //����word����ǿ��ӻ�����
//           Dispatch docs = app.getProperty("Documents").toDispatch();
//           Dispatch doc = Dispatch.invoke(docs,"Open", Dispatch.Method, new Object[]{DocFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();
//           //��word�ļ�
//           Dispatch.invoke(doc,"SaveAs",Dispatch.Method, new Object[]{HtmlFile,new Variant(8)}, new int[1]);
//           //��Ϊhtm��ʽ�����ļ�
//           Dispatch.call(doc, "Close",new Variant(false));
//           //�ر��ļ�
//         }
//         catch (Exception e)
//         {
//           e.printStackTrace();
//         }
//         finally
//         {
//           app.invoke("Quit", new Variant[] {});
//           //�˳�word����
//         }
//         //ת�����
//         return true;
//     }
//     return false;
//   }
////FolderPathΪword���·����FileNameΪword���ơ�ͨ����������Ͱ�word�ļ�ת�ɵ�htm�ļ�����ʱ��Ϳ�����������ȡhtm�ļ��ˣ��������ļȲ������롣���Ҵ��и�ʽ��
////����Ҫǿ������jacob��������jdk,�Լ�windows�汾���й�ϵ�����԰汾һ��Ҫƥ�䡣����ᱨ���汾�������Ҫ����ȥ���ˡ�û�нݾ����ߡ�
////ʵ��2��(����word���ݵ��µ�word�ļ�)
//import java.io.File;
//import com.jacob.com.*;
//import com.jacob.activeX.*;
//
//public class FileExtracter {
//
//   public static void main(String[] args) {
//
//        ActiveXComponent app = new ActiveXComponent("Word.Application");
//        String inFile = "c:\\test.doc";
//        String tpFile = "c:\\temp.htm";
//        String otFile = "c:\\temp.xml";
//        boolean flag = false;
//        try {
//            app.setProperty("Visible", new Variant(false));
//            Object docs = app.getProperty("Documents").toDispatch();
//            Object doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { inFile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
//            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { tpFile, new Variant(8) }, new int[1]);
//            Variant f = new Variant(false);
//            Dispatch.call(doc, "Close", f);
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            app.invoke("Quit", new Variant[] {});
//        }
//
//   }
//}
//ʵ��3��(��̬���ɱ����д������ݡ�����ҳü��ҳ��)
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//public class WordExtractor{
//    // ����һ�������
//    private ActiveXComponent MsWordApp = null;
//    // ��������dispath.
//    private Dispatch document = null;
//    private Dispatch selection = null;
//    // ���캯��
//    public WordExtractor(){
//        super();
//    }
//    /** *//**
//     * ����word����
//     * @param makeVisible
//     * ��ʾ���ǲ���ʾ(true:��ʾ;false:����ʾ)
//     */
//    public void openWord(boolean makeVisible){
//        try{
//            // ��word(���wordδ����ʱ)
//            if (MsWordApp == null){
//                MsWordApp = new ActiveXComponent("Word.Application");
//            }
//            // ����word�ǿɼ��򲻿ɼ�(true:��ʾ;false:����ʾ)
//            Dispatch.put(MsWordApp, "Visible", new Variant(makeVisible));
//        } catch (RuntimeException e){
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//    /** *//**
//     * ����word���ı�����
//     *
//     */
//    public void createNewDocument(){
//        // ����һ��Dispatch����
//        Dispatch documents = Dispatch.get(MsWordApp, "Documents").toDispatch();
//        document = Dispatch.call(documents, "Add").toDispatch();
//    }
//    /** *//**
//     * ��ʽ��ʱ���ַ���
//     * @param date_str
//     *            ԭʼʱ���ַ���
//     * @return �޸ĺ���ַ���
//     */
//    public String ReplaceDateStr(String date_str){
//        String str = "";
//        String[] date_str_arr = Tool.splitString(date_str, "-");
//        // String[] date_str_arr =null;
//        if (date_str_arr.length > 0){
//            str = date_str_arr[0] + "/" + date_str_arr[1] + "/"+ date_str_arr[2];
//        } else{
//            str = date_str;
//        }
//        return str;
//    }
//    /** *//**
//     * д�����ϵ�word��
//     * @param title
//     *            ���ı���
//     * @param textToInsertarr
//     *            Ҫд����������鼯��
//     */
//    public void insertText(String title, ArrayList textToInsertarr){
//        selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
//        Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
//                .toDispatch(); // ���и�ʽ����Ҫ�Ķ���
//        Dispatch font = Dispatch.get(selection, "Font").toDispatch(); // ���͸�ʽ����Ҫ�Ķ���
//        Dispatch font1 = Dispatch.get(selection, "Font").toDispatch(); // ���͸�ʽ����Ҫ�Ķ���
//        // Dispatch image = Dispatch.get(selection,
//        // "InLineShapes").toDispatch(); //����ͼƬ��Ҫ�Ķ���
//        // String mm =
//        // "D:"+File.separator+"IRMAS_COMBINE"+File.separator+"images"+File.separator+"mis_login.jpg";
//        // //ͼƬ��Դ·��
//        // ��ʽ��ʱ��
//        java.util.Date ddate = new Date();
//        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM��-yy",
//                java.util.Locale.TRADITIONAL_CHINESE); // oracleҪ��ʱ���ʽ
//        Timestamp ts1 = new Timestamp(ddate.getTime());
//        String date_str = (ts1.toString()).substring(0, 10);
//        String result_str = ReplaceDateStr(date_str); // ��ʽ�����ʱ�� 2008/04/27
//        // �ļ�����
//        // Dispatch.call(selection, "TypeParagraph"); //��һ�ж���
//        Dispatch.put(alignment, "Alignment", "1"); // (1:���� 2:���� 3:����)
//        Dispatch.put(font1, "Bold", "1"); // ��������
//        Dispatch.put(font1, "Color", "1,0,0,0"); // ������ɫ(1,0,0,0=>��ɫ
//                                                    // 1,1,0,0=>��ɫ)
//        // Dispatch.put(font, "Italic", "1"); //����б��
//        Dispatch.call(selection, "TypeText", title); // д���������
//        // �������
//        Dispatch.call(selection, "TypeParagraph"); // ��һ�ж���
//        Dispatch.put(alignment, "Alignment", "3"); // (1:���� 2:���� 3:����)
//        Dispatch.put(selection, "Text", "        ");
//        Dispatch.call(selection, "MoveDown"); // �α�����һ��
//        // ����ͼƬ
//        /**//*
//         * Dispatch.call(selection, "TypeParagraph"); Dispatch.put(alignment,
//         * "Alignment", "2"); //(1:���� 2:���� 3:����) Dispatch.call(image,
//         * "AddPicture", mm); //д��ͼƬ
//         */
//        /**//*--------�����뵽���ʱ------------
//        //��Ҫ����(�����������е�ֵ)
//        for(int i=0;i<textToInsertarr.size();i++)
//        {
//             String arr_tostr = textToInsertarr.get(i).toString();
//             String arr_substr = arr_tostr.substring(1,arr_tostr.length()-1);    //ȥ��ǰ��'['��']'
//             String[] arr_split = arr_substr.split(",");                         //�ַ�������(�ڷָ�ÿ��Ԫ��ֵ)
//             for(int j=0;j<arr_split.length;j++)
//             {
//                    //��Ҫ����
//                        Dispatch.call(selection, "TypeParagraph");
//                    Dispatch.put(selection, "Text", arr_split[j]);               //д��word������
//                        Dispatch.put(font, "Bold", "0");                             //��������(1:���� 0:ȡ������)
//                    //Dispatch.put(font, "Italic", "1");                         //����б��(1:б�� 0:ȡ��б��)
//                    //Dispatch.put(font, "Underline", "1");                      //���ּӵ���(1:�ӵ��� 0:���ӵ���)
//                    Dispatch.call(selection, "MoveDown");                        //�������һ��(�Ų���ǹ���һ�����λ��)
//             }
//             //ÿд��һ�����Ͽ�һ��,������֮��
//                Dispatch.call(selection, "TypeParagraph");
//             Dispatch.put(selection, "Text", "                   ");
//             Dispatch.call(selection, "MoveDown");                               //�α�����һ��
//        }
//         */
//        // �������ڵ�����Ԫ���ܸ�������Ҫ���뵽��������
//        int all_count = 0;
//        for (int p = 0; p < textToInsertarr.size(); p++){
//            String arr_tostr = textToInsertarr.get(p).toString(); // �Ƚ�����Ԫ��ת���ַ���
//            String arr_substr = arr_tostr.substring(1, arr_tostr.length() - 1); // ȥ��ǰ��'['��']'
//            String[] arr_split = arr_substr.split(","); // �ַ�������(�ڷָ�ÿ��Ԫ��ֵ)
//            int num = arr_split.length;
//            all_count += num; // �ۼӸ���
//        }
//        // �������
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        Dispatch range = Dispatch.get(selection, "Range").toDispatch();
//        Dispatch newTable = Dispatch.call(tables, "Add", range,
//                new Variant(all_count), new Variant(1), new Variant(1))
//                .toDispatch(); // ��������,����,��������
//        Dispatch.call(selection, "MoveRight"); // ����Ƶ����ұ�
//        putTxtToCell(font, alignment, 1, 1, 1, textToInsertarr); // �����д������(�ӵ�1�е�1����ʼ)
//        // mergeCell(1,1,1,all_count,1); //���ϲ�(�ӵ�1�е�1����ʼ,��X�е�1������)
//        // autoFitTable(); //�Զ��������
//        int count = 0; // ����ϲ��������������(����:ǰ���кϲ���һ��,�����¼����ϲ�ʱ,Ҫ���Ǽ��Ϻϲ������һ��)
//        // ����������ϲ����
//        for (int k = 0; k < textToInsertarr.size(); k++){
//            String arr_tostr = textToInsertarr.get(k).toString(); // �Ƚ�����Ԫ��ת���ַ���
//            String arr_substr = arr_tostr.substring(1, arr_tostr.length() - 1); // ȥ��ǰ��'['��']'
//            String[] arr_split = arr_substr.split(","); // �ַ�������(�ڷָ�ÿ��Ԫ��ֵ)
//            int num = arr_split.length;
//            if (k == 0){ // ��һ�κϲ�ʱ,numֵ�����ǰһ��
//                mergeCell(1, k + 1, 1, num, 1);
//                count++;
//            } else{ // �ڶ��κϲ�֮��,numֵҪ��ǰһ��,�Դ�����...
//                mergeCell(1, k + 1, 1, num + count, 1);
//                count++;
//            }
//        }
//        // ȡ��ѡ��(��Ϊ���insert��ȥ�����ֻ���ʾ����,����Ҫȡ��)
//        Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
//        // ����ҳ��ҳβ
//        // ȡ�û�������
//        Dispatch ActiveWindow = MsWordApp.getProperty("ActiveWindow")
//                .toDispatch();
//        // ȡ�û�������
//        Dispatch ActivePane = Dispatch.get(ActiveWindow, "ActivePane")
//                .toDispatch();
//        // ȡ�ô��ڶ���
//        Dispatch View = Dispatch.get(ActivePane, "View").toDispatch();
//        // 9������ҳ��(�α����ڴ�)
//        Dispatch.put(View, "SeekView", "9"); // ҳ���е���Ϣ
//        Dispatch.put(alignment, "Alignment", "2"); // (1:���� 2:���� 3:����)
//        Dispatch.put(selection, "Text", result_str); // ��ʼ��ʱ��
//        // 10������ҳβ(�α����ڴ�)
//        Dispatch.put(View, "SeekView", "10"); // ҳβ�е���Ϣ
//        Dispatch.put(alignment, "Alignment", "1"); // (1:���� 2:���� 3:����)
//        Dispatch.put(selection, "Text", new Variant(1)); // ��ʼ����1��ʼ
//    }
//    /** *//**
//     * �ϲ����
//     *
//     * @param tableIndex
//     *            �����ʼ��
//     * @param fstCellRowIdx
//     *            ��ʼ��
//     * @param fstCellColIdx
//     *            ��ʼ��
//     * @param secCellRowIdx
//     *            ������
//     * @param secCellColIdx
//     *            ������
//     */
//    public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx,
//            int secCellRowIdx, int secCellColIdx){
//        // ���б��
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        // Ҫ���ı��
//        Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
//                .toDispatch();
//        Dispatch fstCell = Dispatch.call(table, "Cell",
//                new Variant(fstCellRowIdx), new Variant(fstCellColIdx))
//                .toDispatch();
//        Dispatch secCell = Dispatch.call(table, "Cell",
//                new Variant(secCellRowIdx), new Variant(secCellColIdx))
//                .toDispatch();
//        Dispatch.call(fstCell, "Merge", secCell);
//    }
//    /** *//**
//     * ��ָ���ı������������
//     *
//     * @param tableIndex
//     *            �����ʼ��
//     * @param cellRowIdx
//     *            �ڼ���
//     * @param cellColIdx
//     *            �ڼ���
//     * @param txt
//     *            �����ַ�������
//     */
//    public void putTxtToCell(Dispatch font, Dispatch alignment, int tableIndex,
//            int cellRowIdx, int cellColIdx, ArrayList txt){
//        // ���б��
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        // ��Ҫ����(�����������е�ֵ)
//        for (int i = 0; i < txt.size(); i++){
//            String arr_tostr = txt.get(i).toString(); // �Ƚ�����Ԫ��ת���ַ���
//            String arr_substr = arr_tostr.substring(1, arr_tostr.length() - 1); // ȥ��ǰ��'['��']'
//            String[] arr_split = arr_substr.split(","); // �ַ�������(�ڷָ�ÿ��Ԫ��ֵ)
//            for (int j = 0; j < arr_split.length; j++){
//                // Ҫ����ı��(�Ա����������������),cellRowIdx++����ӵ�һ�п�ʼ
//                Dispatch table = Dispatch.call(tables, "Item",
//                        new Variant(tableIndex)).toDispatch();
//                Dispatch cell = Dispatch.call(table, "Cell",
//                        new Variant(cellRowIdx++), new Variant(cellColIdx))
//                        .toDispatch();
//                // Dispatch.put(cell, "Height",new Variant(1)); //�����и�
//                Dispatch.call(cell, "Select");
//                // ��Ҫ����
//                // Dispatch.call(selection, "TypeParagraph"); //��һ�ж���
//                // Dispatch.put(alignment, "Alignment", "3"); //(1:���� 2:���� 3:����)
//                if (j == 0){
//                    Dispatch.put(selection, "Text", arr_split[j]); // д��word������
//                    Dispatch.put(font, "Bold", "1"); // ��������(1:���� 0:ȡ������)
//                    Dispatch.put(font, "Color", "1,1,1,1"); // ������ɫ
//                    // Dispatch.put(font, "Italic", "1"); //����б��(1:б�� 0:ȡ��б��)
//                    // Dispatch.put(font, "Underline", "1"); //���ּӵ���
//                    Dispatch.call(selection, "MoveDown"); // �������һ��(�Ų�������ǹ���һ����λ��)
//                } else{
//                    if (arr_split[j].indexOf(" ") != -1){
//                        String str = arr_split[j].replaceAll(" ", "         "); // ���ַ������ж�����
//                        Dispatch.call(selection, "TypeParagraph"); // ��һ�ж���
//                        Dispatch.put(alignment, "Alignment", "3"); // (1:����
//                                                                    // 2:����
//                                                                    // 3:����)
//                        Dispatch.put(selection, "Text", str); // д��word������
//                        Dispatch.put(font, "Bold", "0"); // ��������(1:���� 0:ȡ������)
//                        Dispatch.put(font, "Color", "1,1,1,0"); // ������ɫ
//                        // Dispatch.put(font, "Italic", "1"); //����б��(1:б��
//                        // 0:ȡ��б��)
//                        // Dispatch.put(font, "Underline", "1"); //���ּӵ���
//                        Dispatch.call(selection, "MoveDown"); // �������һ��(�Ų�������ǹ���һ����λ��)
//                    } else{
//                        Dispatch.put(selection, "Text", arr_split[j]); // д��word������
//                        Dispatch.put(font, "Bold", "0"); // ��������(1:���� 0:ȡ������)
//                        Dispatch.put(font, "Color", "1,1,1,0"); // ������ɫ
//                        // Dispatch.put(font, "Italic", "1"); //����б��(1:б��
//                        // 0:ȡ��б��)
//                        // Dispatch.put(font, "Underline", "1"); //���ּӵ���
//                        Dispatch.call(selection, "MoveDown"); // �������һ��(�Ų�������ǹ���һ����λ��)
//                    }
//                }
//            }
//            // ÿд��һ�����Ͽ�һ��,������֮��
//            /**//*
//             * Dispatch.call(selection, "TypeParagraph");
//             * Dispatch.put(selection, "Text", " "); Dispatch.call(selection,
//             * "MoveDown"); //�α�����һ��
//             */
//        }
//    }
//    /** *//**
//     * �Զ��������
//     */
//    public void autoFitTable(){
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        int count = Dispatch.get(tables, "Count").toInt(); // word�еı������
//        for (int i = 0; i < count; i++){
//            Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1))
//                    .toDispatch();
//            Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
//            Dispatch.call(cols, "AutoFit");
//        }
//    }
//    /** *//**
//     * ��浵��
//     * @param filename  Ҫ������ļ�����
//     */
//    public void saveFileAs(String filename){
//        Dispatch.call(document, "SaveAs", filename);
//    }
//    /** *//**
//     * ��ӡword�ļ�
//     */
//    public void printFile(){
//        Dispatch.call(document, "PrintOut");
//    }
//    /** *//**
//     * �ر��ı�����(���δ����word�༭ʱ,�ͷ�ActiveXִ����)
//     */
//    public void closeDocument(){
//        // 0 = û�д���ı�
//        // -1 = �д���ı�
//        // -2 = ��ʾ����ı�
//        Dispatch.call(document, "Close", new Variant(0));
//        document = null;
//    }
//    /** *//**
//     * �ر�word(���δ����word�༭ʱ,�ͷ�ActiveXִ����)
//     */
//    public void closeWord(){
//        Dispatch.call(MsWordApp, "Quit");
//        MsWordApp = null;
//        document = null;
//    }
//    @SuppressWarnings("unchecked")
//    public static void main(String[] args){
//        String title = "��������";
//        // ��ߵ��ַ������ݷ�������ݿ�����ȡ
//        ArrayList all_arr = new ArrayList();
//        ArrayList arr1 = new ArrayList();
//        ArrayList arr2 = new ArrayList();
//        ArrayList arr3 = new ArrayList();
//        ArrayList arr4 = new ArrayList();
//        ArrayList arr5 = new ArrayList();
//        arr1.add("һ��1111���߸�֮  ");
//        arr1.add("�ܹ�˾>���³��һظ����긲�������������������");
//        arr1.add("��죺 ������᰸������װ�����ݡ���1111");
//        arr2.add("����������Ʒ������ҵ1���߸�֮");
//        arr2.add("�ܹ�˾>���³��һظ����긲�������������������");
//        arr2.add("��죺 ������᰸�� tttt�긲�����������������");
//        arr3.add("����CI-101��������Ʒ������ҵ�������߸�֮");
//        arr3.add("�ܹ�˾>���³��һظ����긲�������������������");
//        arr3.add("��죺 ������᰸�� 31231");
//        arr4.add("�ġ��������߸�֮ ");
//        arr4.add("�ܹ�˾>���³��һظ����긲�������������������");
//        arr4.add("��죺 ������᰸�����");
//        arr5.add("�塢12312");
//        arr5.add("��죺 ������᰸��������������");
//        all_arr.add(arr1);
//        all_arr.add(arr2);
//        all_arr.add(arr3);
//        all_arr.add(arr4);
//        all_arr.add(arr5);
//        // String otFile =
//        // request.getRealPath("/")+"test"+File.separator+"sample"+File.separator+"TestDoc.doc";
//        // //Ŀ���ļ�λ��
//        String otFile = "D:/sampleTestDoc.doc";
//        WordExtractor word = new WordExtractor(); // ����һ��WordExtractor����
//        word.openWord(true); // �趨word������ʾ
//        word.createNewDocument(); // �����ļ�����
//        word.insertText(title, all_arr); // Ҫinsert��word�����ݼ���
//        word.saveFileAs(otFile); // �������
//        // word.printFile();
//    }
//}
//public class Tool{
//   public static String [] splitString(String str ,String split){
//       return str.split(split);
//   }
//}
//*/
//}