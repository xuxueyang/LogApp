package LogApp.View.MaxFunctionPanel;

import LogApp.LogStatic;
import LogApp.Model.Tree.NoteTreeBase;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 显示两个面板，一个选择树，一个是编辑想要的内容
 * 最上面是添加的一排按钮（选择跳出创建树的提示输入框），还有一个下拉列表用于选择树（默认为空）
 */
public class noteTree extends InvokeFun implements TreeSelectionListener {
    private HashMap<String,NoteTreeBase> map = new HashMap<>();
    private ArrayList<String> jComboBox_list_UUID = new ArrayList<>();
    private String currentTreeUUID = "";
    private JTree jTree = null;
    private JScrollPane jScrollPane = null;
    private JEditorPane jEditorPane = null;
    DefaultMutableTreeNode top = null; // @jve:decl-index=0:

    public  noteTree(){
        functionPanel = LogStatic.FunctionPanel.noteTree;
//        name
        GridBagLayout gridLayout = new GridBagLayout();
        this.setLayout(gridLayout);
        ArrayList<String> treeName = new ArrayList<>();
        for(String key:map.keySet()){
            NoteTreeBase noteTreeBase = map.get(key);
            if(noteTreeBase!=null&&noteTreeBase.isTop()){
                String tmp = noteTreeBase.getTitle();
                treeName.add(tmp);
                jComboBox_list_UUID.add(noteTreeBase.getUUID());
            }
        }
        JComboBox jComboBox = new JComboBox(treeName.toArray());

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox1 = (JComboBox)e.getSource();
                int index = jComboBox1.getSelectedIndex();
                if(index>-1){
                    getjScrollPane().setViewportView(changeTree(jComboBox_list_UUID.get(index)));
                }
            }
        });
        this.add(jComboBox);
        this.add(getjScrollPane());
        this.add(getJEditorPane());
        //TODO 增加一排按钮，用于点击和删除树
        JButton addButton = new JButton("创建节点");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object value = JOptionPane.showInputDialog(null,"请输入节点名字：\n","标题",JOptionPane.PLAIN_MESSAGE,null,null,"在这输入");
                //TODO 为选择的节点添加一个子节点，刷新
                jTree.repaint();
            }
        });
        //DefaultMutableTreeNode node为上次选中的
        //逻辑删除
        JButton delButton = new JButton("删除节点");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "确定要删除树节点?", "标题",JOptionPane.YES_NO_OPTION);//i=0/1
                //TODO 删除选中的那个节点
                
            }
        });
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weighty=0;
        gridBagConstraints.weightx=0;
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridLayout.addLayoutComponent(addButton,gridBagConstraints);
        gridBagConstraints.gridy=1;
        gridLayout.addLayoutComponent(addButton,gridBagConstraints);
        gridBagConstraints.gridy=2;
        gridLayout.addLayoutComponent(jComboBox,gridBagConstraints);

        gridBagConstraints.gridy=1;
        gridBagConstraints.weightx=1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridLayout.addLayoutComponent(getjScrollPane(),gridBagConstraints);
        gridBagConstraints.gridx=1;
        gridLayout.addLayoutComponent(getJEditorPane(),gridBagConstraints);
    }
    @Override
    public void Invoke(String methodName, Object[] params) throws  IllegalAccessException, InvocationTargetException {
        Class ownerClass = this.getClass();
        Class[] argsClass = new Class[params.length];
        for (int i = 0, j = params.length; i < j; i++) {
            argsClass[i] = params[i].getClass();
        }
        try {
            Method method = ownerClass.getMethod(methodName,argsClass);
            method.invoke(this, params);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JPanel getShowPanel() {
        return this;
    }
    public void Show(){
        this.setVisible(true);
    }
    @Override
    public boolean isNeedShow(String methodName) {
        if(methodName==null||"".equals(methodName)){
            return false;
        }
        switch (methodName){
            case "Show":
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean isNeedHide(String methodName) {
        if(methodName==null||"".equals(methodName)){
            return false;
        }
        switch (methodName){
            default:
                return false;
        }
    }

    public JTree changeTree(String UUID){
        //根据UUID改变树的结构
        for(String key:this.map.keySet()){
            NoteTreeBase noteTreeBase = map.get(key);
//            if(noteTreeBase.isTop()){
                if(noteTreeBase.getUUID().equals(UUID)){
                    this.currentTreeUUID = UUID;
                    jTree = getJTree(noteTreeBase);
                    return jTree;
                }
//            }
        }
        return new JTree();
    }
    public JTree addJTree(){
        return new JTree();
    }
    private JTree getJTree(NoteTreeBase noteTreeBase) {
        if(currentTreeUUID.equals(noteTreeBase.getUUID())&&jTree!=null)
            return jTree;
        else {
            jTree = new JTree();
            top = new DefaultMutableTreeNode(noteTreeBase.getTitle());

        }

        if (jTree == null) {
            top = new DefaultMutableTreeNode("黄山");
            createNodes(top,noteTreeBase.getNotes());
            jTree = new JTree(top);
            jTree.getSelectionModel().setSelectionMode(
                    TreeSelectionModel.SINGLE_TREE_SELECTION);
            jTree.addTreeSelectionListener(this);
        }
        return jTree;
    }
    private void createNodes(DefaultMutableTreeNode top,ArrayList<NoteTreeBase> list) {
        if(list==null||list.size()==0)
            return;
        for(NoteTreeBase noteTreeBase:list){
            DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(noteTreeBase.getTitle());
            createNodes(defaultMutableTreeNode,noteTreeBase.getNotes());
            top.add(defaultMutableTreeNode);
        }
    }
    public JScrollPane getjScrollPane() {
        if(jScrollPane==null){
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(changeTree(currentTreeUUID));
        }
        return jScrollPane;
    }
    private JEditorPane getJEditorPane() {
        if (jEditorPane == null) {
            jEditorPane = new JEditorPane();
        }
        return jEditorPane;
    }
    private DefaultMutableTreeNode node;
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        node = (DefaultMutableTreeNode) jTree
                .getLastSelectedPathComponent();

        if (node == null)
            // Nothing is selected.
            return;
        if(node.isRoot()){
            if(map.containsKey(currentTreeUUID)){
                jEditorPane.setText(map.get(currentTreeUUID).getMessage());
            }
        }
    }
}
