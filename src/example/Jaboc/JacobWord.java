package example.Jaboc;

import javax.swing.text.AbstractDocument.Content;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobWord
{
	// 声明一个word对象
	private ActiveXComponent objWord;
	// 声明四个word组件
	private Dispatch custDocprops;
	private Dispatch builtInDocProps;
	private Dispatch document;
	private Dispatch wordObject;
	private Dispatch wordContent;
	
	public JacobWord()
	{
	}
	
	/** */
	/**
	 * 打开word文挡
	 */
	public void open(String filename)
	{
		// 创建一个word对象
		objWord = new ActiveXComponent("Word.Application");
		// 为wordobject组件附值
		wordObject = (Dispatch) (objWord.getObject()); // 改了这里
		// 生成一个只读方式的word文挡组件
		Dispatch.put(wordObject, "Visible", new Variant(false));
		// 获取文挡属性
		Dispatch documents = objWord.getProperty("Documents").toDispatch();
		// 打开激活文挡
		document = Dispatch.call(documents, "Open", filename).toDispatch();
		// 打开内容
		wordContent = Dispatch.get(document, "Content").toDispatch();
	}
	
	public void selectCustomDocumentProperitiesMode()
	{
		custDocprops = Dispatch.get(document, "CustomDocumentProperties")
		.toDispatch();
	}
	
	public void selectBuiltinPropertiesMode()
	{
		builtInDocProps = Dispatch.get(document, "BuiltInDocumentProperties")
				.toDispatch();
	}
	
	/** */
	/**
	 * 关闭文挡
	 */
	public void close()
	{
		Dispatch.call(document, "Close");
	}
	
	public String getCustomProperty(String cusPropName)
	{
		try
		{
			cusPropName = Dispatch.call((Dispatch) custDocprops, "Item",
					cusPropName).toString();
		}
		catch (ComException e)
		{
			cusPropName = null;
		}
		return cusPropName;
	}
	
	public String getBuiltInProperty(String builtInPropName)
	{
		try
		{
			builtInPropName = Dispatch.call((Dispatch) builtInDocProps,
					"Item", builtInPropName).toString();
		}
		catch (ComException e)
		{
			builtInPropName = null;
		}
		return builtInPropName;
	}
	
	public static void main(String[] args)
	{
		try
		{
			JacobWord jacTest = new JacobWord();
			jacTest.open("E:\\PROJECTS\\testJava\\test\\test.doc");
//			jacTest.selectCustomDocumentProperitiesMode();
//			jacTest.selectBuiltinPropertiesMode();
//			String custValue = jacTest
//					.getCustomProperty("Information Source");
//			String builtInValue = jacTest.getBuiltInProperty("Author");
//			jacTest.setContent();
			jacTest.update();
			jacTest.close();
//			System.out.println("Document Val One: " + custValue);
//			System.out.println("Document Author: " + builtInValue);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	/**
	 * 刷新目录
	 */
	private void update()
	{
//        iEnd = self._m_wordDoc.Content.End
//        self._m_wordDoc.Range(iEnd-1,iEnd).Select
//        self._m_wordApp.Selection.EndKey(constants.wdStory,constants.wdMove)
//        self._m_wordApp.Selection.WholeStory()
//        self._m_wordApp.Selection.Fields.Update()
//        self._m_wordApp.Selection.MoveLeft(constants.wdCharacter,1)
		try
		{
//			int iEnd = Dispatch.get(wordContent, "End").toInt();
//			Dispatch range = Dispatch.call(document, "Range", iEnd-1, iEnd).toDispatch();
//			Dispatch.call(range, "Select");
			Dispatch selection = Dispatch.get(wordObject, "Selection").toDispatch();
			Dispatch.call(selection, "WholeStory");
			Dispatch fields = Dispatch.get(selection, "Fields").toDispatch();
			Dispatch.call(fields, "Update");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void setContent()
	{
		Dispatch.call(wordContent, "InsertAfter", "现在开始尝试插入一个段落");//插入一个段落
		
	}
}