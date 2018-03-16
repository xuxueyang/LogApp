package example.e_20180315;
import com.trolltech.qt.gui.*;
public class QTTest01 extends QDialog{
    QPushButton btnShow;
    QDialog dialogTwo;
    QHBoxLayout hBLayoutMain;
    QTTest01()
    {
        btnShow=new QPushButton(tr("œ‘ æ"));
        dialogTwo=new QDialog();
        hBLayoutMain=new QHBoxLayout();

        hBLayoutMain.addWidget(btnShow);
        this.setLayout(hBLayoutMain);

        btnShow.clicked.connect(dialogTwo,"show()");

    }
    public static void main(String[] args)
    {
        QApplication.initialize(args);
        QTTest01 T=new QTTest01();
        T.show();
        QApplication.execStatic();
        QApplication.shutdown();
//        QApplication.initialize(args);
//        QPushButton hello = new QPushButton("Hello World!");
//        hello.show();
//        QApplication.execStatic();
    }

}
