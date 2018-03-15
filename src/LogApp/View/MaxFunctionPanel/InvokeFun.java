package LogApp.View.MaxFunctionPanel;

import LogApp.LogStatic;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public abstract class InvokeFun extends JPanel {
    public String name="";
    public LogStatic.FunctionPanel functionPanel = null;
    public abstract void Invoke(String methodName,Object[] params) throws  IllegalAccessException, InvocationTargetException;
    public abstract JPanel getShowPanel();
    public abstract boolean isNeedShow(String methodName);
    public abstract boolean isNeedHide(String methodName);

}
