package example.e_20180315;

import com.trolltech.qt.gui.*;

public class  classTestDialog extends QDialog
        {
        QPushButton btnShow;
        QDialog dialogTwo;
        QHBoxLayout hBLayoutMain;
            classTestDialog()
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
        classTestDialog T=new classTestDialog();
        T.show();
        QApplication.execStatic();
        QApplication.shutdown();
        }

        }