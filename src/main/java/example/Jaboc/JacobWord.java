package example.Jaboc;

import javax.swing.text.AbstractDocument.Content;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComException;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JacobWord
{
	// ����һ��word����
	private ActiveXComponent objWord;
	// �����ĸ�word���
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
	 * ��word�ĵ�
	 */
	public void open(String filename)
	{
		// ����һ��word����
		objWord = new ActiveXComponent("Word.Application");
		// Ϊwordobject�����ֵ
		wordObject = (Dispatch) (objWord.getObject()); // ��������
		// ����һ��ֻ����ʽ��word�ĵ����
		Dispatch.put(wordObject, "Visible", new Variant(false));
		// ��ȡ�ĵ�����
		Dispatch documents = objWord.getProperty("Documents").toDispatch();
		// �򿪼����ĵ�
		document = Dispatch.call(documents, "Open", filename).toDispatch();
		// ������
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
	 * �ر��ĵ�
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
	 * ˢ��Ŀ¼
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
		Dispatch.call(wordContent, "InsertAfter", "���ڿ�ʼ���Բ���һ������");//����һ������
		
	}
}