package example.Jaboc;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
//�⼸�칫˾�и���Ŀ��Ҫ��java�����´���word���Լ�����������һ�£���Ҫ�����ַ�����һ��Jakarta POI������jacob������JAva COm Bridge������ʹ���˵ڶ��ַ��������
//vba��ʹ�÷ǳ����� ���ҹ���ǿ���־��Լ�ʹ�õ�һ���ĵü�¼���¡���������Ҫ�����ص�jacob.dll�ļ�������C:/WINDOWS/system32Ŀ¼�£�����jacob.jar��ӵ�CLASSPATH
//�С���
//1.��ȡһ��component���ԣ���Ҫ����Dispatch��get������
//Dispatch tablefont = Dispatch.get(tablerange,"Font").toDispatch();
//��ʾ��ȡTable��Ӧrange��Font���ԣ��൱��vba�������﷨��
//tablefont = tablerange.Font
//
//2.����һ��component���ԣ���Ҫ����Dispatch��put������
//Dispatch.put(tablefont ,"Size",new Integer(14));
//����tablefont�������СΪ14,�൱�����µ�vba�﷨��
//tablefont.Size = 14
//
//3.����һ��component����ķ�������Ҫ����Dispatch��call��callN����������Ϊcall�������ն��ǵ�����callN����:-),right����
//Ʃ�磬��������е�
//this.document = Dispatch.call(this.documents, "Open", templateFile).toDispatch();
//����ͨ���������д���ʵ��ͬ������:-)
//this.document = Dispatch.callN(this.documents, "Open", new Object[]{templateFile}).toDispatch();
//�൱��vba�������﷨��
//Documents.Open FileName:=templateFile
//��󣬼�סjacob�ĵ��е�һ�仰��
//Declare an STA thread using ComThread.InitSTA() if all your method calls for that component are going to come from the same thread.
//������������vba api�ṩ��Objects,Properties,Collections,Methods���Լ�������̽����word������Ԫ�أ��磺���ȡ���������ô�࣬ϣ���Ըտ�ʼ�Ӵ�jacob���������е��
//
//�İ�����
//�Լ�Ҳ�Ǹ�ѧ�����״���֮����Ҳ�벻�ߴͽ�stoutbear
//��ʹ�õ�jacob��1.9�汾����jdk1.5�µ���ͨ����
//        ��������õ��ı������滻Ϊ������һ��jacob��ʹ�÷�����
import java.io.File;
import java.io.FileNotFoundException;


/**
 * @author Stout.Bear
 * 2007/6/28
 * 
 */
public class WordSpirit4J{
 public static final int wdReplaceOne = 1; //�����滻һ��
 public static final int wdReplaceAll = 2;//���Ҳ��滻ȫ��
 
 public static final int wdFindContinue = 1;


 
 
 protected String templateFile;//ģ���ļ�
 protected String outputFile;//����ļ�
 
 /*jacob���*/
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
  * @param templateFile ģ���ļ�·��
  * @param outputFile ���ɵ�doc�ļ�·��
  * @throws IllegalArgumentException templateFile��������Ϊ��
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
  * ��ʼ��jacob��������Dispatch�������
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
  * @param findStr �����ҵ��ַ���
  * @param replaceWith ���ַ���
  * @param replaceNum �滻һ�λ���ȫ��������ȡֵΪwdReplaceOne��wdReplaceAll��
  */
 protected void findAndReplace(String findStr,String replaceWith,int replaceNum){
  Boolean f = new Boolean(false);
  Boolean t  = new Boolean(true);
  Object[] args={findStr,t,f,f,f,f,t,new Integer(wdFindContinue),f,replaceWith,new Integer(replaceNum),f,f,f,f};
  Dispatch.callN(this.find, "Execute",args);
 }
 
 /**
  * �ر�ģ���ļ���������
  *
  */
 protected void closeTemplate(){
  if(this.templateFile!=null && this.document!=null){
   Dispatch.call(this.document, "Close", new Variant(false));
   this.document = null;
  }
   
 }
 
 /**
  * ���ļ�����ʽ�������
  *
  */
 protected void saveAsOutputFile(){
  if(this.document!=null){
   Dispatch.invoke(this.document, "SaveAs", Dispatch.Method, new Object[] {
     this.outputFile, new Variant(0) }, new int[1]);
  }
 }
 
 /**
  * ��������ʽ�������
  * @return �������ɵ�doc�ĵ��������
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

