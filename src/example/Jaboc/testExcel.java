import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;


public class testExcel
{
    private static ActiveXComponent xl = null; //Excel对象(防止打开多个)
    private static Dispatch workbooks = null;  //工作簿对象
    private static Dispatch workbook = null; //具体工作簿
    private Dispatch sheets = null;// 获得sheets集合对象
    private Dispatch currentSheet = null;// 当前sheet
    
    public static void main(String[] a)
    {
        //打开文件
        String filepath = "f:\\s.xlsx";
        xl = new ActiveXComponent("Excel.Application"); //Excel对象
        xl.setProperty("Visible", new Variant(false));//设置是否显示打开excel
        if(workbooks==null)
            workbooks = xl.getProperty("Workbooks").toDispatch(); //工作簿对象
        workbook = Dispatch.invoke(  //打开具体工作簿
                workbooks,
                "Open",
                Dispatch.Method,
                new Object[] { filepath, new Variant(false),
                        new Variant(false) },// 是否以只读方式打开
                        new int[1]).toDispatch();
        
        //添加新的工作表
        Dispatch.get(Dispatch.get(workbook, "sheets").toDispatch(), "add").toDispatch();
        //得到当前的sheet
        Dispatch.get(workbook, "ActiveSheet").toDispatch();//getCurrentSheet()
        //修改当前的表名
        String newSheetName = "zhaocuo";
        Dispatch.put(Dispatch.get(workbook, "ActiveSheet").toDispatch(), "name", newSheetName);
        //得到当前工作表的名称
        Dispatch.get(Dispatch.get(workbook, "ActiveSheet").toDispatch(), "name").toString();
        //得到工作簿的名称
        Dispatch.get(workbook, "name").toString();
        //得到sheets的集合对象
        Dispatch.get(workbook, "sheets").toDispatch();//getSheets()
        //通过工作表名字得到工作表
        String sheetName = "";
        Dispatch.invoke(Dispatch.get(workbook, "sheets").toDispatch(), "Item", Dispatch.Get, new Object[]{sheetName}, new int[1]).toDispatch();
        //通过工作表索引得到工作表(第一个工作簿index为1)
        Dispatch.invoke(Dispatch.get(workbook, "sheets").toDispatch(), "Item", Dispatch.Get, new Object[]{1}, new int[1]).toDispatch();
        //得到sheet的总数
        Dispatch.get(Dispatch.get(workbook, "sheets").toDispatch(), "count").toString();
        /**
         *  单元格写入值
         * @param sheet  被操作的sheet
         * @param position 单元格位置，如：C1
         * @param type 值的属性 如：value
         * @param value
         */
//        public void setValue(Dispatch sheet, String position, String type, Object value) {
//
//            Dispatch cell = Dispatch.invoke(sheet, "Range",
//                    Dispatch.Get, new Object[] { position }, new int[1])
//                    .toDispatch();
//            Dispatch.put(cell, type, value);
        
        /**
         * 单元格读取值
         * @param position 单元格位置，如： C1
         * @param sheet 
         * @return
         */
//        public Variant getValue(String position, Dispatch sheet) {
//            Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get,
//                    new Object[] { position }, new int[1]).toDispatch();
//            Variant value = Dispatch.get(cell, "Value");
//            return value;
//        }
        
        //关闭文件
        Dispatch.call(workbook, "Save");
        Dispatch.call(workbook, "Close", new Variant(false));
        xl.invoke("Quit", new Variant[] {});
        xl = null;
        workbooks = null;
        ComThread.Release();
        System.gc();
    }
}
