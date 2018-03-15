package example;

import com.trolltech.demos.imageviewer.MainWindow;

import com.trolltech.examples.QtJambiExample;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QIcon;

@QtJambiExample(
        name = "Image Viewer"
)
public class ImageViewer extends MainWindow {
    public ImageViewer() {
        this.setWindowIcon(new QIcon("classpath:com/trolltech/images/qt-logo.png"));
    }

    public static void main(String[] args) {
        QApplication.initialize(args);
        ImageViewer viewer = new ImageViewer();
        viewer.show();
        QApplication.execStatic();
        QApplication.shutdown();
    }
}

