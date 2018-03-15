package example.Jaboc;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobExcelTool {
    private static ActiveXComponent xl = null; //Excel����(��ֹ�򿪶��)
    private static Dispatch workbooks = null;  //����������
    private Dispatch workbook = null; //���幤����
    private Dispatch sheets = null;// ���sheets���϶���
    private Dispatch currentSheet = null;// ��ǰsheet
    
    public ActiveXComponent getXl() {
        return xl;
    }

    public Dispatch getWorkbooks() {
        return workbooks;
    }

    public Dispatch getWorkbook() {
        return workbook;
    }

    /**
     * ��excel�ļ�
     * @param filepath �ļ�·������
     * @param visible  �Ƿ���ʾ��
     * @param readonly �Ƿ�ֻ����ʽ��
     */
    public void OpenExcel(String filepath, boolean visible, boolean readonly) {
        try {
            initComponents(); //���ԭʼ����
            ComThread.InitSTA();
            if(xl==null)
                xl = new ActiveXComponent("Excel.Application"); //Excel����
            xl.setProperty("Visible", new Variant(visible));//�����Ƿ���ʾ��excel
            if(workbooks==null)
                workbooks = xl.getProperty("Workbooks").toDispatch(); //����������
            workbook = Dispatch.invoke(  //�򿪾��幤����
                    workbooks,
                    "Open",
                    Dispatch.Method,
                    new Object[] { filepath, new Variant(false),
                            new Variant(readonly) },// �Ƿ���ֻ����ʽ��
                            new int[1]).toDispatch();
        } catch (Exception e) {
            e.printStackTrace();
            releaseSource();
        }
    }
    
    /**
     * ���������Ϊ
     * @param filePath ���Ϊ��·��
     */
    public void SaveAs(String filePath){
           Dispatch.invoke(workbook, "SaveAs", Dispatch.Method,
                                      new Object[] { filePath,
                                               new Variant(44) }, new int[1]);
      }
    
    /**
     * �ر�excel�ĵ�
     * @param f ���岻�� ���ر��Ƿ񱣴棿Ĭ��false��
     */
    public void CloseExcel(boolean f,boolean quitXl) {
        try {
            Dispatch.call(workbook, "Save");
            Dispatch.call(workbook, "Close", new Variant(f));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(quitXl){
                releaseSource();
            }
        }
    }
    
    /**
     * �ͷ���Դ
     */
    public static void releaseSource(){
        if(xl!=null){
            xl.invoke("Quit", new Variant[] {});
            xl = null;
        }
        workbooks = null;
        ComThread.Release();
        System.gc();
    }
    
    /**
     *  ����µĹ�����(sheet)������Ӻ�ΪĬ��Ϊ��ǰ����Ĺ�����
     */
    public Dispatch addSheet() {
        return Dispatch.get(Dispatch.get(workbook, "sheets").toDispatch(), "add").toDispatch();
    }
    
    /**
     * �޸ĵ�ǰ�����������
     * @param newName
     */
    public void modifyCurrentSheetName(String newName) {
        Dispatch.put(getCurrentSheet(), "name", newName);
    }

    /**
     * �õ���ǰ�����������
     * @return
     */
    public String getCurrentSheetName() {
        return Dispatch.get(getCurrentSheet(), "name").toString();
    }

    /**
     * �õ�������������
     * @return
     */
    public String getWorkbookName() {
        if(workbook==null)
            return null;
        return Dispatch.get(workbook, "name").toString();
    }

    /**
     *  �õ�sheets�ļ��϶���
     * @return
     */
    public Dispatch getSheets() {
        if(sheets==null)
            sheets = Dispatch.get(workbook, "sheets").toDispatch();
        return sheets;
    }
    
    /**
     * �õ���ǰsheet
     * @return
     */
    public Dispatch getCurrentSheet() {
        currentSheet = Dispatch.get(workbook, "ActiveSheet").toDispatch();
        return currentSheet;
    }

    /**
     * ͨ�����������ֵõ�������
     * @param name sheetName
     * @return
     */
    public Dispatch getSheetByName(String name) {
        return Dispatch.invoke(getSheets(), "Item", Dispatch.Get, new Object[]{name}, new int[1]).toDispatch();
    }
    
    /**
     * ͨ�������������õ�������(��һ��������indexΪ1)
     * @param index
     * @return  sheet����
     */
    public Dispatch getSheetByIndex(Integer index) {
        return Dispatch.invoke(getSheets(), "Item", Dispatch.Get, new Object[]{index}, new int[1]).toDispatch();
    }

    /**
     * �õ�sheet������
     * @return
     */
    public int getSheetCount() {
        Variant count1 = Dispatch.get(getSheets(), "count");
        //TODO
        int count = Integer.parseInt(count1.toString());
        return count;
    }
    
    /**
     * ����excel��
     * @param macroName ����
     */
    public void callMacro(String macroName){
        Dispatch.call(xl, "Run",new Variant(macroName));
    }
    
    /**
     *  ��Ԫ��д��ֵ
     * @param sheet  ��������sheet
     * @param position ��Ԫ��λ�ã��磺C1
     * @param type ֵ������ �磺value
     * @param value
     */
    public void setValue(Dispatch sheet, String position, String type, Object value) {

        Dispatch cell = Dispatch.invoke(sheet, "Range",
                Dispatch.Get, new Object[] { position }, new int[1])
                .toDispatch();
        Dispatch.put(cell, type, value);
    }
    
    /**
     * ��Ԫ���ȡֵ
     * @param position ��Ԫ��λ�ã��磺 C1
     * @param sheet 
     * @return
     */
    public Variant getValue(String position, Dispatch sheet) {
        Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get,
                new Object[] { position }, new int[1]).toDispatch();
        Variant value = Dispatch.get(cell, "Value");
        return value;
    }
    
    private void initComponents(){
        workbook = null;
        currentSheet = null;
        sheets = null;
    }
    
    public static void main(String[] a)
    {        
//        
//        String path = "f:\\s.xlsx";
//        example.Jaboc.JacobExcelTool test = new example.Jaboc.JacobExcelTool();
//        test.OpenExcel(path, false, false);
////        test.addSheet();
//        System.out.println(test.getWorkbookName());
//        System.out.println(test.getSheets());
////        test.modifyCurrentSheetName("zhaocuo");
//        System.out.println(Dispatch.get(test.getCurrentSheet(), "name").toString());
//        Dispatch.invoke(test.getSheets(), "Item", Dispatch.Get, new Object[]{4}, new int[1]).toDispatch();
//        test.CloseExcel(false, true);
    }
}
