package example.Jaboc;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
//这几天公司有个项目需要在java环境下处理word，自己在网上搜了一下，主要有两种方法：一是Jakarta POI，二是jacob（即：JAva COm Bridge），我使用了第二种方法，结合
//vba，使用非常方便 ，且功能强大。现就自己使用的一点心得记录如下。（首先需要将下载的jacob.dll文件拷贝到C:/WINDOWS/system32目录下，并将jacob.jar添加到CLASSPATH
//中。）
//1.获取一个component属性，需要调用Dispatch的get方法：
//Dispatch tablefont = Dispatch.get(tablerange,"Font").toDispatch();
//表示获取Table对应range的Font属性，相当于vba的如下语法：
//tablefont = tablerange.Font
//
//2.设置一个component属性，需要调用Dispatch的put方法：
//Dispatch.put(tablefont ,"Size",new Integer(14));
//设置tablefont的字体大小为14,相当于如下的vba语法：
//tablefont.Size = 14
//
//3.调用一个component组件的方法，需要调用Dispatch的call和callN方法（我认为call方法最终都是调用了callN方法:-),right？）
//譬如，下面代码中的
//this.document = Dispatch.call(this.documents, "Open", templateFile).toDispatch();
//可以通过下面这行代码实现同样操作:-)
//this.document = Dispatch.callN(this.documents, "Open", new Object[]{templateFile}).toDispatch();
//相当于vba的如下语法：
//Documents.Open FileName:=templateFile
//最后，记住jacob文档中的一句话：
//Declare an STA thread using ComThread.InitSTA() if all your method calls for that component are going to come from the same thread.
//结合上述三点和vba api提供的Objects,Properties,Collections,Methods等自己可以试探处理word的其它元素，如：表格等。罗嗦了这么多，希望对刚开始接触jacob的朋友能有点滴
//
//的帮助。
//自己也是刚学，不妥错误之处，也请不吝赐教stoutbear
//我使用的jacob是1.9版本，在jdk1.5下调试通过。
//        本文以最长用的文本查找替换为例描述一下jacob的使用方法：
import java.io.File;
import java.io.FileNotFoundException;


/**
 * @author Stout.Bear
 * 2007/6/28
 * 
 */
public class WordSpirit4J{
 public static final int wdReplaceOne = 1; //查找替换一次
 public static final int wdReplaceAll = 2;//查找并替换全部
 
 public static final int wdFindContinue = 1;


 
 
 protected String templateFile;//模版文件
 protected String outputFile;//输出文件
 
 /*jacob相关*/
 protected ActiveXComponent word;
 protected Dispatch documents;
 protected Dispatch document;
 protected Dispatch selection;
 protected Dispatch range;
 protected Dispatch find;
 protected Dispatch paragraphs;
 protected Dispatch paragraph;
 
 protected Dispatch tables;
 protected Dispatch table;
 protected Dispatch rows;
 protected Dispatch row;
 protected Dispatch cells;
 protected Dispatch cell;
 protected Dispatch cellrange;
 
 /**
  * 
  * @param templateFile 模版文件路径
  * @param outputFile 生成的doc文件路径
  * @throws IllegalArgumentException templateFile参数不能为空
  */
 public WordSpirit4J(String templateFile, String outputFile) throws FileNotFoundException
 {
  super();
  if(templateFile!=null && new File(templateFile).exists()){
   this.templateFile = templateFile;
   this.outputFile = outputFile;
   initFields();
  }
  else{
   throw new FileNotFoundException("templateFile must exist and not be null!");
  }
 }

 
 
 /**
  * 初始化jacob组件的相关Dispatch对象参数
  *
  */
 protected void initFields(){
  try {
   this.word = new ActiveXComponent("Word.Application");
   this.word.setProperty("Visible", new Variant(false));
   this.documents = this.word.getProperty("Documents").toDispatch();
   this.document = Dispatch.call(this.documents, "Open", templateFile).toDispatch();
   this.selection = this.word.getProperty("Selection").toDispatch();
   this.find = Dispatch.call(this.selection, "Find").toDispatch();
   this.paragraphs = Dispatch.get(document, "Paragraphs").toDispatch();
   this.tables = Dispatch.get(document, "Tables").toDispatch();
   this.range = Dispatch.get(selection, "Range").toDispatch();
  }
  catch(Exception ex){
   System.out.println(ex.getCause().toString());
   ex.printStackTrace();
  }
 }
 
 /**
  * 
  * @param findStr 待查找的字符串
  * @param replaceWith 新字符串
  * @param replaceNum 替换一次还是全部，可能取值为wdReplaceOne和wdReplaceAll。
  */
 protected void findAndReplace(String findStr,String replaceWith,int replaceNum){
  Boolean f = new Boolean(false);
  Boolean t  = new Boolean(true);
  Object[] args={findStr,t,f,f,f,f,t,new Integer(wdFindContinue),f,replaceWith,new Integer(replaceNum),f,f,f,f};
  Dispatch.callN(this.find, "Execute",args);
 }
 
 /**
  * 关闭模版文件，不保存
  *
  */
 protected void closeTemplate(){
  if(this.templateFile!=null && this.document!=null){
   Dispatch.call(this.document, "Close", new Variant(false));
   this.document = null;
  }
   
 }
 
 /**
  * 以文件的形式保存输出
  *
  */
 protected void saveAsOutputFile(){
  if(this.document!=null){
   Dispatch.invoke(this.document, "SaveAs", Dispatch.Method, new Object[] {
     this.outputFile, new Variant(0) }, new int[1]);
  }
 }
 
 /**
  * 以流的形式保存输出
  * @return 返回生成的doc文档的输出流
  *
  */
  
 protected void quit(){
  if(this.word!=null){
   this.word.invoke("Quit", new Variant[] {});
   this.word = null;
  }
 }
 
 public static void main(String[] args) {
 String templatef = "E://tempdoc//template1.doc";
  String outputf = "E://tempdoc//temp2.doc";
  try{
   //Declare an STA thread using ComThread.InitSTA() if all your method calls for that component are going to come from the same thread. 
   ComThread.InitSTA();
   WordSpirit4J myword = new WordSpirit4J(templatef,outputf);
   myword.findAndReplace("old", "new", wdReplaceAll);
   myword.saveAsOutputFile();
   myword.closeTemplate();
   myword.quit();
   System.out.println("ok");
  }
  catch(FileNotFoundException e){
   e.printStackTrace();
  }
 finally{
 }
  ComThread.Release();//must
 }
}

