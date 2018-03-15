package LogApp.Extends;


import LogApp.LogStatic;
import LogApp.Tool.Log;
import LogApp.Tool.LogEvent;
import LogApp.Tool.LogGenerator;
import LogApp.Tool.LogNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad /*implements ActionListener , MouseListener , MouseMotionListener , WindowListener , ItemListener , KeyListener, TextListener */
{
    //成员变量
    private String  uuid;
    private JFrame mainFrame;//主框架
    private JMenuBar mb ;     //菜单条
    private JMenu mFile , mEdit , mFormat , mHelp ; //菜单：文件，编辑，格式，帮助
    private JMenuItem miNew , miOpen , miSave , miSaveAs , miExit ;//文件菜单项：新建，打开，保存，另存为，退出
    private JMenuItem miCut , miCopy , miPaste , miDelete ;//编辑菜单项：剪切，复制，粘贴，删除
    private JMenuItem miFont , miLowtoCapital, miCapitaltoLow ,miEncrypt , miDisencrypt;//格式菜单项：字体
    private JMenuItem miAboutNotepad;//帮助菜单项：关于记事本

    private JTextArea ta;//文本区

    private String tempString;//临时字符串,用于存储需要复制粘贴的字符串

    private boolean textValueChanged = false;

    private int id_font ;//字体

    String fileName = "";//上次保存后的文件名和地址

    public void changeVisible(){
        this.changeVisible(new Point(100,200));
    }
    public void changeVisible(Point point){
        if(mainFrame.isVisible())
            mainFrame.setVisible(false);
        else
            mainFrame.setVisible(true);
        this.mainFrame.setLocation(LogStatic.x+point.x,LogStatic.y+point.y);
    }
    public void setText(String text){
        //避免日后隐藏的无限循环
        if(!ta.getText().equals(text)){
            this.ta.setText(text);
            //TODO notepad不应该和day标记耦合在一起

            LogNotification.broadcast(new LogEvent(text,LogStatic.resource.day_changeMessage.name(),uuid));
        }
    }
    public String getText(){
        return this.ta.getText();
    }
    public Notepad(LogStatic.resource_prefix prefix){
        this(null,prefix);
    }
    public void init(String title){
        this.mainFrame.setTitle(title);
        this.mainFrame.setVisible(false);
        this.ta.setText("");
    }
    //构造函数
    public Notepad(JFrame frame, final LogStatic.resource_prefix prefix){
        if(frame==null){
            frame = new JFrame ("Notepad v0.99       by Launching");
            frame.setSize(800 , 500);
            frame.setLocation( 100 ,100);// 起始位置
            frame.setResizable(true);//不可更改大小
        }
        this.uuid = LogGenerator.getUUID();
        this.mainFrame = frame;
        //框架
//        mainFrame = new Frame ("Notepad v0.99       by Launching");
        mb = new JMenuBar ();
        ta = new JTextArea (30 ,60);
        ta.setLineWrap(true);
        ta.setFont( new Font ( "Times New Rome" , Font.PLAIN , 15));
        ta.setBackground(new Color(0, 0, 0));

        //菜单条
        mFile = new JMenu ( "File");
        mEdit = new JMenu ( "Edit");
        mFormat = new JMenu ("Format");
        mHelp = new JMenu ("Help");

        //"文件"
        miNew = new JMenuItem ("New");
        miOpen = new JMenuItem ("Open");
        miSave = new JMenuItem ("Save");
        miSaveAs = new JMenuItem ("Save as");
        miExit = new JMenuItem ("Exit");

        //"编辑"
        miCut = new JMenuItem ("Cut");
        miCopy = new JMenuItem ("Copy");
        miPaste = new JMenuItem ("Paste");
        miDelete = new JMenuItem ("Delete");

        //"格式"
        miFont = new JMenuItem ("Font");
        miLowtoCapital = new JMenuItem("Low to Capital");
        miCapitaltoLow = new JMenuItem("Capital to Low");
        miEncrypt = new JMenuItem("Encrypt");
        miDisencrypt = new JMenuItem("Disencrypt");

        //"帮助"
        miAboutNotepad = new JMenuItem ("About Notepad");

        //添加文件菜单项
        mFile.add(miNew);
        mFile.add(miOpen);
        mFile.add(miSave);
        mFile.add(miSaveAs);
        mFile.add(miExit);

        //添加编辑菜单项
        mEdit.add(miCut);
        mEdit.add(miCopy);
        mEdit.add(miPaste);
        mEdit.add(miDelete);

        //添加格式菜单项
        mFormat.add(miFont);
        mFormat.add(miLowtoCapital);
        mFormat.add(miCapitaltoLow);
        mFormat.add(miEncrypt);
        mFormat.add(miDisencrypt);

        //添加帮助菜单项
        mHelp.add(miAboutNotepad);

        //菜单条添加菜单
        mb.add(mFile);
        mb.add(mEdit);
        mb.add(mFormat);
        mb.add(mHelp);

        //框架添加菜单条
        mainFrame.setJMenuBar( mb );

        //初始字符串赋为空
        tempString = "";

//添加文本区
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(ta);
        mainFrame.add(jScrollPane, BorderLayout.CENTER);

//        mainFrame.setSize(800 , 500);
//        mainFrame.setLocation( 100 ,100);// 起始位置
//        mainFrame.setResizable(true);//不可更改大小
//        mainFrame.setVisible(true);
        //mainFrame.pack();

        //////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////增加监视器////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////

//主框架
        mainFrame.addWindowListener(new WindowAdapter (){ //关闭窗口
            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//                LogNotification.broadcast(new LogEvent(null,prefix.name() + LogStatic.resource.day_remark_visible.name(),uuid));
                LogNotification.broadcast(new LogEvent(null,prefix.name() + LogStatic.resource_suffix._remark_visible.name(),uuid));

            }
        });

        //文本区
        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                textValueChanged = true ; //键盘按键按下即导致文本修改
               //TODO 广播day被修改。
                LogNotification.broadcast(new LogEvent(ta.getText(),
                        prefix.name()+LogStatic.resource_suffix._changeMessage.name(),
                        uuid));
            }
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });

////////////////"文件"菜单：//////////////////////

        //新建
        miNew.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){

                ta.replaceRange("", 0 , ta.getText().length()) ;//清空文本区的内容

                fileName = "";//文件名清空
            }
        });

//打开
        miOpen.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                final FileDialog d=new FileDialog(mainFrame , "open file" , FileDialog.LOAD );//打开文件对话框

                d.addWindowListener( new WindowAdapter(){ //关闭文件对话框窗口
                    public void windowClosing(WindowEvent ee){
//                        System.exit(0);
                        d.dispose();
                    }
                });
                d.setVisible(true);

                File f = new File( d.getDirectory()+d.getFile() ); //建立新文件

                fileName = d.getDirectory()+d.getFile();//得到文件名

                char ch[] = new char [(int)f.length()];///用此文件的长度建立一个字符数组

                try//异常处理
                {
                    //读出数据，并存入字符数组ch中
                    BufferedReader bw = new BufferedReader( new FileReader(f) );
                    bw.read(ch);
                    bw.close();
                }
                catch( FileNotFoundException fe ){
                    Log.Loggin("file not found",LogStatic.Log_classify.SystemLog.name());
//                    System.exit(0);
                }
                catch( IOException ie){
                    Log.Loggin("IO error",LogStatic.Log_classify.SystemLog.name());
//                    System.exit(0);
                }

                String s =new String (ch);

                setText(s);//设置文本区为所打开文件的内容
            }
        });

        //保存
        miSave.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                if( fileName.equals("") ){ //如果文件没有被保存过,即文件名为空

                    final FileDialog d=new FileDialog(mainFrame , "save file" , FileDialog.SAVE );//保存文件对话框

                    d.addWindowListener( new WindowAdapter(){ //关闭文件对话框窗口
                        public void windowClosing(WindowEvent ee){
//                            System.exit(0);
                                d.dispose();
                        }
                    });
                    d.setVisible(true);

                    String s = ta.getText();//得到所输入的文本内容

                    try//异常处理
                    {
                        File f = new File( d.getDirectory()+d.getFile());//新建文件

                        fileName = d.getDirectory()+d.getFile();//得到文件名

                        BufferedWriter bw = new BufferedWriter( new FileWriter (f));//输入到文件中
                        bw.write(s , 0 , s.length());
                        bw.close();

                    }
                    catch(FileNotFoundException fe_){
//                        System.out.println("file not found");
//                        System.exit(0);
                        Log.Loggin("file not found",LogStatic.Log_classify.SystemLog.name());
                    }
                    catch( IOException ie_)
                    {
                        Log.Loggin(" IO error",LogStatic.Log_classify.SystemLog.name());
//                        System.out.println(" IO error");
//                        System.exit(0);
                    }

                }

                else //如果文件已经保存过
                {
                    String s = ta.getText();//得到所输入的文本内容

                    try//异常处理
                    {
                        File f = new File( fileName );//新建文件

                        BufferedWriter bw = new BufferedWriter( new FileWriter (f));//输入到文件中
                        bw.write(s , 0 , s.length());
                        bw.close();

                    }
                    catch(FileNotFoundException fe_){
//                        System.out.println("file not found");
//                        System.exit(0);
                        Log.Loggin("file not found",LogStatic.Log_classify.SystemLog.name());
                    }
                    catch( IOException ie_)
                    {
                        Log.Loggin(" IO error",LogStatic.Log_classify.SystemLog.name());
//                        System.out.println(" IO error");
//                        System.exit(0);
                    }

                }

            }
        });

//另存为
        miSaveAs.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                FileDialog d=new FileDialog(mainFrame , "save file" , FileDialog.SAVE );//保存文件对话框

                d.addWindowListener( new WindowAdapter(){ //关闭文件对话框窗口
                    public void windowClosing(WindowEvent ee){
                        System.exit(0);
                    }
                });
                d.setVisible(true);

                String s = ta.getText();//得到所输入的文本内容

                try//异常处理
                {
                    File f = new File( d.getDirectory()+d.getFile());//新建文件

                    BufferedWriter bw = new BufferedWriter( new FileWriter (f));//输入到文件中
                    bw.write(s , 0 , s.length());
                    bw.close();

                }
                catch(FileNotFoundException fe_){
//                        System.out.println("file not found");
//                        System.exit(0);
                    Log.Loggin("file not found",LogStatic.Log_classify.SystemLog.name());
                }
                catch( IOException ie_)
                {
                    Log.Loggin(" IO error",LogStatic.Log_classify.SystemLog.name());
//                        System.out.println(" IO error");
//                        System.exit(0);
                }
            }
        });

//退出
        miExit.addActionListener( new ActionListener(){ ///退出程序
            public void actionPerformed(ActionEvent e){
//                mainFrame.setVisible(false);
//                System.exit(0);
                LogNotification.broadcast(new LogEvent(null,
                        prefix.name()+LogStatic.resource_suffix._remark_visible.name()));
            }
        });

        ////////////////"编辑"菜单：////////////////////

        //剪切
        miCut.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tempString = ta.getSelectedText(); ///得到要复制的内容,暂存在tempString中
                StringBuffer tmp = new StringBuffer ( ta.getText());//临时存储文本
                int start = ta.getSelectionStart(); //得到要删除的字符串的起始位置
                int len = ta.getSelectedText().length(); //得到要删除的字符串的长度
                tmp.delete( start , start+len); ///删除所选中的字符串
                setText(tmp.toString());//用新文本设置原文本
            }
        });

        //复制
        miCopy.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tempString = ta.getSelectedText(); ///得到要复制的内容,暂存在tempString中
            }
        });

        //粘贴
        miPaste.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                StringBuffer tmp = new StringBuffer ( ta.getText());//临时存储文本
                int start = ta.getSelectionStart(); //得到要粘贴的位置
                tmp.insert(start , tempString);//查入要粘贴的内容
                setText(tmp.toString());//用新文本设置原文本
            }
        });

        //删除
        miDelete.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                StringBuffer tmp = new StringBuffer ( ta.getText());//临时存储文本
                int start = ta.getSelectionStart(); //得到要删除的字符串的起始位置
                int len = ta.getSelectedText().length(); //得到要删除的字符串的长度
                tmp.delete( start , start+len); ///删除所选中的字符串
                setText(tmp.toString());//用新文本设置原文本
            }
        });

////////////////"格式"菜单：////////////////////

        //字体
        miFont.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                final Dialog d = new Dialog ( mainFrame , "Font");//新建对话框
                d.setLocation( 250 ,250);// 起始位置

                d.setLayout( new BorderLayout());//表格布局

                //////////////////////////上部分面板
                Label l_font = new Label ("font");//font标签
                Panel p_1 = new Panel();
                p_1.add(l_font);
                p_1.setVisible(true);

//////////////////////////中部分面板
                List font_list = new List (6 , false);//字体列表

                //添加字体项目
                font_list.add("Plain");///普通字体
                font_list.add("Bold"); ///粗体
                font_list.add("Italic");//斜体

                font_list.addItemListener( new MyItemListener_font() ); //字体增加监视器

                Panel p_2 = new Panel();
                p_2.add(font_list);
                p_2.setVisible(true);

//////////////////////////下部分面板
                Button ok = new Button ("OK");
                ok.addActionListener( new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        d.dispose();
                    }
                });
                ok.setSize( new Dimension (20 , 5) );

                Panel p_3 = new Panel();//下部分面板
                p_3.add(ok);
                p_3.setVisible(true);

//添加三个面板
                d.add(p_1 , BorderLayout.NORTH);
                d.add(p_2 , BorderLayout.CENTER);
                d.add(p_3 , BorderLayout.SOUTH);
                d.pack();

                d.addWindowListener( new WindowAdapter(){ //关闭对话框窗口
                    public void windowClosing(WindowEvent ee){
                        d.dispose();
                    }
                });

                d.setVisible(true);
            }
        });

        //小写字母转大写
        miLowtoCapital.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//得到所输入的文本内容
                StringBuffer temp = new StringBuffer("");
                for(int i = 0 ; i<s.length() ; i++){
                    if((int)s.charAt(i)>=97 && (int)s.charAt(i)<=122 ){
                        temp.append((char)((int)s.charAt(i)-32));
                    }
                    else
                        temp.append(s.charAt(i));
                }
                s = new String(temp);
                setText(s);
            }
        });

//大写字母转小写
        miCapitaltoLow.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//得到所输入的文本内容
                StringBuffer temp = new StringBuffer("");
                for(int i = 0 ; i<s.length() ; i++){
                    if((int)s.charAt(i)>=65 && (int)s.charAt(i)<=90 ){
                        temp.append((char)((int)s.charAt(i)+32));
                    }
                    else
                        temp.append(s.charAt(i));
                }
                s = new String(temp);
                setText(s);
            }
        });

//加密
        miEncrypt.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//得到所输入的文本内容
                StringBuffer temp = new StringBuffer("");
                for(int i = 0 ; i<s.length() ; i++){
                    if(s.charAt(i)>=40 && s.charAt(i)<=125){
                        if(i%2==0){
                            temp.append((char)(s.charAt(i) + 1 ));
                        }
                        else
                            temp.append((char)(s.charAt(i) - 1 ));
                    }
                    else
                        temp.append(s.charAt(i));

                }
                s = new String(temp);
                setText(s);
            }
        });

        //解密
        miDisencrypt.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//得到所输入的文本内容
                StringBuffer temp = new StringBuffer("");
                for(int i = 0 ; i<s.length() ; i++){
                    if(s.charAt(i)>=40 && s.charAt(i)<=125){
                        if(i%2==0){
                            temp.append((char)(s.charAt(i) - 1 ));
                        }
                        else
                            temp.append((char)(s.charAt(i) + 1 ));
                    }
                    else
                        temp.append(s.charAt(i));
                }
                s = new String(temp);
                setText(s);
            }
        });

////////////////"帮助"菜单：////////////////////

        //关于记事本
        miAboutNotepad.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                final Dialog d = new Dialog ( mainFrame , "AboutNotepad");//新建对话框
                TextArea t = new TextArea("\nwelcome to use Notepad " + "\n\n" + "Copyright@Launching " + "\n\n" + "free software" + "\n\n" + "v0.99");//添加标签
                t.setSize( new Dimension ( 5 , 5));
                t.setEditable(false);
                d.setResizable(false);//不可调整大小
                d.add(t);
                d.pack();

                d.addWindowListener( new WindowAdapter(){ //关闭对话框窗口
                    public void windowClosing(WindowEvent ee){
                        d.dispose();
                    }
                });
                d.setLocation( 100 ,250);// 起始位置
                d.setVisible(true);
            }
        });

    }

    class MyItemListener_font implements ItemListener { //字体监听器
        public void itemStateChanged(ItemEvent e) {
            id_font = ((java.awt.List)e.getSource()).getSelectedIndex();
            switch( id_font){
                case 0:{
                    ta.setFont(new Font("Times New Roman", Font.PLAIN ,ta.getFont().getSize()) );//普通文字
                    break;
                }
                case 1:{
                    ta.setFont(new Font("Times New Roman" , Font.BOLD ,ta.getFont().getSize()) );//粗体文字
                    break;
                }
                case 2:{
                    ta.setFont(new Font("Times New Roman" , Font.ITALIC ,ta.getFont().getSize()) );//斜体文字
                    break;
                }
            }
        }
    }
    /////////////////////////////////////////主函数///////////////////////////////////////////////////
//    public static void main(String arg[]){
//        Notepad test = new Notepad(); ///创建记事本
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////
}