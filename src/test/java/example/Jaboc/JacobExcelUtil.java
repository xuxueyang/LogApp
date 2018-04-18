package example.Jaboc;//JacobExcelUtil
//Java����
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.List;  
  
import com.jacob.activeX.ActiveXComponent;  
import com.jacob.com.ComThread;  
import com.jacob.com.Dispatch;  
import com.jacob.com.Variant;  
  
/** 
 * Jacob excel Util 
 * 
 */  
public class JacobExcelUtil {  
  
    private String file;  
    private boolean readonly = false;  
  
    private static String[] ABC = { "A", "B", "C", "D", "E", "F", "G", "H",  
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",  
            "V", "W", "X", "Y", "Z" };  
  
    public JacobExcelUtil(String file) throws FileNotFoundException {  
        this.file = file;  
        File f = new File(file);  
        if(!f.exists()) {  
            throw new FileNotFoundException();  
        }  
    }  
  
    /** 
     * д������ 
     * @param cells 
     */  
    public void putData(List<ExcelCell> cells) throws Exception {  
        if(cells == null || cells.size() == 0 ) {  
            return;  
        }  
          
        ActiveXComponent excel = null;  
        Dispatch workbooks = null;  
        Dispatch workbook = null;  
        Dispatch sheet = null;  
        String filename = null;  
  
        // ��ʼ��  
        ComThread.InitSTA();  
          
        // open file  
        try {  
            filename = file;  
            excel = new ActiveXComponent("Excel.Application");  
            excel.setProperty("Visible", new Variant(false));  
            workbooks = excel.getProperty("Workbooks").toDispatch();  
            workbook = Dispatch.invoke(  
                    workbooks,  
                    "Open",  
                    Dispatch.Method,  
                    new Object[] { filename, new Variant(false),  
                            new Variant(readonly) },    // �Ƿ���ֻ����ʽ��  
                    new int[1]).toDispatch();  
      
            // put data  
            sheet = Dispatch.get(workbook, "ActiveSheet").toDispatch();  
            String position = null;  
            int row = 0;  
            int col = 0;  
            int max = 26*26-1;  
            for (ExcelCell c : cells) {  
                row = c.getRow();  
                col = c.getCol();  
                if (row < 0 || col < 0 || col > max || c.getValue() == null  
                        || c.getValue().trim().equals("")) {  
                    continue;  
                }  
                position = translateLocation(c.getRow(), c.getCol());  
                setValue(sheet, position, c.getValue());  
            }  
              
        } finally {  
            // close file  
            try {  
                Dispatch.call(workbook, "Save");  
                Dispatch.call(workbook, "Close", new Variant(false));  
            } finally {  
                excel.invoke("Quit", new Variant[] {});  
                ComThread.Release();  
            }  
        }  
    }  
  
    /** 
     * ת����Ԫ��λ�� 
     * ���֧��26*26�� 
     * @param i 
     * @param j 
     * @return 
     */  
    public String translateLocation(int i, int j) {  
        String loc = "";  
        if (i <= 26) {  
            loc = ABC[i-1] + (j);  
        } else {  
            loc = ABC[i/26-1] + ABC[i%26-1] + (j);  
        }  
  
        return loc;  
    }  
  
    /** 
     * д��ֵ 
     *  
     * @param sheet 
     * @param position 
     * @param value 
     */  
    protected void setValue(Dispatch sheet, String position, String value) {  
        Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get,  
                new Object[] { position }, new int[1]).toDispatch();  
        Dispatch.put(cell, "Value", value);  
    }  
  
    /** 
     * ��ȡֵ 
     *  
     * @param sheet 
     * @param position 
     * @return 
     */  
    protected String GetValue(Dispatch sheet, String position) {  
        Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get,  
                new Object[] { position }, new int[1]).toDispatch();  
        String value = Dispatch.get(cell, "Value").toString();  
        return value;  
    }  
      
    public static void main(String[] args) {  
          
    }  
  
}  
 
