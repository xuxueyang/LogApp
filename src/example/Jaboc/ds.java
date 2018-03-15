////jacob简介
////参考资料：
////jacob的官方文档：http://danadler.com/jacob/
////微软的javasdk文档：http://f2.grp.yahoofs.com/v1/IIwuQ7rQyGCn-b-f4biL9n_xAwI_Oh3ieuWVDkLfPpaKBmRUjeUzAtCy5oljSMgzdV6OZMRRgrEwU1_RtIXS-h0h7O9W4RB7aUs48A/sdkdocs.zip.aa
////http://f1.grp.yahoofs.com/v1/IIwuQzpwMrin-b-fbkPTfyGj7CXbfLb0h3lkvGL1GKMwCNpXXRKiKDwe3cx9aUVjO3KWdb7cPJWmqAXUCY_As6XeyLDKoGLYd7ez6g/sdkdocs.zip.ab
////注意：这个压缩包是被分割的，在console中使用type sdkdocs.zip.aa sdkdocs.ab >sdkdocs.zip，进行合并。
////
////　　JACOB is a JAVA-COM Bridge that allows you to call COM Automation components from Java。这是jacob官方网站的原话，本人就不再多说了。jacob的实现有些像封装了com功能的jni调用的集合及承载com对象的容器。jacob作者Dan Adler使用了c++编写了一批程序库实现对com的引用/承载/调用，然后使用java的jni技术调用这些程序库，实现JAVA-COM Bridge。
////
////　　关于作者如何封装的可以参考http://danadler.com/jacob/，其方法和类与微软的javasdk文档一致，有兴趣的朋友可以look一下。
////jacob是在java与微软的com组件之间的桥梁，通过使用jacob自带的dll动态链接库通过jni的方式实现了在sun java平台上的程序对com调用!
////下载地址：
////http://sourceforge.net/project/showfiles.php?group_id=109543&package_id=118368
////可用的版本很多
////功能：可以实现word与pdf和excel的操作，与转换！
////配置说明：：
////解压文件：jacob_1.11.1.zip ，copy jacob.jar文件加入到classpath
////copy jacob.dll 放在java jdk bin目录下
////copy template.doc到web-inf下
////实例1：（替换文本、读取word标签的值和application的UserName属性）
//public class  ds{
///*
//public static void main(String[] args) {
//    //启动word,生成一个ActivexComponent对象
//    ActiveXComponent app = new ActiveXComponent("Word.Application");
//    //要转换的word文件
//    String inFile = "D:\\project\\java_word\\java_word\\wordtemplet.doc";
//    //要报存的目标文件
//    String tpFile ="D:\\project\\java_word\\java_word\\wordtemplet3.doc";
//    boolean flag = false;
//    Dispatch xlo = app.getObject();
//    String oldText="1234";
//    String newText="test";
//    boolean visible=false;
//    String bookMarkKey="LB_KJGG";
//    try {
//      //设置word不可见
//      app.setProperty("Visible", new Variant(visible));
//      //log.info("设置word不可见成功!");
//      System.out.println("设置word不可见成功!");
//      Dispatch docs = app.getProperty("Documents").toDispatch();
//      Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method
//                                   ,new Object[]
//                                   {inFile, new Variant(false), new Variant(true)}
//                                   , new int[1]).toDispatch(); //打开word文件
//      //在word2003的vba文档中application有UserName属性。
//      String userName=app.getPropertyAsString("UserName");
//      System.out.println("用户名："+userName);
//      Dispatch selection=app.getProperty("Selection").toDispatch();
//      //得到一个组件
//      System.out.println("Selection");
//      Dispatch find = app.call(selection, "Find").toDispatch();
//       //查找什么文本
//       Dispatch.put(find, "Text", oldText);
//       //替换文本
//       Dispatch.call(find,"ClearFormatting");
//       Dispatch.put(find, "Text", oldText);
//       Dispatch.call(find, "Execute");
//       Dispatch.put(selection, "Text", newText);
//       // Dispatch.call(app, "SaveAs", inFile);
//       System.out.println("replace");
//
//       //把指定的值设置到指定的标签中去
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
//                    //取标签的值
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
//      //保存文件
//      Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {tpFile, new Variant(0)}                      , new int[1]);
//      //作为word格式保存到目标文件
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
////实例2：（.word->.html）
////因为微软没有公开word源代码，所以直接用java流来读取word的后果是读出来的全是乱码。所以必须通过jacob这个中间桥 。当然也可用poi来读取。
////     先说用poi读取的方法吧。用poi读取的话，先要下载tm-extractors-0.4.jar百度一下可以找到。代码如下：
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
////filepath为word文档路径，返回一个temp字符串。这样读出来的不是乱码了，但是效果并不如意。因为把word格式给丢掉了。
////再说用jacob. 先到官方网站上去下载：http://sourceforge.net/project/showfiles.php?group_id=109543&package_id=118368 jacob.zip.    下载之后解压，把jacob.jar放到项目/web-inf/lib下面。把jacob .dll放到c:/windos/system32/以及java/jdk*.*/jre/bin下面。这样就算是配置完成了。说代码：
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
//         System.out.println("word文件路径："+DocFile);
//         //word文件的完整路径
//         String HtmlFile = DocFile.substring(0, (DocFile.length() - 4)) + ".htm";
//         System.out.println("htm文件路径："+HtmlFile);
//         //html文件的完整路径
//         ActiveXComponent app = new ActiveXComponent("Word.Application");
//         //启动word
//         try
//         {
//           app.setProperty("Visible", new Variant(false));
//           //设置word程序非可视化运行
//           Dispatch docs = app.getProperty("Documents").toDispatch();
//           Dispatch doc = Dispatch.invoke(docs,"Open", Dispatch.Method, new Object[]{DocFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();
//           //打开word文件
//           Dispatch.invoke(doc,"SaveAs",Dispatch.Method, new Object[]{HtmlFile,new Variant(8)}, new int[1]);
//           //作为htm格式保存文件
//           Dispatch.call(doc, "Close",new Variant(false));
//           //关闭文件
//         }
//         catch (Exception e)
//         {
//           e.printStackTrace();
//         }
//         finally
//         {
//           app.invoke("Quit", new Variant[] {});
//           //退出word程序
//         }
//         //转化完毕
//         return true;
//     }
//     return false;
//   }
////FolderPath为word存放路径。FileName为word名称。通过这个方法就把word文件转成的htm文件。这时候就可以用流来读取htm文件了，读出来的既不是乱码。并且带有格式。
////另外要强调的是jacob这个组件和jdk,以及windows版本都有关系。所以版本一定要匹配。否则会报错。版本的问题就要挨个去试了。没有捷径可走。
////实例2：(拷贝word内容到新的word文件)
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
//实例3：(动态生成表格及填写表格内容、设置页眉和页脚)
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//public class WordExtractor{
//    // 创建一个组件。
//    private ActiveXComponent MsWordApp = null;
//    // 建立两个dispath.
//    private Dispatch document = null;
//    private Dispatch selection = null;
//    // 构造函数
//    public WordExtractor(){
//        super();
//    }
//    /** *//**
//     * 开启word档案
//     * @param makeVisible
//     * 显示或是不显示(true:显示;false:不显示)
//     */
//    public void openWord(boolean makeVisible){
//        try{
//            // 打开word(如果word未开启时)
//            if (MsWordApp == null){
//                MsWordApp = new ActiveXComponent("Word.Application");
//            }
//            // 设置word是可见或不可见(true:显示;false:不显示)
//            Dispatch.put(MsWordApp, "Visible", new Variant(makeVisible));
//        } catch (RuntimeException e){
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//    /** *//**
//     * 建立word的文本内容
//     *
//     */
//    public void createNewDocument(){
//        // 建立一个Dispatch对象
//        Dispatch documents = Dispatch.get(MsWordApp, "Documents").toDispatch();
//        document = Dispatch.call(documents, "Add").toDispatch();
//    }
//    /** *//**
//     * 格式化时间字符串
//     * @param date_str
//     *            原始时间字符串
//     * @return 修改后的字符串
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
//     * 写入资料到word中
//     * @param title
//     *            本文标题
//     * @param textToInsertarr
//     *            要写入的内容数组集合
//     */
//    public void insertText(String title, ArrayList textToInsertarr){
//        selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // 输入内容需要的对象
//        Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
//                .toDispatch(); // 行列格式化需要的对象
//        Dispatch font = Dispatch.get(selection, "Font").toDispatch(); // 字型格式化需要的对象
//        Dispatch font1 = Dispatch.get(selection, "Font").toDispatch(); // 字型格式化需要的对象
//        // Dispatch image = Dispatch.get(selection,
//        // "InLineShapes").toDispatch(); //放入图片需要的对象
//        // String mm =
//        // "D:"+File.separator+"IRMAS_COMBINE"+File.separator+"images"+File.separator+"mis_login.jpg";
//        // //图片来源路径
//        // 格式化时间
//        java.util.Date ddate = new Date();
//        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM月-yy",
//                java.util.Locale.TRADITIONAL_CHINESE); // oracle要的时间格式
//        Timestamp ts1 = new Timestamp(ddate.getTime());
//        String date_str = (ts1.toString()).substring(0, 10);
//        String result_str = ReplaceDateStr(date_str); // 格式化后的时间 2008/04/27
//        // 文件标题
//        // Dispatch.call(selection, "TypeParagraph"); //空一行段落
//        Dispatch.put(alignment, "Alignment", "1"); // (1:置中 2:靠右 3:靠左)
//        Dispatch.put(font1, "Bold", "1"); // 字型租体
//        Dispatch.put(font1, "Color", "1,0,0,0"); // 字型颜色(1,0,0,0=>红色
//                                                    // 1,1,0,0=>棕色)
//        // Dispatch.put(font, "Italic", "1"); //字型斜体
//        Dispatch.call(selection, "TypeText", title); // 写入标题内容
//        // 标题格行
//        Dispatch.call(selection, "TypeParagraph"); // 空一行段落
//        Dispatch.put(alignment, "Alignment", "3"); // (1:置中 2:靠右 3:靠左)
//        Dispatch.put(selection, "Text", "        ");
//        Dispatch.call(selection, "MoveDown"); // 游标往下一行
//        // 插入图片
//        /**//*
//         * Dispatch.call(selection, "TypeParagraph"); Dispatch.put(alignment,
//         * "Alignment", "2"); //(1:置中 2:靠右 3:靠左) Dispatch.call(image,
//         * "AddPicture", mm); //写入图片
//         */
//        /**//*--------不输入到表格时------------
//        //主要内容(即参数数组中的值)
//        for(int i=0;i<textToInsertarr.size();i++)
//        {
//             String arr_tostr = textToInsertarr.get(i).toString();
//             String arr_substr = arr_tostr.substring(1,arr_tostr.length()-1);    //去掉前后'['和']'
//             String[] arr_split = arr_substr.split(",");                         //字符串数组(在分隔每个元素值)
//             for(int j=0;j<arr_split.length;j++)
//             {
//                    //主要内容
//                        Dispatch.call(selection, "TypeParagraph");
//                    Dispatch.put(selection, "Text", arr_split[j]);               //写入word的内容
//                        Dispatch.put(font, "Bold", "0");                             //字型租体(1:租体 0:取消租体)
//                    //Dispatch.put(font, "Italic", "1");                         //字型斜体(1:斜体 0:取消斜体)
//                    //Dispatch.put(font, "Underline", "1");                      //文字加底线(1:加底线 0:不加底线)
//                    Dispatch.call(selection, "MoveDown");                        //光标往下一行(才不会盖过上一输入的位置)
//             }
//             //每写入一次资料空一行,以区隔之用
//                Dispatch.call(selection, "TypeParagraph");
//             Dispatch.put(selection, "Text", "                   ");
//             Dispatch.call(selection, "MoveDown");                               //游标往下一行
//        }
//         */
//        // 依参数内的数组元素总个数计算要输入到表格的列数
//        int all_count = 0;
//        for (int p = 0; p < textToInsertarr.size(); p++){
//            String arr_tostr = textToInsertarr.get(p).toString(); // 先将数组元素转成字符串
//            String arr_substr = arr_tostr.substring(1, arr_tostr.length() - 1); // 去掉前后'['和']'
//            String[] arr_split = arr_substr.split(","); // 字符串数组(在分隔每个元素值)
//            int num = arr_split.length;
//            all_count += num; // 累加个数
//        }
//        // 建立表格
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        Dispatch range = Dispatch.get(selection, "Range").toDispatch();
//        Dispatch newTable = Dispatch.call(tables, "Add", range,
//                new Variant(all_count), new Variant(1), new Variant(1))
//                .toDispatch(); // 设置列数,栏数,表格外框宽度
//        Dispatch.call(selection, "MoveRight"); // 光标移到最右边
//        putTxtToCell(font, alignment, 1, 1, 1, textToInsertarr); // 表格内写入内容(从第1列第1栏开始)
//        // mergeCell(1,1,1,all_count,1); //表格合并(从第1列第1栏开始,第X列第1栏结束)
//        // autoFitTable(); //自动调整表格
//        int count = 0; // 计算合并表格后的列数递增(例如:前五列合并成一列,则往下继续合并时,要考虑加上合并后的那一列)
//        // 依数组笔数合并表格
//        for (int k = 0; k < textToInsertarr.size(); k++){
//            String arr_tostr = textToInsertarr.get(k).toString(); // 先将数组元素转成字符串
//            String arr_substr = arr_tostr.substring(1, arr_tostr.length() - 1); // 去掉前后'['和']'
//            String[] arr_split = arr_substr.split(","); // 字符串数组(在分隔每个元素值)
//            int num = arr_split.length;
//            if (k == 0){ // 第一次合并时,num值不需加前一列
//                mergeCell(1, k + 1, 1, num, 1);
//                count++;
//            } else{ // 第二次合并之后,num值要加前一列,以此类推...
//                mergeCell(1, k + 1, 1, num + count, 1);
//                count++;
//            }
//        }
//        // 取消选择(因为最后insert进去的文字会显示反白,所以要取消)
//        Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));
//        // 插入页首页尾
//        // 取得活动窗体对象
//        Dispatch ActiveWindow = MsWordApp.getProperty("ActiveWindow")
//                .toDispatch();
//        // 取得活动窗格对象
//        Dispatch ActivePane = Dispatch.get(ActiveWindow, "ActivePane")
//                .toDispatch();
//        // 取得窗口对象
//        Dispatch View = Dispatch.get(ActivePane, "View").toDispatch();
//        // 9是设置页首(游标所在处)
//        Dispatch.put(View, "SeekView", "9"); // 页首中的信息
//        Dispatch.put(alignment, "Alignment", "2"); // (1:置中 2:靠右 3:靠左)
//        Dispatch.put(selection, "Text", result_str); // 初始化时间
//        // 10是设置页尾(游标所在处)
//        Dispatch.put(View, "SeekView", "10"); // 页尾中的信息
//        Dispatch.put(alignment, "Alignment", "1"); // (1:置中 2:靠右 3:靠左)
//        Dispatch.put(selection, "Text", new Variant(1)); // 初始化从1开始
//    }
//    /** *//**
//     * 合并表格
//     *
//     * @param tableIndex
//     *            表格起始点
//     * @param fstCellRowIdx
//     *            开始列
//     * @param fstCellColIdx
//     *            开始栏
//     * @param secCellRowIdx
//     *            结束列
//     * @param secCellColIdx
//     *            结束栏
//     */
//    public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx,
//            int secCellRowIdx, int secCellColIdx){
//        // 所有表格
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        // 要填充的表格
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
//     * 在指定的表格里填入内容
//     *
//     * @param tableIndex
//     *            表格起始点
//     * @param cellRowIdx
//     *            第几列
//     * @param cellColIdx
//     *            第几栏
//     * @param txt
//     *            内容字符串数组
//     */
//    public void putTxtToCell(Dispatch font, Dispatch alignment, int tableIndex,
//            int cellRowIdx, int cellColIdx, ArrayList txt){
//        // 所有表格
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        // 主要内容(即参数数组中的值)
//        for (int i = 0; i < txt.size(); i++){
//            String arr_tostr = txt.get(i).toString(); // 先将数组元素转成字符串
//            String arr_substr = arr_tostr.substring(1, arr_tostr.length() - 1); // 去掉前后'['和']'
//            String[] arr_split = arr_substr.split(","); // 字符串数组(在分隔每个元素值)
//            for (int j = 0; j < arr_split.length; j++){
//                // 要填入的表格(对表格列依序填入内容),cellRowIdx++代表从第一列开始
//                Dispatch table = Dispatch.call(tables, "Item",
//                        new Variant(tableIndex)).toDispatch();
//                Dispatch cell = Dispatch.call(table, "Cell",
//                        new Variant(cellRowIdx++), new Variant(cellColIdx))
//                        .toDispatch();
//                // Dispatch.put(cell, "Height",new Variant(1)); //设置列高
//                Dispatch.call(cell, "Select");
//                // 主要内容
//                // Dispatch.call(selection, "TypeParagraph"); //空一行段落
//                // Dispatch.put(alignment, "Alignment", "3"); //(1:置中 2:靠右 3:靠左)
//                if (j == 0){
//                    Dispatch.put(selection, "Text", arr_split[j]); // 写入word的内容
//                    Dispatch.put(font, "Bold", "1"); // 字型租体(1:租体 0:取消租体)
//                    Dispatch.put(font, "Color", "1,1,1,1"); // 字型颜色
//                    // Dispatch.put(font, "Italic", "1"); //字型斜体(1:斜体 0:取消斜体)
//                    // Dispatch.put(font, "Underline", "1"); //文字加底线
//                    Dispatch.call(selection, "MoveDown"); // 光标往下一行(才不会输入盖过上一输入位置)
//                } else{
//                    if (arr_split[j].indexOf(" ") != -1){
//                        String str = arr_split[j].replaceAll(" ", "         "); // 作字符串隔行对齐用
//                        Dispatch.call(selection, "TypeParagraph"); // 空一行段落
//                        Dispatch.put(alignment, "Alignment", "3"); // (1:置中
//                                                                    // 2:靠右
//                                                                    // 3:靠左)
//                        Dispatch.put(selection, "Text", str); // 写入word的内容
//                        Dispatch.put(font, "Bold", "0"); // 字型租体(1:租体 0:取消租体)
//                        Dispatch.put(font, "Color", "1,1,1,0"); // 字型颜色
//                        // Dispatch.put(font, "Italic", "1"); //字型斜体(1:斜体
//                        // 0:取消斜体)
//                        // Dispatch.put(font, "Underline", "1"); //文字加底线
//                        Dispatch.call(selection, "MoveDown"); // 光标往下一行(才不会输入盖过上一输入位置)
//                    } else{
//                        Dispatch.put(selection, "Text", arr_split[j]); // 写入word的内容
//                        Dispatch.put(font, "Bold", "0"); // 字型租体(1:租体 0:取消租体)
//                        Dispatch.put(font, "Color", "1,1,1,0"); // 字型颜色
//                        // Dispatch.put(font, "Italic", "1"); //字型斜体(1:斜体
//                        // 0:取消斜体)
//                        // Dispatch.put(font, "Underline", "1"); //文字加底线
//                        Dispatch.call(selection, "MoveDown"); // 光标往下一行(才不会输入盖过上一输入位置)
//                    }
//                }
//            }
//            // 每写入一次资料空一行,以区隔之用
//            /**//*
//             * Dispatch.call(selection, "TypeParagraph");
//             * Dispatch.put(selection, "Text", " "); Dispatch.call(selection,
//             * "MoveDown"); //游标往下一行
//             */
//        }
//    }
//    /** *//**
//     * 自动调整表格
//     */
//    public void autoFitTable(){
//        Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//        int count = Dispatch.get(tables, "Count").toInt(); // word中的表格数量
//        for (int i = 0; i < count; i++){
//            Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1))
//                    .toDispatch();
//            Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
//            Dispatch.call(cols, "AutoFit");
//        }
//    }
//    /** *//**
//     * 另存档案
//     * @param filename  要储存的文件名称
//     */
//    public void saveFileAs(String filename){
//        Dispatch.call(document, "SaveAs", filename);
//    }
//    /** *//**
//     * 打印word文件
//     */
//    public void printFile(){
//        Dispatch.call(document, "PrintOut");
//    }
//    /** *//**
//     * 关闭文本内容(如果未开启word编辑时,释放ActiveX执行绪)
//     */
//    public void closeDocument(){
//        // 0 = 没有储存改变
//        // -1 = 有储存改变
//        // -2 = 提示储存改变
//        Dispatch.call(document, "Close", new Variant(0));
//        document = null;
//    }
//    /** *//**
//     * 关闭word(如果未开启word编辑时,释放ActiveX执行绪)
//     */
//    public void closeWord(){
//        Dispatch.call(MsWordApp, "Quit");
//        MsWordApp = null;
//        document = null;
//    }
//    @SuppressWarnings("unchecked")
//    public static void main(String[] args){
//        String title = "报告内容";
//        // 这边的字符串内容仿真从数据库所捞取
//        ArrayList all_arr = new ArrayList();
//        ArrayList arr1 = new ArrayList();
//        ArrayList arr2 = new ArrayList();
//        ArrayList arr3 = new ArrayList();
//        ArrayList arr4 = new ArrayList();
//        ArrayList arr5 = new ArrayList();
//        arr1.add("一、1111详察具复之  ");
//        arr1.add("总公司>董事长室回复：申覆处理或申述查明情由与");
//        arr1.add("拟办： 本案拟结案。正在装载数据……1111");
//        arr2.add("二、金融商品买卖作业1详察具复之");
//        arr2.add("总公司>董事长室回复：申覆处理或申述查明情由与");
//        arr2.add("拟办： 本案拟结案。 tttt申覆处理或申述查明情由");
//        arr3.add("三、CI-101（金融商品买卖作业）：详察具复之");
//        arr3.add("总公司>董事长室回复：申覆处理或申述查明情由与");
//        arr3.add("拟办： 本案拟结案。 31231");
//        arr4.add("四、标题详察具复之 ");
//        arr4.add("总公司>董事长室回复：申覆处理或申述查明情由与");
//        arr4.add("拟办： 本案拟结案。拟办");
//        arr5.add("五、12312");
//        arr5.add("拟办： 本案拟结案。碁碁碁佩许功盖");
//        all_arr.add(arr1);
//        all_arr.add(arr2);
//        all_arr.add(arr3);
//        all_arr.add(arr4);
//        all_arr.add(arr5);
//        // String otFile =
//        // request.getRealPath("/")+"test"+File.separator+"sample"+File.separator+"TestDoc.doc";
//        // //目标文件位置
//        String otFile = "D:/sampleTestDoc.doc";
//        WordExtractor word = new WordExtractor(); // 建立一个WordExtractor对象
//        word.openWord(true); // 设定word开启显示
//        word.createNewDocument(); // 建立文件内容
//        word.insertText(title, all_arr); // 要insert入word的内容集合
//        word.saveFileAs(otFile); // 档案另存
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