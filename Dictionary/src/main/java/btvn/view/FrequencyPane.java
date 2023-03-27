package btvn.view;

import btvn.controller.ButtonListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
public class FrequencyPane extends JPanel {
    public JComboBox cbbLanguage;
    public JButton btnBack;
    public JLabel lbBegin,lbEnd;
    public JTextField tfBegin, tfEnd;
    public JButton btnSearch;
    public JTable table;
    public DefaultTableModel modeltable;

    private ActionListener btnListener;

    public FrequencyPane(DictionaryView view) {
        this.cbbLanguage = new JComboBox(new String[]{"Anh-Việt","Việt-Anh"});
        this.btnBack = new JButton("Trở về");
        
        this.modeltable = new DefaultTableModel(new Object[][]{{}}, new String[]{"Từ vựng","Số lần"}){
            Class[] types = new Class[]{String.class,String.class};
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        this.table = new JTable(this.modeltable);
        this.lbBegin = new JLabel("Từ ngày");
        this.lbEnd = new JLabel("Đến ngày");
        this.tfBegin = new JTextField("",8);
        this.tfEnd = new JTextField("",8);
        Dimension sizeTextField = new Dimension(60,30);
        this.tfBegin.setPreferredSize(sizeTextField);
        this.tfEnd.setPreferredSize(sizeTextField);
        this.btnSearch = new JButton("Tìm kiếm");
        
        this.btnListener = new ButtonListener(view);
        this.btnBack.addActionListener(btnListener);
        this.btnSearch.addActionListener(btnListener);


        JPanel headerPane =new JPanel(new FlowLayout());
        headerPane.add(btnBack);
        headerPane.add(cbbLanguage);
        headerPane.add(lbBegin);
        headerPane.add(tfBegin);
        headerPane.add(lbEnd);
        headerPane.add(tfEnd);
        headerPane.add(btnSearch);
        
        Border titleBorder = BorderFactory.createTitledBorder("Bảng tần suất");
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
