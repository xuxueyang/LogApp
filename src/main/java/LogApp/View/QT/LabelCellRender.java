package LogApp.View.QT;

import javax.swing.*;
import java.awt.*;

public class  LabelCellRender  extends JLabel implements  ListCellRenderer  {
//    private  Color[] color;
    public  LabelCellRender()  {
        this.setOpaque(true);
//        this.color = color;
//        this.setBackground(color);
    }

    public Component getListCellRendererComponent(JList  list, Object  value, int  index, boolean  isSelected, boolean  cellHasFocus)  {
        if(value!=null)
        {
            setText("±ê¼Ç");
//            setBackground(isSelected  ?  Color.darkGray:  (Color)value);
            setBackground((Color)value);
//            setForeground(isSelected  ?  (Color)value :  Color.black);
//            System.out.println(this);
//            setBackground(color);
        }
        return  this;
    }
}