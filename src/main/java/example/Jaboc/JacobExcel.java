package example.Jaboc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

//import org.freehep.graphicsio.emf.EMFHandleManager;
//import org.freehep.graphicsio.emf.EMFOutputStream;;

public class JacobExcel
{
	private static ActiveXComponent xl;
	private static Dispatch workbooks = null;
	private static Dispatch workbook = null;
	private static Dispatch sheet = null;
	private static String filename = null;
	private static boolean readonly = false;
	
	// 打开Excel文档
	private static void OpenExcel(String file, boolean f)
	{
		try
		{
			filename = file;
			xl = new ActiveXComponent("Excel.Application");
			xl.setProperty("Visible", new Variant(f));
			workbooks = xl.getProperty("Workbooks").toDispatch();
			workbook = Dispatch.invoke(
					workbooks,
					"Open",
					Dispatch.Method,
					new Object[] { filename, new Variant(false),
							new Variant(readonly) },// 是否以只读方式打开
					new int[1]).toDispatch();
//			workbook = Dispatch.call(workbooks, "Open", filename, false, readonly).toDispatch();
			sheet = Dispatch.get(workbook, "ActiveSheet").toDispatch();
//			sheet = Dispatch.call(workbook, "Sheets", "Sheet1").toDispatch();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// 关闭Excel文档
	private static void CloseExcel(boolean f)
	{
		try
		{
			Dispatch.call(workbook, "Save");
			Dispatch.call(workbook, "Close", new Variant(f));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			xl.invoke("Quit", new Variant[] {});
		}
	}
	
	// 写入值
	private static void SetValue(String position, String type, String value)
	{
		Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		Dispatch.put(cell, type, value);
	}
	
	// 读取值
	private static String GetValue(String position)
	{
		Dispatch cell = Dispatch.invoke(sheet, "Range", Dispatch.Get,
				new Object[] { position }, new int[1]).toDispatch();
		String value = Dispatch.get(cell, "Value").toString();
		return value;
	}
	
	public static void main(String[] args)
	{
		String file = "E:\\PROJECTS\\testJava\\test\\test.xlsx";
		OpenExcel(file, false);// false为不显示打开Excel
//		SetValue("A1", "Value", "2");
//		System.out.println(GetValue("A3"));
//		InsertOleObject("E:\\PROJECTS\\testJava\\test\\projectInfo.xml");
//		CopyPic();
		CloseExcel(false);
		System.out.println("GUN !");
	}
	
	private static void CopyPic()
	{
//	    bRet = False
//	    try:
//	        chart.CopyPicture()
//	        CF_ENHMETAFILE = 14
//	        win32clipboard.OpenClipboard(0)
//	        handleMetaData = win32clipboard.GetClipboardDataHandle(14)
//	        retHandle = GDI32.CopyEnhMetaFileA(handleMetaData, strFile)
//	        win32clipboard.EmptyClipboard()
//	        win32clipboard.CloseClipboard()
//	        if retHandle != 0:
//	            bRet = True
//	        GDI32.DeleteEnhMetaFile(retHandle)
//	    except Exception,detail:
//	        raise detail
//	    return bRet
		try
		{
			Dispatch chart1 = Dispatch.call(sheet, "ChartObjects", "Chart 1").toDispatch();
			Dispatch.call(chart1, "CopyPicture");
			Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
			if(t!=null){

				if(t.isDataFlavorSupported(DataFlavor.imageFlavor)){ //判断内容是否为文本类型stringFlavor

					Image img = (Image)t.getTransferData(DataFlavor.imageFlavor); //返回指定flavor类型的数据
//				            new File("E:\\PROJECTS\\testJava\\test\\test.jpg"));
					int width = img.getWidth(null);
			        int height = img.getHeight(null);
			        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			        Graphics g = bi.getGraphics();
			        g.drawImage(img,0,0,width,height,null);
			        g.dispose();
			        File f = new File("E:\\PROJECTS\\testJava\\test\\test.emf");
			        ImageIO.write(bi, "emf", f);

			        
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
	}

	private static void InsertOleObject(String filePath)
	{
		try
		{
			Dispatch oleObjects = Dispatch.get(sheet, "OLEObjects")
					.toDispatch();
			Dispatch.invoke(oleObjects, "Add", Dispatch.Method, 
					new Object[] {filePath},
					new int[1]);
//			Dispatch.call(oleObjects, "Add", filePath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
