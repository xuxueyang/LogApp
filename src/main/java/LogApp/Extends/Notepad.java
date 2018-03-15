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
    //��Ա����
    private String  uuid;
    private JFrame mainFrame;//�����
    private JMenuBar mb ;     //�˵���
    private JMenu mFile , mEdit , mFormat , mHelp ; //�˵����ļ����༭����ʽ������
    private JMenuItem miNew , miOpen , miSave , miSaveAs , miExit ;//�ļ��˵���½����򿪣����棬���Ϊ���˳�
    private JMenuItem miCut , miCopy , miPaste , miDelete ;//�༭�˵�����У����ƣ�ճ����ɾ��
    private JMenuItem miFont , miLowtoCapital, miCapitaltoLow ,miEncrypt , miDisencrypt;//��ʽ�˵������
    private JMenuItem miAboutNotepad;//�����˵�����ڼ��±�

    private JTextArea ta;//�ı���

    private String tempString;//��ʱ�ַ���,���ڴ洢��Ҫ����ճ�����ַ���

    private boolean textValueChanged = false;

    private int id_font ;//����

    String fileName = "";//�ϴα������ļ����͵�ַ

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
        //�����պ����ص�����ѭ��
        if(!ta.getText().equals(text)){
            this.ta.setText(text);
            //TODO notepad��Ӧ�ú�day��������һ��

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
    //���캯��
    public Notepad(JFrame frame, final LogStatic.resource_prefix prefix){
        if(frame==null){
            frame = new JFrame ("Notepad v0.99       by Launching");
            frame.setSize(800 , 500);
            frame.setLocation( 100 ,100);// ��ʼλ��
            frame.setResizable(true);//���ɸ��Ĵ�С
        }
        this.uuid = LogGenerator.getUUID();
        this.mainFrame = frame;
        //���
//        mainFrame = new Frame ("Notepad v0.99       by Launching");
        mb = new JMenuBar ();
        ta = new JTextArea (30 ,60);
        ta.setLineWrap(true);
        ta.setFont( new Font ( "Times New Rome" , Font.PLAIN , 15));
        ta.setBackground(new Color(0, 0, 0));

        //�˵���
        mFile = new JMenu ( "File");
        mEdit = new JMenu ( "Edit");
        mFormat = new JMenu ("Format");
        mHelp = new JMenu ("Help");

        //"�ļ�"
        miNew = new JMenuItem ("New");
        miOpen = new JMenuItem ("Open");
        miSave = new JMenuItem ("Save");
        miSaveAs = new JMenuItem ("Save as");
        miExit = new JMenuItem ("Exit");

        //"�༭"
        miCut = new JMenuItem ("Cut");
        miCopy = new JMenuItem ("Copy");
        miPaste = new JMenuItem ("Paste");
        miDelete = new JMenuItem ("Delete");

        //"��ʽ"
        miFont = new JMenuItem ("Font");
        miLowtoCapital = new JMenuItem("Low to Capital");
        miCapitaltoLow = new JMenuItem("Capital to Low");
        miEncrypt = new JMenuItem("Encrypt");
        miDisencrypt = new JMenuItem("Disencrypt");

        //"����"
        miAboutNotepad = new JMenuItem ("About Notepad");

        //����ļ��˵���
        mFile.add(miNew);
        mFile.add(miOpen);
        mFile.add(miSave);
        mFile.add(miSaveAs);
        mFile.add(miExit);

        //��ӱ༭�˵���
        mEdit.add(miCut);
        mEdit.add(miCopy);
        mEdit.add(miPaste);
        mEdit.add(miDelete);

        //��Ӹ�ʽ�˵���
        mFormat.add(miFont);
        mFormat.add(miLowtoCapital);
        mFormat.add(miCapitaltoLow);
        mFormat.add(miEncrypt);
        mFormat.add(miDisencrypt);

        //��Ӱ����˵���
        mHelp.add(miAboutNotepad);

        //�˵�����Ӳ˵�
        mb.add(mFile);
        mb.add(mEdit);
        mb.add(mFormat);
        mb.add(mHelp);

        //�����Ӳ˵���
        mainFrame.setJMenuBar( mb );

        //��ʼ�ַ�����Ϊ��
        tempString = "";

//����ı���
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(ta);
        mainFrame.add(jScrollPane, BorderLayout.CENTER);

//        mainFrame.setSize(800 , 500);
//        mainFrame.setLocation( 100 ,100);// ��ʼλ��
//        mainFrame.setResizable(true);//���ɸ��Ĵ�С
//        mainFrame.setVisible(true);
        //mainFrame.pack();

        //////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////���Ӽ�����////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////

//�����
        mainFrame.addWindowListener(new WindowAdapter (){ //�رմ���
            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//                LogNotification.broadcast(new LogEvent(null,prefix.name() + LogStatic.resource.day_remark_visible.name(),uuid));
                LogNotification.broadcast(new LogEvent(null,prefix.name() + LogStatic.resource_suffix._remark_visible.name(),uuid));

            }
        });

        //�ı���
        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                textValueChanged = true ; //���̰������¼������ı��޸�
               //TODO �㲥day���޸ġ�
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

////////////////"�ļ�"�˵���//////////////////////

        //�½�
        miNew.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){

                ta.replaceRange("", 0 , ta.getText().length()) ;//����ı���������

                fileName = "";//�ļ������
            }
        });

//��
        miOpen.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                final FileDialog d=new FileDialog(mainFrame , "open file" , FileDialog.LOAD );//���ļ��Ի���

                d.addWindowListener( new WindowAdapter(){ //�ر��ļ��Ի��򴰿�
                    public void windowClosing(WindowEvent ee){
//                        System.exit(0);
                        d.dispose();
                    }
                });
                d.setVisible(true);

                File f = new File( d.getDirectory()+d.getFile() ); //�������ļ�

                fileName = d.getDirectory()+d.getFile();//�õ��ļ���

                char ch[] = new char [(int)f.length()];///�ô��ļ��ĳ��Ƚ���һ���ַ�����

                try//�쳣����
                {
                    //�������ݣ��������ַ�����ch��
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

                setText(s);//�����ı���Ϊ�����ļ�������
            }
        });

        //����
        miSave.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                if( fileName.equals("") ){ //����ļ�û�б������,���ļ���Ϊ��

                    final FileDialog d=new FileDialog(mainFrame , "save file" , FileDialog.SAVE );//�����ļ��Ի���

                    d.addWindowListener( new WindowAdapter(){ //�ر��ļ��Ի��򴰿�
                        public void windowClosing(WindowEvent ee){
//                            System.exit(0);
                                d.dispose();
                        }
                    });
                    d.setVisible(true);

                    String s = ta.getText();//�õ���������ı�����

                    try//�쳣����
                    {
                        File f = new File( d.getDirectory()+d.getFile());//�½��ļ�

                        fileName = d.getDirectory()+d.getFile();//�õ��ļ���

                        BufferedWriter bw = new BufferedWriter( new FileWriter (f));//���뵽�ļ���
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

                else //����ļ��Ѿ������
                {
                    String s = ta.getText();//�õ���������ı�����

                    try//�쳣����
                    {
                        File f = new File( fileName );//�½��ļ�

                        BufferedWriter bw = new BufferedWriter( new FileWriter (f));//���뵽�ļ���
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

//���Ϊ
        miSaveAs.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                FileDialog d=new FileDialog(mainFrame , "save file" , FileDialog.SAVE );//�����ļ��Ի���

                d.addWindowListener( new WindowAdapter(){ //�ر��ļ��Ի��򴰿�
                    public void windowClosing(WindowEvent ee){
                        System.exit(0);
                    }
                });
                d.setVisible(true);

                String s = ta.getText();//�õ���������ı�����

                try//�쳣����
                {
                    File f = new File( d.getDirectory()+d.getFile());//�½��ļ�

                    BufferedWriter bw = new BufferedWriter( new FileWriter (f));//���뵽�ļ���
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

//�˳�
        miExit.addActionListener( new ActionListener(){ ///�˳�����
            public void actionPerformed(ActionEvent e){
//                mainFrame.setVisible(false);
//                System.exit(0);
                LogNotification.broadcast(new LogEvent(null,
                        prefix.name()+LogStatic.resource_suffix._remark_visible.name()));
            }
        });

        ////////////////"�༭"�˵���////////////////////

        //����
        miCut.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tempString = ta.getSelectedText(); ///�õ�Ҫ���Ƶ�����,�ݴ���tempString��
                StringBuffer tmp = new StringBuffer ( ta.getText());//��ʱ�洢�ı�
                int start = ta.getSelectionStart(); //�õ�Ҫɾ�����ַ�������ʼλ��
                int len = ta.getSelectedText().length(); //�õ�Ҫɾ�����ַ����ĳ���
                tmp.delete( start , start+len); ///ɾ����ѡ�е��ַ���
                setText(tmp.toString());//�����ı�����ԭ�ı�
            }
        });

        //����
        miCopy.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tempString = ta.getSelectedText(); ///�õ�Ҫ���Ƶ�����,�ݴ���tempString��
            }
        });

        //ճ��
        miPaste.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                StringBuffer tmp = new StringBuffer ( ta.getText());//��ʱ�洢�ı�
                int start = ta.getSelectionStart(); //�õ�Ҫճ����λ��
                tmp.insert(start , tempString);//����Ҫճ��������
                setText(tmp.toString());//�����ı�����ԭ�ı�
            }
        });

        //ɾ��
        miDelete.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                StringBuffer tmp = new StringBuffer ( ta.getText());//��ʱ�洢�ı�
                int start = ta.getSelectionStart(); //�õ�Ҫɾ�����ַ�������ʼλ��
                int len = ta.getSelectedText().length(); //�õ�Ҫɾ�����ַ����ĳ���
                tmp.delete( start , start+len); ///ɾ����ѡ�е��ַ���
                setText(tmp.toString());//�����ı�����ԭ�ı�
            }
        });

////////////////"��ʽ"�˵���////////////////////

        //����
        miFont.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                final Dialog d = new Dialog ( mainFrame , "Font");//�½��Ի���
                d.setLocation( 250 ,250);// ��ʼλ��

                d.setLayout( new BorderLayout());//��񲼾�

                //////////////////////////�ϲ������
                Label l_font = new Label ("font");//font��ǩ
                Panel p_1 = new Panel();
                p_1.add(l_font);
                p_1.setVisible(true);

//////////////////////////�в������
                List font_list = new List (6 , false);//�����б�

                //���������Ŀ
                font_list.add("Plain");///��ͨ����
                font_list.add("Bold"); ///����
                font_list.add("Italic");//б��

                font_list.addItemListener( new MyItemListener_font() ); //�������Ӽ�����

                Panel p_2 = new Panel();
                p_2.add(font_list);
                p_2.setVisible(true);

//////////////////////////�²������
                Button ok = new Button ("OK");
                ok.addActionListener( new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        d.dispose();
                    }
                });
                ok.setSize( new Dimension (20 , 5) );

                Panel p_3 = new Panel();//�²������
                p_3.add(ok);
                p_3.setVisible(true);

//����������
                d.add(p_1 , BorderLayout.NORTH);
                d.add(p_2 , BorderLayout.CENTER);
                d.add(p_3 , BorderLayout.SOUTH);
                d.pack();

                d.addWindowListener( new WindowAdapter(){ //�رնԻ��򴰿�
                    public void windowClosing(WindowEvent ee){
                        d.dispose();
                    }
                });

                d.setVisible(true);
            }
        });

        //Сд��ĸת��д
        miLowtoCapital.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//�õ���������ı�����
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

//��д��ĸתСд
        miCapitaltoLow.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//�õ���������ı�����
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

//����
        miEncrypt.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//�õ���������ı�����
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

        //����
        miDisencrypt.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String s = ta.getText();//�õ���������ı�����
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

////////////////"����"�˵���////////////////////

        //���ڼ��±�
        miAboutNotepad.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                final Dialog d = new Dialog ( mainFrame , "AboutNotepad");//�½��Ի���
                TextArea t = new TextArea("\nwelcome to use Notepad " + "\n\n" + "Copyright@Launching " + "\n\n" + "free software" + "\n\n" + "v0.99");//��ӱ�ǩ
                t.setSize( new Dimension ( 5 , 5));
                t.setEditable(false);
                d.setResizable(false);//���ɵ�����С
                d.add(t);
                d.pack();

                d.addWindowListener( new WindowAdapter(){ //�رնԻ��򴰿�
                    public void windowClosing(WindowEvent ee){
                        d.dispose();
                    }
                });
                d.setLocation( 100 ,250);// ��ʼλ��
                d.setVisible(true);
            }
        });

    }

    class MyItemListener_font implements ItemListener { //���������
        public void itemStateChanged(ItemEvent e) {
            id_font = ((java.awt.List)e.getSource()).getSelectedIndex();
            switch( id_font){
                case 0:{
                    ta.setFont(new Font("Times New Roman", Font.PLAIN ,ta.getFont().getSize()) );//��ͨ����
                    break;
                }
                case 1:{
                    ta.setFont(new Font("Times New Roman" , Font.BOLD ,ta.getFont().getSize()) );//��������
                    break;
                }
                case 2:{
                    ta.setFont(new Font("Times New Roman" , Font.ITALIC ,ta.getFont().getSize()) );//б������
                    break;
                }
            }
        }
    }
    /////////////////////////////////////////������///////////////////////////////////////////////////
//    public static void main(String arg[]){
//        Notepad test = new Notepad(); ///�������±�
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////
}