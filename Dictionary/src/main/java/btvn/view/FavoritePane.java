package btvn.view;

import btvn.controller.ButtonListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;


public class FavoritePane extends JPanel {
    public JComboBox cbbLanguage,cbbSort;
    public JButton btnBack;
    public JTable table;
    public DefaultTableModel modeltable;
    private ActionListener btnListener;

    public FavoritePane(DictionaryView view) {
        this.cbbLanguage = new JComboBox(new String[]{"Anh-Việt","Việt-Anh"});
        this.cbbSort = new JComboBox(new String[]{"A-Z","Z-A"});
        this.btnBack = new JButton("Trở về");
        this.modeltable = new DefaultTableModel(new Object[][]{{"Data"}}, new String[]{"Từ vựng"}){
            Class[] types = new Class[]{String.class};
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        this.table = new JTable(this.modeltable);
        
        this.btnListener = new ButtonListener(view);
        this.btnBack.addActionListener(btnListener);
        this.cbbLanguage.addActionListener(btnListener);
        this.cbbLanguage.setActionCommand("cbbLanguage");
        this.cbbSort.addActionListener(btnListener);
        this.cbbSort.setActionCommand("cbbSort");

        JPanel headerPane =new JPanel(new FlowLayout());
        headerPane.add(btnBack);
        headerPane.add(cbbLanguage);
        headerPane.add(cbbSort);
        
        Border titleBorder = BorderFactory.createTitledBorder("Danh sách từ yêu thích");
        JScrollPane mainPane =new JScrollPane(table );
        mainPane.setBorder(titleBorder);
        
        this.setLayout(new BorderLayout());
        this.add(headerPane,BorderLayout.NORTH);
        this.add(mainPane,BorderLayout.CENTER);
        
    }
    
    public ActionListener getButtonListener(){
        return this.btnListener;
    }
    
    
}
