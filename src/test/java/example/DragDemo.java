package example;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * �ļ���קѡ��ʾ��
 *
 * @author jianggujin
 *
 */
@SuppressWarnings("serial")
public class DragDemo extends JFrame
{
    public DragDemo()
    {
        super("DragDemo");

        final JTextArea area = new JTextArea();
        area.setLineWrap(true);
        add(new JScrollPane(area));

        new DropTarget(area, DnDConstants.ACTION_COPY_OR_MOVE,
                new DropTargetAdapter()
                {
                    @Override
                    public void drop(DropTargetDropEvent dtde)
                    {
                        try
                        {
                            // ���������ļ���ʽ��֧��
                            if (dtde
                                    .isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                            {
                                // ������ק��������
                                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                                @SuppressWarnings("unchecked")
                                List<File> list = (List<File>) (dtde.getTransferable()
                                        .getTransferData(DataFlavor.javaFileListFlavor));
                                area.setText("");
                                for (File file : list)
                                {
                                    area.append(file.getAbsolutePath());
                                    area.append("\r\n");
                                }
                                // ָʾ��ק���������
                                dtde.dropComplete(true);
                            }
                            else
                            {
                                // �ܾ���ק��������
                                dtde.rejectDrop();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new DragDemo();
    }
}