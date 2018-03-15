import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class testExcel
{
    private static ActiveXComponent xl = null; //Excel����(��ֹ�򿪶��)
    private static Dispatch workbooks = null;  //����������
    private static Dispatch workbook = null; //���幤����
    private Dispatch sheets = null;// ���sheets���϶���
    private Dispatch currentSheet = null;// ��ǰsheet
    
    public static void main(String[] a)
    {
        //���ļ�
        String filepath = "f:\\s.xlsx";
        xl = new ActiveXComponent("Excel.Application"); //Excel����
        xl.setProperty("Visible", new Variant(false));//�����Ƿ���ʾ��excel
        if(workbooks==null)
            workbooks = xl.getProperty("Workbooks").toDispatch(); //����������
        workbook = Dispatch.invoke(  //�򿪾��幤����
                workbooks,
                "Open",
                Dispatch.Method,
                new Object[] { filepath, new Variant(false),
                        new Variant(false) },// �Ƿ���ֻ����ʽ��
                        new int[1]).toDispatch();
        
        //����µĹ�����
        Dispatch.get(Dispatch.get(workbook, "sheets").toDispatch(), "add").toDispatch();
        //�õ���ǰ��sheet
        Dispatch.get(workbook, "ActiveSheet").toDispatch();//getCurrentSheet()
        //�޸ĵ�ǰ�ı���
        String newSheetName = "zhaocuo";
        Dispatch.put(Dispatch.get(workbook, "ActiveSheet").toDispatch(), "name", newSheetName);
        //�õ���ǰ�����������
        Dispatch.get(Dispatch.get(workbook, "ActiveSheet").toDispatch(), "name").toString();
        //�õ�������������
        Dispatch.get(workbook, "name").toString();
        //�õ�sheets�ļ��϶���
        Dispatch.get(workbook, "sheets").toDispatch();//getSheets()
        //ͨ�����������ֵõ�������
        String sheetName = "";
        Dispatch.invoke(Dispatch.get(workbook, "sheets").toDispatch(), "Item", Dispatch.Get, new Object[]{sheetName}, new int[1]).toDispatch();
        //ͨ�������������õ�������(��һ��������indexΪ1)
        Dispatch.invoke(Dispatch.get(workbook, "sheets").toDispatch(), "Item", Dispatch.Get, new Object[]{1}, new int[1]).toDispatch();
        //�õ�sheet������
        Dispatch.get(Dispatch.get(workbook, "sheets").toDispatch(), "count").toString();
        /**
         *  ��Ԫ��д��ֵ
         * @param sheet  ��������sheet
         * @param position ��Ԫ��λ�ã��磺C1
         * @param type ֵ������ �磺value
         * @param value
         */
//        public void setValue(Dispatch sheet, String position, String type, Object value) {
//
//            Dispatch cell = Dispatch.invoke(sheet, "Range",
//                    Dispatch.Get, new Object[] { position }, new int[1])
//                    .toDispatch();
//            Dispatch.put(cell, type, value);
        
        /**
         * ��Ԫ���ȡֵ
         * @param position ��Ԫ��λ�ã��磺 C1
         * @param sheet 
         * @return
         */
//        public Variant getValue(String position, Dispatch sheet) {
//            Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get,
//                    new Object[] { position }, new int[1]).toDispatch();
//            Variant value = Dispatch.get(cell, "Value");
//            return value;
//        }
        
        //�ر��ļ�
        Dispatch.call(workbook, "Save");
        Dispatch.call(workbook, "Close", new Variant(false));
        xl.invoke("Quit", new Variant[] {});
        xl = null;
        workbooks = null;
        ComThread.Release();
        System.gc();
    }
}
