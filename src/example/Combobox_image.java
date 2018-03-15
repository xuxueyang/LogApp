package example;

import javax.swing.*;
import java.awt.*;

public class Combobox_image extends JFrame {
    JComboBox comb = new JComboBox();
    public Combobox_image()

    {

        LabelCellRender  label  =  new  LabelCellRender();
        label.setIcon(new  ImageIcon("image/3.jpg"));
        comb.setRenderer(label);
        for(int i=0;i <5;i++){





            comb.addItem("label"+i);
        }
        JPanel p=new JPanel();
        p.add(comb);
        add(p);
        setSize(300, 200);
        setLocation(300,300);
        setVisible(true);
    }

    public static void main(String[] args) {
        Combobox_image t=new Combobox_image();

    }

}
class  LabelCellRender  extends  JLabel  implements  ListCellRenderer  {

    public  LabelCellRender()  {
        this.setOpaque(true);
    }

    public  Component  getListCellRendererComponent(JList  list,  Object  value,  int  index,  boolean  isSelected,  boolean  cellHasFocus)  {
        if(value!=null)
        {
            setText(value.toString());
            setBackground(isSelected  ?  Color.darkGray:  Color.white);
//            setForeground(isSelected  ?  Color.white  :  Color.black);
        }
        return  this;
    }
}
